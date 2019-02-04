/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;

import org.team997coders.spartanlib.hardware.roborio.Servo;
import org.team997coders.spartanlib.interfaces.IServo;

import frc.robot.RobotMap;
import frc.robot.commands.ProcessCameraMountCommands;

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
    this(new Servo(RobotMap.Ports.panservo), 
      new Servo(RobotMap.Ports.tiltservo, 544, 2250),
      tiltLowerLimitInDegrees,
      tiltUpperLimitInDegrees,
      panLowerLimitInDegrees,
      panUpperLimitInDegrees);
  }

  /**
   * Constructor that sets travel limits of servos to 0..180
   * 
   * @param panServo    The pan servo
   * @param tiltServo   The tilt servo
   */
  public CameraMount(IServo panServo, IServo tiltServo) {
    this(panServo, tiltServo, 0, 180, 0, 180);
  }

  /**
   * Constructor to set travel limits.
   * 
   * @param panServo
   * @param tiltServo
   * @param tiltLowerLimitInDegrees
   * @param tiltUpperLimitInDegrees
   * @param panLowerLimitInDegrees
   * @param panUpperLimitInDegrees
   */
  public CameraMount(IServo panServo, 
      IServo tiltServo, 
      int tiltLowerLimitInDegrees, 
      int tiltUpperLimitInDegrees, 
      int panLowerLimitInDegrees,
      int panUpperLimitInDegrees) {
    super(panServo, 
      tiltServo, 
      tiltLowerLimitInDegrees, 
      tiltUpperLimitInDegrees, 
      panLowerLimitInDegrees, 
      panUpperLimitInDegrees);
  }

  /**
   * Set the default command to process commands sent from remote CameraVision application
   */
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    try {
      setDefaultCommand(new ProcessCameraMountCommands());
    } catch (IOException e) {
      System.out.println(String.format("Cannot process default command for CameraMount. Pan/tilt will not work. Error=%s", e));
    }
  } 
}
