/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Scheduler;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorUppity extends Command {
  public ElevatorUppity() {
    requires(Robot.elevator);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.elevator.SetPower(.3);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.elevator.getTopLimitSwitch();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("finished elevatorup");
    Robot.elevator.SetPosition(Robot.elevator.GetPosition());
    Scheduler.getInstance().add(new LockElevator());
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("up interupted");
    end();
  }
}
