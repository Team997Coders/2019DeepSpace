/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here
 */
public class LiftGear extends Subsystem {

  private Solenoid piston;
  private AnalogInput distanceIR;

  public boolean pistonState;
  
  // How will you be able to use piston1 and piston2 if they are null allocated objects??? How will you fix this???
  // Hint hint: use a constructor

  public LiftGear() {
    piston = new Solenoid(0);
    piston.set(false);
    pistonState = false;

    distanceIR = new AnalogInput(1);
  }
  
  public void extend() {
    piston.set(true);
    pistonState = true;
  }

  public void retract() {
    piston.set(false);
    pistonState = false;
  }

  public double getIRVoltage() {
    return distanceIR.getVoltage();
  }

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Distance IR", distanceIR.getVoltage());
  }
  
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
