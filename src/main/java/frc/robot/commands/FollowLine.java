/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import javax.inject.Inject;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.LineFollowing;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrain;

/**
 * Follow a line on the floor and stop when range is close
 * to target.
 */
public class FollowLine extends Command {
  private LineFollowing m_lineFollowing;
  private DriveTrain m_driveTrain;

<<<<<<< HEAD

  //private boolean test = false;
@Inject
  public FollowLine(LineFollowing lineFollowing, DriveTrain driveTrain) {
    //test = true;
=======
  public FollowLine(LineFollowing lineFollowing, DriveTrain driveTrain) {
>>>>>>> 955cf6b3feaeab718ad6202cd39be853d62d60e4
    m_driveTrain = driveTrain;
    m_lineFollowing = lineFollowing;
    requires(m_driveTrain);
    requires(m_lineFollowing);
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

    if(m_lineFollowing.centerLineSeen()){
      SmartDashboard.putString("Do you see the line?", "Yes");
      SmartDashboard.putString("Centered?", "Yes! :) ");
      SmartDashboard.putString("Do you see two lines?", "No");

<<<<<<< HEAD
   // if (test) {
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
      //return;
    //}
=======
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

      m_driveTrain.stop();
    }      
>>>>>>> 955cf6b3feaeab718ad6202cd39be853d62d60e4
  }

  



  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(m_lineFollowing.rightLineSeen() == true || m_lineFollowing.centerLineSeen() == true || m_lineFollowing.leftLineSeen() == true){
      if (m_lineFollowing.isCloseToTarget()) {
        return true;
      } else {
        return false;
      }
    }else{
      return true;
    }    
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    m_driveTrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
