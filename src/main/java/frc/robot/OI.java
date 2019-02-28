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
  Joystick gamepad3;


  //temporary elevator testing buttons.
  public JoystickButton elevatorGoUp;
  public JoystickButton elevatorGoDown;
  public JoystickButton ballIntake;
  public JoystickButton ballOutake;
  public JoystickButton driveSafe;

  public JoystickButton ArmForward;
  public JoystickButton ArmReverse;

  private JoystickTrigger deployLandingGear;
  private JoystickButton retractLandingGear;
  private JoystickButton toggleHatch;
  private JoystickTrigger followLine;
  private JoystickButton autoDriveToTarget;

  public OI() {
    // driver controls... game sticks control the motion of the robot
    //    left stick Y-axis is drive power
    //    right stick X-axis is drive direction
    gamepad1 = new Joystick(RobotMap.Buttons.GamePad1);

    ballOutake = new JoystickButton(gamepad1, RobotMap.Buttons.buttonA);
    ballOutake.whenPressed(new BallOuttake());

    // buttonB is spare

    ballIntake = new JoystickButton(gamepad1, RobotMap.Buttons.buttonX);
    ballIntake.whenPressed(new BallIntake());

    toggleHatch = new JoystickButton(gamepad1, RobotMap.Buttons.buttonY);
    toggleHatch.whenPressed(new ToggleHatch());

    deployLandingGear = new JoystickTrigger(gamepad1, RobotMap.Buttons.buttonLeftTrigger);
    deployLandingGear.whenPressed(new DeployLandingGear());

    driveSafe = new JoystickButton(gamepad1, RobotMap.Buttons.buttonRightShoulder);
    driveSafe.whenPressed(new SafeMode());  // TODO: implement safe mode
    
    followLine = new JoystickTrigger(gamepad1, RobotMap.Buttons.buttonRightTrigger);
    followLine.whenPressed(new FollowLineAndDeliverHatch());

    // buttonStart is spare

    retractLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonBack);
    retractLandingGear.whenPressed(new RetractLandingGear());

    /*
     * aux/manual controls for testing
     */
    gamepad3 = new Joystick(RobotMap.Buttons.GamePad3);

    ArmReverse = new JoystickButton(gamepad3, RobotMap.Buttons.buttonBack);
    ArmReverse.whileHeld(new MoveArm(-0.5));
    ArmReverse.whenInactive(new LockArm());

    ArmForward = new JoystickButton(gamepad3, RobotMap.Buttons.buttonStart);
    ArmForward.whileHeld(new MoveArm(0.5));
    ArmForward.whenInactive(new LockArm());

    /* Adding Setpoint buttons for testing */
    elevatorGoUp = new JoystickButton(gamepad3, RobotMap.Buttons.buttonX);
    elevatorGoUp.whileHeld(new ElevatorUppity());
    elevatorGoUp.whenInactive(new LockElevator());

    elevatorGoDown = new JoystickButton(gamepad3, RobotMap.Buttons.buttonY);
    elevatorGoDown.whileHeld(new ElevatorDownity());
    elevatorGoDown.whenInactive(new LockElevator());      
    
    ballIntake = new JoystickButton(gamepad3, RobotMap.Buttons.buttonLeftShoulder);
    ballIntake.whileHeld(new BallIntake());

    ballOutake = new JoystickButton(gamepad3, RobotMap.Buttons.buttonRightShoulder);
    ballOutake.whileHeld(new BallOuttake());

    elevatorGoDown = new JoystickButton(gamepad3, RobotMap.Buttons.buttonY);
    elevatorGoDown.whileHeld(new ElevatorDownity());
    elevatorGoDown.whenInactive(new LockElevator());
    
    elevatorGoUp = new JoystickButton(gamepad3, RobotMap.Buttons.buttonLeftShoulder);
    elevatorGoUp.whenPressed(new SetElevatorHeight(RobotMap.ElevatorHeights.elevatorFrontMiddleCargoHeight, 10));

    elevatorGoUp = new JoystickButton(gamepad3, RobotMap.Buttons.buttonRightShoulder);
    elevatorGoUp.whenPressed(new SetElevatorHeight(RobotMap.ElevatorHeights.elevatorFrontBottomHatchHeight, 10));

    autoDriveToTarget = new JoystickButton(gamepad3, RobotMap.Buttons.buttonA);
    autoDriveToTarget.whenPressed(new AutoDriveToTarget());
  }

  public double getLeftYAxis() {
    return condition_gamepad_axis(0.05, -gamepad1.getRawAxis(RobotMap.Buttons.leftYAxis), -1, 1);
  }

  public double getLeftYAxis2() {
    return condition_gamepad_axis(0.05, -gamepad3.getRawAxis(RobotMap.Buttons.leftYAxis), -1, 1);
  }

  public double getRightXAxis() {
    return condition_gamepad_axis(0.05, gamepad1.getRawAxis(RobotMap.Buttons.rightXAxis), -1, 1);
  }

  public double getRightYAxis() {
    return condition_gamepad_axis(0.05, -gamepad1.getRawAxis(RobotMap.Buttons.rightYAxis), -1, 1);
  }

  /**
   * Make the gamepad axis less sensitive to changes near their null/zero point.
   * 
   * @param value raw value from the gamepad axis
   * @param dead  value for the deadband size 
   * @return
   */
  public double deadBand(double value, double dead) {
    if (Math.abs(value) < dead) {
      return 0;
    } else {
      return value;
    }
  }

  /**
   * Clamp/Limit the value to only be within two limits
   * @param min lower limit
   * @param max upper limit
   * @param val value to check
   * @return
   */
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
   * Combine both a joystick limit and a clamp within standard limits.
   * 
   * I really wish programmers would name methods descriptively so that I do
   * not have to waste my time figuring out what things like "bing" and "stuff" do!
   * I am guessing that this does good "stuff" to my joystick. CCB.
   * 
   * @param dead deadband limit, no output within this limit. Normally 0.05
   * @param val raw value from axis
   * @param min lower limit for axis. Normally -1
   * @param max upper limit on axis. Normally +1
   * @return    conditioned value from axis (limited -1 to +1, with a )
   */
  public double condition_gamepad_axis(double dead, double val, double min, double max) {
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
