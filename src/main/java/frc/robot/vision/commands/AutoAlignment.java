/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LineDetector;



public class AutoAlignment extends Command {

  public AutoAlignment() {
    requires(Robot.driveTrain);
    requires(Robot.backLineDetector);
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
  
   double cameraAngle = Robot.logitechVisionOI.getVisionLeftXAxis();

    while(cameraAngle >= 90 ){
      Robot.driveTrain.setVolts(-.5,-.5);//Negative because the camera is on the back
      while(cameraAngle !=90){
        Robot.driveTrain.setVolts(.5,-.5);
      }
    }
    while(cameraAngle <= 90){
      Robot.driveTrain.setVolts(-.5, -.5);//Negative because the camera is on the back
      while(cameraAngle !=90){
        Robot.driveTrain.setVolts(-.5,.5);
      }
    }
    if(cameraAngle == 90 && LineDetector.noLineSeen()){ //TODO: make 'noLineSeen' static before testing
      Robot.driveTrain.setVolts(-.5, -.5); //Negativve because the camera is on the back
    }    
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


























