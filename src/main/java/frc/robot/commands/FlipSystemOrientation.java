/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.FlipScoringSide;
import frc.robot.Robot;
import frc.robot.commands.ArcadeDrive;
public class FlipSystemOrientation extends CommandGroup {
  /**
   * Add your docs here.
   */
  public FlipSystemOrientation() {
    addSequential(new FlipScoringSide(Robot.scoringSideReversed));
  }
}