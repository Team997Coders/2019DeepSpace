/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Hab2ToRightCargoShip extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Hab2ToRightCargoShip() {
    addSequential(new DriveToDistance(47.64 / 12));//goes to Hab1
    addSequential(new DriveToDistance(24.14 / 12));//gets off platform
    addSequential(new DriveToAngle(21.7223));//Turns robot in direction of right cargo ship
    addSequential(new DriveToDistance(127.332 / 12));//goes to the right cargo ship
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
