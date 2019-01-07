/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.*;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {

  public DriveTrain() {
    System.out.println("Starting Drivetrain...");
  }

  public void setVolts(double left, double right) {
    // Set Motor Volts
    System.out.println("Set Volts Called");
  }

  public void stopVolts() {
    // Set Motor Volts to 0
    System.out.println("Stop Volts Called");
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
    //setDefaultCommand(new TankDrive());
  }
}
