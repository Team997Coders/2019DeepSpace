/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.vision.CameraControlStateMachine;
import frc.robot.vision.SelectedTarget;

/**
 * The pièces de résistance<p>
 * Hunter, work your magic...<p>
 * This command will start when the driver pushes the
 * start driving button on the vision HUD.
 */
public class AutoDriveToTarget extends Command {
  private final DriveTrain driveTrain;
  private final CameraControlStateMachine cameraControlStateMachine;
  private boolean weMadeIt;

  public AutoDriveToTarget() {
    this(Robot.driveTrain, Robot.cameraControlStateMachine);
  }

  public AutoDriveToTarget(DriveTrain driveTrain, CameraControlStateMachine cameraControlStateMachine) {
    this.driveTrain = driveTrain;
    this.cameraControlStateMachine = cameraControlStateMachine;
    requires(driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    weMadeIt = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SelectedTarget selectedTarget = cameraControlStateMachine.getSelectedTarget();
    /*
      selectedTarget contains: 
      rangeInInches
      cameraAngleInDegrees - camermount pan angle relative to robot, -90 to 90, with 0 being center
      angleToTargetInDegrees - robot's angle to target, from target's POV, -90 to 90, with 0 being perpenticular to target
    */
    weMadeIt = true;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return cameraControlStateMachine.getState() != CameraControlStateMachine.State.DrivingToTarget || weMadeIt;
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
