/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RoboMisc;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.misc.GearBox;

import com.ctre.phoenix.motorcontrol.ControlMode;
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

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
    //setDefaultCommand(new TankDrive());
  }
}
