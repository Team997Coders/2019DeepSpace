/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import frc.robot.commands.BackingUpWhenLineFollowIsComplete;
import frc.robot.commands.DeployLandingGear;
import frc.robot.commands.FlipSystemOrientation;
import frc.robot.commands.RetractLandingGear;

import frc.robot.commands.FlipDriveTrainOrientation;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class ButtonBox {

  Joystick buttonBox = new Joystick(2);

  private JoystickButton deployLandingGear;
  private JoystickButton retractLandingGear;
  private JoystickButton followLine;

  private JoystickButton flipSystemOrientation;

  private JoystickButton flipDriveTrainOrientation;

  public ButtonBox() {
    buttonBox = new Joystick(RobotMap.Buttons.GamePad1);

    deployLandingGear = new JoystickButton(buttonBox, RobotMap.Buttons.buttonB);
    deployLandingGear.whenPressed(new DeployLandingGear());

    retractLandingGear = new JoystickButton(buttonBox, RobotMap.Buttons.buttonBack);
    retractLandingGear.whenPressed(new RetractLandingGear());

    flipSystemOrientation = new JoystickButton(buttonBox, RobotMap.Buttons.buttonX);
    flipSystemOrientation.whenPressed(new FlipSystemOrientation());

    flipDriveTrainOrientation = new JoystickButton(buttonBox, RobotMap.Buttons.buttonY);
    flipDriveTrainOrientation.whenPressed(new FlipDriveTrainOrientation(Robot.scoringSideReversed));

//    followLine = new JoystickButton(buttonBox, 1);
//    followLine.whileHeld(new BackingUpWhenLineFollowIsComplete());

    //toggleHatch = new JoystickButton(gamepad1, RobotMap.Buttons.buttonX);
    //toggleHatch.whenPressed(new ToggleHatchHolder());
  }

  // KEEP THESE COMMENTS
  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
