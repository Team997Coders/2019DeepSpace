/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.team997coders.spartanlib.hardware.roborio.Servo;

import frc.robot.RobotMap;
import frc.robot.commands.ControlCamera;

/**
 * A subsystem to define an automated camera mount that uses servos for panning and tilting.
 */
public class CameraMount extends org.team997coders.spartanlib.subsystems.CameraMount {
  private final double maxDegreesPerHeartbeat;

  /**
   * Convenience constructor to hard-wire CameraMount to Roborio servo implementation.
   */
  public CameraMount(int tiltLowerLimitInDegrees, 
      int tiltUpperLimitInDegrees, 
      int panLowerLimitInDegrees,
      int panUpperLimitInDegrees,
      double slewRate180DegreesInSec,
      double heartbeatRateInMs) {
    super(new Servo(RobotMap.Ports.panservo), 
      new Servo(RobotMap.Ports.tiltservo, 544, 2250),
      tiltLowerLimitInDegrees,
      tiltUpperLimitInDegrees,
      panLowerLimitInDegrees,
      panUpperLimitInDegrees);
      this.maxDegreesPerHeartbeat = (180D / slewRate180DegreesInSec) / (1000D / heartbeatRateInMs);
    }

  /**
   * Set the default command to ControlCamera
   */
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new ControlCamera());
  } 

  /**
   * Center the camera 90 degrees on both axes.
   */
  public void center() {
    // TODO: Pull this function into SpartanLib
    panToAngle(90d);
    tiltToAngle(90d);
  }

  /**
   * Slew the camera.
   * 
   * @param panRate   A value between -1 and 1 the represents the percentage of maximum pan slewage.
   * @param tiltRate  A value between -1 and 1 the represents the percentage of maximum tilt slewage.
   */
  public void slew(double panRate, double tiltRate) {
    // TODO: Pulls this function into SpartanLib; remove SlewCamera command?
    double panAngle = getPanAngleInDegrees() + (maxDegreesPerHeartbeat * panRate);
    double tiltAngle = getTiltAngleInDegrees() + (maxDegreesPerHeartbeat * tiltRate);
    panToAngle(panAngle);
    tiltToAngle(tiltAngle);
  }
}
