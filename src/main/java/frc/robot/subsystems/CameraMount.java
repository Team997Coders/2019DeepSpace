/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.LEDChannel;

import org.team997coders.spartanlib.hardware.roborio.Servo;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.buttonbox.ButtonBox;
import frc.robot.commands.ControlCamera;

/**
 * A subsystem to define an automated camera mount that uses servos for panning and tilting.
 */
public class CameraMount extends org.team997coders.spartanlib.subsystems.CameraMount {
  private final double maxDegreesPerHeartbeat;
  private final CANifier canifier;
  private final LEDChannel lightRingLEDChannel;
  private final ButtonBox.ScoringDirectionStates scoringDirection;

  /**
   * Convenience constructor to hard-wire CameraMount to Roborio servo implementation.
   */
  public CameraMount(int tiltLowerLimitInDegrees, 
      int tiltUpperLimitInDegrees, 
      int panLowerLimitInDegrees,
      int panUpperLimitInDegrees,
      double slewRate180DegreesInSec,
      double heartbeatRateInMs,
      LEDChannel lightRingLEDChannel,
      int panServoPortId,
      int tiltServoPortId,
      ButtonBox.ScoringDirectionStates scoringDirection) {
    // Call the constructor to reverse servo behavior based on motion
    // of production servos mounted to comp bot.
    super(new Servo(panServoPortId), 
      new Servo(tiltServoPortId, 544, 2250),
      tiltLowerLimitInDegrees,
      tiltUpperLimitInDegrees,
      true,                       // Reverses tilt servo
      panLowerLimitInDegrees,
      panUpperLimitInDegrees,
      true);                      // Reverses pan servo
      canifier = Robot.elevatorCanifier;
      this.lightRingLEDChannel = lightRingLEDChannel;
      this.scoringDirection = scoringDirection;
      this.maxDegreesPerHeartbeat = (180D / slewRate180DegreesInSec) / (1000D / heartbeatRateInMs);
    }

  /**
   * Set the default command to ControlCamera
   */
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new ControlCamera(scoringDirection));
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
   * Set the output of the camera mount light ring.
   * @param percentOutput   PWM dutycycle expressed as a percentage.
   */
  public void setLightRingOutput(double percentOutput) {
    canifier.setLEDOutput(percentOutput, lightRingLEDChannel);
  }

  /**
   * Turn off the light ring.
   */
  public void setLightRingOff() {
    setLightRingOutput(0);
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

  /**
   * Updates the SmartDashboard with subsystem data
   */
  public void updateSmartDashboard() {
    SmartDashboard.putNumber(String.format("%s Camera Pan Angle", scoringDirection.toString()), getPanAngleInDegrees());
    SmartDashboard.putNumber(String.format("%s Camera Tilt Angle", scoringDirection.toString()), getTiltAngleInDegrees());
  }
}
