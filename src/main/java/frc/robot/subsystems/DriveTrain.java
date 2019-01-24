/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.BlackHole;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.misc.GearBox;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.*;
import com.kauailabs.navx.frc.AHRS;


/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {

  // GearBox class stores information for the motor controllers for one gearbox
  private GearBox leftBox, rightBox;
  private TalonSRX leftTalon, rightTalon;
  private VictorSPX leftVictor1, leftVictor2, rightVictor1, rightVictor2;

  private boolean gyroExists = false;
  public AHRS gyro;
  public double initAngle;

  public double DriveAngleP;
  public double DriveAngleI;
  public double DriveAngleD;

  public DriveTrain() {
    System.out.println("Starting Drivetrain...");

    // This uses the blackhole function standTalonSRXSetup(int, int, int, boolean) to initialize a Talon and 2 slave victors
    leftBox = BlackHole.standTalonSRXSetup(RobotMap.Ports.leftTalon, RobotMap.Ports.leftVictor1, 
      RobotMap.Ports.leftVictor2, false);
    rightBox = BlackHole.standTalonSRXSetup(RobotMap.Ports.rightTalon, RobotMap.Ports.rightVictor1,
      RobotMap.Ports.rightVictor2, true);

    // Grab the objects created by the blackhole function and store them in this class
    leftTalon = leftBox.talon;
    rightTalon = rightBox.talon;
    leftVictor1 = leftBox.victor1;
    leftVictor2 = leftBox.victor2;
    rightVictor1 = rightBox.victor1;
    rightVictor2 = rightBox.victor2;

    try {
      gyro = new AHRS(RobotMap.Ports.AHRSPort);
      gyro.reset();
      initAngle = gyro.getAngle();
      System.out.println("Gyro booted with initAngle " + initAngle);
      gyroExists = true;
    } catch(RuntimeException e) {
      System.out.println("AHRS broke in driveTrain constructor");
      e.printStackTrace();
    }

    setCoastMode();
  }

  // Apply left and right as percentage voltage
  public void setVolts(double left, double right) {
    leftTalon.set(ControlMode.PercentOutput, left);
    rightTalon.set(ControlMode.PercentOutput, right);
  }

  public double getAngle() {
    return gyro.getAngle();
  }

  public void setBrakeMode() {
    leftTalon.setNeutralMode(NeutralMode.Brake);
    rightTalon.setNeutralMode(NeutralMode.Brake);
    leftVictor1.setNeutralMode(NeutralMode.Brake);
    leftVictor2.setNeutralMode(NeutralMode.Brake);
    rightVictor1.setNeutralMode(NeutralMode.Brake);
    rightVictor2.setNeutralMode(NeutralMode.Brake);
  }

  public void setCoastMode() {
    leftTalon.setNeutralMode(NeutralMode.Coast);
    rightTalon.setNeutralMode(NeutralMode.Coast);
    leftVictor1.setNeutralMode(NeutralMode.Coast);
    leftVictor2.setNeutralMode(NeutralMode.Coast);
    rightVictor1.setNeutralMode(NeutralMode.Coast);
    rightVictor2.setNeutralMode(NeutralMode.Coast);
  }

  public double getLeftEncoderTicks() {
		/* CTRE Magnetic Encoder relative, same as Quadrature */
		leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
		return leftTalon.getSelectedSensorPosition(0);
	}

	public double getRightEncoderTicks() {
		rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
		return rightTalon.getSelectedSensorPosition(0);
	}

	public double getLeftEncoderRate() {
		return leftTalon.getSelectedSensorVelocity(0);
	}

	public double getRightEncoderRate() {
		return rightTalon.getSelectedSensorVelocity(0);
	}

	public void resetEncoders() {
		leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
		leftTalon.setSelectedSensorPosition(0, 0, 10);
		rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
		rightTalon.setSelectedSensorPosition(0, 0, 10);
    System.out.println("Encoders reset!");
  }


  public void driveToPosition(double position) {
    leftTalon.set(ControlMode.Position, position);
    rightTalon.set(ControlMode.Position, position);
  }

  public void configurePid(){
    double DriveP = SmartDashboard.getNumber("DriveP", 0);
    double DriveI = SmartDashboard.getNumber("DriveI", 0);
    double DriveD = SmartDashboard.getNumber("DriveD", 0);

    DriveAngleP = SmartDashboard.getNumber("DriveAngleP", 0);
    DriveAngleI = SmartDashboard.getNumber("DriveAngleI", 0);
    DriveAngleD = SmartDashboard.getNumber("DriveAngleD", 0);

    //leftTalon.config_kF(0, 0.1097, 10);
    //leftTalon.config_kP(0, 0.113333, 10);
    leftTalon.config_kP(0, DriveP, 10);
    //leftTalon.config_kI(0, 0, 10);
    leftTalon.config_kI(0, DriveI, 10);
    //leftTalon.config_kD(0, 0, 10);		
    leftTalon.config_kD(0, DriveD, 10);

		//rightTalon.config_kF(0, 0.1097, 10);
    //rightTalon.config_kP(0, 0.113333, 10);
    rightTalon.config_kP(0, DriveP, 10);
    //rightTalon.config_kI(0, 0, 10);
    rightTalon.config_kI(0, DriveI, 10);
    //rightTalon.config_kD(0, 0, 10);	
    rightTalon.config_kD(0, DriveD, 10);
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
}

