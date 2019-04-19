/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SetElevatorHeight extends Command {

  private double setpoint, tolerance;

  public SetElevatorHeight(double setpoint, double tolerance) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.elevator);

    this.setpoint = setpoint;
    this.tolerance = tolerance;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("init-ed elevatorToHeight with setpoint " + setpoint);
    //SmartDashboard.putNumber("Elevator Setpoint", setpoint);
    //Robot.elevator.updatePID();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.elevator.SetPosition(setpoint);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //SmartDashboard.putNumber("Elevator Error", setpoint - Robot.elevator.GetPosition());
    return ((Robot.elevator.GetPosition() < setpoint + tolerance) && Robot.elevator.GetPosition() > setpoint - tolerance);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevator.SetPower(0);
    Scheduler.getInstance().add(new LockElevator());
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
