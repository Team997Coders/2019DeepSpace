/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.misc.SecretSpeedBoost;

public class SpeedBoostB extends Command {
  public SpeedBoostB() { }

  @Override
  protected void initialize() { }

  @Override
  protected void execute() { SecretSpeedBoost.checkB = true; }

  @Override
  protected boolean isFinished() { return true; }

  // Called once after isFinished returns true
  @Override
  protected void end() { }

  @Override
  protected void interrupted() { }
}
