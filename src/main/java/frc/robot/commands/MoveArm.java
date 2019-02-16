/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 *
 */
public class MoveArm extends Command {

  double speed;
  //public double value;
  //public double position;

  public MoveArm(double speed) {
    requires(Robot.arm);
    this.speed = speed;
    //this.value = _value;
  }

  protected void initialize() {
    Robot.arm.releaseBrake();
    //position = Robot.arm.readEncoder();
  }

  protected void execute() {
    // if (Robot.arm.readEncoder() >= RobotMap.Values.armBackLimit && value > 0) {
    //   Scheduler.getInstance().add(new LockArm());
    // } else {
    //   Robot.arm.releaseBrake();
    //   Robot.elevator.SetPower(value);
    // }

    System.out.println("Moving Arm " + speed);
    Robot.arm.setPower(speed);
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.arm.engageBrake();
    Robot.arm.setPower(0);
    //Robot.arm.SetPostion(Robot.arm.readEncoder());
  }

  protected void interrupted() {
    end();
  }
}