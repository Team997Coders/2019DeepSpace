/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

import frc.robot.RobotMap;
import frc.robot.subsystems.DriveTrain;

public class TankDrive extends Command {
  private Joystick gamepad1;  
  private DriveTrain driveTrain;

  public TankDrive(Joystick gamepad1, DriveTrain driveTrain) {
    this.gamepad1 = gamepad1;
    this.driveTrain = driveTrain;
    requires(driveTrain);
  }

  @Override
  protected void initialize() { }

  private double getLeftYAxis() {
    return -gamepad1.getRawAxis(RobotMap.Ports.leftYAxis);
  }

  private double getRightYAxis() {    
    return -gamepad1.getRawAxis(RobotMap.Ports.rightYAxis);
  }

  @Override
  protected void execute() {
    double left = getLeftYAxis();
    double right = getRightYAxis();

    driveTrain.setVolts(left, right);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    driveTrain.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }
}