/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.buttonbox.ButtonBox;
import frc.robot.subsystems.Elevator;
import frc.robot.RobotMap;

public class SetButtonBoxElevatorHeight extends Command {
  Elevator elevator;
  ButtonBox buttonBox;

  public SetButtonBoxElevatorHeight() {
    this(Robot.elevator, Robot.buttonBox);
    // Use requires() here to declare subsystem dependencies
    requires(Robot.elevator);
  }

  public SetButtonBoxElevatorHeight(Elevator elevator, ButtonBox buttonBox) {
    this.elevator = elevator;
    this.buttonBox = buttonBox;

    // Use requires() here to declare subsystem dependencies
    requires(elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    switch (buttonBox.getScoringDirectionState()) {
      case Front:
        switch (buttonBox.getScoringArtifactState()) {
          case Ball:
            switch (buttonBox.getScoringDestinationState()) {
              case Rocket:
                switch (buttonBox.getPositionState()) {
                  case High:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorFrontTopCargoHeight);
                    break;
                  case Medium:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorFrontMiddleCargoHeight);
                    break;
                  case Low:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorFrontBottomCargoHeight);
                    break;
                  case None:
                    break;
                }
                break;
              case CargoShip:
                elevator.SetPosition(RobotMap.ElevatorHeights.elevatorFrontShipCargoHeight);
                break;
              case None:
                break;
            }
            break;
          case Hatch:
            switch (buttonBox.getScoringDestinationState()) {
              case Rocket:
                switch (buttonBox.getPositionState()) {
                  case High:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorFrontTopHatchHeight);
                    break;
                  case Medium:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorFrontMiddleHatchHeight);
                    break;
                  case Low:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorFrontBottomHatchHeight);
                    break;
                  case None:
                    //TODO: add in what should happen when none
                    break;
                }
                break;
              case CargoShip:
                elevator.SetPosition(RobotMap.ElevatorHeights.elevatorFrontShipHatchHeight);
                break;
              case None:
                //TODO Add in case
                break;
            }
            break;
          case None:
            //TODO add in case
            break;
        }
        break;
      case Back:
        switch (buttonBox.getScoringArtifactState()) {
          case Ball:
            switch (buttonBox.getScoringDestinationState()) {
              case Rocket:
                switch (buttonBox.getPositionState()) {
                  case High:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorBackTopCargoHeight);
                    break;
                  case Medium:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorBackMiddleCargoHeight);
                    break;
                  case Low:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorBackBottomCargoHeight);
                    break;
                  case None:
                    break;
                }
                break;
              case CargoShip:
                elevator.SetPosition(RobotMap.ElevatorHeights.elevatorBackShipCargoHeight);
                break;
              case None:
                //TODO: add in case
                break;
            }
            break;
          case Hatch:
            switch (buttonBox.getScoringDestinationState()) {
              case Rocket:
                switch (buttonBox.getPositionState()) {
                  case High:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorBackTopHatchHeight);
                    break;
                  case Medium:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorBackMiddleHatchHeight);
                    break;
                  case Low:
                    elevator.SetPosition(RobotMap.ElevatorHeights.elevatorBackBottomHatchHeight);
                    break;
                  case None:
                    //TODO: add in what should happen when none
                    break;
                }
                break;
              case CargoShip:
                elevator.SetPosition(RobotMap.ElevatorHeights.elevatorBackShipHatchHeight);
                break;                
              case None:
                //TODO: add in case
                break;
            }
            break;
          case None:
            //TODO: add in case
            break;
        }
        break;
      case None:
        //TODO: add in case
        break;
    }
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
