/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.CameraMount;
import frc.robot.vision.CameraControlStateMachine;

public class ControlCamera extends Command {
  private final CameraMount cameraMount;
  private final CameraControlStateMachine cameraControlStateMachine;

  public ControlCamera() {
    this(Robot.cameraMount, Robot.cameraControlStateMachine);
  }

  public ControlCamera(CameraMount cameraMount, CameraControlStateMachine cameraControlStateMachine) {
    this.cameraMount = cameraMount;
    this.cameraControlStateMachine = cameraControlStateMachine;
    requires(cameraMount);
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
        cameraControlStateMachine.getState() == CameraControlStateMachine.State.Slewing) {
      cameraMount.slew(cameraControlStateMachine.getPanRate(), cameraControlStateMachine.getTiltRate());
    } else if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.SlewingToTarget || 
        cameraControlStateMachine.getState() == CameraControlStateMachine.State.TargetLocked ||
        cameraControlStateMachine.getState() == CameraControlStateMachine.State.TargetLocked) {
      // slew based on PID values related to how close we are to slewpoint
    }
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
