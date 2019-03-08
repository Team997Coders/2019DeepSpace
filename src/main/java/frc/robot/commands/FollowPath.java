/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.MotionProfile;
import frc.robot.PathManager;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FollowPath extends Command {
  private String _pathName;

  private MotionProfile path;

  public boolean failsafe = false;

  public FollowPath(String Pathname) {
    requires(Robot.driveTrain);
    this._pathName = Pathname;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    path = PathManager.getInstance().getProfile(_pathName);

    if (path == null) {
      System.out.println("\nPath '" + _pathName + "' is null\n");
      failsafe = true;
    } else {
      path.startPath();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (failsafe) { return; }

    path.followPath();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    if (failsafe) { return true; }

    return path.isFinished();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    
    path.Abort();

    Robot.driveTrain.setVolts(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}