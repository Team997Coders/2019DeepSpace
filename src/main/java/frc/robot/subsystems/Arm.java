/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

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
import frc.robot.data.ArmData;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
/**
 * Manage the Arm on the Elevator:
 * Need to encode the logic to prevent the arm from running into the elevator
 * There needs to be some basic intelligence to say: "Are we moving the arm to the 
 * other side of the robot?" and then if not we can move.  If we are moving to the 
 * other side of the robot: "Is the elevator carriage high enough so that the arm
 * won't hit the elevator.  Is the elevator above the 'ElevatorArmSafeHeight?'?"
 * If the elevator is above the safe height, then we can move.  If the elevator carriage
 * is too low, then we will have to move the elevator!
 *   a - save the current elevator position
 *   b - schedule the elevator to move to the safe height
 *   c - wait (loop) until the elevator is high enough
 *   d - move arm across to other side to specified angle
 *   e - move the elevator back to the original position
 *   f - clean up our variables
 * If we knew the new expected elevator position, we could check if we should move it
 * first.   Maybe this is a good case for a unified Elevator and Arm Movement method.
 * 
 */
public class Arm extends Subsystem {

  public CANPIDController pidController;

  private CANSparkMax sparkMax;
  private CANifier dataBus;
  private CANDigitalInput frontLimitSwitch, backLimitSwitch;

  private Solenoid discBrake;

  // Read Encoder Vars
  private final double MAX = 1022;
  private final double LIMIT = 500;
  private double prevRead = -1;
  private int revs = 0;
  private int flipModifier = 1;
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

    dataBus = Robot.armCanifier;

    discBrake = new Solenoid(RobotMap.Ports.discBrake);

    SmartDashboard.putNumber("Arm/Arm Pid P", RobotMap.Values.armPidP);
    SmartDashboard.putNumber("Arm/Arm Pid I", RobotMap.Values.armPidI);
    SmartDashboard.putNumber("Arm/Arm Pid D", RobotMap.Values.armPidD);
    SmartDashboard.putNumber("Arm/Arm Pid F", RobotMap.Values.armMaxPidF);
  }

  /**
   * Implement logic to determine if it is safe to move the arm
   * @return boolean to indicate if we are safe to move the arm to a given new angle
   */
  public boolean checkSafeMove(double m_newangle) {
    // we probably need new angles that form a 'V' at the top of the elevator
    //   and create a no-mans-land that we can't move into when the elevator
    //   isn't high enough and not just one variable.

    //1 - get the current arm angle and elevator height
    int m_height = Robot.elevator.GetPosition();
    double m_angle = this.readEncoder();
    
    // 2 - check if we are trying to move to the other side
    if ((m_angle < RobotMap.Values.armEncoderCenter && 
            m_newangle < RobotMap.Values.armEncoderCenter) ||
            (m_angle > RobotMap.Values.armEncoderCenter && 
            m_newangle > RobotMap.Values.armEncoderCenter))
             {
              // Move is on same side of elevator.
              return true; // safe.
            }
    // 3 - check if the elevator is high enough
    else if (m_height > RobotMap.Values.armSwitchHeight) {
      // elevator is high enough so that we can move the arm
      return true;
    }
    // otherwise we are in trouble and need to move the elevator first.
    System.out.println("Unsafe Arm Movement, need to lift the elevator first!");
    return false;
  }

  public void setPower(double speed) {
    //sparkMax.set(speed);
    pidController.setReference(speed, ControlType.kDutyCycle);
  }
  
  public void resetArmToUpperQuadrants() {
    if (getArmSide()) {
      // front-side
      if (readEncoder() < RobotMap.ElevatorHeights.armFrontParallel) {
        this.SetPosition(RobotMap.ElevatorHeights.armFrontParallel);
      }
    } else {
      // back-side
      if (readEncoder() > RobotMap.ElevatorHeights.armBackParallel) {
        this.SetPosition(RobotMap.ElevatorHeights.armBackParallel);
      }
    }
  }

  public boolean getArmSide() {
    if (readEncoder() < RobotMap.Values.armEncoderCenter) {
      return true; // arm on the front of the robot
    } else {
      return false;
    }
  }

  // This function only works if the inital read of the arm is horizontal
  public void UpdateF(){
     pidController.setFF((Math.abs(Math.cos(readEncoder() - RobotMap.ElevatorHeights.armFrontParallel) * RobotMap.Values.ticksToRadiansArm)) * fConstant);
  }

  public double getCurrent() {
    return sparkMax.getOutputCurrent();
  }

  public void SetPosition(double setpoint){
    if (checkSafeMove((double) setpoint)) {
      releaseBrake();
      System.out.println("Set position to " + setpoint);
      pidController.setReference(setpoint - readEncoder(), ControlType.kPosition);
      UpdateF();
    }
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
    return (int)((revs * MAX) + newVal);
  }

  private int getRawEncoder() {
    double[] a = new double[2];
    dataBus.getPWMInput(PWMChannel.PWMChannel0, a);
    SmartDashboard.putNumber("Duty Cycle", a[1]);
    return (int) a[0];
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

  public double getMotorTemp() {
    return sparkMax.getMotorTemperature();
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
    SmartDashboard.putNumber("Arm/Arm Absolute Raw", getRawEncoder());
    SmartDashboard.putNumber("Arm/Absolute Parsed", readEncoder());
    SmartDashboard.putBoolean("Arm/Disc Brake state: ", discBrake.get());
    SmartDashboard.putBoolean("Arm/Arm forward limit switch", getForwardLimitSwitch());
    pidController.setP(SmartDashboard.getNumber("Arm/Arm Pid P", RobotMap.Values.armPidP));
    pidController.setI(SmartDashboard.getNumber("Arm/Arm Pid I", RobotMap.Values.armPidI));
    pidController.setD(SmartDashboard.getNumber("Arm/Arm Pid D", RobotMap.Values.armPidD));
    fConstant = SmartDashboard.getNumber("Arm/Arm Pid F", RobotMap.Values.armMaxPidF);
    SmartDashboard.putNumber("Arm/Arm F", pidController.getFF());
    SmartDashboard.putBoolean("Arm/Arm Front Limit", frontLimitSwitch.get());
    SmartDashboard.putBoolean("Arm/Arm Back Limit", backLimitSwitch.get());
    SmartDashboard.putNumber("Arm/Arm voltage", sparkMax.getAppliedOutput());
    SmartDashboard.putNumber("Arm/Arm Current", getCurrent());
    SmartDashboard.putNumber("Arm/Arm Motor Temp", getMotorTemp());
  }


//Started copying over Hunter's PID code from SetArmPosition cuz PID ain't
//supposed to go in commands.
  public boolean pidError(double setpoint, double tolerance) {
    return (readEncoder() > setpoint - tolerance) && (readEncoder() < setpoint + tolerance);
  }

}