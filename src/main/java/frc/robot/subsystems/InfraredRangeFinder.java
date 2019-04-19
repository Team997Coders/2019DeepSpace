/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for infrared range finder sensor
 */
public class InfraredRangeFinder extends Subsystem {
  private final AnalogInput m_infraredSensorInput;

  public InfraredRangeFinder(int port) {
    m_infraredSensorInput = new AnalogInput(port);
  }

  public int getRawValue() {
    return m_infraredSensorInput.getValue();
  }

  public int getRangeInInches() {
    // TODO: Calibrate and fill in...
    return 0;
  }

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Raw infrared sensor", getRawValue());
    SmartDashboard.putNumber("Infrared range (in)", getRangeInInches());
  }

  @Override
  public void initDefaultCommand() {
    // none
  }
}
