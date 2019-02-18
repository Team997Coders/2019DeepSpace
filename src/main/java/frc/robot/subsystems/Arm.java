/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static org.junit.Assume.assumeNoException;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.PWMChannel;
import com.ctre.phoenix.CANifier.GeneralPin;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Robot;
import frc.robot.commands.LockArm;
import frc.robot.data.ArmData;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
/**
 * Add your docs here.
 */
public class Arm extends Subsystem {

  public CANPIDController pidController;

  private CANSparkMax sparkMax;
  private CANifier dataBus;
  private CANDigitalInput frontLimitSwitch, backLimitSwitch;

  private Solenoid discBrake;

  // Read Encoder Vars
  private final double MAX = 1022;
  private final double LIMIT = 0.5;
  private double initRead = 0;
  private double prevRead = -1;
  private int revs = 0;
  private int flipModifier = 1;
  private double tolerance;
  private double fConstant = RobotMap.Values.armMaxPidF;
  public boolean armState;

  public Arm() {

    sparkMax = new CANSparkMax(RobotMap.Ports.armSpark, MotorType.kBrushless);
    
    sparkMax.restoreFactoryDefaults();

    sparkMax.setInverted(true);

    sparkMax.setOpenLoopRampRate(0);

    //sparkMax.setIdleMode(IdleMode.kBrake);
    
    pidController = sparkMax.getPIDController();
    pidController.setP(RobotMap.Values.armPidP);
    pidController.setI(RobotMap.Values.armPidI);
    pidController.setD(RobotMap.Values.armPidD);
    pidController.setOutputRange(-0.6, 0.6);

    frontLimitSwitch = new CANDigitalInput(sparkMax, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyOpen);
    frontLimitSwitch.enableLimitSwitch(true);
    backLimitSwitch = new CANDigitalInput(sparkMax, LimitSwitch.kReverse, LimitSwitchPolarity.kNormallyOpen);
    backLimitSwitch.enableLimitSwitch(true);

    dataBus = new CANifier(RobotMap.Ports.armCanifier);

    discBrake = new Solenoid(RobotMap.Ports.discBrake);

    initRead = getRawEncoder();
  }

  public void setPower(double speed) {
    //sparkMax.set(speed);
    pidController.setReference(speed, ControlType.kDutyCycle);
  }
  

  public boolean getArmSide(){
    if(readEncoder() < RobotMap.Values.armEncoderCenter){
      return true; // arm on the front of the robot
    }
    else{
      return false;
    }

  }

  // This function only works if the inital read of the arm is horizontal
  public void UpdateF(){
     pidController.setFF((Math.abs(Math.cos(readEncoder()) * RobotMap.Values.ticksToRadiansArm)) * fConstant);
  }

  public double getCurrent() {
    return sparkMax.getOutputCurrent();
  }

  public void SetPostion(double setpoint){
    releaseBrake();
    System.out.println("Set position to " + setpoint);
    pidController.setReference(setpoint - readEncoder(), ControlType.kPosition);
    UpdateF();
  }

  public double readEncoder() {
    double newVal = getRawEncoder();
    
    /*if (test) {
      newVal = testInput; // Read test input
    } else {
      newVal = getRawEncoder(); // Read encoder data
    }*/

    if (prevRead == -1) { 
      prevRead = newVal;
    }

    if (Math.abs(prevRead - newVal) > LIMIT) {
      if (newVal > prevRead) {
        revs -= flipModifier;
      } else {
        revs += flipModifier;
      }
    }
    prevRead = newVal;
    return (int)(((revs * MAX) + (newVal - initRead)));
  }

  private double getRawEncoder() {
    double[] a = new double[2];
    dataBus.getPWMInput(PWMChannel.PWMChannel0, a);
    SmartDashboard.putNumber("Duty Cycle", a[1]);
    return a[0];
  }

  public void engageBrake() {
    discBrake.set(false);
    SmartDashboard.putBoolean("Brake", true);
  }

  public void releaseBrake() {
    discBrake.set(true);
    SmartDashboard.putBoolean("Brake", false);
  }

  public void Lock() {
    sparkMax.setIdleMode(IdleMode.kBrake);
  }

  public void Unlock() {
    sparkMax.setIdleMode(IdleMode.kCoast);
  }

  public void stop() {
    // need to disable the PID and stop the motor
    engageBrake();
    pidController.setReference(0.0, ControlType.kDutyCycle);
  }

  public boolean getForwardLimitSwitch() {
    return !dataBus.getGeneralInput(GeneralPin.LIMR);
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new LockArm());
  }

  public ArmData getArmData() {
    ArmData a = new ArmData();
    a.output = sparkMax.getAppliedOutput();
    a.current = sparkMax.getOutputCurrent();
    a.ticks = readEncoder();
    a.velocity = 0;
    a.front = frontLimitSwitch.get();
    a.back = backLimitSwitch.get();

    return a;
  }

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Arm Absolute Raw", getRawEncoder());
    SmartDashboard.putNumber("Absolute Parsed", readEncoder());
    SmartDashboard.putBoolean("Disc Brake state: ", discBrake.get());
    SmartDashboard.putBoolean("Arm forward limit switch", getForwardLimitSwitch());
    pidController.setP(SmartDashboard.getNumber("Arm Pid P", RobotMap.Values.armPidP));
    pidController.setI(SmartDashboard.getNumber("Arm Pid I", RobotMap.Values.armPidI));
    pidController.setD(SmartDashboard.getNumber("Arm Pid D", RobotMap.Values.armPidD));
    fConstant = SmartDashboard.getNumber("Arm Pid F", RobotMap.Values.armMaxPidF);
    SmartDashboard.putNumber("Arm F", pidController.getFF());
    SmartDashboard.putBoolean("Arm Front Limit", frontLimitSwitch.get());
    SmartDashboard.putBoolean("Arm Back Limit", backLimitSwitch.get());
    SmartDashboard.putNumber("Arm voltage", sparkMax.getAppliedOutput());
    SmartDashboard.putNumber("Arm Current", getCurrent());
  }


//Started copying over Hunter's PID code from SetArmPosition cuz PID ain't
//supposed to go in commands.
  public boolean pidError(double setpoint, double tolerance) {
    return (readEncoder() > setpoint - tolerance) && (readEncoder() < setpoint + tolerance);
  }

}