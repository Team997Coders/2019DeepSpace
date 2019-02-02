/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  Joystick gamepad1; 
  JoystickButton deployLandingGear;
  JoystickButton retractLandingGear;

  public OI() {
    gamepad1 = new Joystick(RobotMap.Ports.GamePad1);

    deployLandingGear = new JoystickButton(gamepad1, RobotMap.Ports.buttonB);
    deployLandingGear.whenPressed(new DeployLandingGear());

    retractLandingGear = new JoystickButton(gamepad1, RobotMap.Ports.buttonBack);
    retractLandingGear.whenPressed(new RetractLandingGear());
  }

  public double getLeftYAxis() {
    return bing(0.05, -gamepad1.getRawAxis(RobotMap.Ports.leftYAxis), -1, 1);
  }

  public double getRightXAxis() {
    return bing(0.05, gamepad1.getRawAxis(RobotMap.Ports.rightXAxis), -1, 1);
  }

  public double getRightYAxis() {
    return bing(0.05, -gamepad1.getRawAxis(RobotMap.Ports.rightYAxis), -1, 1);
  }

  public double deadBand(double value, double dead) {
    if (Math.abs(value) < dead) {
      return 0;
    } else {
      return value;
    }
  }

  public double bing(double dead, double val, double min, double max) {
    return clamp(min, max, deadBand(val, dead));
  }

  public double clamp(double min, double max, double val) {
    if (min > val) {
      return min;
    } else if (max < val) {
      return max;
    } else {
      return val;
    }
  }
}
