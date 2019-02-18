/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.LEDChannel;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class CameraLights extends Subsystem {
  private final CANifier canifier;

  public CameraLights() {
    this.canifier = new CANifier(29);
  }

  public void setLEDOn() {
    canifier.setLEDOutput(100, LEDChannel.LEDChannelA);
  }

  public void setLEDOff() {
    canifier.setLEDOutput(0, LEDChannel.LEDChannelA);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
