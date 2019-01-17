/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.LineFollowing;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FollowLine extends Command {
  public FollowLine() {
    requires(Robot.lineFollowing);
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    boolean[] Sensorsdata = Robot.lineFollowing.returnOutput();

    if(Sensorsdata[1] == true){
      SmartDashboard.putString("Do you see the line?", "Yes");
      SmartDashboard.putString("Centered?", "Yes! :) ");
      Robot.driveTrain.setVolts(.25, .25);
    }else if(Sensorsdata[0] == true){
      SmartDashboard.putString("Do you see the line?", "Yes");
      SmartDashboard.putString("Centered?", "No! :( ");
      Robot.driveTrain.setVolts(-.25, .25);
     }else if(Sensorsdata[2] == true){
      SmartDashboard.putString("Do you see the line?", "Yes");
      SmartDashboard.putString("Centered?", "No! :( ");
      Robot.driveTrain.setVolts(.25, -.25);
    }else {
      SmartDashboard.putString("Do you see the line?", "No");
      Robot.driveTrain.setVolts(.25, .25);
    }
  }



  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
