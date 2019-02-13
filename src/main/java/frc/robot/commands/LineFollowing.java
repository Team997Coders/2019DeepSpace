/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Sensors;

public class LineFollowing extends CommandGroup {
  /**
   * Add your docs here.
   */
  public LineFollowing() {
    addSequential(new FollowLine(
      new Sensors(RobotMap.Ports.lineSensorFrontLeft, 
        RobotMap.Ports.lineSensorFrontCenter, 
        RobotMap.Ports.lineSensorFrontRight, 
        RobotMap.Ports.frontInfraredSensor),
      Robot.driveTrain,
      1000
      )
    );
  /*addSequential(new Waittill(.5));
  addSequential(new BackingUp());*/
}
}
