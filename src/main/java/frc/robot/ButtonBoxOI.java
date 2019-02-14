/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.buttonbox.commands.*;
import frc.robot.commands.AutoDoNothing;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class ButtonBoxOI {

  Joystick buttonBoxJoystick;

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

  /**
   * Default constructor which maps joystick id to RobotMap
   */
  public ButtonBoxOI() {
    this(RobotMap.Buttons.ButtonBox.ButtonBoxJoystickId);
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
  public ButtonBoxOI(int joystickId) {
    // Instantiate joystick
    buttonBoxJoystick = new Joystick(joystickId);

    Robot.buttonBox.whenActivateClicked(new AutoDoNothing());       // TODO: Change to command group that does all elevator/arm movements
    Robot.buttonBox.whenVisionAClicked(new AutoDoNothing());        // TODO: Wire up to vision subsystem once merged
    Robot.buttonBox.whenVisionBClicked(new AutoDoNothing());        // TODO: Wire up to vision subsystem once merged
    Robot.buttonBox.whenVisionXClicked(new AutoDoNothing());        // TODO: Wire up to vision subsystem once merged
    Robot.buttonBox.whenVisionCenterClicked(new AutoDoNothing());   // TODO: Wire up to vision subsystem once merged
    Robot.buttonBox.whenVisionLeftClicked(new AutoDoNothing());     // TODO: Wire up to vision subsystem once merged
    Robot.buttonBox.whenVisionRightClicked(new AutoDoNothing());    // TODO: Wire up to vision subsystem once merged
    
    activate = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.activateJoystickButtonId);
    activate.whenPressed(new Activate());

    backDirection = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.backJoystickButtonId);
    backDirection.whenPressed(new BackDirection());

    frontDirection = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.frontJoystickButtonId);
    frontDirection.whenPressed(new FrontDirection());

    ballArtifact = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.ballJoystickButtonId);
    ballArtifact.whenPressed(new BallArtifact());

    hatchArtifact = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.hatchJoystickButtonId);
    hatchArtifact.whenPressed(new HatchArtifact());

    highHeight = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.highHeightJoystickButtonId);
    highHeight.whenPressed(new HighHeight());

    mediumHeight = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.mediumHeightJoystickButtonId);
    mediumHeight.whenPressed(new MediumHeight());

    lowHeight = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.lowHeightJoystickButtonId);
    lowHeight.whenPressed(new LowHeight());

    rocketDestination = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.rocketJoystickButtonId);
    rocketDestination.whenPressed(new RocketDestination());

    cargoShipDestination = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.cargoShipJoystickButtonId);
    cargoShipDestination.whenPressed(new CargoShipDestination());

    cancel = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.cancelJoystickButtonId);
    cancel.whenPressed(new Cancel());

    intake = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.intakeJoystickButtonId);
    intake.whenPressed(new Intake());

    visionA = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.AJoystickButtonId);
    visionA.whenPressed(new VisionA());

    visionB = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.BJoystickButtonId);
    visionB.whenPressed(new VisionB());

    visionX = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.XJoystickButtonId);
    visionX.whenPressed(new VisionX());

    visionLeft = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.leftJoystickHatAngle);
    visionLeft.whenPressed(new VisionLeft());

    visionCenter = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.centerJoystickHatAngle);
    visionCenter.whenPressed(new VisionCenter());

    visionRight = new JoystickButton(buttonBoxJoystick, RobotMap.Buttons.ButtonBox.rightJoystickHatAngle);
    visionRight.whenPressed(new VisionRight());
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
