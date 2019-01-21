/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.DriveTrain;

public class ArcadeDrive extends Command {

  public ArcadeDrive() {
    requires(Robot.driveTrain);
    System.out.println("Arcade Init");
  }

  @Override
  protected void initialize() { }

  @Override
  protected void execute() {
    //System.out.println("Arcade Execute");
    double left = Robot.oi.getLeftY() + Robot.oi.getRightX();
    double right = Robot.oi.getLeftY() - Robot.oi.getRightX();

    SmartDashboard.putNumber("Left Voltage", left);
    SmartDashboard.putNumber("RIght Voltage", right);

    Robot.driveTrain.setVolts(left, right);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.driveTrain.setVolts(0, 0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
