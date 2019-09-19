/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 *
 */
public class PDriveToDistance extends Command {

  private double distSetpoint;
  private double minError = 3;
  public Timer timer = new Timer();
  private double lastTime = 0;
  private double lastVoltage = 0;
  private double deltaT = 0;
  private double speed = 0.5;
  private double initYaw = -999;
  private double Ktheta = 0.02;
  private double integral, previous_error = 0;

  public PDriveToDistance(double _speed, double _dist) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);
    // requires(Robot.collector);
    distSetpoint = _dist;
    speed = _speed;
  }

  public PDriveToDistance(double _dist) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);
    // requires(Robot.collector);
    distSetpoint = _dist;
    speed = 0.5;
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    lastVoltage = 0;
    Robot.driveTrain.resetEncoders();
    Robot.driveTrain.setBrake();
    initYaw = Robot.driveTrain.getGyroAngle();
    this.previous_error = this.piderror();
    timer.reset();
    timer.start();
    lastTime = 0;
  }

  // current algorithm assumes that we are starting
  // from a stop
  private double linearAccel(double input) {
    double Klin = 0.8;
    double deltaT = timer.get() - lastTime;
    lastTime = timer.get();

    double Volts = lastVoltage + Klin * (deltaT);
    if (Volts > input) {
      Volts = input;
    }
    lastVoltage = Volts;
    return Volts;
  }

  public double pFactor() {
    // Calculate full PID
    // pfactor = (P × error) + (I × ∑error) + (D × δerrorδt)
    double error = this.piderror();
    // Integral is increased by the error*time (which is .02 seconds using normal
    // IterativeRobot)
    this.integral += (error * .02);
    // Derivative is change in error over time
    double derivative = (error - this.previous_error) / .02;
    this.previous_error = error;
    return (RobotMap.Values.driveDistanceP * error) + (RobotMap.Values.driveDistanceI * this.integral)
        + (RobotMap.Values.driveDistanceD * derivative);
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    // compute the pid P value
    double pfactor = speed * Robot.oi.clamp(-1, 1, this.pFactor());
    double pfactor2 = linearAccel(pfactor);
    double deltaTheta = Robot.driveTrain.getGyroAngle() - initYaw;
    deltaT = timer.get() - lastTime;
    lastTime = timer.get();

    // calculate yaw correction
    double yawcorrect = deltaTheta * Ktheta;

    // set the output voltage
    Robot.driveTrain.setVolts(pfactor2 - yawcorrect, pfactor2 + yawcorrect);
    // Robot.driveTrain.SetVoltages(-pfactor, -pfactor); //without yaw correction,
    // accel

    // Debug information to be placed on the smart dashboard.
    SmartDashboard.putNumber("PDTD/Setpoint", distSetpoint);
    SmartDashboard.putNumber("PDTD/Encoder Distance", this.encoderDistance());
    SmartDashboard.putNumber("PDTD/Distance Error", piderror());
    SmartDashboard.putNumber("PDTD/K-P factor", pfactor);
    SmartDashboard.putNumber("PDTD/K-P factor Accel", pfactor2);
    SmartDashboard.putNumber("PDTD/deltaT", deltaT);
    SmartDashboard.putNumber("PDTD/Theta Correction", yawcorrect);
    SmartDashboard.putBoolean("PDTD/On Target", onTarget());
    SmartDashboard.putNumber("PDTD/NavX Heading", Robot.driveTrain.getGyroAngle());
    SmartDashboard.putNumber("PDTD/Init Yaw", initYaw);
  }

  private double piderror() {
    // shouldn't we average this out between both of the encoders?
    return distSetpoint - this.encoderDistance();
  }

  private double encoderDistance() {
    return (Robot.driveTrain.leftEncoderTicks() + Robot.driveTrain.rightEncoderTicks()) / 2;
  }

  private boolean onTarget() {
    return piderror() < minError;
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    if (Robot.driveTrain.leftEncoderVelocity() <= 0 && Robot.driveTrain.rightEncoderVelocity() <= 0 && onTarget()) {
      return onTarget();
    } 
    return false;
  }

  // Called once after isFinished returns true
  protected void end() {
    timer.stop();
    Robot.driveTrain.setVolts(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
    end();
  }
}