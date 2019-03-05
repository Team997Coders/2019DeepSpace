/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision.commands;

import edu.wpi.first.wpilibj.command.Command;


import frc.robot.vision.CameraControlStateMachine;
import frc.robot.Robot;
import frc.robot.vision.SelectedTarget;
import frc.robot.subsystems.DriveTrain;
import frc.robot.buttonbox.ButtonBox;

public class DriveParallelToTarget extends Command {
  private CameraControlStateMachine camera = Robot.cameraControlStateMachine;
  public DriveTrain driveTrain = Robot.driveTrain;
  public ButtonBox buttonBox = Robot.buttonBox;

  public DriveParallelToTarget() {
  
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    if(buttonBox.getScoringDirectionState() == ButtonBox.ScoringDirectionStates.Front){
      driveTrain.setVolts(.5,.5);
    }else if(buttonBox.getScoringDirectionState() == ButtonBox.ScoringDirectionStates.Back){
      driveTrain.setVolts(-.5,-.5);
    }
    
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    SelectedTarget selectedTarget = camera.getSelectedTarget();

    if (selectedTarget.angleToTargetInDegrees <= 2 && selectedTarget.angleToTargetInDegrees >= -2) {
      return true;
    }else{
      return false;
    }
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
