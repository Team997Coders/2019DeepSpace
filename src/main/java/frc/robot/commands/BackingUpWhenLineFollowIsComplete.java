/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*
 *  Notice!!! This command group is BROKEN!!!
 *  Since all of the commands in the command group are scheduled
 *  during initialization time, the conditionals are only computed once!
 *  We will need to break this apart into multiple smaller command groups.
 * 
 *  Also why are we backing up?  Do we know that we scored?  We didn't activate
 *  any scoring mechanisms.  And what if we never found the line? Or made it to 
 *  the goal?
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Sensors;

public class BackingUpWhenLineFollowIsComplete extends CommandGroup {
  /**
   * Add your docs here.
   */
  public BackingUpWhenLineFollowIsComplete() {
    if(Robot.scoringSideReversed){
      addSequential(new FlipDriveTrainOrientation(Robot.scoringSideReversed));
      addSequential(new FollowLine(
        new Sensors(RobotMap.Ports.lineSensorBackLeft, 
          RobotMap.Ports.lineSensorBackCenter, 
          RobotMap.Ports.lineSensorBackRight, 
          RobotMap.Ports.backInfraredSensor),
        Robot.driveTrain,
        1000
        )
      );
      addSequential(new FlipDriveTrainOrientation(!Robot.scoringSideReversed));
    }else{
      addSequential(new FollowLine(
        new Sensors(RobotMap.Ports.lineSensorFrontLeft, 
          RobotMap.Ports.lineSensorFrontCenter, 
          RobotMap.Ports.lineSensorFrontRight, 
          RobotMap.Ports.frontInfraredSensor),
        Robot.driveTrain,
        1000
        )
      );
    }
    addSequential(new Waittill(.5));
    addSequential(new BackingUp());
  }
}