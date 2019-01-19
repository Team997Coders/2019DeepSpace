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

public class FollowLine extends Command {

  private LineFollowing m_lineFollowing;
  private DriveTrain m_driveTrain;


  public FollowLine(LineFollowing lineFollowing, DriveTrain driveTrain){
    m_lineFollowing = lineFollowing;
    m_driveTrain = driveTrain;
    requires(lineFollowing);
    requires(driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    System.out.println("Inside Command execute");

    
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


    
   /* boolean[] Sensorsdata = Robot.lineFollowing.returnOutput();

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
    }*/
  }



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
