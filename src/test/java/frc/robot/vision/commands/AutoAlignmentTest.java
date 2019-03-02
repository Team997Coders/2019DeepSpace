/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.subsystems.DriveTrain;
import frc.robot.vision.commands.AutoAlignment;
import frc.robot.Robot;
import frc.robot.RobotMap;
import static org.mockito.Mockito.*;
import frc.robot.helpers.DriveTrainMocks;


import frc.robot.subsystems.InfraredRangeFinder;
import frc.robot.subsystems.LineDetector;

import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.assertEquals;



public class AutoAlignmentTest extends Command {
  DriveTrain driveTrain;
  AutoAlignment autoAlignment;

  @Before
  public void initializeMocks() {

    driveTrain = mock(DriveTrain.class);
    autoAlignment= mock(AutoAlignment.class);

    
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  @Test
  public void goStraightIfCameraAngleAt90(){
    //Assemble
    //DriveTrainMocks driveTrainMocks = new DriveTrainMocks();
    assertEquals(90, 90, 0);
    
    //Assert
    autoAlignment.execute();
    //assertEquals();
    //Act
    verify(driveTrain, times(1)).setVolts(-.5, -.5);
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
