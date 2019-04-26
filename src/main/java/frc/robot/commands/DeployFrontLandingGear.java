/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

public class DeployFrontLandingGear extends Command {
  public DeployFrontLandingGear() {
    requires(Robot.liftGear);
  }
  
  @Override
  protected void initialize() { }
  
  @Override
  protected void execute() {
    if (!Robot.liftGear.getFrontPistonState()) {
      Robot.liftGear.extendFront();
    }
  }
  
  @Override
  protected boolean isFinished() {
    return true; //Robot.liftGear.getFrontIRSensorVoltage() < 0.4;
  }

  @Override
  protected void end() {
    Scheduler.getInstance().add(new AutoRetractLandingGear());
  }
  
  @Override
  protected void interrupted() { }
}
