/**----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.ArcadeDrive;
//import frc.robot.misc.GearBox;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {

  // GearBox class stores information for the motor controllers for one gearbox
  //private GearBox leftBox, rightBox;
  private TalonSRX leftTalon, rightTalon;
  private VictorSPX leftVictor1, leftVictor2, rightVictor1, rightVictor2;
  private SensorCollection leftTalonSensorCollection, rightTalonSensorCollection;
  private ArcadeDrive driveTrainStyle;
  
  int delayCount = 0;

  public DriveTrain() {
    leftTalon = new TalonSRX(RobotMap.Ports.leftTalon);
    rightTalon = new TalonSRX(RobotMap.Ports.rightTalon);
    leftVictor1 = new VictorSPX(RobotMap.Ports.leftVictor1);
    leftVictor2 = new VictorSPX(RobotMap.Ports.leftVictor2);
    rightVictor1 = new VictorSPX(RobotMap.Ports.rightVictor1);
    rightVictor2 = new VictorSPX(RobotMap.Ports.rightVictor2);
    leftTalonSensorCollection = new SensorCollection(leftTalon);
    rightTalonSensorCollection = new SensorCollection(rightTalon);

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
		   

    //shiftSolenoid = new DoubleSolenoid(RobotMap.Ports.gearPistonFor, RobotMap.Ports.gearPistonRev);
  }

  /**
   * Apply a factor between 0 and 1 as a percentage of voltage
   * @param left  Gain between 0 and 1 for left wheel
   * @param right Gain between 0 and 1 for right wheel
   */
  public void setVolts(double left, double right) {
    leftTalon.set(ControlMode.PercentOutput, left);
    rightTalon.set(ControlMode.PercentOutput, right);
  }

  /**
   * Stop the drive train
   */
  public void stop() {
    // Set Motor Volts to 0
    driveStraight(0);
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
   * Set the drive train style by setting the default command for
   * the drive train using a command that defines the style. The style
   * is annotated with "@DriveTrainStyle" and the style is selected 
   * in the DriveTrain guice module configure method with a binding
   * to the desired command.
   * 
   * @see frc.robot.guice.modules.DriveTrain#configure()
   */
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
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
  public void updateDashboard(){
    if (delayCount ==10){
      SmartDashboard.putNumber("DT - Left master voltage", leftTalon.getMotorOutputVoltage());
			SmartDashboard.putNumber("DT - Right master voltage", rightTalon.getMotorOutputVoltage());
			SmartDashboard.putNumber("DT - Left Encoder", getLeftEncoderTicks());
			SmartDashboard.putNumber("DT - Right Encoder", getRightEncoderTicks());
			SmartDashboard.putNumber("DT - Left Encoder distance in inches", getLeftEncoderTicks()*RobotMap.Values.inchesPerTick);
			SmartDashboard.putNumber("DT - Right Encoder distance in inches", getRightEncoderTicks()*RobotMap.Values.inchesPerTick);
			SmartDashboard.putNumber("DT - Left Encoder Velocity", leftTalon.getSelectedSensorVelocity(0));
			SmartDashboard.putNumber("DT - Right EncoderVelocity", rightTalon.getSelectedSensorVelocity(0));

			delayCount = 0;
		} else {
			delayCount++;
		}		
  }
}
