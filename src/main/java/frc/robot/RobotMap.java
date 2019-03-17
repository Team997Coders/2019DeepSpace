/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.ctre.phoenix.CANifier.LEDChannel;

import edu.wpi.first.wpilibj.SerialPort;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  public static class Buttons {
    public static class ButtonBox {
      public static int
        ButtonBoxJoystickId = 1,          
        hatchJoystickButtonId = 1,
        ballJoystickButtonId = 2,
        mediumHeightJoystickButtonId = 3,
        activateJoystickButtonId = 4,
        lowHeightJoystickButtonId = 5,
        frontJoystickButtonId = 6,
        backJoystickButtonId = 7,
        rocketJoystickButtonId = 8,
        cargoShipJoystickButtonId = 9,
        highHeightJoystickButtonId = 10,
        cancelJoystickButtonId = 11,
        intakeJoystickButtonId = 12,
        AJoystickButtonId = 13,
        BJoystickButtonId = 14,
        XJoystickButtonId = 15,
        leftJoystickHatAngle = 315,
        centerJoystickHatAngle = 0,
        rightJoystickHatAngle = 45;      
    }

    public static class Logitech {
      public static int 
        Gamepad4 = 3,
        buttonA = 2,
        buttonB = 3,
        buttonX = 1,
        buttonY = 4,
        buttonLeftThumbstick = 11,
        buttonRightThumbstick = 12,
        leftJoystickHatAngle = 270,
        centerJoystickHatAngle = 0,
        rightJoystickHatAngle = 90,
        leftXAxis = 0,
        leftYAxis = 1;
      }

    public static int

      GamePad1 = 0,
      GamePad3 = 2, 
      buttonA = 1,
      buttonB = 2,
      buttonX = 3,            
      buttonY = 4,            // TODO: Need to check this
      buttonLeftShoulder = 5,       // TODO: Need to check this
      buttonRightShoulder = 6,      // TODO: Need to check this
      buttonLeftThumbstick = 9,     // TODO: Need to check this
      buttonRightThumbstick = 10,    // TODO: Need to check this
      buttonRightTrigger = 9,        // TODO: Need to check this
      buttonLeftTrigger = 10,      // TODO: Need to check this
      buttonBack = 7,
      buttonStart = 8,

      //AXIS PORTS
      leftXAxis = 0,
      leftYAxis = 1,
      rightXAxis = 4,
      rightYAxis = 5,

      end_of_buttons;
  }

  public static class Ports {
    public static final SerialPort.Port AHRS = SerialPort.Port.kUSB;
    public static int
      //TALON PORTS
      leftTalon = 4,
      rightTalon = 1,

      //VICTOR PORTS
      leftVictor1 = 5,
      leftVictor2 = 6,
      rightVictor1 = 2,
      rightVictor2 = 3,

      //SENSOR PORTS
      lineSensorFrontLeft = 4,  
      lineSensorFrontCenter = 5,
      lineSensorFrontRight = 6,
      frontUltrasonicSensor = 0, 
      frontInfraredSensor = 2,  

      lineSensorBackLeft = 2,    
      lineSensorBackCenter = 1,  
      lineSensorBackRight = 3,   
      backInfraredSensor = 3,  



      //BALL PORTS
      ballMotor = 12,
      // ball sensor on the arm canifier rev limit switch connection 

      //HATCH PORTS
      hatchSolenoid = 1,
  
      //ARM PORTS
      discBrake = 2, 
      armSpark = 62,
      armCanifier = 23,

      // ELEVATOR PORTS
      elevatorCanifier = 29,
      masterElevatorMotor= 42,
      followerElevatorMotor = 43,

      //LANDING GEAR PORTS
      landingGearPiston = 0,
      landingGearFloorSensor = 1,

      //CAMERA PORTS
      frontPanServo = 9,
      frontTiltServo = 8,
      backPanServo = 1,
      backTiltServo = 0,

      // placeholder so we can always just end with commas :-)
      end_of_ports = 999;
      
    public static LEDChannel
      frontLightRing = LEDChannel.LEDChannelA,
      backLightRing = LEDChannel.LEDChannelC;
  }

  public static class Values {
    public static int 
      ticksPerRev = 4096; // Protobot
    public static double
      // PDriveToDistance PID Variables
      driveDistanceP = .003, //placeholders
      driveDistanceI = 0.0,
      driveDistanceD = 0.0,
      protobotTickPerFoot= 2449,

      // PDriveToAngle PID Variables
      driveAngleP = .007, //placeholders
      driveAngleI = 0.0,
      driveAngleD = 0.0,

      //

      inchesPerTick = (3.954*Math.PI)/4096, //inches per encoder tick
      ticksPerFoot = ((49152/(3.97*Math.PI)))*.9, //3940, //encoder ticks per foot

      //INFRARED DISTANCES for Line Following
      frontUltrasonicSensorHatchCargoship = 91,     //TODO: Need to check this      
      frontInfraredSensorHatchCargoship = 550,      
      frontUltrasonicSensorHatchRocket = 5,         //TODO: Need to check this   
      frontInfraredSensorHatchRocket = 5,           //TODO: Need to check this 
      frontUltrasonicSensorBallCargoship = 5,       //TODO: Need to check this      
      frontInfraredSensorBallCargoship = 5,         //TODO: Need to check this      
      frontUltrasonicSensorBallRocket = 5,          //TODO: Need to check this   
      frontInfraredSensorBallRocket = 5,            //TODO: Need to check this   
      backInfraredSensorHatchCargoship = 1200,  
      backInfraredSensorBallRocket = 5,             //TODO: Need to check this
      backInfraredSensorBallCargoship = 5,          //TODO: Need to check this 

      // Line following voltage values
      powerMotor = 0.5,       //TODO: Need to check this 
      noPowerMotor = -.25,    //TODO: Need to check this 
      normal = .10,        //for double line seen
      straight = .3,          //TODO: Need to check this 

      // Arm and Elevator Values
      elevatorPidP = 0.00005, // 0.0005
      elevatorPidI = 0.0,
      elevatorPidD = 0.000, // 0.0005
      elevatorPidF = 0.0000,
      elevatorTopHeight = 100000000, //placeholder

      armSwitchHeight= 9002, //placeholer for height in order for arm to switch
      armEncoderCenter= 462,//another place holder
      armFrontLimit = 195, //encoder ticks @ the front limit. placeholder.
      armBackLimit = 702, //encoder ticks @ the back limit. placeholder.

      armPidP = 0.0006,
      armPidI = 0,
      armPidD = 0,
      armPidK = 0,
      armMaxPidF = 0.0055, // 0.0055
      ticksToRadiansArm= 3.141592653589793238/(Math.abs(armBackLimit-armFrontLimit)),

      // Camera values
      leftAngleInDegrees = 75,
      rightAngleInDegrees = 115,
      
      // Elevator Deccel/Accel Values
      bottomElevatorAccelPosLimit = 5000,
      bottomElevatorLimitVelocity = -0.28,
      topElevatorAccelPosLimit = 49000,
      topElevatorLimitVelocity = 0.4,
      //PathFnder values
      pf_timestep = 0.02,
      pf_max_vel = 2.5, // max velocity in ft/sec.
      pf_max_acc = 6.79,
      pf_max_jerk = 60,
      pf_Kp = 0.001,
      pf_Ki = 0.0,
      pf_Kd = 0.06,
      pf_Kv = (1/pf_max_vel),
      pf_Ka = 0.0,
      //pf_Kt = 0.35,
      

      robotLength = 0.75, //in inches (includes bumpers)
      robotWidth = 0.8,	
      robotWheelBase = 0.62, // inches or 2.5ft or 0.6 meters.  Use 0.0254 meters/in or 39.37in/m
      robotWheelDia = 0.15;// Javadocs requests that this is in meters not feet-> 6/12; // remember all pf variables are in ft.  Need to convert when used.
      
    public static boolean
      pf_path_ready = false;
  }

  public static class ElevatorHeights {
    //in inches
    public static int
      elevatorBackTopHatchHeight = 0, //impossible
      elevatorBackMiddleHatchHeight = 0, //impossible
      elevatorBackBottomHatchHeight = 0, //impossible
      elevatorBackShipHatchHeight = 8900, //impossible

      elevatorBackTopCargoHeight = 52400, // probably higher
      elevatorBackMiddleCargoHeight = 34400,
      elevatorBackBottomCargoHeight = 9100,
      elevatorBackShipCargoHeight = 24650,

      elevatorFrontTopHatchHeight = 52400, // probably higher
      elevatorFrontMiddleHatchHeight = 30530,
      elevatorFrontBottomHatchHeight = 1520,
      elevatorFrontShipHatchHeight = 1520,

      elevatorFrontTopCargoHeight = 52400,
      elevatorFrontMiddleCargoHeight = 45360,
      elevatorFrontBottomCargoHeight = 22200,
      elevatorFrontShipCargoHeight = 38125,

      elevatorCollectCargoHeight = 0,
      
      elevatorSafeFlipHeight = 23000,

      armBackParallel = 702,
      armFrontParallel = 195,
      armVertical = 462;

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
}
