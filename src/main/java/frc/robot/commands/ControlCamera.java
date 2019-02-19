/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.misc.MiniPID;
import frc.robot.subsystems.CameraMount;
import frc.robot.vision.CameraControlStateMachine;
import frc.robot.vision.SelectedTarget;

/**
 * This command interacts with the CameraControlStateMachine to
 * control CameraMount servo movements.
 */
public class ControlCamera extends Command {
  private final CameraMount cameraMount;
  private final CameraControlStateMachine cameraControlStateMachine;
  private final NetworkTable visionNetworkTable;
  private final MiniPID pidX;
  private final MiniPID pidY;
  private final double lockThresholdFactor;

  /**
   * Default constructor to be used as the default command
   * for the CameraMount subsystem.
   */
  public ControlCamera() {
    this(Robot.cameraMount, 
      Robot.cameraControlStateMachine, 
      Robot.visionNetworkTable, 
      new MiniPID(0.25, 0, 0), 
      new MiniPID(0.25, 0, 0),
      0.05);
  }

  /**
   * Accept dependencies to make this easily testable.
   * 
   * @param cameraMount                 The camera mount pan/tilt system to act upon.
   * @param cameraControlStateMachine   A state machine that manages current state of the camera control system.
   * @param visionNetworkTable          A network table containing state shared between robot and Pi CameraVision application.
   * @param pidX                        A mini PID implementation to control CameraMount automated panning.
   * @param pidY                        A mini PID implementation to control CameraMount automated tilting.
   * @param lockThresholdFactor         A percentage of error (n < 1) that we will consider target locked. Smaller the percentage
   *                                    the tighter the tolerance.
   */
  public ControlCamera(CameraMount cameraMount, 
      CameraControlStateMachine cameraControlStateMachine, 
      NetworkTable visionNetworkTable,
      MiniPID pidX,
      MiniPID pidY,
      double lockThresholdFactor) {
    this.cameraMount = cameraMount;
    this.cameraControlStateMachine = cameraControlStateMachine;
    this.visionNetworkTable = visionNetworkTable;
    this.pidX = pidX;
    this.pidY = pidY;
    this.lockThresholdFactor = lockThresholdFactor;
    requires(cameraMount);
    // Set up a listener to fire when state changes to SlewingToTarget so we can reset pids
    visionNetworkTable.addEntryListener("State", (table, key, entry, value, flags) -> {
        CameraControlStateMachine.State state = Enum.valueOf(CameraControlStateMachine.State.class, value.getString());
        if (state == CameraControlStateMachine.State.SlewingToTarget) {
          pidX.reset();
          pidY.reset();
        }
      }, 
      EntryListenerFlags.kNew | EntryListenerFlags.kUpdate
    );
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    cameraMount.setLightRingOutput(100);
    cameraMount.center();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Slew the camera under operator control
    if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.IdentifyingTargets || 
        cameraControlStateMachine.getState() == CameraControlStateMachine.State.LockFailed ||
        cameraControlStateMachine.getState() == CameraControlStateMachine.State.LockLost ||
        cameraControlStateMachine.getState() == CameraControlStateMachine.State.Slewing) {
      cameraMount.slew(cameraControlStateMachine.getPanRate(), cameraControlStateMachine.getTiltRate());
    // Center the camera under operator control
    } else if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.Centering) {
      cameraMount.center();
      cameraControlStateMachine.identifyTargets();
    // Slew the camera under automated control. Determine when locked.
    } else if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.SlewingToTarget) {
      // Get the selected target to process
      SelectedTarget selectedTarget = new SelectedTarget(visionNetworkTable);
      if (selectedTarget.active) {
        // Follow it
        followTarget(selectedTarget);
        // Check to see if we are locked on
        // We could do this in the vision project, since most other triggers are fired
        // there, but leaving it here makes it more easily tweakable, unless we wanted
        // to put the factor in networktables...
        if (selectedTarget.normalizedPointFromCenter.x >= (-1.0 * lockThresholdFactor) &&
            selectedTarget.normalizedPointFromCenter.x <= lockThresholdFactor &&
            selectedTarget.normalizedPointFromCenter.y >= (-1.0 * lockThresholdFactor) &&
            selectedTarget.normalizedPointFromCenter.y <= lockThresholdFactor) {
          cameraControlStateMachine.lockOn();
        }
      }
    // Keep the camera in the center of FOV under automated control.
    } else if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.TargetLocked || 
        cameraControlStateMachine.getState() == CameraControlStateMachine.State.DrivingToTarget) {
      // Get the selected target to process
      SelectedTarget selectedTarget = new SelectedTarget(visionNetworkTable);
      // Follow it
      if (selectedTarget.active) {
        followTarget(selectedTarget);
      }
    } 
  }

  /**
   * Tickle PIDs to figure out how much to slew to keep target locked.
   * 
   * @param selectedTarget  The selected target which contains how far off from center we are.
   */
  private void followTarget(SelectedTarget selectedTarget) {
    // slew based on PID values related to how close we are to slewpoint
    double panRate = pidX.getOutput(selectedTarget.normalizedPointFromCenter.x);
    double tiltRate = pidY.getOutput(selectedTarget.normalizedPointFromCenter.y) * -1.0;
    cameraMount.slew(panRate, tiltRate);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    cameraMount.setLightRingOutput(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
