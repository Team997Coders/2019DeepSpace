/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.CameraMount;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LiftGear;
import frc.robot.subsystems.LineFollowing;
import frc.robot.vision.cameravisionclient.CameraVisionClient;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Will the getInstance call get the ArcadeDrive? It should.
  //private final Command defaultDriveTrain;
  public static OI oi;

  //(no drieTrain in merge)public static DriveTrain driveTrain;
  public static BallManipulator ballManipulator;
  //public static DriveTrain driveTrain;
  public static HatchManipulator hatchManipulator;


  public static LiftGear liftGear;
  public static DriveTrain driveTrain;
  public static LineFollowing lineFollowing;
  public static CameraMount cameraMount;
  // Note this could be null and because we continue to wire these up
  // in this manner (statics), guards will have to be put around all accesses.
  // Otherwise null pointer exceptions will drive you crazy, in the case
  // we do not connect to the Pi for some reason.
  public static CameraVisionClient cameraVisionClient;
  public PanTiltCamera panTiltCamera;

  

  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  public Robot(DriveTrain a, LineFollowing b) {
    super();
    driveTrain = a;
    lineFollowing = b;
  }

  public Robot() { super(); }
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    // ADD SUBSYSTEMS HERE
    //(no drive train in merge)driveTrain = new DriveTrain();
    ballManipulator = new BallManipulator();
    
    //driveTrain = new DriveTrain();

    liftGear = new LiftGear();
    driveTrain = new DriveTrain();
    lineFollowing = new LineFollowing();
    cameraMount = new CameraMount(0, 120, 10, 170);

    // Connect to remote vision subsystem
    try {
      cameraVisionClient = new CameraVisionClient("10.9.97.6");
    } catch (IOException e) {
      // TODO: What is going to be the timing of roborio network availability, boot speed,
      // and Pi boot speed? Need to test.
      System.out.println("Can't connect to vision subsystem...do we need to put in a retry loop?");
      System.out.println("Robot will proceed blind.");
    }


    oi = new OI();

    // Because there is no hardware subsystem directly hooked up
    // to this command (it is a proxy for calling CameraVision on Pi)
    // there is not default command to keep this active. So manually start
    // here...
    panTiltCamera = new PanTiltCamera();
    panTiltCamera.start();

    chooser.setDefaultOption("Do Nothing", new AutoDoNothing());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", chooser);

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
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    // Start your engines
    //defaultDriveTrain.start();
  }

  @Override
  public void teleopPeriodic() {
    lineFollowing.isCloseToTarget();

    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }

  public void updateSmartDashboard() {
    liftGear.updateSmartDashboard();
    driveTrain.updateSmartDashboard();
    lineFollowing.updateSmarts();
  }
}
