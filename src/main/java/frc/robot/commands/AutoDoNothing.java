/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class AutoDoNothing extends Command {
  public AutoDoNothing() { }

  @Override
  protected void initialize() { }

  @Override
  protected void execute() { }

  @Override
  protected boolean isFinished() { return true; }
  
  @Override
  protected void end() { System.out.println("Did nothing! Aren't you proud of me Daddy?"); }

  @Override
  protected void interrupted() { end(); }
}
