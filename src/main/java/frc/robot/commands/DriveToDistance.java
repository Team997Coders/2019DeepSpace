/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveToDistance extends Command {
  double distance;
  double minError = 3;
  public DriveToDistance(double setpoint) {
    requires(Robot.driveTrain);
    distance = setpoint * RobotMap.Values.ticksInFoot; //changes parameter (feet) to ticks
    

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
    Robot.driveTrain.driveToPosition(distance);
    
  }

  /*protected double encoderDistance() {
    return (Robot.driveTrain.getLeftEncoderTicks() + Robot.driveTrain.getRightEncoderTicks())/ 2;
  }
  

  protected double distanceError() {

    return distance - this.encoderDistance();
  }

  protected boolean onTarget() {
    return distanceError() < minError;

  }*/

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.stopVolts();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
    System.out.println("(PDTD-INTERRUPTED) I got interrupted!! D:");
  }
}
