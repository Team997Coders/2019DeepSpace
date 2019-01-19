/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.SparkySpin;
/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  //private Spark armMotor;
  private CANSparkMax sparkMax;
  public CANDigitalInput limitSwitch;
  public AnalogInput encoder;

  // Read Encoder Vars
  private final double MAX = 5;
  private final double LIMIT = 3;
  private double initRead = 0;
  private double prevRead = -1;
  private int revs = 0;
  private int flipModifier = 1;

  public Arm() {

    sparkMax = new CANSparkMax(42, MotorType.kBrushless);
    limitSwitch = new CANDigitalInput(sparkMax, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyOpen);
    limitSwitch.enableLimitSwitch(true);
    //sparkMax.setIdleMode(IdleMode.kBrake);

    SmartDashboard.putBoolean("FWD Limit Switch", limitSwitch.get());
    
    encoder = new AnalogInput(3);

    initRead = encoder.getVoltage();
  }

  public void setSpeed(double speed) {
    sparkMax.set(speed);
  }

  public double readEncoder(boolean test, double testInput) {
    double newVal;
    
    if (test) {
      newVal = testInput; // Read test input
    } else {
      newVal = encoder.getVoltage(); // Read encoder data
    }

    if (prevRead == -1) { 
      prevRead = newVal;
    }

    if (Math.abs(prevRead - newVal) > LIMIT) {
      if (newVal > prevRead) {
        revs -= flipModifier;
      } else {
        revs += flipModifier;
      }
    }
    prevRead = newVal;
    return (revs * MAX) + (newVal - initRead);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new SparkySpin());
  }

  public AnalogInput getEncoder() { return encoder; }
}
