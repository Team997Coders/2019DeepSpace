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

  private CurrentConfig currentConfig = CurrentConfig.Manual;

  //temporary elevator testing buttons.
  public JoystickButton elevatorGoUp; // Y 2
  public JoystickButton elevatorGoDown; // X 2
  public JoystickButton ballIntake; // Right Bumper 2
  public JoystickButton ballOutake; // Left Bumper 2
  public JoystickButton driveSafe; // Right Bumper 1
  public JoystickButton flip;

  public JoystickButton ArmForward; // Back 2
  public JoystickButton ArmReverse; // Start 2

  private JoystickButton deployLandingGear; // B 1
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

    deployLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonB);
    deployLandingGear.whenPressed(new DeployFrontLandingGear());

    deployBackLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonA);
    deployBackLandingGear.whenPressed(new ToggleRearLandingGear());

    retractLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonBack);
    retractLandingGear.whenPressed(new RetractLandingGear());

    driveSafe = new JoystickButton(gamepad1, RobotMap.Buttons.buttonRightShoulder);
    driveSafe.whenPressed(new SafeMode());

    flip = new JoystickButton(gamepad1, RobotMap.Buttons.buttonX);
    flip.whenPressed(new FlipArmChain());

    //flip = new JoystickButton(gamepad1, RobotMap.Buttons.buttonX);
    //flip.whenPressed(new SetArmPosition(RobotMap.Values.armFrontParallel, 10));

    
    //followLine = new JoystickButton(gamepad1, RobotMap.Buttons.buttonA);
    //followLine.whenPressed(new FollowLine(1000));

    //#endregion

    //#region Gamepad2 Controls

    ArmForward = new JoystickButton(gamepad3, RobotMap.Buttons.buttonStart);
    ArmForward.whileHeld(new MoveArm(-0.5));

    ArmReverse = new JoystickButton(gamepad3, RobotMap.Buttons.buttonBack);
    ArmReverse.whileHeld(new MoveArm(0.5));

    ballIntake = new JoystickButton(gamepad3, RobotMap.Buttons.buttonLeftShoulder);
    ballIntake.whileHeld(new BallIntake());

    ballOutake = new JoystickButton(gamepad3, RobotMap.Buttons.buttonRightShoulder);
    ballOutake.whileHeld(new BallOuttake());

    autoDriveToTarget = new JoystickButton(gamepad3, RobotMap.Buttons.buttonA);
    toggleHatch = new JoystickButton(gamepad3, RobotMap.Buttons.buttonB);
    elevatorGoDown = new JoystickButton(gamepad3, RobotMap.Buttons.buttonX);
    elevatorGoUp = new JoystickButton(gamepad3, RobotMap.Buttons.buttonY);

    //autoDriveToTarget.whenPressed(new SetElevatorHeight(RobotMap.ElevatorHeights.elevatorFrontTopHatchHeight, 100));
    //toggleHatch.whenPressed(new ToggleHatch());
    //elevatorGoDown.whileHeld(new ElevatorDownity());
    //elevatorGoUp.whileHeld(new ElevatorUppity());

    manualConfig();

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

  public int getPOV() { return gamepad3.getPOV(); }

  //#endregion

  //#region Configuration Methods

  /**
   * Purge the contorls
   * 
   * @deprecated Not need to remap controls. Just run one of the other configurations
   */
  public void purgeConfig() {
    autoDriveToTarget.close();
    toggleHatch.close();
    elevatorGoDown.close();
    elevatorGoUp.close();
  }

  public void manualConfig() {

    SmartDashboard.putString("Controller Config", "Manual");

    autoDriveToTarget.whenPressed(new AutoDoNothing());
    toggleHatch.whenPressed(new ToggleHatch());
    elevatorGoUp.whileHeld(new ElevatorUppity());
    elevatorGoDown.whileHeld(new ElevatorDownity());

    currentConfig = CurrentConfig.Manual;
  }

  public void cargoFrontConfig() {

    SmartDashboard.putString("Controller Config", "Cargo Front");

    //autoDriveToTarget = new JoystickButton(gamepad3, RobotMap.Buttons.buttonA);
    autoDriveToTarget.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontShipCargoHeight, RobotMap.Values.armFrontParallel));

    //toggleHatch = new JoystickButton(gamepad3, RobotMap.Buttons.buttonB);
    toggleHatch.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontTopCargoHeight, RobotMap.Values.armFrontParallel));

    //elevatorGoUp = new JoystickButton(gamepad3, RobotMap.Buttons.buttonX);
    elevatorGoUp.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontBottomCargoHeight, RobotMap.Values.armFrontParallel));
    //elevatorGoUp.whenInactive(new AutoDoNothing());

    //elevatorGoDown = new JoystickButton(gamepad3, RobotMap.Buttons.buttonY);
    elevatorGoDown.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontMiddleCargoHeight, RobotMap.Values.armFrontParallel));
    //elevatorGoDown.whenInactive(new AutoDoNothing());

    currentConfig = CurrentConfig.CargoFront;
  }

  public void cargoBackConfig() {

    SmartDashboard.putString("Controller Config", "Cargo Back");

    //autoDriveToTarget = new JoystickButton(gamepad3, RobotMap.Buttons.buttonA);
    autoDriveToTarget.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackShipCargoHeight, RobotMap.Values.armBackParallel));

    //toggleHatch = new JoystickButton(gamepad3, RobotMap.Buttons.buttonB);
    toggleHatch.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackTopCargoHeight, RobotMap.Values.armBackParallel));

    //elevatorGoUp = new JoystickButton(gamepad3, RobotMap.Buttons.buttonX);
    elevatorGoUp.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackBottomCargoHeight, RobotMap.Values.armBackParallel));
    //elevatorGoUp.whenInactive(new AutoDoNothing());

    //elevatorGoDown = new JoystickButton(gamepad3, RobotMap.Buttons.buttonY);
    elevatorGoDown.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackMiddleCargoHeight, RobotMap.Values.armBackParallel));
    //elevatorGoDown.whenInactive(new AutoDoNothing());

    currentConfig = CurrentConfig.CargoBack;
  }

  public void hatchFrontConfig() {

    SmartDashboard.putString("Controller Config", "Hatch Front");

    //autoDriveToTarget = new JoystickButton(gamepad3, RobotMap.Buttons.buttonA);
    autoDriveToTarget.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontShipHatchHeight, RobotMap.Values.armFrontParallel));

    //toggleHatch = new JoystickButton(gamepad3, RobotMap.Buttons.buttonB);
    toggleHatch.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontTopHatchHeight, RobotMap.Values.armFrontParallel));

    //elevatorGoUp = new JoystickButton(gamepad3, RobotMap.Buttons.buttonX);
    elevatorGoUp.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontBottomHatchHeight, RobotMap.Values.armFrontParallel));
    //elevatorGoUp.whenInactive(new AutoDoNothing());
    
    //elevatorGoDown = new JoystickButton(gamepad3, RobotMap.Buttons.buttonY);
    elevatorGoDown.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontMiddleHatchHeight, RobotMap.Values.armFrontParallel));
    //elevatorGoDown.whenInactive(new AutoDoNothing());

    currentConfig = CurrentConfig.HatchFront;
  }

  public void hatchBackConfig() {

    SmartDashboard.putString("Controller Config", "Hatch Back");

    //autoDriveToTarget = new JoystickButton(gamepad3, RobotMap.Buttons.buttonA);
    autoDriveToTarget.whenPressed(new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackShipHatchHeight, RobotMap.Values.armBackParallel));

    //toggleHatch = new JoystickButton(gamepad3, RobotMap.Buttons.buttonB);
    toggleHatch.whenPressed(new AutoDoNothing());

    //elevatorGoUp = new JoystickButton(gamepad3, RobotMap.Buttons.buttonX);
    elevatorGoUp.whenPressed(new AutoDoNothing());
    //elevatorGoUp.whenInactive(new AutoDoNothing());

    //elevatorGoDown = new JoystickButton(gamepad3, RobotMap.Buttons.buttonY);
    elevatorGoDown.whenPressed(new AutoDoNothing());
    //elevatorGoDown.whenInactive(new AutoDoNothing());

    currentConfig = CurrentConfig.HatchBack;
  }

  //#endregion

  //#region Configuration Processors

  public void reconfigureButtons() {
    int pov = gamepad3.getPOV(0);

    if (currentConfig.isSame(pov)) {
      return;
    }

    //purgeConfig();

    switch (pov) {
      case -1:
        manualConfig();
        return;
      case 0: // Cargo Front
        cargoFrontConfig();
        return;
      case 90: // Hatch Front
        hatchFrontConfig();
        return;
      case 180: // Hatch Back
        hatchBackConfig();
        return;
      case 270: // Cargo Back
        cargoBackConfig();
        return;
    }
  }

  private enum CurrentConfig {
    Manual(-1), CargoFront(0), CargoBack(270),
    HatchFront(90), HatchBack(180);

    public int value;
    private CurrentConfig(int value) {
      this.value = value;
    }

    public boolean isSame(int val) {
      return val == value;
    }
  }

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
