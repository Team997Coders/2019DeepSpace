/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutoDoNothing;
import frc.robot.buttonbox.ButtonBox;
//import frc.robot.subsystems.*;
import frc.robot.commands.*;

//import spartanlib.subsystem.drivetrain.TankDrive;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.BallManipulator;

import frc.robot.subsystems.CameraMount;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.HatchManipulator;
import frc.robot.subsystems.LiftGear;
import frc.robot.subsystems.Logger;
import frc.robot.subsystems.Sensors;
import frc.robot.vision.cameravisionclient.CameraVisionClient;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static boolean scoringSideReversed = false;
  private FlipSystemOrientation flipSystemOrientation;
  public static Arm arm;
  public static Elevator elevator;
  public static BallManipulator ballManipulator;
  public static HatchManipulator hatchManipulator;
  public static LiftGear liftGear;
  public static DriveTrain driveTrain;
  public static CameraMount cameraMount;
  public static Logger logger;
  public static PowerDistributionPanel pdp;
  public static Sensors sensors;
  public static ButtonBox buttonBox;

  // Note this could be null and because we continue to wire these up
  // in this manner (statics), guards will have to be put around all accesses.
  // Otherwise null pointer exceptions will drive you crazy, in the case
  // we do not connect to the Pi for some reason.
  public static CameraVisionClient cameraVisionClient;
  public PanTiltCamera panTiltCamera;

  public static OI oi;
  public static ButtonBoxOI bb;

  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  public static int heightIndex; 
  // used by the scoringHeight logic commands to grab the correct height from
  // the height array in RobotMap.

  public Robot(DriveTrain a, Sensors b) {
    super();
    driveTrain = a;
    sensors = b;
  }

  public Robot() {
    super();
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    arm = new Arm();
    ballManipulator = new BallManipulator();

    hatchManipulator = new HatchManipulator();

    elevator = new Elevator();

    // driveTrain = new DriveTrain();

    liftGear = new LiftGear();
    driveTrain = new DriveTrain();
    cameraMount = new CameraMount(0, 120, 10, 170);
    buttonBox = new ButtonBox();

    // Connect to remote vision subsystem
    try {
      cameraVisionClient = new CameraVisionClient("10.9.97.6");
    } catch (IOException e) {
      // TODO: What is going to be the timing of roborio network availability, boot
      // speed,
      // and Pi boot speed? Need to test.
      System.out.println("Can't connect to vision subsystem...do we need to put in a retry loop?");
      System.out.println("Robot will proceed blind.");
    }

    // Create the logging instance so we can use it for tuning the PID subsystems
    logger = Logger.getInstance();

    // Instanciate the Power Distribution Panel so that we can get the currents
    // however, we need to clear the faults so that the LEDs on the PDP go green.
    // I can never (and I have tried) find the source of the warnings that cause
    // the LED's to be Amber.
    pdp = new PowerDistributionPanel();
    pdp.clearStickyFaults();

    // Because there is no hardware subsystem directly hooked up
    // to this command (it is a proxy for calling CameraVision on Pi)
    // there is not default command to keep this active. So manually start
    // here...
    panTiltCamera = new PanTiltCamera();
    panTiltCamera.start();

    chooser.setDefaultOption("Do Nothing", new AutoDoNothing());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", chooser);

    // Need to flip the system orientation to the rear to begin the match
    flipSystemOrientation = new FlipSystemOrientation();
    flipSystemOrientation.start();

    // Make these last so to chase away the dreaded null subsystem errors!
    oi = new OI();
    bb = new ButtonBoxOI();
  }

  @Override
  public void robotPeriodic() {
    updateSmartDashboard();
  }

  @Override
  public void disabledInit() {
    driveTrain.setCoast(); // So the drivers don't want to kill us ;)
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    autonomousCommand = chooser.getSelected();

    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.

    System.out.println("--------------------");

    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    // Start your engines
    // defaultDriveTrain.start();
  }

  double lastTime = 0;

  /**
   * This function is called periodically during operator control.
   */

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }

  public void updateSmartDashboard() {
    SmartDashboard.putBoolean("Scoring Side Reversed?", scoringSideReversed);
    liftGear.updateSmartDashboard();
    driveTrain.updateSmartDashboard();
    arm.updateSmartDashboard();
    elevator.updateSmartDashboard();
  }
}
