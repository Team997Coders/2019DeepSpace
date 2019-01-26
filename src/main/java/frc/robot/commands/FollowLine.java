/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LineFollowing;
import edu.wpi.first.wpilibj.command.Command;
import java.util.concurrent.TimeUnit;

/**
 * Follow a line on the floor and stop when range is close
 * to target.
 */
public class FollowLine extends Command {

  private double powerMotor = 0.8;
  private double noPowerMotor = -.5;
  private double normal = .1; //for double line seen
  private double straight = .35;
  private long extratimems;
  private long starts;

  public FollowLine(long extratimems) {
    requires(Robot.driveTrain);
    requires(Robot.lineFollowing);
    this.extratimems = extratimems;
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

    if(Robot.lineFollowing.leftCenterLineSeen()){

      Robot.driveTrain.setVolts(normal, powerMotor);

    }else if(Robot.lineFollowing.rightCenterLineSeen()){

      Robot.driveTrain.setVolts(powerMotor , normal);
    
    }else if(Robot.lineFollowing.leftLineSeen()){

      Robot.driveTrain.setVolts(noPowerMotor, powerMotor);
    
    }else if(Robot.lineFollowing.rightLineSeen()){

      Robot.driveTrain.setVolts(powerMotor, noPowerMotor);; 

    }else if(Robot.lineFollowing.centerLineSeen()){

      Robot.driveTrain.setVolts(straight, straight);

    }else{ 
        this.starts = System.currentTimeMillis();
    }      
  }
 



  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Robot.lineFollowing.noLineSeen()){
      if((starts + extratimems)<System.currentTimeMillis()){
        return true;
      }else{
        if(Robot.lineFollowing.centerLineSeen() == true){
          if (Robot.lineFollowing.isCloseToTarget()) {
            System.out.println("I am finished");
            return true;
          } else {
            return false;
          }
        }else{
          return false;
        }   
      }
    }else{
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
