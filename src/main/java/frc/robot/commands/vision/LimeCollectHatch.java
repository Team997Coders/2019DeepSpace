/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.LockArm;
import frc.robot.misc.MiniPID;

public class LimeCollectHatch extends Command {

  private MiniPID controller;
  private double 
    P = 0.2,
    I = 0.0,
    D = 0.0;

  public LimeCollectHatch() {
    requires(Robot.hatchManipulator);
    controller = new MiniPID(P, I, D);
    controller.setOutputLimits(-0.4, 0.4);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.hatchManipulator.retract();
    Scheduler.getInstance().add(new LockArm(RobotMap.Values.armFrontParallel));
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double sideCorrection = controller.getOutput(0, Robot.limeLight.getDouble(RobotMap.LimeLight.targetX, 0));
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
