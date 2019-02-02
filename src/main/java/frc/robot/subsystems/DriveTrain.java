package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ArcadeDrive;
import frc.robot.misc.GearBox;
import frc.robot.RoboMisc;

/**
 * This is ourr drivetrain. This year I split up the configuration for the drivetrain
 * into another class so this one isn't comically long. We have a few different methods
 * such as normal set volts and stop volts. Along with SetVoltsDecel and SetPosition.
 * NOTE: Grayson hates deceleration code but he reeeealllly needs it. Also not sure
 * if the decel code works so definetly check that first. Make sure the dampRate doesn't need adjusting.
 */
public class DriveTrain extends Subsystem {

  public boolean decell = false;

  // Decell Data
  private double dampRate = 0.01;
  private double prevL = 0, prevR = 0;

  // GearBox class stores information for the motor controllers for one gearbox
  private GearBox leftBox, rightBox;
  private TalonSRX leftTalon, rightTalon;
  private VictorSPX leftVictor1, leftVictor2, rightVictor1, rightVictor2;

  //Test

  public DriveTrain() {
    leftTalon = new TalonSRX(RobotMap.Ports.leftTalon);
    rightTalon = new TalonSRX(RobotMap.Ports.rightTalon);
    leftVictor1 = new VictorSPX(RobotMap.Ports.leftVictor1);
    leftVictor2 = new VictorSPX(RobotMap.Ports.leftVictor2);
    rightVictor1 = new VictorSPX(RobotMap.Ports.rightVictor1);
    rightVictor2 = new VictorSPX(RobotMap.Ports.rightVictor2);

    this.setUp();
  }


