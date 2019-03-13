/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.CANifier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.AutoDoNothing;
import frc.robot.commands.PDriveToDistance;
//import frc.robot.subsystems.Logger;
import frc.robot.buttonbox.ButtonBox;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.BallManipulator;
import frc.robot.subsystems.CameraMount;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.HatchManipulator;
import frc.robot.subsystems.InfraredRangeFinder;
import frc.robot.subsystems.LiftGear;
import frc.robot.subsystems.LineDetector;
import frc.robot.vision.CameraControlStateMachine;
import frc.robot.vision.commands.*;
import edu.wpi.first.wpilibj.Watchdog;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static final boolean DEBUG = false;

  public static Arm arm;
 // public StaticDeoptimizingNode;               
  public static Elevator elevator;
  public static BallManipulator ballManipulator;
  public static HatchManipulator hatchManipulator;
  public static LiftGear liftGear;
  public static DriveTrain driveTrain;
  //public static MotionProfile motionProfile;
  public static PathManager pathManager;
  public static CameraMount frontCameraMount;
  public static CameraMount backCameraMount;
  private NetworkTableInstance networkTableInstance;
  public static NetworkTable visionNetworkTable;
  public static CameraControlStateMachine cameraControlStateMachine;
  //public static Logger logger;
  //public static PowerDistributionPanel pdp;
  public static LineDetector frontLineDetector;
  //public static LineDetector backLineDetector;
  public static InfraredRangeFinder frontInfraredRangeFinder;
  public static InfraredRangeFinder backInfraredRangeFinder;
  public static CANifier armCanifier;
  public static CANifier elevatorCanifier;

  public static ButtonBox buttonBox;
  public static OI oi;
  public static ButtonBoxOI bb;
  public static LogitechVisionOI logitechVisionOI;

  Command autonomousCommand;
  SendableChooser<AutonomousOptions> chooser = new SendableChooser<>();

  public static int heightIndex;
  // used by the scoringHeight logic commands to grab the correct height from
  // the height array in RobotMap.

  private double lastTime = 0;
  public static double kDeltaTime;

  /*public Robot() {
    super(0.02);
  }*/

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    armCanifier = new CANifier(RobotMap.Ports.armCanifier);
    elevatorCanifier = new CANifier(RobotMap.Ports.elevatorCanifier);
    arm = new Arm();
    ballManipulator = new BallManipulator();
    //pdp = new PowerDistributionPanel();
    hatchManipulator = new HatchManipulator();
    elevator = new Elevator();
    liftGear = new LiftGear();
    driveTrain = new DriveTrain();
    //frontCameraMount = new CameraMount(0, 120, 10, 170, 2, 40, RobotMap.Ports.frontLightRing, RobotMap.Ports.frontPanServo, RobotMap.Ports.frontTiltServo, ButtonBox.ScoringDirectionStates.Front);
    //backCameraMount = new CameraMount(0, 120, 10, 170, 2, 40, RobotMap.Ports.backLightRing, RobotMap.Ports.backPanServo, RobotMap.Ports.backTiltServo,  ButtonBox.ScoringDirectionStates.Back);
    /*
      backLineDetector =  new LineDetector(RobotMap.Ports.lineSensorBackLeft, 
      RobotMap.Ports.lineSensorBackCenter, 
      RobotMap.Ports.lineSensorBackRight,
      ButtonBox.ScoringDirectionStates.Back);
    */
    frontLineDetector = new LineDetector(RobotMap.Ports.lineSensorFrontLeft, 
      RobotMap.Ports.lineSensorFrontCenter, 
      RobotMap.Ports.lineSensorFrontRight); 
      //ButtonBox.ScoringDirectionStates.Front);
    //backInfraredRangeFinder = new InfraredRangeFinder(RobotMap.Ports.backInfraredSensor, ButtonBox.ScoringDirectionStates.Back);
    frontInfraredRangeFinder = new InfraredRangeFinder(RobotMap.Ports.frontInfraredSensor, ButtonBox.ScoringDirectionStates.Front);

    //networkTableInstance = NetworkTableInstance.getDefault();
    //visionNetworkTable = networkTableInstance.getTable("Vision");
    //cameraControlStateMachine = new CameraControlStateMachine();
    buttonBox = new ButtonBox();

    // Create the logging instance so we can use it for tuning the PID subsystems
    //logger = Logger.getInstance();

    // Instanciate the Power Distribution Panel so that we can get the currents
    // however, we need to clear the faults so that the LEDs on the PDP go green.
    // I can never (and I have tried) find the source of the warnings that cause
    // the LED's to be Amber.
    //pdp = new PowerDistributionPanel();
    //pdp.clearStickyFaults();

    chooser.setDefaultOption("Do Nothing", AutonomousOptions.DoNothing);
    chooser.addOption("Left Cargo Ship", AutonomousOptions.LeftCargoShip);
    chooser.addOption("Right Cargo Ship", AutonomousOptions.RightCargoShip);
    chooser.addOption("Left Bottom Rocket", AutonomousOptions.LeftBottomRocket);
    chooser.addOption("Right Bottom Rocket", AutonomousOptions.RightBottomRocket);
    chooser.addOption("Hab 1", AutonomousOptions.DriveOffHab1);
    chooser.addOption("Hab 2", AutonomousOptions.DriveOffHab2);
    SmartDashboard.putData("Auto mode", chooser);


    // Make these last so to chase away the dreaded null subsystem errors!
    oi = new OI();
    //bb = new ButtonBoxOI();
    //logitechVisionOI = new LogitechVisionOI();

    //motionProfile = MotionProfile.getInstance();
    
    pathManager = PathManager.getInstance();
  }

  @Override
  public void robotPeriodic() {

    kDeltaTime = (System.currentTimeMillis() - lastTime) / 1000;
    lastTime = System.currentTimeMillis();

    if (DEBUG)
      updateSmartDashboard();
    else
      updateSmartDashboardRequired();
  }

  @Override
  public void disabledInit() {
    //cameraControlStateMachine.identifyTargets();
    driveTrain.setCoast(); // So the drivers don't want to kill us ;)
    arm.Unlock();
    //logger.close();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
    elevator.ZeroElevator();
  }

  @Override
  public void autonomousInit() {
    

    // NOTE: There must be a delay of AT LEAST 20ms to give
    // the camera subsystem time to ingest some frames. The assumption
    // is that autolock vision will be sandwiched between other commands,
    // and so there should be no problem.

    // Get the autonomous chooser option
    AutonomousOptions autonomousOption = chooser.getSelected();

    // An autonomous command must be set as a result of this activity

    if (autonomousOption == null) {
      // If it is null for some reason, do nothing. This should not happen and maybe
      // should be logged...
      autonomousCommand = new AutoDoNothing();
    } else {
      // TODO: Fill in these commands with the appropriate actions.
      // You can call cameraControlStateMachine.autoLockRight(), 
      // cameraControlStateMachine.autoLockLeft(), or cameraControlStateMachine.autoLock()
      // from your commands if you want to sandwich in vision autolocking after initial
      // motion profile driving. Once initiated, then in a subsequent command to perform
      // auto-drive based on vision feedback, use if (cameraControlStateMachine.getState() == CameraControlStateMachine.State.AutoLocked)
      // conditional to determine if target is locked. Finally, use cameraControlStateMachine.getSelectedTarget() to get information
      // about target. This function goes to network tables for you and gets the information about the lock on target
      // as documented here: https://github.com/Team997Coders/2019DSHatchFindingVision/tree/master/CameraVision
      switch(autonomousOption) {
        case LeftCargoShip:
          autonomousCommand = new AutoDoNothing();
          break;
        case RightCargoShip:
          autonomousCommand = new AutoDoNothing();
          //new Hab1ToCargoShipEndRightSide();
          break;
        case LeftBottomRocket:
          autonomousCommand = new AutoDoNothing();
          break;
        case RightBottomRocket:
          autonomousCommand = new AutoDoNothing();
          break;
        case DoNothing:
          autonomousCommand = new AutoDoNothing();
          break;
        case DriveOffHab1:
          autonomousCommand = new PDriveToDistance(0.4, 4);
          break;
        case DriveOffHab2:
          autonomousCommand = new PDriveToDistance(0.4, 9);
      }
    }
    autonomousCommand.start();
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // Init hatch target finding vision camera
    //cameraControlStateMachine.identifyTargets();

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.

    System.out.println("---------------------");

    arm.Lock();

    //logger.openFile();

    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    // Start your engines
    // defaultDriveTrain.start();
  }

  /**
   * This function is called periodically during operator control.
   */

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    // Set current vision pan/tilt joystick values if Chuck's logitech joystick is plugged in
    /*if (logitechVisionOI != null) {
      cameraControlStateMachine.slew(logitechVisionOI.getVisionLeftXAxis(), logitechVisionOI.getVisionLeftYAxis());
    }*/

    //logger.logAll();
  }

  @Override
  public void testPeriodic() {
  }

  public void updateSmartDashboard() {
    liftGear.updateSmartDashboard();
    driveTrain.updateSmartDashboard();
    //frontCameraMount.updateSmartDashboard();
    //backCameraMount.updateSmartDashboard();
    arm.updateSmartDashboard();
    elevator.updateSmartDashboard();
    frontLineDetector.updateSmartDashboard();
   // backLineDetector.updateSmartDashboard();
    frontInfraredRangeFinder.updateSmartDashboard();
    backInfraredRangeFinder.updateSmartDashboard();
    buttonBox.updateSmartDashboard();

    SmartDashboard.putNumber("Delta Time", kDeltaTime);
    SmartDashboard.putBoolean("Paths Loaded", PathManager.getInstance().loaded);
  }

  public void updateSmartDashboardRequired() {
    SmartDashboard.putNumber("Delta Time", kDeltaTime);
  }

  public enum AutonomousOptions {
    LeftCargoShip, RightCargoShip, LeftBottomRocket,
    RightBottomRocket, DoNothing, DriveOffHab1,
    DriveOffHab2
  }
}
