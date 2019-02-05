/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

<<<<<<< HEAD
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LineFollowing;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
=======
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
>>>>>>> master

/**
 * Follow a line on the floor and stop when range is close
 * to target.
 */
public class FollowLine extends Command {

  private double powerMotor = 0.8;
  private double noPowerMotor = -.5;
  private double normal = .1; //for double line seen
  private double straight = .35;
  private long extratimems = 1000;
  private long starts;
  private boolean firstTime;
  private boolean backup;

  public FollowLine(long extratimems) {
    requires(Robot.driveTrain);
    requires(Robot.lineFollowing);
    this.extratimems = extratimems;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {    
    firstTime = true;
    backup = false;
    Robot.driveTrain.setBrake();
  }

  // Called repeatedly when this Command is scheduled to run
  // TODO: add different value on the motor power when the ultrasensor value is smaller
  // instead of setVolts.


  @Override
  protected void execute() {

    if(Robot.lineFollowing.noLineSeen()){
      if(this.firstTime == true){
        this.starts = System.currentTimeMillis();
        firstTime = false;
        
      }
      else{
        if((starts + extratimems) > System.currentTimeMillis()){
  
          Robot.driveTrain.setVolts(straight, straight);
        
        }
        else if(Robot.lineFollowing.anyLineSeen()){
         
          Robot.driveTrain.setBrake();

        }
        else{
  
          Robot.driveTrain.setVolts(0,0);
  
        }
      }
    }
  else{
      if(Robot.lineFollowing.leftCenterLineSeen()){

        Robot.driveTrain.setVolts(normal, powerMotor);

      }else if(Robot.lineFollowing.rightCenterLineSeen()){

        Robot.driveTrain.setVolts(powerMotor , normal);
    
<<<<<<< HEAD
      }else if(Robot.lineFollowing.leftLineSeen()){

        Robot.driveTrain.setVolts(noPowerMotor, powerMotor);
    
      }else if(Robot.lineFollowing.rightLineSeen()){

        Robot.driveTrain.setVolts(powerMotor, noPowerMotor);; 

      }else if(Robot.lineFollowing.centerLineSeen()){

        Robot.driveTrain.setVolts(straight, straight);

      }else{

        Robot.driveTrain.setVolts(0,0);
      }
    }
  }      
 



  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Robot.lineFollowing.centerLineSeen()){
      if (Robot.lineFollowing.isCloseToTarget()) {
        SmartDashboard.putString("Are you done?", "Yes!");
=======
    SmartDashboard.putBoolean("LineFollowing is Active!", true);
    System.out.println("Processing");

    if(Robot.lineFollowing.centerLineSeen()){
      SmartDashboard.putBoolean("Do you see the line?", true);
      SmartDashboard.putBoolean("Centered?", true);
      SmartDashboard.putBoolean("Do you see two lines?", false);
      
      Robot.driveTrain.setVolts(.25, .25);
    }else if(Robot.lineFollowing.rightLineSeen()){
      SmartDashboard.putBoolean("Do you see the line?", true);
      SmartDashboard.putBoolean("Centered?", false);
      SmartDashboard.putBoolean("Do you see two lines?", false);
      
      Robot.driveTrain.setVolts(.25, .15);
    }else if(Robot.lineFollowing.leftLineSeen()){
      SmartDashboard.putBoolean("Do you see the line?", true);
      SmartDashboard.putBoolean("Centered?", false);
      SmartDashboard.putBoolean("Do you see two lines?", false);
      
      Robot.driveTrain.setVolts(.15, .25);
    }else if(Robot.lineFollowing.rightCenterLineSeen()){
      SmartDashboard.putBoolean("Do you see the line?", true);
      SmartDashboard.putBoolean("Centered?", false);
      SmartDashboard.putBoolean("Do you see two lines?", true);
      
      Robot.driveTrain.setVolts(.25 , .2);
    }else if(Robot.lineFollowing.leftCenterLineSeen()){
      SmartDashboard.putBoolean("Do you see the line?", true);
      SmartDashboard.putBoolean("Centered?", false);
      SmartDashboard.putBoolean("Do you see two lines?", true);
      
      Robot.driveTrain.setVolts(.2, .25);
    }else{
      SmartDashboard.putBoolean("Do you see the line?", false);
      SmartDashboard.putBoolean("Do you see two lines?", false);

      Robot.driveTrain.setVolts(0, 0);
    }      
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    /*if(Robot.lineFollowing.rightLineSeen() == true || Robot.lineFollowing.centerLineSeen() == true || Robot.lineFollowing.leftLineSeen() == true){
      if (Robot.lineFollowing.isCloseToTarget()) {
>>>>>>> master
        return true;
    } else {
        return false;
      }
<<<<<<< HEAD
    } 
    else{
      return false;
    }
=======
    }else{
      return true;
    } */
    return false;
>>>>>>> master
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
<<<<<<< HEAD
    Robot.driveTrain.setVolts(0,0);
    Robot.driveTrain.setCoast();
=======
    Robot.driveTrain.stopVolts();
  }
>>>>>>> master

  }
  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
    //Should it run this method when this is interrupted; it will stop the drivetrain untill another command runs
  }
}
