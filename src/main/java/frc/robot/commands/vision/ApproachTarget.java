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
import frc.robot.RobotMap;
import frc.robot.misc.MiniPID;

public class ApproachTarget extends Command {

  private double speed, maximumArea;

  private MiniPID controller;

  public ApproachTarget(double speed, double maximumArea) {
    requires(Robot.driveTrain);
    this.speed = speed;
    this.maximumArea = maximumArea;
  }

  @Override
  protected void initialize() {
    controller = new MiniPID(RobotMap.Values.limeTestP, RobotMap.Values.limeTestI, RobotMap.Values.limeTestD);
  }

  @Override
  protected void execute() {
    double error = Robot.limeLight.getDouble("tx", 0);
    double turn = controller.getOutput(0, error);
    Robot.driveTrain.setVolts(speed + turn, speed - turn);
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
