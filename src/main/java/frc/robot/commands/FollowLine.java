/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotMap;
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
  private long starts;
  private Sensors sensors;
  private DriveTrain driveTrain;
  private boolean gracePeriod;
  private boolean scoringSideReversed;

  public FollowLine(long extratimems, boolean scoringSideReversed) {
    this.scoringSideReversed = scoringSideReversed;
    this.extratimems = extratimems;
    requires(driveTrain);
    requires(sensors);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {    
    gracePeriod = false;
    driveTrain.setBrake();
    if(scoringSideReversed){
      new Sensors(RobotMap.Ports.lineSensorBackLeft, 
      RobotMap.Ports.lineSensorBackCenter, 
      RobotMap.Ports.lineSensorBackRight, 
      RobotMap.Ports.frontInfraredSensor,
      RobotMap.Ports.backUltrasonicSensor);
    } else{
      new Sensors(RobotMap.Ports.lineSensorFrontLeft, 
      RobotMap.Ports.lineSensorFrontCenter, 
      RobotMap.Ports.lineSensorFrontRight, 
      RobotMap.Ports.frontInfraredSensor,
      RobotMap.Ports.frontUltrasonicSensor);
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(sensors.noLineSeen() && !gracePeriod){
      gracePeriod = true;
      starts = System.currentTimeMillis();
      driveTrain.setVolts(straight, straight);
    } else if(sensors.noLineSeen() && gracePeriod) {
      driveTrain.setVolts(straight, straight);
    } else if (sensors.anyLineSeen() && gracePeriod) {
      driveTrain.setBrake();
      gracePeriod = false;
    } else {
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

  private boolean gracePeriodExpired() {
    return (System.currentTimeMillis() > (starts + extratimems));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (sensors.isCloseToTarget()) {
      return true;
    } else if (gracePeriod && gracePeriodExpired()) {
      return true;
    } else {
      return false;
    }
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