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
import frc.robot.Robot;

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
  private Sensors sensors;
  private boolean gracePeriod;
  private boolean scoringSideReversed;

  public FollowLine(long extratimems, boolean scoringSideReversed) {
    this(Robot.frontSensors, Robot.backSensors, extratimems, scoringSideReversed);
  }

  public FollowLine(Sensors frontSensors, Sensors backSensors, long extratimems, boolean scoringSideReversed) {
    this.scoringSideReversed = scoringSideReversed;
    this.extratimems = extratimems;
    System.out.println(scoringSideReversed);
    if(scoringSideReversed){
      sensors = backSensors;
      System.out.println("Back");
    } else{
      sensors = frontSensors;
      System.out.println("Front");
    }
    requires(sensors);
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {    
    gracePeriod = false;
    Robot.driveTrain.setBrake();
    if(scoringSideReversed){
      System.out.println("init new value");
      powerMotor = -powerMotor;
      noPowerMotor = -noPowerMotor;
      normal = -normal;
      straight = -straight;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(sensors.noLineSeen() && !gracePeriod){
      gracePeriod = true;
      starts = System.currentTimeMillis();
      Robot.driveTrain.setVolts(straight, straight);
    } else if(sensors.noLineSeen() && gracePeriod) {
      Robot.driveTrain.setVolts(straight, straight);
    } else if (sensors.anyLineSeen() && gracePeriod) {
      Robot.driveTrain.setBrake();
      gracePeriod = false;
    } else {
      if(sensors.leftCenterLineSeen()){
        Robot.driveTrain.setVolts(normal, powerMotor);
      }else if(sensors.rightCenterLineSeen()){
        Robot.driveTrain.setVolts(powerMotor , normal);
      }else if(sensors.lineSensorLeft()){
        Robot.driveTrain.setVolts(noPowerMotor, powerMotor);
      }else if(sensors.lineSensorRight()){
        Robot.driveTrain.setVolts(powerMotor, noPowerMotor);
      }else if(sensors.lineSensorCenter()){
        Robot.driveTrain.setVolts(straight, straight);
      }else{
        Robot.driveTrain.stopVolts();
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
    Robot.driveTrain.setVolts(0,0);
    Robot.driveTrain.setCoast();
  }
  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
    //Should it run this method when this is interrupted; it will stop the drivetrain untill another command runs
  }
}