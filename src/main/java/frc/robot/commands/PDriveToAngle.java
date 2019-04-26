/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Utils;

/**
 *
 */
public class PDriveToAngle extends Command {
  private double angSetpoint;
  private double minError = 2;
  private double initYaw = -999;
  private double integral = 0;
  private double previousError = 0;

  public PDriveToAngle(double _ang) {
    requires(Robot.driveTrain);
    angSetpoint = _ang;
  }

  protected void initialize() {
    initYaw = Robot.driveTrain.getGyroAngle();
    Robot.driveTrain.setBrake();
  }

  public double yawCorrect() {
    // Calculate full PID
    // pfactor = (P × error) + (I × ∑error) + (D × δerrorδt)
    double error = this.piderror();
    // Integral is increased by the error*time (which is .02 seconds using normal
    // IterativeRobot)
    this.integral += (error * .02);
    // Derivative is change in error over time
    double derivative = (error - this.previousError) / .02;
    this.previousError = error;
    return (RobotMap.Values.driveAngleP * error) + (RobotMap.Values.driveAngleI * this.integral)
        + (RobotMap.Values.driveAngleD * derivative);
  }

  protected void execute() {
    // calculate yaw correction
    double yawcorrect = this.yawCorrect();
    Robot.driveTrain.setVolts(Utils.clamp(-1, 1, yawcorrect), Utils.clamp(-1, 1, -yawcorrect));
    // Debug information to be placed on the smart dashboard.
    SmartDashboard.putNumber("PDTA/Angle Error", piderror());
    SmartDashboard.putNumber("PDTA/Theta Angle Correction", yawcorrect);
    SmartDashboard.putBoolean("PDTA/On Angle Target", onTarget());
  }

  private double piderror() {
    return initYaw + angSetpoint - Robot.driveTrain.getGyroAngle();
  }

  private boolean onTarget() {
    return Math.abs(piderror()) < minError;
  }

  protected boolean isFinished() {
    return onTarget();
  }

  protected void end() {
    Robot.driveTrain.stopVolts();
  }

  protected void interrupted() {
    end();
  }
}