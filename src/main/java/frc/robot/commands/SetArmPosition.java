/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SetArmPosition extends Command {

  private double setpoint;
  private double tolerance;

  public SetArmPosition(double setpoint, double tolerance) {

    this.setpoint = setpoint;
    this.tolerance = tolerance;

    requires(Robot.arm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Initted armToPosition");
    //Robot.arm.updatePID();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.arm.SetPostion(setpoint);
    //Robot.arm.UpdateF();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Robot.arm.readEncoder() > setpoint - tolerance) && (Robot.arm.readEncoder() < setpoint + tolerance);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.arm.engageBrake();
    Robot.arm.setPower(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("ArmToPosition interrupted");
    end();
  }
}
