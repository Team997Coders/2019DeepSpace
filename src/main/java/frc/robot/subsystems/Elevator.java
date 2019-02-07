/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.Encoder;
/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  private CANSparkMax master, follower;
  private CANPIDController pidController;
  private CANDigitalInput limitSwitchTop;
  private CANDigitalInput limitSwitchBottom;
  private Encoder encoder;
  //public int index = 0;
  //public double[]  heightList;
  public boolean gamePieceType; 
  //This is to switch between balls and hatches for elevator heights.
  //// Balls = true Hatches = false
  public boolean yeet;


   public Elevator() {
    master = new CANSparkMax(RobotMap.Ports.masterElevatorMotor, MotorType.kBrushless);
    follower = new CANSparkMax(RobotMap.Ports.followerElevatorMotor, MotorType.kBrushless);
    encoder = new Encoder(RobotMap.Ports.elevatorEncoderPort1, RobotMap.Ports.elevatorEncoderPort2);
    limitSwitchTop = new CANDigitalInput(master, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyOpen);
    limitSwitchTop.enableLimitSwitch(true);
    
    limitSwitchBottom= new CANDigitalInput(master, LimitSwitch.kReverse , LimitSwitchPolarity.kNormallyOpen);
    limitSwitchBottom.enableLimitSwitch(true);

    master.setIdleMode(IdleMode.kBrake);
    follower.setIdleMode(IdleMode.kBrake);

    follower.follow(master);
    
    follower.setInverted(false);

    pidController = master.getPIDController();
    pidController.setP(RobotMap.Values.elevatorPidP);
    pidController.setI(RobotMap.Values.elevatorPidI);
    pidController.setD(RobotMap.Values.elevatorPidD);
    pidController.setFF(RobotMap.Values.elevatorPidF);
    
    pidController.setReference(0.0/*total - current*/, ControlType.kPosition); 
   }

   public void SetPosition(double height){
     
    pidController.setReference(height, ControlType.kPosition);
  }

  public int GetPositon(){
    return encoder.get();
  }

  public void Stop(){
    master.set(0);
  }

  public void SetVoltage(double volts){
    master.set(volts);
  }

  /*public void incrementIndex() {
    index++;
    if (index > heightList.length - 1) {
      index = heightList.length - 1;
    }
  }

  public void decrementIndex() {
    index--;
    if(index < 0) {
      index = 0;
    }
  }
  public double getHeightFromArray() {
    return heightList[index];
  }*/
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
