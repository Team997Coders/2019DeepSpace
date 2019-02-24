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

  private final int HATCHDISTANCELIMIT = 91; 
  private DigitalInput m_sensorFrontLeftInput;
  private DigitalInput m_sensorFrontRightInput;
  private DigitalInput m_sensorFrontCenterInput;

  private DigitalInput m_sensorBackLeftInput;
  private DigitalInput m_sensorBackRightInput;
  private DigitalInput m_sensorBackCenterInput;

  public AnalogInput m_ultrasonicSensorInput;

  
  public LineFollowing() {
    m_sensorFrontLeftInput = new DigitalInput(RobotMap.Ports.lineSensorFrontLeft);
    m_sensorFrontRightInput = new DigitalInput(RobotMap.Ports.lineSensorFrontCenter);
    m_sensorFrontCenterInput = new DigitalInput(RobotMap.Ports.lineSensorFrontRight);
    
    m_sensorBackLeftInput = new DigitalInput(RobotMap.Ports.lineSensorBackLeft);
    m_sensorBackCenterInput = new DigitalInput(RobotMap.Ports.lineSensorBackCenter);
    m_sensorBackRightInput = new DigitalInput(RobotMap.Ports.lineSensorBackRight);

    m_ultrasonicSensorInput = new AnalogInput(RobotMap.Ports.frontUltraSonicSensor);
  }

  /**
   * Returns a boolean representing whether or not the left linesensor is seeing white
   * 
   * @return True if white is seen, false if not.
   */
  public boolean returnLineSensorBackLeft() {
    return(m_sensorBackLeftInput.get());
  }

  /**
   * Returns a boolean representing whether or not the middle linesensor is seeing white
   * 
   * @return True if white is seen, false if not.
   */
  public boolean returnLineSensorBackCenter() {
    return(m_sensorBackCenterInput.get());
  }

  /**
   * Returns a boolean representing whether or not the right linesensor is seeing white
   * 
   * @return True if white is seen, false if not.
   */
  public boolean returnLineSensorBackRight() {
    return(m_sensorBackRightInput.get());
  }

  /**
   * Returns a boolean representing whether or not any of the linesensors see any white
   * 
   * @return True if white is seen, false if not.
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
   * @return True if the value is less than the target value, false if not.
   * 
   * @deprecated
   */
  public boolean isCloseToTarget() {
    SmartDashboard.putNumber("Ultrasonic sensor", m_ultrasonicSensorInput.getValue());
    SmartDashboard.putBoolean("Line Lock", centerLineSeen() && !leftLineSeen() && !rightLineSeen());
    return (m_ultrasonicSensorInput.getValue() < HATCHDISTANCELIMIT);
  }
  @Override
  public void initDefaultCommand() { }

}