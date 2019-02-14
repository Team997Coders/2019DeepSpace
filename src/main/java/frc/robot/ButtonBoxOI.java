/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.buttonbox.commands.Activate;
import frc.robot.buttonbox.commands.BackDirection;
import frc.robot.buttonbox.commands.FrontDirection;
import frc.robot.commands.AutoDoNothing;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class ButtonBoxOI {

  Joystick buttonBoxJoystick = new Joystick(RobotMap.Buttons.ButtonBox.ButtonBoxJoystickId);

  private JoystickButton activate;
  private JoystickButton backDirection;
  private JoystickButton frontDirection;
  private JoystickButton ballArtifact;
  private JoystickButton hatchArtifact;
  private JoystickButton highHeight;
  private JoystickButton mediumHeight;
  private JoystickButton lowHeight;
  private JoystickButton rocketDestination;
  private JoystickButton cargoShipDestination;
  private JoystickButton cancel;
  private JoystickButton intake;
  // Do not worry about these for now...need to merge NT-Auto first
  private JoystickButton visionA;
  private JoystickButton visionB;
  private JoystickButton visionX;
  private JoystickButton visionLeft;
  private JoystickButton visionCenter;
  private JoystickButton visionRight;

  public ButtonBoxOI() {
    Robot.buttonBox.whenActivateClicked(new AutoDoNothing());       // TODO: Change to command group that does all elevator/arm movements
    
    activate = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.activateJoystickButtonId);
    activate.whenPressed(new Activate());

    backDirection = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.backJoystickButtonId);
    backDirection.whenPressed(new BackDirection());

    frontDirection = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.frontJoystickButtonId);
    frontDirection.whenPressed(new FrontDirection());
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
