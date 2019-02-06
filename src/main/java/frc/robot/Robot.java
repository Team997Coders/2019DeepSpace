/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.net.UnknownHostException;

import org.team997coders.spartanlib.commands.CenterCamera;

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
  public static LiftGear liftGear;
  public static DriveTrain driveTrain;
  public static LineFollowing lineFollowing;
  public static CameraMount cameraMount;
  // Note this could be null and because we continue to wire these up
  // in this manner (statics), guards will have to be put around all accesses.
  // Otherwise null pointer exceptions will drive you crazy, in the case
  // we do not connect to the Pi for some reason.
  public static CameraVisionClient cameraVisionClient;
  private PanTiltCamera panTiltCamera;
  private ProcessCameraMountCommands processCameraMountCommands;
  private CenterCamera centerCamera;
  
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
    liftGear = new LiftGear();
    driveTrain = new DriveTrain();
    lineFollowing = new LineFollowing();
    cameraMount = new CameraMount(45, 120, 20, 160);

    // Connect to remote vision subsystem
    centerCamera = new CenterCamera(cameraMount);
    try {
      cameraVisionClient = new CameraVisionClient("10.9.97.6");
      cameraVisionClient.connect();
    } catch (UnknownHostException e) {
      System.out.println("Can't connect to vision subsystem...incorrect Pi IP address.");
      System.out.println("Robot will proceed without camera pan/tilt control.");
    }
    panTiltCamera = new PanTiltCamera();
    try {
      processCameraMountCommands = new ProcessCameraMountCommands();
    } catch (Exception e)
    {}

    oi = new OI();

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
    centerCamera.start();
    panTiltCamera.start();
    processCameraMountCommands.start();
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
    centerCamera.start();
    panTiltCamera.start();
    processCameraMountCommands.start();
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
