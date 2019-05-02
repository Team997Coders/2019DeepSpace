package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utils;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  Joystick gamepad1;
  Joystick gamepad3;

  //main configuration buttons.
  public POVTrigger elevatorGoUp; // Y 2
  public POVTrigger elevatorGoDown; // X 2
  public JoystickButton ballIntake; // Right Bumper 2
  public JoystickButton ballOutake; // Left Bumper 2
  public JoystickButton driveSafe; // Right Bumper 1
  public JoystickButton flip;
  public JoystickButton elevatorToggle;

  public POVTrigger ArmForward; // POV RIGHT
  public POVTrigger ArmReverse; // POV LEFT

  private JoystickButton deployFrontLandingGear; // B 1
  private JoystickButton deployBackLandingGear; // Y 1
  private JoystickButton retractLandingGear; // Back 1
  private JoystickButton toggleHatch; // B 2
  private JoystickButton autoDriveToTarget; // A 2

  public OI() {
    // driver controls... game sticks control the motion of the robot
    //    left stick Y-axis is drive power
    //    right stick X-axis is drive direction
    gamepad1 = new Joystick(RobotMap.Buttons.GamePad1);
    gamepad3 = new Joystick(RobotMap.Buttons.GamePad3);

    //#region Gamepad1 Controls

    deployFrontLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonB);
    deployFrontLandingGear.whenPressed(new DeployFrontLandingGear());

    deployBackLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonA);
    deployBackLandingGear.whenPressed(new ToggleRearLandingGear());

    retractLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonBack);
    retractLandingGear.whenPressed(new RetractLandingGear());

    driveSafe = new JoystickButton(gamepad1, RobotMap.Buttons.buttonRightShoulder);
    driveSafe.whenPressed(new SafeMode());

    flip = new JoystickButton(gamepad1, RobotMap.Buttons.buttonX);
    flip.whenPressed(new FlipArmChain());

    // reserve for vision drive
    //vision = new JoystickButton(gamepad1, RobotMap.Buttons.buttonY);
    //vision.whenPressed(new DriveWithVision())

    //#endregion

    //#region Gamepad2 Controls
    elevatorGoDown = new POVTrigger(gamepad3, RobotMap.POVStates.DOWN);
    elevatorGoDown.whileHeld(new ElevatorDownity());

    elevatorGoUp = new POVTrigger(gamepad3, RobotMap.POVStates.UP);
    elevatorGoUp.whileHeld(new ElevatorUppity());

    ArmForward = new POVTrigger(gamepad3, RobotMap.POVStates.RIGHT);
    ArmForward.whileHeld(new MoveArm(-0.5));

    ArmReverse = new POVTrigger(gamepad3, RobotMap.POVStates.LEFT);
    ArmReverse.whileHeld(new MoveArm(0.5));

    ballIntake = new JoystickButton(gamepad3, RobotMap.Buttons.buttonLeftShoulder);
    ballIntake.whileHeld(new BallIntake());

    ballOutake = new JoystickButton(gamepad3, RobotMap.Buttons.buttonRightShoulder);
    ballOutake.whileHeld(new BallOuttake());

    autoDriveToTarget = new JoystickButton(gamepad3, RobotMap.Buttons.buttonA);
    toggleHatch = new JoystickButton(gamepad3, RobotMap.Buttons.buttonB);

    elevatorToggle = new JoystickButton(gamepad3, RobotMap.Buttons.buttonStart);
    elevatorToggle.whenPressed(new SetElevatorMode());
    //#endregion
  }

  //#region Controller Data

  public double getLeftYAxis() {
    return Utils.condition_gamepad_axis(0.05, -gamepad1.getRawAxis(RobotMap.Buttons.leftYAxis), -1, 1);
  }

  public double getLeftYAxis2() {
    return Utils.condition_gamepad_axis(0.05, -gamepad3.getRawAxis(RobotMap.Buttons.leftYAxis), -1, 1);
  }

  public double getRightXAxis() {
    return Utils.condition_gamepad_axis(0.05, gamepad1.getRawAxis(RobotMap.Buttons.rightXAxis), -1, 1);
  }

  public double getRightYAxis() {
    return Utils.condition_gamepad_axis(0.05, -gamepad1.getRawAxis(RobotMap.Buttons.rightYAxis), -1, 1);
  }

  //autoDriveToTarget.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontShipCargoHeight, RobotMap.Values.armFrontParallel));

  //#endregion

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
