/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
/**
 * Add your docs here.
 */
public class Sensors extends Subsystem {
  private DigitalInput m_sensorLeftInput;
  private DigitalInput m_sensorRightInput;
  private DigitalInput m_sensorCenterInput;

  public AnalogInput m_ultrasonicSensorInput;
  public AnalogInput m_infraredSensorInput;

  public Sensors(int leftPort, int centerPort, int rightPort, int infraredPort, int ultrasonic){
    m_sensorLeftInput = new DigitalInput(leftPort);
    m_sensorRightInput = new DigitalInput(rightPort);
    m_sensorCenterInput = new DigitalInput(centerPort);

    m_ultrasonicSensorInput = new AnalogInput(ultrasonic);
    m_infraredSensorInput = new AnalogInput(infraredPort);
  }

   /**
   * Returns a boolean representing whether or not the left linesensor is seeing white
   * 
   * @return True if white is seen. False if not
   */
  public boolean lineSensorLeft() {
    return(m_sensorLeftInput.get());
  }

  /**
   * Returns a boolean representing whether or not the right linesensor is seeing white
   * 
   * @return True if white is seen. False if not
   */
  public boolean lineSensorRight() {
    return(m_sensorRightInput.get());
  }

  /**
   * Returns a boolean representing whether or not the middle linesensor is seeing white
   * 
   * @return True if white is seen. False if not
   */
  public boolean lineSensorCenter() {
    return(m_sensorCenterInput.get());
  }

    /**
   * Returns a boolean representing whether or not any of the linesensors see any white
   * 
   * @return True if white is seen. False if not
   */
  public boolean anyLineSeen() {
    return(m_sensorCenterInput.get() || m_sensorLeftInput.get() || m_sensorRightInput.get());
  }

    /**
   * Returns boolean representing no white is seen by any line sensor.
   * This function is !anyLineSeen();
   * 
   * @return True if not white is seen. False if there is
   */
  public boolean noLineSeen() {
    return !anyLineSeen();
  }

   /**
   * Returns boolean dependent on the middle and left sensors see white
   * 
   * @return True if middle and left sensors see white. False if only one or neither see white
   */
  public boolean leftCenterLineSeen(){
    return(m_sensorCenterInput.get() && m_sensorLeftInput.get());
  }

    /**
   * Returns boolean dependent on the middle and right sensors see white
   * 
   * @return True if middle and right sensors see white. False if only on or neither see white
   */
  public boolean rightCenterLineSeen(){
    return(m_sensorCenterInput.get() && m_sensorRightInput.get());
  }

    /**
   * Returns boolean whether the value from the ultrasonic Sensor and Infrared Sensor is close to the target.
   * WARNING: I'm pretty sure we will be using a Infared Sensor so I will depricate
   *     this function for now.
   * 
   * @return True if the value is less than the target value. False if not
   * 
   * @deprecated
   */

   //This is the logic order when the robot need to stop
  public boolean isCloseToTarget() {
    if(Robot.scoringSideReversed){
      /*if(rocketTarget){
          if(hatchIsTargetType){
            return("Invalid condition to happen") //TODO: Need to make this work!
          } else{
            return(m_infraredSensorInput.getValue() < RobotMap.Values.backInfraredSensorBallRocket);
          }
      } else{
          if(hatchIsTargetType){
            return(m_infraredSensorInput.getValue() < RobotMap.Values.backInfraredSensorHatchCargoShip);
          } else{
            return(m_infraredSensorInput.getValue() < RobotMap.Values.backInfraredSensorBallCargoShip);
          }
      }
*/
      return(m_infraredSensorInput.getValue() < RobotMap.Values.backInfraredSensorHatchCargoship);
    } else{
      /*if(rocketTarget){
          if(hatchIsTargetType){
            return(m_ultrasonicSensorInput.getValue() < RobotMap.Values.frontUltrasonicSensorHatchRocket &&
            m_infraredSensorInput.getValue() < RobotMap.Values.frontInfraredSensorHatchRocket);
          } else{
            return(m_ultrasonicSensorInput.getValue() < RobotMap.Values.frontUltrasonicSensorBallCargoShip &&
            m_infraredSensorInput.getValue() < RobotMap.Values.frontInfraredSensorBallCargoShip);
          }
      } else{
          if(hatchIsTargetType){
            return(m_ultrasonicSensorInput.getValue() < RobotMap.Values.frontUltrasonicSensorHatchCargoship &&
            m_infraredSensorInput.getValue() < RobotMap.Values.frontInfraredSensorHatchCargoship);
          } else{
            return(m_ultrasonicSensorInput.getValue() < RobotMap.Values.frontUltrasonicSensorBallCargoship &&
            m_infraredSensorInput.getValue() < RobotMap.Values.frontInfraredSensorBallCargoship);
          }
      }*/
      return (m_ultrasonicSensorInput.getValue() < RobotMap.Values.frontUltrasonicSensorHatchCargoship /*&&
      m_infraredSensorInput.getValue() < RobotMap.Values.frontInfraredSensorHatchCargoship*/);
    }
  }

  public int getUltrasonicSensorValue(){
    return m_ultrasonicSensorInput.getValue();
  }

  public int getInfraredValue(){
    return m_infraredSensorInput.getValue();
  }

  public void updateSmartDashboard(){
    SmartDashboard.putNumber("Ultrasonic Sensor Value", getUltrasonicSensorValue());
    SmartDashboard.putNumber("Infrared Sensor Value", getInfraredValue());
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}