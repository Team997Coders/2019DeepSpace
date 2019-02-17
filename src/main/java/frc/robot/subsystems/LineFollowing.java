package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Three sensor subsystem to detect whether white tape ahead of target is seen
 * and position of robot in relation to tape.
 */
public class LineFollowing extends Subsystem {

  //private final int HATCHDISTANCELIMIT = 91; 
  private DigitalInput m_sensorLeftInput;
  private DigitalInput m_sensorRightInput;
  private DigitalInput m_sensorCenterInput;
  public AnalogInput m_ultrasonicSensorInput;

  
  public LineFollowing() {
    m_sensorLeftInput = new DigitalInput(RobotMap.Ports.lineSensorFrontLeft);
    m_sensorRightInput = new DigitalInput(RobotMap.Ports.lineSensorFrontRight);
    m_sensorCenterInput = new DigitalInput(RobotMap.Ports.lineSensorFrontCenter);
    m_ultrasonicSensorInput = new AnalogInput(RobotMap.Ports.frontUltrasonicSensor);
  }

  /**
   * Returns a boolean representing whether or not the left linesensor is seeing white
   * 
   * @return True if white is seen. False if not
   */
  public boolean leftLineSeen() {
    return(m_sensorLeftInput.get());
  }

  /**
   * Returns a boolean representing whether or not the right linesensor is seeing white
   * 
   * @return True if white is seen. False if not
   */
  public boolean rightLineSeen() {
    return(m_sensorRightInput.get());
  }

  /**
   * Returns a boolean representing whether or not the middle linesensor is seeing white
   * 
   * @return True if white is seen. False if not
   */
  public boolean centerLineSeen() {
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
   * Returns boolean representing no white is seen by any sensor.
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
   * Returns boolean whether the value from the ultrasonicSensor is close to the target.
   * WARNING: I'm pretty sure we will be using a Infared Sensor so I will depricate
   *     this function for now.
   * 
   * @return True if the value is less than the target value. False if not
   * 
   * @deprecated
   */
  public boolean isCloseToTarget() {
    return (m_ultrasonicSensorInput.getValue() < RobotMap.Values.frontUltrasonicSensorHatchRocket);
  }

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Ultrasonic sensor", m_ultrasonicSensorInput.getValue());
    SmartDashboard.putBoolean("Line Lock", centerLineSeen() && !leftLineSeen() && !rightLineSeen());

    //The lookification sensors for the lines.
    SmartDashboard.putBoolean("Center Line Seen", centerLineSeen());
    SmartDashboard.putBoolean("Left Line Seen", leftLineSeen());
    SmartDashboard.putBoolean("Right Line Seen", rightLineSeen());
  }

  @Override
  public void initDefaultCommand() { }

}