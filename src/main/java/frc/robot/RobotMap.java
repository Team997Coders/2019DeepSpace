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
  public static class Ports {
    public static int

      GamePad1 = 0,
      GamePad2 = 1,           // TODO: Need to check this
      buttonA = 1,            // TODO: Need to check this
      buttonB = 2,
      buttonX = 3,            // TODO: Need to check this
      buttonY = 4,            // TODO: Need to check this
      buttonLeftShoulder = 5,       // TODO: Need to check this
      buttonRightShoulder = 6,      // TODO: Need to check this
      buttonLeftThumbstick = 9,     // TODO: Need to check this
      buttonRightThumbstick = 10,    // TODO: Need to check this
      buttonLeftTrigger = 9,        // TODO: Need to check this
      buttonRightTrigger = 10,      // TODO: Need to check this
      buttonBack = 7,

      //AXIS PORTS
      leftXAxis = 0,
      leftYAxis = 1,
      rightXAxis = 4,
      rightYAxis = 5,

      //VICTOR PORTS
      leftVictor1 = 5,
      leftVictor2 = 6,
      rightVictor1 = 2,
      rightVictor2 = 3,

      //TALON PORTS
      leftTalon = 4,
      rightTalon = 1,

      //SENSOR PORTS
      lineSensorFrontLeft = 4,  
      lineSensorFrontCenter = 5,
      lineSensorFrontRight = 6,
      frontUltrasonicSensor = 2, //TODO: Need to check this
      frontInfraredSensor = 5,  //TODO: Need to check this

      lineSensorBackLeft = 3,    
      lineSensorBackCenter = 2,  
      lineSensorBackRight = 1,   
      backInfraredSensor = 2,  //TODO: Need to check this 

      //BALL PORTS
      ballMotor = 9,

      //HATCH PORTS
      hatchSolenoid = 1,
      discBrake = 2, 
  
      //ARM PORTS
      armSpark = 10,
      armCanifier = 23,

      //LANDING GEAR PORTS
      landingGearPiston = 0,

      //CAMERA PORTS
      panservo = 9,
      tiltservo = 8,

      // placeholder so we can always just end with commas :-)
      end_of_ports = 999;
  }

  public class Values {

    public static final double
      inchesPerTick = (3.954*Math.PI)/4096, //inches per encoder tick
      ticksPerFoot = ((49152/(3.97*Math.PI)))*.9,//3940, //encoder ticks per foot
    
      armPidP = 0.1,
      armPidI = 0,
      armPidD = 0,
      armPidK = 0,

      P = 0.0002,
      I = 0.0,
      D = 0.0,
      //INFRARED DISTANCES
      frontUltrasonicSensorHatchCargoship = 5,     //TODO: Need to check this      
      frontInfraredSensorHatchCargoship = 5,  //TODO: Need to check this      
      frontUltrasonicSensorHatchRocket = 5,        //TODO: Need to check this   
      frontInfraredSensorHatchRocket = 5,     //TODO: Need to check this 
  
      frontUltrasonicSensorBallCargoship = 5,     //TODO: Need to check this      
      frontInfraredSensorBallCargoship = 5,  //TODO: Need to check this      
      frontUltrasonicSensorBallRocket = 5,        //TODO: Need to check this   
      frontInfraredSensorBallRocket = 5,     //TODO: Need to check this   
  
      backInfraredSensorHatchCargoship = 5,   //TODO: Need to check this  
  
      backInfraredSensorBallRocket = 5,      //TODO: Need to check this
      backInfraredSensorBallCargoship = 5;   //TODO: Need to check this  
  }
}
