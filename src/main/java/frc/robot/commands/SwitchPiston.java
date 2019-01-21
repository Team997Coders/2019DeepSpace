/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.subsystems.HatchManipulator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
  * Name this something more specific
  */
public class SwitchPiston extends Command {
  public SwitchPiston() {
    requires(Robot.hatchManipulator);
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
    if (Robot.hatchManipulator.hatchPistonState == true){
      Robot.hatchManipulator.retract();
    }
    else {
      Robot.hatchManipulator.extend();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false; // You want this true so it doesn't constantly open and close and have a f***ing seizure
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    // Call end();
  }
}
