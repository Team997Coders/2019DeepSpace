/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.ElevatorArmSetpoint;
import frc.robot.commands.FollowLine;
import frc.robot.commands.PDriveToDistance;
import frc.robot.commands.Waittill;

public class Hab1ToCargoShipEndRightSide extends CommandGroup {
  public Hab1ToCargoShipEndRightSide() {
    addSequential(new PDriveToDistance(1000));            // Takes ticks unfortunately. We need to drive off the hab. How far is that in ticks? How about taking inches, please?
    // addSequential(new ElevatorArmSetpoint());          // Do we need to do this? Which values to use?
    addSequential(new AutoLock(AutoLock.Side.Right));     // Auto-lock the camera to the right side of the end of the cargo ship.
    addSequential(new TurnParallelToTarget());            // Turn to drive across the target
    addSequential(new DriveParallelToTarget());           // Drive across the target until directly in front of it
    addSequential(new TurnToFaceTarget());                // Turn to directly face target
    addSequential(new DriveStraightToTarget());           // Drive to the target and stop when over line following line
    addSequential(new FollowLine(1000));                  // Follow the line
    addSequential(new Waittill(.5));                      // Give hatch time to grab velcro
    //addSequential(new ReleaseHatchIfOnTarget());        // If distance sensor says we are close to something, release the hatch
    //addSequential(new BackingUp());                     // Finally back off
  }
}
