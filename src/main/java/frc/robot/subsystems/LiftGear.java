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

/**
 * Add your docs here
 */
public class LiftGear extends Subsystem {

  private Solenoid piston;
  private AnalogInput distanceIR;

  public LiftGear() {
    piston = new Solenoid(0);
    piston.set(false);

    distanceIR = new AnalogInput(1);
  }
  
  /**
   * Extends the single solenoid piston
   */
  public void extend() {
    piston.set(true);
  }

  /**
   * Retracts the single solenoid piston
   */
  public void retract() {
    piston.set(false);
  }

  /**
   * Gives you the current state of the piston
   * 
   * @return True for piston is extended and False for retracted
   */
  public boolean getPistonState() { return piston.get(); }

  /**
   * Gets the voltage coming for the Infared Sensor's analog input
   * 
   * @return The voltage from the sensor. (For proto bot) Either around 1 ish for on
   * the ground or around 0.3 ish for up in the air
   */
  public double getIRSensorVoltage() {
    return distanceIR.getVoltage();
  }

  /**
   * Updates the SmartDashboard with subsystem data
   */
  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Distance IR", distanceIR.getVoltage());
  }

  @Override
  public void initDefaultCommand() { }
}
