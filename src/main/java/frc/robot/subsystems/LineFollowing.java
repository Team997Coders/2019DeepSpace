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
  //private final int HATCHDISTANCELIMIT = 80; 
  private DigitalInput m_sensorLeftInput;
  private DigitalInput m_sensorRightInput;
  private DigitalInput m_sensorCenterInput;
  private AnalogInput m_ultrasonicSensorInput;
  
  public LineFollowing() {
    m_sensorLeftInput = new DigitalInput(RobotMap.Ports.linesensorleft);
    m_sensorRightInput = new DigitalInput(RobotMap.Ports.linesensorright);
    m_sensorCenterInput = new DigitalInput(RobotMap.Ports.linesensorcenter);
    m_ultrasonicSensorInput = new AnalogInput(RobotMap.Ports.ultrasonicsensor);
  }

  public boolean leftLineSeen() {
    return(m_sensorLeftInput.get());
  }

  public boolean rightLineSeen() {
    return(m_sensorRightInput.get());
  }

  public boolean centerLineSeen() {
    return(m_sensorCenterInput.get());
  }

  public boolean anyLineSeen() {
    return(m_sensorCenterInput.get() || m_sensorLeftInput.get() || m_sensorRightInput.get());
  }

  public boolean noLineSeen() {
    return !anyLineSeen();
  }

  public boolean leftCenterLineSeen(){
    return(m_sensorCenterInput.get() && m_sensorLeftInput.get());
  }

  public boolean rightCenterLineSeen(){
    return(m_sensorCenterInput.get() && m_sensorRightInput.get());
  }

  // IF we thought this proximity sensor might be used in other ways,
  // we might break up this functionality into its own subsystem. Not sure
  // that we will so it's ok for now that it lives here.
  public boolean isCloseToTarget() {
    return m_ultrasonicSensorInput.getValue() < 75;
  }

  @Override
  public void initDefaultCommand(){ }

  public void updateSmarts() {
    SmartDashboard.putNumber("Ultrasonic", m_ultrasonicSensorInput.getValue());
    SmartDashboard.putBoolean("Line Lock", centerLineSeen() && !leftLineSeen() && !rightLineSeen());
  }
}
