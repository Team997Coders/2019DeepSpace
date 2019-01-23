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

  public class Point {
    public double x ,y;
    public Point(double x, double y) {
      this.x = x; this.y = y;
    }
  }

  public Point cubicBezier(Point p0, Point p1, Point p2, Point p3, t) {
    pFinal = new Point(0, 0);
    double newX = Math.pow(1 - t, 3) * p0.x +
                    Math.pow(1 - t, 2) * 3 * t * p1.x +
                    (1 - t) * 3 * t * t * p2.x + t * t * t * p3.x;
    double newY = Math.pow(1 - t, 3) * p0.y +
                    Math.pow(1 - t, 2) * 3 * t * p1.y +
                    (1 - t) * 3 * t * t * p2.y + t * t * t * p3.y;
    pFinal.x = newX;
    pFinal.y = newY;
    return pFinal;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new SparkySpin());
  }

  public AnalogInput getEncoder() { return encoder; }
}
