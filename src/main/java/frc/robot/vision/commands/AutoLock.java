/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.vision.CameraControlStateMachine;

public class AutoLock extends Command {
  private final CameraControlStateMachine cameraControlStateMachine;
  private final Side side;

  /**
   * Auto-lock camera on to left or right most target
   * that are closest to the center of the FOV.
   * 
   * @param side  Go to the left or right side target. If only one
   *              target is in the FOV, it will go to that target. 
   */
  public AutoLock(Side side) {
    this(Robot.cameraControlStateMachine, side);
  }

  /**
   * Pass in all depdendencies for auto-lock so that we can easily
   * test as desired.
   */
  public AutoLock(CameraControlStateMachine cameraControlStateMachine, Side side) {
    this.cameraControlStateMachine = cameraControlStateMachine;
    this.side = side;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    switch(side) {
      case Right:
        cameraControlStateMachine.autoLockLeft();
        break;
      case Left:
        cameraControlStateMachine.autoLockRight();
        break;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.SlewingToTarget) {
      return false;
    } else {
      return true;
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

  public enum Side {
    Right, Left
  }
}
