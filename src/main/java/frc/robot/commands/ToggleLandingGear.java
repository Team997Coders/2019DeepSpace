/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import  frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

// Make a retract command
public class ToggleLandingGear extends Command {
  public ToggleLandingGear() {
    requires(Robot.liftGear);
  }

  @Override
  protected void initialize() { }

  @Override
  protected void execute() {
    if (Robot.liftGear.getPistonState()) {
      Robot.liftGear.retract();
    } else {
      Robot.liftGear.extend();
    }
  }
  
  @Override
  protected boolean isFinished() {
    return true;
  }
  
  @Override
  protected void end() { }
  
  @Override
  protected void interrupted() {
    end();
  }
}
