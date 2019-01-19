/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DigitalInput;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Add your docs here.
 */
public class LineFollowing extends Subsystem {

  //Test
  private DigitalInput m_sensorLeftInput;
  private DigitalInput m_sensorRightInput;
  private DigitalInput m_sensorCenterInput;
  private DigitalInput m_untrasonicSensorInput;


@Inject
  public LineFollowing(@Named("sensorLeftInput") DigitalInput sensorLeftInput, 
  @Named("sensorCenterInput") DigitalInput sensorCenterInput, @Named("sensorRightInput")DigitalInput sensorRightInput) {
    m_sensorLeftInput = sensorLeftInput;
    m_sensorRightInput = sensorRightInput;
    m_sensorCenterInput = sensorCenterInput;
    m_untrasonicSensorInput = untrasonicSensorInput;


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

  public boolean leftCenterLineSeen(){
    return(m_sensorCenterInput.get() && m_sensorLeftInput.get());
  }

  public boolean rightCenterLineSeen(){
    return(m_sensorCenterInput.get() && m_sensorRightInput.get());
  }

  public boolean untrasonicDepositeSeen(){
    return(m_untrasonucSensorInput.get());
  }

  public double ultrasonicVoltage() {
    return m_untrasonucSensorInput.getVoltage();
  }

  /*

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }*/
}
