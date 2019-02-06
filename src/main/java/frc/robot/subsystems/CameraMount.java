/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.team997coders.spartanlib.hardware.roborio.Servo;

import frc.robot.RobotMap;
import frc.robot.commands.SlewCamera;

/**
 * A subsystem to define an automated camera mount that uses servos for panning and tilting.
 */
public class CameraMount extends org.team997coders.spartanlib.subsystems.CameraMount {

  /**
   * Convenience constructor to hard-wire CameraMount to Roborio servo implementation.
   */
  public CameraMount(int tiltLowerLimitInDegrees, 
      int tiltUpperLimitInDegrees, 
      int panLowerLimitInDegrees,
      int panUpperLimitInDegrees) {
    super(new Servo(RobotMap.Ports.panservo), 
      new Servo(RobotMap.Ports.tiltservo, 544, 2250),
      tiltLowerLimitInDegrees,
      tiltUpperLimitInDegrees,
      panLowerLimitInDegrees,
      panUpperLimitInDegrees);
  }

  /**
   * Set the default command to process commands sent to/from remote CameraVision application
   */
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new SlewCamera());
  } 
}
