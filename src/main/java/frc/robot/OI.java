package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.commands.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  Joystick gamepad1 = new Joystick(0);
  Joystick gamepad2 = new Joystick(1);

  //temporary elevator testing buttons.
  public JoystickButton elevatorGoUp;
  public JoystickButton elevatorGoDown;
  public JoystickButton followLine;
  public JoystickButton ballIntake;
  public JoystickButton ballOutake;

  public JoystickButton ArmForward;
  public JoystickButton ArmReverse;

  private JoystickButton deployLandingGear;
  private JoystickButton retractLandingGear;
  //private JoystickButton followLine;
  private JoystickButton toggleHatch;

  private JoystickButton visionButtonA;
  private JoystickButton visionButtonB;
  private JoystickButton visionButtonX;
  private JoystickButton visionButtonY;
  private JoystickButton visionButtonLeftShoulder;
  private JoystickButton visionButtonRightShoulder;
  private JoystickButton visionButtonLeftTrigger;
  private JoystickButton visionButtonRightTrigger;
  private JoystickButton visionButtonLeftThumbstick;
  private JoystickButton visionButtonRightThumbstick;

  private JoystickButton flipSystemOrientation;

  private JoystickButton flipDriveTrainOrientation;

  public OI() {
    gamepad1 = new Joystick(RobotMap.Buttons.GamePad1);
    gamepad2 = new Joystick(RobotMap.Buttons.GamePad2);

    deployLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonB);
    deployLandingGear.whenPressed(new DeployLandingGear());

    retractLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonBack);
    retractLandingGear.whenPressed(new RetractLandingGear());

    flipSystemOrientation = new JoystickButton(gamepad1, RobotMap.Buttons.buttonX);
    flipSystemOrientation.whenPressed(new FlipSystemOrientation());

    flipDriveTrainOrientation = new JoystickButton(gamepad1, RobotMap.Buttons.buttonY);
    flipDriveTrainOrientation.whenPressed(new FlipDriveTrainOrientation(Robot.scoringSideReversed));

    followLine = new JoystickButton(gamepad1, RobotMap.Buttons.buttonA);
      followLine.whenPressed(new FollowLine(1000));

    toggleHatch = new JoystickButton(gamepad2, RobotMap.Buttons.buttonB);
    toggleHatch.whenPressed(new ToggleHatch());

    ArmReverse = new JoystickButton(gamepad2, RobotMap.Buttons.buttonBack);
    ArmReverse.whileHeld(new MoveArm(-0.35));
    //ArmReverse.whenInactive(new LockArm());

    ArmForward = new JoystickButton(gamepad2, RobotMap.Buttons.buttonStart);
    ArmForward.whileHeld(new MoveArm(0.35));
    //ArmForward.whenInactive(new LockArm());

    // Adds LockArm to the scheduler so I locks immediately.
    //Scheduler.getInstance().add(new LockArm());

    //followLine = new JoystickButton(gamepad1, 1);
    //followLine.whileHeld(new BackingUpWhenLineFollowIsComplete());

    //toggleHatch = new JoystickButton(gamepad1, RobotMap.Buttons.buttonX);
    //toggleHatch.whenPressed(new ToggleHatchHolder());

      elevatorGoUp = new JoystickButton(gamepad2, RobotMap.Buttons.buttonX);
      elevatorGoUp.whileHeld(new ElevatorUppity());
      elevatorGoUp.whenInactive(new LockElevator());

      elevatorGoDown = new JoystickButton(gamepad2, RobotMap.Buttons.buttonY);
      elevatorGoDown.whileHeld(new ElevatorDownity());
      elevatorGoDown.whenInactive(new LockElevator());

      ballIntake = new JoystickButton(gamepad2, RobotMap.Buttons.buttonLeftShoulder);
      ballIntake.whileHeld(new BallIntake());

      ballOutake = new JoystickButton(gamepad2, RobotMap.Buttons.buttonRightShoulder);
      ballOutake.whileHeld(new BallOuttake());

    //visionButtonA = new JoystickButton(gamepad2, RobotMap.Buttons.buttonA);
    //visionButtonA.whenPressed(new VisionPressA());

    //visionButtonB = new JoystickButton(gamepad2, RobotMap.Buttons.buttonB);
    //visionButtonB.whenPressed(new VisionPressB());

    //visionButtonX = new JoystickButton(gamepad2, RobotMap.Buttons.buttonX);
    //visionButtonX.whenPressed(new VisionPressX());

    //visionButtonY = new JoystickButton(gamepad2, RobotMap.Buttons.buttonY);
    //visionButtonY.whenPressed(new VisionPressY());

    visionButtonLeftThumbstick = new JoystickButton(gamepad2, RobotMap.Buttons.buttonLeftThumbstick);
    visionButtonLeftThumbstick.whenPressed(new VisionPressLeftThumbstick());

    visionButtonRightThumbstick = new JoystickButton(gamepad2, RobotMap.Buttons.buttonRightThumbstick);
    visionButtonRightThumbstick.whenPressed(new VisionPressRightThumbstick());

    //visionButtonLeftShoulder = new JoystickButton(gamepad2, RobotMap.Buttons.buttonLeftShoulder);
    //visionButtonLeftShoulder.whenPressed(new VisionPressLeftShoulder());

    //visionButtonRightShoulder = new JoystickButton(gamepad2, RobotMap.Buttons.buttonRightShoulder);
    //visionButtonRightShoulder.whenPressed(new VisionPressRightShoulder());

    visionButtonLeftTrigger = new JoystickButton(gamepad2, RobotMap.Buttons.buttonLeftTrigger);
    visionButtonLeftTrigger.whenPressed(new VisionPressLeftTrigger());

    visionButtonRightTrigger = new JoystickButton(gamepad2, RobotMap.Buttons.buttonRightTrigger);
    visionButtonRightTrigger.whenPressed(new VisionPressRightTrigger());
  }

  public double getLeftYAxis() {
    return bing(0.05, -gamepad1.getRawAxis(RobotMap.Buttons.leftYAxis), -1, 1);
  }

  public double getLeftYAxis2() {
    return bing(0.05, -gamepad2.getRawAxis(RobotMap.Buttons.leftYAxis), -1, 1);
  }

  public double getRightXAxis() {
    return bing(0.05, gamepad1.getRawAxis(RobotMap.Buttons.rightXAxis), -1, 1);
  }

  public double getRightYAxis() {
    return bing(0.05, -gamepad1.getRawAxis(RobotMap.Buttons.rightYAxis), -1, 1);
  }


  public int getVisionLeftYAxis() {
    // TODO: Are these ports the same across joysticks?
    // I also do not think this should be negated for pan/tilt servos.
    return (int)Math.round(bing(0.05, gamepad2.getRawAxis(RobotMap.Buttons.leftYAxis), -1, 1) * 100);
  }

  public int getVisionLeftXAxis() {
    // TODO: Are these ports the same across joysticks?
    return (int)Math.round(bing(0.05, gamepad2.getRawAxis(RobotMap.Buttons.rightXAxis), -1, 1) * 100);
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
   * I really wish programmers would name methods descriptively so that I do
   * not have to waste my time figuring out what things like "bing" and "stuff" do!
   * I am guessing that this does good "stuff" to my joystick. CCB.
   * 
   * @param dead
   * @param val
   * @param min
   * @param max
   * @return    I wish I knew
   */
  public double bing(double dead, double val, double min, double max) {
    return clamp(min, max, deadBand(val, dead));
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
