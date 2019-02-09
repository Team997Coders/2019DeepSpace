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

  public static class ElevatorHeights {

    public static int
      elevatorBackTopHatchHeight = 1, //hey, placeholders.
      elevatorBackMiddleHatchHeight = 1, 
      elevatorBackBottomHatchHeight = 2,
      elevatorBackShipHatchHeight = 3,

      elevatorBackTopCargoHeight = 5,
      elevatorBackMiddleCargoHeight = 8,
      elevatorBackBottomCargoHeight = 13,
      elevatorBackShipCargoHeight = 21,

      elevatorFrontTopHatchHeight = 34,
      elevatorFrontMiddleHatchHeight = 55,
      elevatorFrontBottomHatchHeight = 89,
      elevatorFrontShipHatchHeight = 135,

      elevatorFrontTopCargoHeight = 224,
      elevatorFrontMiddleCargoHeight = 359,
      elevatorFrontBottomCargoHeight = 583,
      elevatorFrontShipCargoHeight = 912;

      public static int[]
      elevatorFrontHatchHeightArray = {
        
        elevatorFrontTopHatchHeight, 
        elevatorFrontMiddleHatchHeight, 
        elevatorFrontBottomHatchHeight 
        
      },
    
      elevatorBackHatchHeightArray = {
        elevatorBackTopHatchHeight, 
        elevatorBackMiddleHatchHeight,
        elevatorBackBottomHatchHeight

      },

      elevatorFrontCargoHeightArray = {
        
        elevatorFrontTopCargoHeight,
        elevatorFrontMiddleCargoHeight,
        elevatorFrontBottomCargoHeight
      },

      elevatorBackCargoHeightArray = {

        
      elevatorBackTopCargoHeight,
      elevatorBackMiddleCargoHeight,
      elevatorBackBottomCargoHeight
    

      };

  
      



  }

  public static class Values{
    

    public static double
      elevatorPidP = 655520, // all placeholders
      elevatorPidI = 123456,
      elevatorPidD = 123456789,
      elevatorPidF = 1234567,

      armSwitchHeight= 9002, //placeholer for height in order for arm to switch
      armEncoderCenter= 9002,//another place holder
      armFrontLimit = 9002, //encoder ticks @ the front limit. placeholder.
      armBackLimit = 9002, //encoder ticks @ the back limit. placeholder.

      armPidP = 0.1,
      armPidI = 0,
      armPidD = 0,
      armPidK = 0;
  }

  public static class Ports{
    public static int
    masterElevatorMotor= 7,
    followerElevatorMotor = 8,
    elevatorEncoderPort1 = 3217589,
    elevatorEncoderPort2 = 3452354,

    buttonA= 1,
    buttonB= 2,
    buttonX= 3,
    buttonY= 4,

    leftTrigger = 2,
    rightTrigger= 3,

    dPadY = 1,
    dPadX = 0,

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
    rightTalon = 5,

    armSpark = 10,
    armCanifier = 23,

    discBrake = 1; /* Placeholder */


    
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

