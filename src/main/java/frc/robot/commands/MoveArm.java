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
import frc.robot.RobotMap;

/**
 *
 */
public class MoveArm extends Command {
  public double value;
  public double position;

  public MoveArm(double _value) {
    requires(Robot.arm);
    this.value = _value;
  }

  protected void initialize() {
    position = Robot.arm.readEncoder();
  }

  protected void execute() {
    if (Robot.arm.readEncoder() >= RobotMap.Values.armBackLimit && value > 0) {
      Scheduler.getInstance().add(new LockArm());
    } else {
      Robot.arm.releaseBrake();
      Robot.elevator.SetPower(value);
    }
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
  }

  protected void interrupted() {
    // end();
  }
}