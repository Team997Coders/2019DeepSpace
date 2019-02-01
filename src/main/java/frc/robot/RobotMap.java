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

  public class Ports {
    
    public static final int 
      gamepad1 = 0,
      gamepad2 = 1,

      leftYAxis = 1,
      rightXAxis = 4,
      rightYAxis = 5,

      leftVictor1 = 2,
      leftVictor2 = 3,
      rightVictor1 = 5,
      rightVictor2 = 6,

      leftTalon = 1,
      rightTalon = 4;

  }

  public class Values {

    public static final double
      P = 0.0002,
      I = 0.0,
      D = 0.0;

  }

}