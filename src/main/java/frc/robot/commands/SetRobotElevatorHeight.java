/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.data.RobotState;

public class SetRobotElevatorHeight extends Command {

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Command setpoint = null;

    switch (RobotState.getScoringDirectionState()) {
      case Front:
        switch (RobotState.getScoringArtifactState()) {
          case Ball:
            switch (RobotState.getScoringDestinationState()) {
              case Rocket:
                switch (RobotState.getPositionState()) {
                  case High:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontTopCargoHeight, 45);
                    break;
                  case Medium:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontMiddleCargoHeight, 0);
                    break;
                  case Low:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontBottomCargoHeight, 0);
                    break;
                  case None:
                    break;
                }
                break;
              case CargoShip:
                setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontShipCargoHeight, 0);
                break;
              case None:
                break;
            }
            break;
          case Hatch:
            switch (RobotState.getScoringDestinationState()) {
              case Rocket:
                switch (RobotState.getPositionState()) {
                  case High:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontTopHatchHeight, 0);
                    break;
                  case Medium:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontMiddleHatchHeight, 0);
                    break;
                  case Low:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontBottomHatchHeight, 0);
                    break;
                  case None:
                    //TODO: add in what should happen when none
                    break;
                }
                break;
              case CargoShip:
                setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorFrontShipHatchHeight, 0);
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
        switch (RobotState.getScoringArtifactState()) {
          case Ball:
            switch (RobotState.getScoringDestinationState()) {
              case Rocket:
                switch (RobotState.getPositionState()) {
                  case High:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackTopCargoHeight, 180);
                    break;
                  case Medium:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackMiddleCargoHeight, 180);
                    break;
                  case Low:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackBottomCargoHeight, 180);
                    break;
                  case None:
                    break;
                }
                break;
              case CargoShip:
                setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackShipCargoHeight, 180);
                break;
              case None:
                //TODO: add in case
                break;
            }
            break;
          case Hatch:
            switch (RobotState.getScoringDestinationState()) {
              case Rocket:
                switch (RobotState.getPositionState()) {
                  case High:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackTopHatchHeight, 180);
                    break;
                  case Medium:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackMiddleHatchHeight, 180);
                    break;
                  case Low:
                    setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackBottomHatchHeight, 180);
                    break;
                  case None:
                    //TODO: add in what should happen when none
                    break;
                }
                break;
              case CargoShip:
                setpoint = new ElevatorArmSetpoint(RobotMap.ElevatorHeights.elevatorBackShipHatchHeight, 180);
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
    if (setpoint != null) {
      setpoint.start();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
