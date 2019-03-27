/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * This subsystem manipulates the LiftGear
 */
public class LiftGear extends Subsystem {

  private Solenoid frontPiston;
  private Solenoid backPiston;
  private AnalogInput frontDistanceIR;
  private AnalogInput backDistanceIR;
  
  public LiftGear() {
    frontPiston = new Solenoid(RobotMap.Ports.landingGearPiston);
    frontPiston.set(false);

    frontDistanceIR = new AnalogInput(RobotMap.Ports.landingGearFloorSensor);

    backPiston = new Solenoid(RobotMap.Ports.rearGearPiston);
    backPiston.set(false);

    //backDistanceIR = new AnalogInput(RobotMap.Ports.rearGearFloorSensor);
  }
  
  /**
   * Extends the single solenoid piston
   */
  public void extendFront() {
    frontPiston.set(true);
  }

  /**
   * Retracts the single solenoid piston
   */
  public void retractFront() {
    frontPiston.set(false);
  }

  public void extendBack() {
    backPiston.set(true);
  }

  public void retractBack() {
    backPiston.set(false);
  }

  /**
   * Gives you the current state of the piston
   * 
   * @return True for piston is extended and False for retracted
   */
  public boolean getFrontPistonState() { return frontPiston.get(); }
  public boolean getBackPistonState() { return backPiston.get(); }

  /**
   * Gets the voltage coming for the Infared Sensor's analog input
   * 
   * @return The voltage from the sensor. (For proto bot) Either around 1 ish for on
   * the ground or around 0.3 ish for up in the air
   */
  public double getFrontIRSensorVoltage() {
    return frontDistanceIR.getVoltage();
  }

  public double getBackIRSensorVoltage() {
    return backDistanceIR.getVoltage();
  }

  /**
   * Updates the SmartDashboard with subsystem data
   */
  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Front Distance IR", frontDistanceIR.getVoltage());
    //SmartDashboard.putNumber("Back Distance IR", backDistanceIR.getVoltage());
  }

  @Override
  public void initDefaultCommand() { }
}
