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

/**
 * Follow a line on the floor and stop when range is close
 * to target.
 */
public class FollowLine extends Command {

  public FollowLine() {
    requires(Robot.driveTrain);
    requires(Robot.lineFollowing);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {    
  }

  // Called repeatedly when this Command is scheduled to run
  // TODO: Define your gain parameters as constants
  // TODO: Use drivetrain convenience methods to turn right and left
  // instead of setVolts.
  @Override
  protected void execute() {
    
    SmartDashboard.putString("LineFollowing is Active!", "I think so?");
    System.out.println("Processing");

    if(Robot.lineFollowing.centerLineSeen()){
      SmartDashboard.putString("Do you see the line?", "Yes");
      SmartDashboard.putString("Centered?", "Yes! :) ");
      SmartDashboard.putString("Do you see two lines?", "No");

      Robot.driveTrain.setVolts(.25, .25);
    }else if(Robot.lineFollowing.rightLineSeen()){
      SmartDashboard.putString("Do you see the line?", "Yes");
      SmartDashboard.putString("Centered?", "No! :( ");
      SmartDashboard.putString("Do you see two lines?", "No");

      Robot.driveTrain.setVolts(.25, .15);
    }else if(Robot.lineFollowing.leftLineSeen()){
      SmartDashboard.putString("Do you see the line?", "Yes");
      SmartDashboard.putString("Centered?", "No! :( ");
      SmartDashboard.putString("Do you see two lines?", "No");

      Robot.driveTrain.setVolts(.15, .25);
    }else if(Robot.lineFollowing.rightCenterLineSeen()){
      SmartDashboard.putString("Do you see the line?", "Yes");
      SmartDashboard.putString("Centered?", "No! :( ");
      SmartDashboard.putString("Do you see two lines?", "Yes");

      Robot.driveTrain.setVolts(.25 , .2);
    }else if(Robot.lineFollowing.leftCenterLineSeen()){
      SmartDashboard.putString("Do you see the line?", "Yes");
      SmartDashboard.putString("Centered?", "No! :( ");
      SmartDashboard.putString("Do you see two lines?", "Yes");

      Robot.driveTrain.setVolts(.2, .25);
    }else{
      SmartDashboard.putString("Do you see the line?", "No");
      SmartDashboard.putString("Do you see two lines?", "No");

      Robot.driveTrain.setVolts(0, 0);
    }      
  }

  



  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    /*if(Robot.lineFollowing.rightLineSeen() == true || Robot.lineFollowing.centerLineSeen() == true || Robot.lineFollowing.leftLineSeen() == true){
      if (Robot.lineFollowing.isCloseToTarget()) {
        return true;
      } else {
        return false;
      }
    }else{
      return true;
    } */
    return false;   
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.setVolts(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