  private void setUp(){
    leftVictor1.follow(leftTalon);
    leftVictor2.follow(leftTalon);
    rightVictor1.follow(rightTalon);
    rightVictor2.follow(rightTalon);

    leftTalon.setInverted(false);
    rightTalon.setInverted(true);

    leftVictor1.setInverted(false);
    leftVictor2.setInverted(false);
    rightVictor1.setInverted(true);
    rightVictor2.setInverted(true);

    leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		leftTalon.setSensorPhase(true);
		rightTalon.setSensorPhase(true);
		
		leftTalon.setNeutralMode(NeutralMode.Coast);
		rightTalon.setNeutralMode(NeutralMode.Coast);
		
		/* set the peak, nominal outputs */
		leftTalon.configNominalOutputForward(0, 10);
		leftTalon.configNominalOutputReverse(0, 10);
		//leftTalon.configPeakOutputForward(1, 10);	//Use for PB
		//leftTalon.configPeakOutputReverse(-1, 10); //Use for PB
		leftTalon.configPeakOutputForward(0.4, 10);	//Use for extrasensitive CB
		leftTalon.configPeakOutputReverse(-0.4, 10); //Use for extrasensitive CB
		
		leftTalon.configPeakCurrentLimit(40, 10);
		leftTalon.configPeakCurrentDuration(100, 10);
		leftTalon.configContinuousCurrentLimit(30, 10);
    leftTalon.enableCurrentLimit(true);
    
		rightTalon.configNominalOutputForward(0, 10);
		rightTalon.configNominalOutputReverse(0, 10);
		//rightTalon.configPeakOutputForward(1, 10); //Use for PB
		//rightTalon.configPeakOutputReverse(-1, 10); //Use for PB
		rightTalon.configPeakOutputForward(0.4, 10);  //Use for extrasensitive CB
		rightTalon.configPeakOutputReverse(-0.4, 10); //Use for extrasensitive CB
		
		rightTalon.configPeakCurrentLimit(40, 10);
    rightTalon.configPeakCurrentDuration(100, 10);
		rightTalon.configContinuousCurrentLimit(30, 10);
    rightTalon.enableCurrentLimit(true);
    
		leftTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 40, 10);
		//leftTalon.configOpenloopRamp(0.25, 10);
		rightTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 40, 10);
		//rightTalon.configOpenloopRamp(0.25, 10);
		
		/* set closed loop gains in slot0 */
		leftTalon.config_kF(0, 0.1097, 10);
    leftTalon.config_kP(0, 0.113333, 10);
    //leftTalon.config_kP(0, SmartDashboard.getNumber("P", 0), 10);
    leftTalon.config_kI(0, 0, 10);
    //leftTalon.config_kI(0, SmartDashboard.getNumber("I", 0), 10);
    leftTalon.config_kD(0, 0, 10);		
    //leftTalon.config_kD(0, SmartDashboard.getNumber("D", 0), 10);

		rightTalon.config_kF(0, 0.1097, 10);
    rightTalon.config_kP(0, 0.113333, 10);
    //rightTalon.config_kP(0, SmartDashboard.getNumber("P", 0), 10);
    rightTalon.config_kI(0, 0, 10);
    //rightTalon.config_kI(0, SmartDashboard.getNumber("I", 0), 10);
    rightTalon.config_kD(0, 0, 10);	
    //rightTalon.config_kD(0, SmartDashboard.getNumber("D", 0), 10);
  }

  // Apply left and right as percentage voltage
  public void setVolts(double left, double right) {
    leftTalon.set(ControlMode.PercentOutput, left);
    rightTalon.set(ControlMode.PercentOutput, right);
  }

  // Set the percentage of volts to 0
  public void stopVolts() {
    // Set Motor Volts to 0
    //System.out.println("Stop Volts Called");
    leftTalon.set(ControlMode.PercentOutput, 0);
    rightTalon.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
    //setDefaultCommand(new TankDrive());
  }

  /**
   * Turn right (without having to remember which wheel to slow down ;-)
   * Use the gain to control how fast the fastest wheel will go and offset
   * to control how fast the slower wheel will go in relation to the faster wheel.
   * 
   * @param gain    Number between 0 and 1 representing the factor of full power
   * @param offset  Number between 0 and 1 representing the factor applied to gain for slower wheel
   */
  public void turnRight(double gain, double offset) {
    leftTalon.set(ControlMode.PercentOutput, gain);
    rightTalon.set(ControlMode.PercentOutput, gain * offset);
  }

  /**
   * Turn right (without having to remember which wheel to slow down ;-)
   * Use the gain to control how fast the fastest wheel will go and offset
   * to control how fast the slower wheel will go in relation to the faster wheel.
   * 
   * @param gain    Number between 0 and 1 representing the factor of full power
   * @param offset  Number between 0 and 1 representing the factor applied to gain for slower wheel
   */
  public void turnLeft(double gain, double offset) {
    leftTalon.set(ControlMode.PercentOutput, gain * offset);
    rightTalon.set(ControlMode.PercentOutput, gain);
  }

  /**
   * Well, it's pretty obvious, no?
   * 
   * @param gain  Number between 0 and 1 representing the factor of full power
   */
  public void driveStraight(double gain) {
    setVolts(gain, gain);
  }

  /**
   * Sets the percentage input for the left and right talon
   * but with a deceleration dampener.
   * 
   * @param left Percentage input for the left talon
   * @param right Percentage input for the right talon
   */
  public void setVoltsDecel(double left, double right) {
    double L = left;
    double R = right;

    if (Math.abs(left) > Math.abs(prevL) + dampRate) {
      L = prevL + ((prevL / Math.abs(prevL)) * dampRate);
    }

    if (Math.abs(right) > Math.abs(prevR) + dampRate) {
      R = prevR + ((prevR / Math.abs(prevR)) * dampRate);
    }

    setVolts(L, R);
  }

  /**
   * Sets the desired tick position for the left and right talon
   * 
   * @param left Desired tick position for the left talon
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
   * Gets PID constants from the SmartDashboard and then uses setPIDValues(double, double, double)
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
  }
}