/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  //private Spark armMotor;
  private CANSparkMax sparkMax;

  private CANifier dataBus;

  // Read Encoder Vars
  private final double MAX = 5;
  private final double LIMIT = 3;
  private double initRead = 0;
  private double prevRead = -1;
  private int revs = 0;
  private int flipModifier = 1;

  public Arm() {
    dataBus = new CANifier(RobotMap.Ports.armCanifier);

    initRead = 4.0;
  }

  public void setSpeed(double speed) {
    sparkMax.set(speed);
  }

  public double readEncoder(boolean test, double testInput) {
    double newVal;
    
    if (test) {
      newVal = testInput; // Read test input
    } else {
      newVal = initRead; // Read encoder data
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

  public void resetEncoder() {
    revs = 0;
    prevRead = -1;
    initRead = 0; // Read data from encoder object
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
