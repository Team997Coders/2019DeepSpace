/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.data.RobotState.ScoringDirectionStates;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Scheduler;

public class FlipArm extends Command {

  private double tolerance;
  private double setpoint;

  public FlipArm(double tolerance) {
    requires(Robot.arm);
    this.setpoint = Robot.arm.readEncoder();
    this.tolerance = tolerance;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //if (Robot.arm.getArmSide() == ScoringDirectionStates.Front) {
      //setpoint = RobotMap.Values.armBackParallel;
    //} else {
      setpoint = RobotMap.Values.armFrontParallel;
    //}

    //System.out.println("Initialized flipArm with setpoint " + setpoint);

    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.arm.SetPostion(setpoint);
      //System.out.println("FlipArm error is " + Math.abs(setpoint - Robot.arm.readEncoder()));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Math.abs(setpoint - Robot.arm.readEncoder()) <= tolerance);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.arm.setPower(0);
    Scheduler.getInstance().add(new LockArm());
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
