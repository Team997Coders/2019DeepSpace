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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class HatchManipulator extends Subsystem {

  private DoubleSolenoid hatchPiston;
  public boolean hatchPistonState;

  public HatchManipulator() {
    // Instantiate hatchPiston
  }
  public void extend() {
    hatchPiston.set(Value.kForward);
    hatchPistonState = true;
  }
  
  public void retract() {
    hatchPiston.set(Value.kReverse);
    hatchPistonState = false;
  }
  @Override
  public void initDefaultCommand() {
  }
  public void updateDashboard(){
    SmartDashboard.putBoolean("HatPiston Extended", hatchPistonState);
  }
}