/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

  private Joystick gamepad1, gamepad2;

  public OI() {
    gamepad1 = new Joystick(RobotMap.Ports.gamepad1);
    gamepad2 = new Joystick(RobotMap.Ports.gamepad2);
  }

  public double getLeftYAxis() {
    return -gamepad1.getRawAxis(RobotMap.Ports.leftYAxis);
  }

  public double getRightXAxis() {
    return gamepad1.getRawAxis(RobotMap.Ports.rightXAxis);
  }

  public double getRightYAxis() {
    return -gamepad1.getRawAxis(RobotMap.Ports.rightYAxis);
  }

  public double deadBand(double value, double min, double max) {
    if (value > max) {
      return max;
    } else if (value < min) {
      return min;
    }
    
    return value;
  }
}
