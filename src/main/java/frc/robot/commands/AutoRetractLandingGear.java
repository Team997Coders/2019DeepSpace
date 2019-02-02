/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoRetractLandingGear extends Command {

  private boolean retracted = false;

  public AutoRetractLandingGear() {
    requires(Robot.liftGear);
  }
  
  @Override
  protected void initialize() { }
  
  @Override
  protected void execute() {
    if (Robot.liftGear.getIRVoltage() > 0.95) {
      retracted = true;
      Robot.liftGear.retract();
    }
  }
  
  @Override
  protected boolean isFinished() { return retracted; }
  
  @Override
  protected void end() { }
  
  @Override
  protected void interrupted() { end(); }
}
