package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.commands.vision.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  Joystick gamepad1;
  Joystick gamepad3;

  // temporary elevator testing buttons.
  public JoystickButton elevatorGoUp; // Y 2
  public JoystickButton elevatorGoDown; // X 2
  public JoystickButton ballIntake; // Right Bumper 2
  public JoystickButton ballOutake; // Left Bumper 2
  public JoystickButton driveSafe; // Right Bumper 1
  public JoystickButton flip;
  //public JoystickButton elevatorSetPositionMid; // Left Bumper 1

  public JoystickButton ArmForward; // Back 2
  public JoystickButton ArmReverse; // Start 2

  private JoystickButton deployLandingGear; // B 1
  private JoystickButton deployBackLandingGear; // A 1
  private JoystickButton retractLandingGear; // Back 1
  private JoystickButton toggleHatch; // B 2
  private JoystickButton autoDriveToTarget; // A 2
  private JoystickButton limelightDrive;

  public int secretModeCounterA = 0, secretModeCounterB = 0;

  public CurrentConfig currentConfig;

  public OI() {
    // driver controls... game sticks control the motion of the robot
    // left stick Y-axis is drive power
    // right stick X-axis is drive direction
    gamepad1 = new Joystick(RobotMap.Buttons.GamePad1);
    gamepad3 = new Joystick(RobotMap.Buttons.GamePad3);

    // #region Gamepad1 Controls

    deployLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonB);
    deployLandingGear.whenPressed(new ToggleFrontLandingGear());

    deployBackLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonY);
    deployBackLandingGear.whenPressed(new ToggleRearLandingGear());

    retractLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonBack);
    retractLandingGear.whenPressed(new RetractLandingGear());

    flip = new JoystickButton(gamepad1, RobotMap.Buttons.buttonX);
    flip.whenPressed(new FlipArmChain());

    autoDriveToTarget = new JoystickButton(gamepad1, RobotMap.Buttons.buttonA);

    limelightDrive = new JoystickButton(gamepad1, RobotMap.Buttons.buttonStart);
    limelightDrive.whenPressed(new ApproachTarget(0.2, 19));
    
    //elevatorSetPositionMid = new JoystickButton(gamepad1, RobotMap.Buttons.buttonLeftTrigger);
    //elevatorSetPositionMid.whenPressed(new SetArmPosition(12, 1));
    //flip = new JoystickButton(gamepad1, RobotMap.Buttons.buttonX);
    //flip.whenPressed(new SetArmPosition(RobotMap.Values.armFrontParallel, 10));

    // #endregion

    // #region Gamepad2 Controls
    elevatorGoUp = new JoystickButton(gamepad3, RobotMap.Buttons.buttonY);
    elevatorGoUp.whileHeld(new ElevatorUppity());

    elevatorGoDown = new JoystickButton(gamepad3, RobotMap.Buttons.buttonX);
    elevatorGoDown.whileHeld(new ElevatorDownity());

    ArmForward = new JoystickButton(gamepad3, RobotMap.Buttons.buttonStart);
    ArmForward.whileHeld(new MoveArm(-0.2));

    ArmReverse = new JoystickButton(gamepad3, RobotMap.Buttons.buttonBack);
    ArmReverse.whileHeld(new MoveArm(0.2));

    ballIntake = new JoystickButton(gamepad3, RobotMap.Buttons.buttonRightShoulder);
    ballIntake.whileHeld(new BallIntake());

    ballOutake = new JoystickButton(gamepad3, RobotMap.Buttons.buttonLeftShoulder);
    ballOutake.whileHeld(new BallOuttake());

    toggleHatch = new JoystickButton(gamepad3, RobotMap.Buttons.buttonB);
    toggleHatch.whenPressed(new ToggleHatch());

    manualConfig();

    // #endregion
  }

  // #region Controller Data

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

  public int getPOV() {
    return gamepad3.getPOV();
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
   * 
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
   * @param dead deadband limit, no output within this limit. Normally 0.05
   * @param val  raw value from axis
   * @param min  lower limit for axis. Normally -1
   * @param max  upper limit on axis. Normally +1
   * @return conditioned value from axis (limited -1 to +1, with a )
   */
  public double condition_gamepad_axis(double dead, double val, double min, double max) {
    return clamp(min, max, deadBand(val, dead));
  }

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

    autoDriveToTarget.whenPressed(new ToggleLight());
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
}
