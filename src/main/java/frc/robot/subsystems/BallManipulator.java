/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class BallManipulator extends Subsystem {
   private VictorSPX ballMotor;

  public BallManipulator(){
    ballMotor = new VictorSPX(RobotMap.Ports.ballMotor);
    ballMotor.setInverted(true);

    ballMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void ballIntake(){
    //if (!Robot.arm.getForwardLimitSwitch()) {
      //System.out.println("Intaking ball");
      ballMotor.set(ControlMode.PercentOutput, 1);
    //} else {
      //ballMotor.set(ControlMode.PercentOutput, 0);
    //}
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
  public void updateDashboard(){
    SmartDashboard.putNumber("BallManipulator Volts", ballMotor.getSelectedSensorVelocity());
  }
  
}
