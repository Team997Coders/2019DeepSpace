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
import frc.robot.subsystems.DriveTrain;

//TODO: Drive to distance should take inches, not ticks.

public class DriveToDistance extends Command {

  private double errorMargin = 0;
  private double leftTarget = 0;
  private double rightTarget = 0;
  private final DriveTrain driveTrain;

  /**
   * Default constructor to use from robot program. Passes
   * drivetrain from static robot program variable.
   */
  public DriveToDistance(double errorMargin,
      double leftTarget, 
      double rightTarget) {
    this(Robot.driveTrain, errorMargin, leftTarget, rightTarget);
  }

  /**
   * Constructor to use for testing. This takes a reference
   * to our drivetrain hardware so we can mock it.
   */
  public DriveToDistance(DriveTrain driveTrain, 
      double errorMargin,
      double leftTarget, 
      double rightTarget) {
    this.driveTrain = driveTrain;
    this.errorMargin = errorMargin;
    this.leftTarget = leftTarget;
    this.rightTarget = rightTarget;
    requires(driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    driveTrain.resetEncoders();
    driveTrain.setPIDValues(RobotMap.Values.driveToDistance_kP, RobotMap.Values.driveToDistance_kI, RobotMap.Values.driveToDistance_kD);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    driveTrain.setPosition(error(driveTrain.leftEncoderTicks(), leftTarget),
      error(driveTrain.rightEncoderTicks(), rightTarget)); // May take in ticks and not error. Need to test
  }

  public double error(double pos, double target) {
    return target - pos;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean onLeftTarget = (Math.abs(error(driveTrain.leftEncoderTicks(), leftTarget))) < errorMargin;
    boolean onRightTarget = (Math.abs(error(driveTrain.rightEncoderTicks(), rightTarget))) < errorMargin;
    return onLeftTarget && onRightTarget;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("DriveTrain reached target");
    driveTrain.setVolts(0, 0);
    driveTrain.setBrake();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() { end(); }
}
