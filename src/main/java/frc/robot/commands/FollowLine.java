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
import frc.robot.subsystems.DriveTrain;
import 

public class FollowLine extends Command {

  private LineFollowing m_lineFollowing;
  private DriveTrain m_driveTrain;

  private boolean test = false;

  public FollowLine() { // Use the static instances in Robot
    requires(Robot.lineFollowing);
    requires(Robot.driveTrain);
    requires()
  }

  public FollowLine(LineFollowing lineFollowing, DriveTrain driveTrain) {
    test = true;
    m_driveTrain = driveTrain;
    m_lineFollowing = lineFollowing;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    System.out.println("Inside Command execute");

   

    //So a switch for this command would look something like this

    switch (true) { // What you want to compare the cases to
      case Robot.lineFollowing.centerLineSeen(true):
        SmartDashboard.putString("Do you see the line?", "Yes");
        SmartDashboard.putString("Centered?", "Yes! :) ");
        SmartDashboard.putString("Do you see two lines?", "No");

        m_driveTrain.setVolts(.25, .25);
      break; 
      case Robot.linFollowing.rightLineSeen(true):
        SmartDashboard.putString("Do you see the line?", "Yes");
        SmartDashboard.putString("Centered?", "No! :( ");
        SmartDashboard.putString("Do you see two lines?", "No");

        m_driveTrain.setVolts(.25, .15);
      break;
      case Robot.lineFollowing.leftLineSeen(true):
        SmartDashboard.putString("Do you see the line?", "Yes");
        SmartDashboard.putString("Centered?", "No! :( ");
        SmartDashboard.putString("Do you see two lines?", "No");

        m_driveTrain.setVolts(.15, .25);
      break;
      case Robot.lineFollowing.rightCenterLineSeen(true):
        SmartDashboard.putString("Do you see the line?", "Yes");
        SmartDashboard.putString("Centered?", "No! :( ");
        SmartDashboard.putString("Do you see two lines?", "Yes");

        m_driveTrain.setVolts(.25 , .2);  
      break;
      case:Robot.lineFollowing.leftCenterLineSeen(true):
        SmartDashboard.putString("Do you see the line?", "Yes");
        SmartDashboard.putString("Centered?", "No! :( ");
        SmartDashboard.putString("Do you see two lines?", "Yes");

        m_driveTrain.setVolts(.2, .25);
      break;
      default:  
        SmartDashboard.putString("Do you see the line?", "No");
        SmartDashboard.putString("Do you see two lines?", "No");
  
        m_driveTrain.setVolts(0, 0);
        break;
    }

    
    /*
    if (test) {
      if(m_lineFollowing.centerLineSeen()){
        SmartDashboard.putString("Do you see the line?", "Yes");
        SmartDashboard.putString("Centered?", "Yes! :) ");
        SmartDashboard.putString("Do you see two lines?", "No");
  
        m_driveTrain.setVolts(.25, .25);
      }else if(m_lineFollowing.rightLineSeen()){
        SmartDashboard.putString("Do you see the line?", "Yes");
        SmartDashboard.putString("Centered?", "No! :( ");
        SmartDashboard.putString("Do you see two lines?", "No");
  
        m_driveTrain.setVolts(.25, .15);
      }else if(m_lineFollowing.leftLineSeen()){
        SmartDashboard.putString("Do you see the line?", "Yes");
        SmartDashboard.putString("Centered?", "No! :( ");
        SmartDashboard.putString("Do you see two lines?", "No");
  
        m_driveTrain.setVolts(.15, .25);
      }else if(m_lineFollowing.rightCenterLineSeen()){
        SmartDashboard.putString("Do you see the line?", "Yes");
        SmartDashboard.putString("Centered?", "No! :( ");
        SmartDashboard.putString("Do you see two lines?", "Yes");
  
        m_driveTrain.setVolts(.25 , .2);
      }else if(m_lineFollowing.leftCenterLineSeen()){
        SmartDashboard.putString("Do you see the line?", "Yes");
        SmartDashboard.putString("Centered?", "No! :( ");
        SmartDashboard.putString("Do you see two lines?", "Yes");

        m_driveTrain.setVolts(.2, .25);
      }else{
        SmartDashboard.putString("Do you see the line?", "No");
        SmartDashboard.putString("Do you see two lines?", "No");
  
        m_driveTrain.setVolts(0, 0);
      }

      return;
    }*/
    /*
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
  }*/



  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
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
