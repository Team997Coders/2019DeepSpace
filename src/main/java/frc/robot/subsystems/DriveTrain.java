package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ArcadeDrive;
import frc.robot.misc.GearBox;
import frc.robot.misc.RoboMisc;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.kauailabs.navx.frc.AHRS;

/**
 * This is our drivetrain. This year I split up the configuration for the
 * drivetrain into another class so this one isn't comically long. We have a few
 * different methods such as normal set volts and stop volts. Along with
 * SetVoltsDecel and SetPosition. NOTE: Grayson hates deceleration code but he
 * reeeealllly needs it. Also not sure if the decel code works so definetly
 * check that first. Make sure the dampRate doesn't need adjusting.
 */
public class DriveTrain extends Subsystem {

  public boolean decell = false;

  // Decell Data
  private double dampRate = 1.66; // Power / Seconds ^2
  private double prevL = 0, prevR = 0;

  // GearBox class stores information for the motor controllers for one gearbox
  private GearBox leftBox, rightBox;
  private TalonSRX leftTalon, rightTalon;
  private VictorSPX leftVictor1, leftVictor2, rightVictor1, rightVictor2;
  private AHRS gyro;

  private NetworkTable table;

  public DriveTrain() {
    System.out.println("Starting Drivetrain...");

    // This uses the RoboMisc function standTalonSRXSetup(int, int, int, boolean) to
    // initialize a Talon and 2 slave victors
    leftBox = RoboMisc.standTalonSRXSetup(RobotMap.Ports.leftTalon, RobotMap.Ports.leftVictor1,
        RobotMap.Ports.leftVictor2, false);
    rightBox = RoboMisc.standTalonSRXSetup(RobotMap.Ports.rightTalon, RobotMap.Ports.rightVictor1,
        RobotMap.Ports.rightVictor2, true);

    // Grab the objects created by the RoboMisc function and store them in this class
    leftTalon = leftBox.talon;
    rightTalon = rightBox.talon;
    leftVictor1 = leftBox.victor1;
    leftVictor2 = leftBox.victor2;
    rightVictor1 = rightBox.victor1;
    rightVictor2 = rightBox.victor2;
    
    try {
			gyro = new AHRS(RobotMap.Ports.AHRS);
			System.out.println("ahrs is coolio!");
      gyro.reset();
		} catch (RuntimeException e) {
			System.out.println("DT- Im been a bad Gyro daddy uwu");
		}

    resetEncoders();
    setCoast();

    table = NetworkTableInstance.create().getTable("SmartDashboard");
  }

  /**
   * Set a percent input to the left and right talons
   * 
   * @param left  Percentage input for the left talon.
   * @param right Percentage input for the right talon.
   */
  public void setVolts(double left, double right) {
    leftTalon.set(ControlMode.PercentOutput, left);
    rightTalon.set(ControlMode.PercentOutput, right);
  }


