/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  public static class Values{
   public static double
    pidP = 655520,
    pidI = 123456,
    pidD = 123456789,
    elevatorTopHeight= 543333,
    elevatorMiddleHeight = 544333,
    elevatorBottomHeight = 555434;
  }

  public static class Ports{
    public static int
    masterElevatorMotor= 7,
    followerElevatorMotor = 8,
    elevatorEncoderPort1 = 3217589,
    elevatorEncoderPort2 = 3452354;

    
  }
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
