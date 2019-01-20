/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.google.inject.Inject;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.guice.annotations.LineFollowing.SensorCenterInput;
import frc.robot.guice.annotations.LineFollowing.SensorLeftInput;
import frc.robot.guice.annotations.LineFollowing.SensorRightInput;
import frc.robot.guice.annotations.LineFollowing.UltrasonicSensorInput;

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
  public LineFollowing(@SensorLeftInput DigitalInput sensorLeftInput, 
      @SensorCenterInput DigitalInput sensorCenterInput, 
      @SensorRightInput DigitalInput sensorRightInput,
      @UltrasonicSensorInput AnalogInput ultrasonicSensorInput) {
    m_sensorLeftInput = sensorLeftInput;
    m_sensorRightInput = sensorRightInput;
    m_sensorCenterInput = sensorCenterInput;
    m_ultrasonicSensorInput = ultrasonicSensorInput;
  }

  @Override
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

  // IF we thought this proximity sensor might be used in other ways,
  // we might break up this functionality into its own subsystem. Not sure
  // that we will so it's ok for now that it lives here.
  public boolean isCloseToTarget() {
    // TODO: Read datasheet and confirm this is correct!
    // Assume voltage goes down as we get closer to target.
    // What voltage is the right distance? Put in a private function
    // that converts voltage to distance and then put in a constant
    // for the threshold distance so that we can easily see what distance
    // we want to stop at.
    return m_ultrasonicSensorInput.getAverageVoltage() < 0.5;
  }
}