  public double getGyroAngle(){
    if (gyro != null){
    return gyro.getAngle();
  } else{
    return 0;
  }
}
  /**
   * Sets the percentage input for the left and right talon to zero
   */
  public void stopVolts() {
    // Set Motor Volts to 0
    // System.out.println("Stop Volts Called");
    leftTalon.set(ControlMode.PercentOutput, 0);
    rightTalon.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Sets the percentage input for the left and right talon but with a
   * deceleration dampener.
   * 
   * @param left  Percentage input for the left talon
   * @param right Percentage input for the right talon
   */
  public void setVoltsDecel(double left, double right) {
    double L = left;
    double R = right;

    double deltaTime = Robot.getDeltaTime();

    if (Math.abs(left) > Math.abs(prevL) + (dampRate * deltaTime)) {
      L = prevL + ((prevL / Math.abs(prevL)) * (dampRate * deltaTime));
    }

    if (Math.abs(right) > Math.abs(prevR) + (dampRate * deltaTime)) {
      R = prevR + ((prevR / Math.abs(prevR)) * (dampRate * deltaTime));
    }

    setVolts(L, R);
  }

  /**
   * Sets the desired tick position for the left and right talon
   * 
   * @param left  Desired tick position for the left talon
   * @param right Desired tick position for the right talon
   */
  public void setPosition(double left, double right) {
    leftTalon.set(ControlMode.Position, left);
    rightTalon.set(ControlMode.Position, right);
  }

  /**
   * Gets the left talon's position output
   * 
   * @return Current tick position of the left talon's encoder
   */
  public double leftEncoderTicks() {
    leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return leftTalon.getSelectedSensorPosition(0);
  }

  /**
   * Gets the right talon's position output
   * 
   * @return Current tick position of the right talon's encoder
   */
  public double rightEncoderTicks() {
    rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return rightTalon.getSelectedSensorPosition(0);
  }

  /**
   * Gets the left talon's velocity output
   * 
   * @return Current tick velocity of the left talon's encoder
   */
  public double leftEncoderVelocity() {
    leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return leftTalon.getSelectedSensorVelocity(0);
  }

  /**
   * Gets the right talon's velocity output
   * 
   * @return Current tick velocity of the right talon's encoder
   */
  public double rightEncoderVelocity() {
    rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return rightTalon.getSelectedSensorVelocity(0);
  }

  /**
   * Resets the encoders for both talons to 0
   */
  public void resetEncoders() {
    leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
    leftTalon.setSelectedSensorPosition(0, 0, 10);
    rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
    rightTalon.setSelectedSensorPosition(0, 0, 10);
  }

  /**
   * Gets PID constants from the SmartDashboard and then uses setPIDValues(double,
   * double, double)
   */
  public void setPIDValues() {
    double p = SmartDashboard.getNumber("P", 1);
    double i = SmartDashboard.getNumber("I", 0);
    double d = SmartDashboard.getNumber("D", 0);
    setPIDValues(p, i, d);
  }

  /**
   * Sets the parameter PID constants to the talons
   * 
   * @param p Proportional PID constant
   * @param i Integral PID constant
   * @param d derivative PID constant
   */
  public void setPIDValues(double p, double i, double d) {
    leftTalon.config_kP(0, p, 0);
    rightTalon.config_kP(0, p, 0);
    leftTalon.config_kP(0, i, 0);
    rightTalon.config_kP(0, i, 0);
    leftTalon.config_kP(0, d, 0);
    rightTalon.config_kP(0, d, 0);
  }

  /**
   * Sets the talons and their follow victors into BrakeMode
   */
  public void setBrake() {
    leftTalon.setNeutralMode(NeutralMode.Brake);
    rightTalon.setNeutralMode(NeutralMode.Brake);

    leftVictor1.setNeutralMode(NeutralMode.Brake);
    rightVictor1.setNeutralMode(NeutralMode.Brake);
    leftVictor2.setNeutralMode(NeutralMode.Brake);
    rightVictor2.setNeutralMode(NeutralMode.Brake);
  }

  /**
   * Sets the talons and their follow victors into CoastMode
   */
  public void setCoast() {
    leftTalon.setNeutralMode(NeutralMode.Coast);
    rightTalon.setNeutralMode(NeutralMode.Coast);

    leftVictor1.setNeutralMode(NeutralMode.Coast);
    rightVictor1.setNeutralMode(NeutralMode.Coast);
    leftVictor2.setNeutralMode(NeutralMode.Coast);
    rightVictor2.setNeutralMode(NeutralMode.Coast);
  }

  /**
   * Updates the SmartDashboard with subsystem data
   */
  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Left Ticks DriveTrain", leftEncoderTicks());
    SmartDashboard.putNumber("Right Ticks DriveTrain", rightEncoderTicks());
    SmartDashboard.putNumber("Left Velocity Drivetrain", leftEncoderVelocity());
    SmartDashboard.putNumber("Right Velocity Drivetrain", rightEncoderVelocity());
    SmartDashboard.putNumber("Gyro angle", getGyroAngle());
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
    // setDefaultCommand(new TankDrive());
  }
}
