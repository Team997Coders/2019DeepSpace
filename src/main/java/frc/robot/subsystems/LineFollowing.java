/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Three sensor subsystem to detect whether white tape ahead of target is seen
 * and position of robot in relation to tape.
 */
public class LineFollowing extends Subsystem {
  private DigitalInput m_sensorLeftInput;
  private DigitalInput m_sensorRightInput;
  private DigitalInput m_sensorCenterInput;
  private AnalogInput m_ultrasonicSensorInput;

  @Inject
  public LineFollowing(@Named("sensorLeftInput") DigitalInput sensorLeftInput, 
  @Named("sensorCenterInput") DigitalInput sensorCenterInput, @Named("sensorRightInput")DigitalInput sensorRightInput,
  @Named("ultrasonicSensorInput") AnalogInput ultrasonicSensorInput) {
    m_sensorLeftInput = sensorLeftInput;
    m_sensorRightInput = sensorRightInput;
    m_sensorCenterInput = sensorCenterInput;
    m_ultrasonicSensorInput = ultrasonicSensorInput;
  }

  public void initDefaultCommand(){}

  public boolean leftLineSeen(){
    return(m_sensorLeftInput.get());
  }

  public boolean rightLineSeen(){
    return(m_sensorRightInput.get());
  }

  public boolean centerLineSeen(){
    return(m_sensorCenterInput.get());
  }

  public boolean anyLineSeen(){
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

  public boolean isCloseToTarget() {
    // TODO: Read datasheet and confirm this is correct!
    // Assume voltage goes down as we get closer to target
    return m_ultrasonicSensorInput.getAverageVoltage() < 0.5;
  }

  /*

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }*/
}
