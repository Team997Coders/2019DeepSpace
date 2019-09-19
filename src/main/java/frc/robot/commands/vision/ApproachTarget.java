/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class ApproachTarget extends Command {

  private double speed, maximumArea;

  public ApproachTarget(double speed, double maximumArea) {
    requires(Robot.driveTrain);
    this.speed = speed;
    this.maximumArea = maximumArea;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    double mod = 0.01;
    double error = Robot.limeLight.getDouble("tx", 0);
    double turn = mod * error;
    Robot.driveTrain.setVolts(speed + turn, speed - turn);

    SmartDashboard.putNumber("Vision Turn Value", turn);
  }

  @Override
  protected boolean isFinished() {
    return Robot.limeLight.getDouble("ta", 0) >= maximumArea ? true : false;
  }

  @Override
  protected void end() {
    SmartDashboard.putBoolean("Approaching", false);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
