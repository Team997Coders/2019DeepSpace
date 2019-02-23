/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import frc.robot.buttonbox.commands.BackDirection;
import frc.robot.buttonbox.commands.FrontDirection;
import frc.robot.vision.commands.PressA;
import frc.robot.vision.commands.PressB;
import frc.robot.vision.commands.PressLeftThumbStick;
import frc.robot.vision.commands.PressX;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class LogitechVisionOI {

  Joystick joystick;

  private JoystickButton visionA;
  private JoystickButton visionB;
  private JoystickButton visionX;
  private JoystickButton leftThumbstickButton;
  private JoystickButton rightThumbstickButton;
  private POVButton visionLeft;
  private POVButton frontScoringDirection;
  private POVButton backScoringDirection;
  private JoystickButton visionCenter;
  private POVButton visionRight;
  private POVButton visionStopPanning;

  /**
   * Default constructor which maps joystick id to RobotMap
   */
  public LogitechVisionOI() {
    this(RobotMap.Buttons.Logitech.Gamepad4);
  }

  /**
   * Instantiate the operator interface for the operator #2 button box.
   * The device that drives the interface is a custom HID USB device that
   * acts like a joystick with a bunch of buttons. It uses a Teensy 3.5
   * and Arduino-based custom firmware to map arcade buttons to joystick buttons.
   * It also sets LED status lights to show state.

   * @param joystickId  Id of the joystick...it will have the name Keyboard/Mouse/Joystick
   * on the FRC driver station.
   * 
   * @see https://www.pjrc.com/teensy/td_joystick.html
   * @see https://github.com/Team997Coders/2019DSOperator2Console
   */
  public LogitechVisionOI(int joystickId) {
    // Instantiate joystick
    joystick = new Joystick(joystickId);
    
//    backDirection = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.backJoystickButtonId);
//    backDirection.whenPressed(new BackDirection());

//    frontDirection = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.frontJoystickButtonId);
//    frontDirection.whenPressed(new FrontDirection());

    visionA = new JoystickButton(joystick, RobotMap.Buttons.Logitech.buttonA);
    visionA.whenPressed(new PressA());

    visionB = new JoystickButton(joystick, RobotMap.Buttons.Logitech.buttonB);
    visionB.whenPressed(new PressB());

    visionX = new JoystickButton(joystick, RobotMap.Buttons.Logitech.buttonX);
    visionX.whenPressed(new PressX());

//    visionLeft = new POVButton(joystick, RobotMap.Buttons.Logitech.leftJoystickHatAngle);
//    visionLeft.whenPressed(new PressPanLeft());

    frontScoringDirection = new POVButton(joystick, 0);
    frontScoringDirection.whenPressed(new FrontDirection());

    backScoringDirection = new POVButton(joystick, 180);
    backScoringDirection.whenPressed(new BackDirection());

    visionCenter = new JoystickButton(joystick, RobotMap.Buttons.Logitech.buttonLeftThumbstick);
    visionCenter.whenPressed(new PressLeftThumbStick());

//    visionRight = new POVButton(joystick, RobotMap.Buttons.Logitech.rightJoystickHatAngle);
//    visionRight.whenPressed(new PressPanRight());

//    visionStopPanning = new POVButton(joystick, 0);
//    visionStopPanning.whenPressed(new StopPanning());
  }

  public double getVisionLeftYAxis() {
    // This should be negated for pan/tilt servos.
    return clampWithDeadband(0.05, joystick.getRawAxis(RobotMap.Buttons.Logitech.leftYAxis), -1, 1);
  }

  public double getVisionLeftXAxis() {
    return -clampWithDeadband(0.05, joystick.getRawAxis(RobotMap.Buttons.Logitech.leftXAxis), -1, 1);
  }

  public double deadBand(double value, double dead) {
    if (Math.abs(value) < dead) {
      return 0;
    } else {
      return value;
    }
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

  /**
   * Take an input value and clamp it within a range while adding
   * a dead band if the values are really small.
   * 
   * @param dead  Deadband range to zero
   * @param val   Input value to process
   * @param min   Min clamp value
   * @param max   Max clamp value
   * @return      Processed value
   */
  public double clampWithDeadband(double dead, double val, double min, double max) {
    return clamp(min, max, deadBand(val, dead));
  }
}
