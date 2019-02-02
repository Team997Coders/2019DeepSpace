package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RoboMisc;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.misc.GearBox;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * Add your docs here.
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

  public DriveTrain() {
    System.out.println("Starting Drivetrain...");

    // This uses the RoboMisc function standTalonSRXSetup(int, int, int, boolean) to initialize a Talon and 2 slave victors
    leftBox = RoboMisc.standTalonSRXSetup(RobotMap.Ports.leftTalon,
      RobotMap.Ports.leftVictor1, RobotMap.Ports.leftVictor2, false);
    rightBox = RoboMisc.standTalonSRXSetup(RobotMap.Ports.rightTalon,
      RobotMap.Ports.rightVictor1, RobotMap.Ports.rightVictor2, true);

    // Grab the objects created by the RoboMisc function and store them in this class
    leftTalon = leftBox.talon;
    rightTalon = rightBox.talon;
    leftVictor1 = leftBox.victor1;
    leftVictor2 = leftBox.victor2;
    rightVictor1 = rightBox.victor1;
    rightVictor2 = rightBox.victor2;

    resetEncoders();
    setCoast();
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

  public void setVoltsDecell(double left, double right) {
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

  public void setPosition(double left, double right) {
    leftTalon.set(ControlMode.Position, left);
    rightTalon.set(ControlMode.Position, right);
  }

  public double leftEncoderTicks() {
    leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return leftTalon.getSelectedSensorPosition(0);
  }

  public double rightEncoderTicks() {
    rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return rightTalon.getSelectedSensorPosition(0);
  }
  
  public double leftEncoderVelocity() {
    leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return leftTalon.getSelectedSensorVelocity(0);
  }

  public double rightEncoderVelocity() {
    rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return rightTalon.getSelectedSensorVelocity(0);
  }
  
  public void resetEncoders() {
    leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
		leftTalon.setSelectedSensorPosition(0, 0, 10);
		rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
    rightTalon.setSelectedSensorPosition(0, 0, 10);
  }
  
  public void setPIDValues() {
    double p = SmartDashboard.getNumber("P", 0);
    double i = SmartDashboard.getNumber("I", 0);
    double d = SmartDashboard.getNumber("D", 0);
    setPIDValues(p, i, d);
  }
  
  public void setPIDValues(double p, double i, double d) {
    leftTalon.config_kP(0, p, 0);
    rightTalon.config_kP(0, p, 0);
    leftTalon.config_kP(0, i, 0);
    rightTalon.config_kP(0, i, 0);
    leftTalon.config_kP(0, d, 0);
    rightTalon.config_kP(0, d, 0);
  }

  public void setBrake() {
		leftTalon.setNeutralMode(NeutralMode.Brake);
		rightTalon.setNeutralMode(NeutralMode.Brake);
		
		leftVictor1.setNeutralMode(NeutralMode.Brake);
		rightVictor1.setNeutralMode(NeutralMode.Brake);
		leftVictor2.setNeutralMode(NeutralMode.Brake);
		rightVictor2.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setCoast() {
		leftTalon.setNeutralMode(NeutralMode.Coast);
		rightTalon.setNeutralMode(NeutralMode.Coast);
		
		leftVictor1.setNeutralMode(NeutralMode.Coast);
		rightVictor1.setNeutralMode(NeutralMode.Coast);
		leftVictor2.setNeutralMode(NeutralMode.Coast);
		rightVictor2.setNeutralMode(NeutralMode.Coast);
}

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Left Ticks DriveTrain", leftEncoderTicks());
    SmartDashboard.putNumber("Right Ticks DriveTrain", rightEncoderTicks());
    SmartDashboard.putNumber("Left Velocity Drivetrain", leftEncoderVelocity());
    SmartDashboard.putNumber("Right Velocity Drivetrain", rightEncoderVelocity());
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
    //setDefaultCommand(new TankDrive());
  }
}
