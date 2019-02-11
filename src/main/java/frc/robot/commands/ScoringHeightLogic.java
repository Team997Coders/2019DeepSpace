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
import frc.robot.subsystems.Arm;

public class ScoringHeightLogic extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ScoringHeightLogic(boolean armSide, boolean gamePieceType,int scoringHeight, boolean scoreDestination) {
    int elevatorHeight= 0;
    if(armSide != Robot.arm.getArmSide()) {
      if(Robot.elevator.GetPosition() < RobotMap.Values.armSwitchHeight) {
        addSequential(new ElevatorToArmHeight(10));
      }
    }
    if(scoreDestination) {               //rocket = true cargoship = false
      if(gamePieceType) {                 //hatch = true cargo = false
        if(armSide) {
          elevatorHeight = RobotMap.ElevatorHeights.elevatorBackHatchHeightArray[scoringHeight];
        } else {
          elevatorHeight = RobotMap.ElevatorHeights.elevatorFrontHatchHeightArray[scoringHeight];
        } 
      } else {
        if(armSide) {
          elevatorHeight = RobotMap.ElevatorHeights.elevatorBackCargoHeightArray[scoringHeight];
        } else {
          elevatorHeight = RobotMap.ElevatorHeights.elevatorFrontCargoHeightArray[scoringHeight];
        }
      }
    }else{
      if(gamePieceType) {
        if(armSide) {
          elevatorHeight = RobotMap.ElevatorHeights.elevatorBackShipHatchHeight;
        } else {
          elevatorHeight = RobotMap.ElevatorHeights.elevatorFrontShipHatchHeight;
        }
      } else {
        if(armSide) {
          elevatorHeight = RobotMap.ElevatorHeights.elevatorBackShipCargoHeight;
        } else {
          elevatorHeight = RobotMap.ElevatorHeights.elevatorFrontShipCargoHeight;
        }
      }
    }
    

    addSequential(new ScoreGamePiece(gamePieceType, armSide, elevatorHeight));
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
