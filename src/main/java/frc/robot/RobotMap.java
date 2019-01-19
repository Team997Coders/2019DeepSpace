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

  public class Values {
    public static final double 
    platform = 4.023333333333,
    habToShuttleDistance = 172.25/12,
    lv2HabToLv1HabDistance = 96.69035/12,
    middleHabLv1ToLineOnShuttle = 138.56349/12;

}
  public class Ports {
    
    public static final int 
      gamepad1 = 0,
      gamepad2 = 1,

      leftYAxis = 0,
      rightXAxis = 4,
      rightYAxis = 5,

      leftVictor1 = 0,
      leftVictor2 = 1,
      rightVictor1 = 2,
      rightVictor2 = 3,

      leftTalon = 4,
      rightTalon = 5;

  }

}
