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
import frc.robot.vision.TargetNotLockedException;

/**
 * The pièces de résistance<p>
 * Hunter, work your magic...<p>
 * This command will start when the driver pushes the
 * start driving button on the vision HUD.<p>
 * This maybe should be named AutoDriveToTargetUnderVisionControl...because
 * we will probably have a command group called AutoDriveToTarget that combines
 * this command with LineFollowing.<p>
 * I'll keep going...probably another called ScoreNow which moves manipulators
 * and auto drives.
 */
public class AutoDriveToTarget extends Command {
  private final DriveTrain driveTrain;
  private boolean weMadeIt;

  public AutoDriveToTarget() {
    this(Robot.driveTrain);
  }

  public AutoDriveToTarget(DriveTrain driveTrain) {
    this.driveTrain = driveTrain;
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
    try {
      SelectedTarget selectedTarget = Robot.cameraControlStateMachine.getSelectedTarget();
      /*
        selectedTarget contains: 
        rangeInInches
        cameraAngleInDegrees - camermount pan angle relative to robot, -90 to 90, with 0 being center
        angleToTargetInDegrees - robot's angle to target, from target's POV, -90 to 90, with 0 being perpenticular to target
      */
    } catch (TargetNotLockedException e) {
      weMadeIt = false;
    }
    // Set when finished...commented out to test driving cancelling from HUD
    // weMadeIt = true;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (weMadeIt) {
      // Flip camera control back to identifying targets
      Robot.cameraControlStateMachine.identifyTargets();
      return true;
    } else if (Robot.cameraControlStateMachine.getState() != CameraControlStateMachine.State.DrivingToTarget) {
      // Something happended and we stopped auto driving (user probably cancelled)
      return true;
    } else {
      return false;
    }
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
