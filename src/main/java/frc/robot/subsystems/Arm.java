/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static org.junit.Assert.assertArrayEquals;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.PWMChannel;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
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
  private final double MAX = 1.022;
  private final double LIMIT = 0.5;
  private double initRead = 0;
  private double prevRead = -1;
  private int revs = 0;
  private int flipModifier = 1;

  public Arm() {

    sparkMax = new CANSparkMax(RobotMap.Ports.armSpark, MotorType.kBrushless);
    pidController = sparkMax.getPIDController();
    pidController.setP(RobotMap.Values.armPidP);
    pidController.setI(RobotMap.Values.armPidI);
    pidController.setD(RobotMap.Values.armPidD);

    dataBus = new CANifier(RobotMap.Ports.armCanifier);

    discBrake = new Solenoid(RobotMap.Ports.discBrake);

    initRead = getRawEncoder();
  }

  public void setSpeed(double speed) {
    sparkMax.set(speed);
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
    return (revs * MAX) + (newVal - initRead);
  }

  private double getRawEncoder() {
    double[] a = new double[2];
    dataBus.getPWMInput(PWMChannel.PWMChannel0, a);
    SmartDashboard.putNumber("Duty Cycle", a[1]);
    return a[0] / 1000;
  }

  public void engageBrake() {
    discBrake.set(true);
  }

  public void releaseBrake() {
    discBrake.set(false);
  }

  @Override
  public void initDefaultCommand() {
  }

  public void updateSmarts() {
    SmartDashboard.putNumber("Absolute Raw", getRawEncoder());
    SmartDashboard.putNumber("Absolute Parsed", readEncoder());
  }
}
