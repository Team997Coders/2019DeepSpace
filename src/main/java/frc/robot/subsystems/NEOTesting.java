/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
//import com.revrobotics.CANEncoder;
//import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class NEOTesting extends Subsystem {

  private CANSparkMax sparky;

  private CANEncoder internalEncoder;
  private CANPIDController PIDController;

  public NEOTesting() {
    sparky = new CANSparkMax(0, MotorType.kBrushless);
    internalEncoder = sparky.getEncoder();
    PIDController = new CANPIDController(sparky);
  }

  public void setSpeed(double speed) {
    sparky.set(speed);
  }

  public void configurePIDController() {
    //PIDController.setI(0.2);
  }

  public double getPosition() { return internalEncoder.getPosition(); }

  public double getVelocity() { return internalEncoder.getVelocity(); }

  @Override
  public void initDefaultCommand() { }
}
