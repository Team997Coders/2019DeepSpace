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

public class ControlCamera extends Command {
  private final CameraMount cameraMount;
  private final CameraControlStateMachine cameraControlStateMachine;
  private final NetworkTable visionNetworkTable;
  private final MiniPID pidX;
  private final MiniPID pidY;
  private final double lockThresholdFactor;

  public ControlCamera() {
    this(Robot.cameraMount, 
      Robot.cameraControlStateMachine, 
      Robot.visionNetworkTable, 
      new MiniPID(0.20, 0, 0), 
      new MiniPID(0.20, 0, 0),
      0.05);
  }

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
    cameraMount.center();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.IdentifyingTargets || 
        cameraControlStateMachine.getState() == CameraControlStateMachine.State.LockFailed ||
        cameraControlStateMachine.getState() == CameraControlStateMachine.State.LockLost ||
        cameraControlStateMachine.getState() == CameraControlStateMachine.State.Slewing) {
      cameraMount.slew(cameraControlStateMachine.getPanRate(), cameraControlStateMachine.getTiltRate());
    } else if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.Centering) {
      cameraMount.center();
      cameraControlStateMachine.identifyTargets();
    } else if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.SlewingToTarget) {
      // Get the selected target to process
      SelectedTarget selectedTarget = new SelectedTarget(visionNetworkTable);
      if (selectedTarget.active) {
        // Follow it
        followTarget(selectedTarget);
        // Check to see if we are locked on
        if (selectedTarget.normalizedPointFromCenter.x >= (-1.0 * lockThresholdFactor) &&
            selectedTarget.normalizedPointFromCenter.x <= lockThresholdFactor &&
            selectedTarget.normalizedPointFromCenter.y >= (-1.0 * lockThresholdFactor) &&
            selectedTarget.normalizedPointFromCenter.y <= lockThresholdFactor) {
          cameraControlStateMachine.lockOn();
        }
      }
    } else if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.TargetLocked) {
      // Get the selected target to process
      SelectedTarget selectedTarget = new SelectedTarget(visionNetworkTable);
      // Follow it
      if (selectedTarget.active) {
        followTarget(selectedTarget);
      }
    }
  }

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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
