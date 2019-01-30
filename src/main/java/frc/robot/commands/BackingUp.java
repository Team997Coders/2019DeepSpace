/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BackingUp extends Command {

  private long starts;
  private long extratimems = 500;
  private double straight = .35;

  public BackingUp() {
   requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Timer start");
    this.starts = System.currentTimeMillis();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    Robot.driveTrain.setVolts(-straight, -straight);
    SmartDashboard.putString("Are you backing up?", "Yes!");


  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if((starts + extratimems) > System.currentTimeMillis()){
      return false;
    } else{
      return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    SmartDashboard.putString("Are you backing up?", "No!");
    Robot.driveTrain.setVolts(0,0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
