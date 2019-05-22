/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Scheduler;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Used for moving the elevator high enough to swap the arm's side if so required.
 */
public class ElevatorToArmHeight extends Command {
  private double tolerance;

  public ElevatorToArmHeight() {
    requires(Robot.elevator);
    //this.tolerance = tolerance;
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
    Robot.elevator.SetPosition(RobotMap.Values.armSwitchHeight + 2250);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //return (Math.abs(RobotMap.Values.armSwitchHeight - Robot.elevator.GetPosition()) < tolerance);
    return (Robot.elevator.GetPosition() >= RobotMap.Values.armSwitchHeight);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("finished arm to height");
    Robot.elevator.SetPower(0);
    Scheduler.getInstance().add(new LockElevator());
    Scheduler.getInstance().add(new SetArmPosition(RobotMap.Values.armFrontParallel, 10));
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
