/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Sensors;
import edu.wpi.first.wpilibj.command.Command;

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
  private boolean timeout;
  private long starts;
  private boolean firstTime;
  private Sensors sensors;
  private DriveTrain driveTrain;

  public FollowLine(Sensors sensors, DriveTrain driveTrain, long extratimems) {
    this.sensors = sensors;
    this.driveTrain = driveTrain;
    requires(driveTrain);
    requires(sensors);

    this.extratimems = extratimems;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {    
    firstTime = true;
    driveTrain.setBrake();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if(sensors.noLineSeen()){
      if(this.firstTime == true){
        this.starts = System.currentTimeMillis();
        firstTime = false;
        timeout = false;
      } else{
        long delta = System.currentTimeMillis() - (starts + extratimems);
        if((starts + extratimems) > System.currentTimeMillis()){
          System.out.println("No line seen ... driving straight, timeout =" + delta);
          driveTrain.setVolts(straight, straight);
          timeout = false;
        } else{
          System.out.println("No line seen ... Timed Out.");
          timeout = true;
          driveTrain.stopVolts();
        }
      }
    }
  else{
      if(sensors.leftCenterLineSeen()){
        driveTrain.setVolts(normal, powerMotor);
      }else if(sensors.rightCenterLineSeen()){
        driveTrain.setVolts(powerMotor , normal);
      }else if(sensors.lineSensorLeft()){
        driveTrain.setVolts(noPowerMotor, powerMotor);
      }else if(sensors.lineSensorRight()){
        driveTrain.setVolts(powerMotor, noPowerMotor);
      }else if(sensors.lineSensorCenter()){
        driveTrain.setVolts(straight, straight);
      }else{
        driveTrain.stopVolts();
      }
    }
  }      

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(sensors.lineSensorCenter()){
      if (sensors.isCloseToTarget()) {
        return true;
      } 
    } else if (timeout && sensors.noLineSeen()) {
      return true;
    } 
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    driveTrain.setVolts(0,0);
    driveTrain.setCoast();
  }
  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
    //Should it run this method when this is interrupted; it will stop the drivetrain untill another command runs
  }
}