/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TankDrive extends Command {

  public TankDrive() {
    requires(Robot.driveTrain);
  }

  @Override
  protected void initialize() { }

  @Override
  protected void execute() {
    double left = Robot.oi.getLeftYAxis();
    double right = Robot.oi.getRightYAxis();

    Robot.driveTrain.setVolts(left, right);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.driveTrain.setVolts(0, 0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}