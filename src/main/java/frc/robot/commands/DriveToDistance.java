/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveToDistance extends Command {

  private double errorMargin = 0;
  private double leftTarget = 0;
  private double rightTarget = 0;

  public DriveToDistance(double errorMargin,
    double leftTarget, double rightTarget) {
    requires(Robot.driveTrain);
    this.errorMargin = errorMargin;
    this.leftTarget = leftTarget;
    this.rightTarget = rightTarget;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.driveTrain.resetEncoders();
    Robot.driveTrain.setPIDValues(RobotMap.Values.P, RobotMap.Values.I, RobotMap.Values.D);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.driveTrain.setPosition(error(Robot.driveTrain.leftEncoderTicks(), leftTarget),
      error(Robot.driveTrain.rightEncoderTicks(), rightTarget)); // May take in ticks and not error. Need to test
  }

  public double error(double pos, double target) {
    return target - pos;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean onLeftTarget = ((error(Robot.driveTrain.leftEncoderTicks(), leftTarget) < leftTarget + errorMargin)
          && (error(Robot.driveTrain.leftEncoderTicks(), leftTarget) > leftTarget - errorMargin));
    boolean onRightTarget = ((error(Robot.driveTrain.rightEncoderTicks(), rightTarget) < rightTarget + errorMargin)
          && (error(Robot.driveTrain.rightEncoderTicks(), rightTarget) > rightTarget - errorMargin));
    return onLeftTarget && onRightTarget;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("DriveTrain reached target");
    Robot.driveTrain.setVolts(0, 0);
    Robot.driveTrain.setBrake();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() { end(); }
}
