/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DeployBackLandingGear extends Command {

  private boolean deployed;

  public DeployBackLandingGear() {
    requires(Robot.liftGear);
    deployed = false;
  }
  
  @Override
  protected void initialize() { }
  
  @Override
  protected void execute() {
    if (!Robot.liftGear.getBackPistonState()) {
      Robot.liftGear.extendBack();
    }

    if (Robot.liftGear.getBackIRSensorVoltage() < 0.7) {
      deployed = true;
    }
  }
  
  @Override
  protected boolean isFinished() {
    return (Robot.liftGear.getBackIRSensorVoltage() > 0.7 && deployed);
  }

  @Override
  protected void end() {
    if (Robot.liftGear.getBackPistonState()) {
      Robot.liftGear.retractBack();
    }
  }
  
  @Override
  protected void interrupted() { 
    if (Robot.liftGear.getBackPistonState()) {
      Robot.liftGear.retractBack();
    }
  }
}
