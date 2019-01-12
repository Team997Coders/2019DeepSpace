/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class BallManipulator extends Subsystem {
   private VictorSPX ballMotor;

   public void ballIntake(){    
     ballMotor.set(ControlMode.PercentOutput, 1);
   }

   public void ballOuttake(){
     ballMotor.set(ControlMode.PercentOutput, -1);
   }

   public void stopMotor(){
     ballMotor.set(ControlMode.PercentOutput, 0);
   }
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
