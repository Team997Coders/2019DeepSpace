/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HatchManipulator extends Subsystem {

  private Solenoid hatchPiston;
  public boolean hatchPistonState;

  public HatchManipulator() {
    hatchPiston = new Solenoid(RobotMap.Ports.hatchSolenoid);
    hatchPistonState = hatchPiston.get();
  }

  public void extend() {
    hatchPiston.set(true);
    hatchPistonState = true;
  }
  
  public void retract() {
    hatchPiston.set(false);
    hatchPistonState = false;
  }

  public void toggle() {
    if (hatchPistonState) {
      retract();
    } else {
      extend();
    }
  }

  @Override
  public void initDefaultCommand() {
  }
  
  public void updateDashboard(){
    SmartDashboard.putBoolean("Hatch Piston Extended", hatchPistonState);
  }
}
