/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class LiftGear extends Subsystem {
  private DoubleSolenoid piston1, piston2;
  public boolean piston1State, piston2State;
  
  public void extend() {
    piston1.set(Value.kForward);
    piston2.set(Value.kForward);
    piston1State = true;
    piston2State = true;
  }
  public void retract() {
    piston1.set(Value.kReverse);
    piston2.set(Value.kReverse);
    piston1State = false;
    piston2State = false;
  } 
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
