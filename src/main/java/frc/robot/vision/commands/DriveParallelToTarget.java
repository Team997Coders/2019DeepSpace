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
  private final CameraControlStateMachine cameraControlStateMachine;
  private final DriveTrain driveTrain;
  private final ButtonBox buttonBox;
  private final double speed = 0.16;

  public DriveParallelToTarget() {
    this(Robot.cameraControlStateMachine, Robot.driveTrain, Robot.buttonBox);
  }

  public DriveParallelToTarget(CameraControlStateMachine cameraControlStateMachine, 
      DriveTrain driveTrain, 
      ButtonBox buttonBox) {
    this.cameraControlStateMachine = cameraControlStateMachine;
    this.driveTrain = driveTrain;
    this.buttonBox = buttonBox;
    requires(driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    if(buttonBox.getScoringDirectionState() == ButtonBox.ScoringDirectionStates.Front){
      driveTrain.setVolts(speed,speed);
    }else if(buttonBox.getScoringDirectionState() == ButtonBox.ScoringDirectionStates.Back){
      driveTrain.setVolts(-speed,-speed);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // If we are not locked on to target, stop.
    if (cameraControlStateMachine.getState() != CameraControlStateMachine.State.AutoLocked) {
      return true;
    }

    SelectedTarget selectedTarget = cameraControlStateMachine.getSelectedTarget();

    if (selectedTarget.angleToTargetInDegrees <= 2 && selectedTarget.angleToTargetInDegrees >= -2) {
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
   
    driveTrain.setBrake();
    driveTrain.stopVolts();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
