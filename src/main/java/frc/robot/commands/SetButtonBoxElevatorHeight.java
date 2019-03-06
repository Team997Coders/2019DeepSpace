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
import frc.robot.RobotMap;

public class SetButtonBoxElevatorHeight extends Command {
  ButtonBox buttonBox;
  ElevatorArmSetpoint setpoint;

  public SetButtonBoxElevatorHeight() {
    this(Robot.buttonBox, new ElevatorArmSetpoint());
    // Use requires() here to declare subsystem dependencies
  }

  public SetButtonBoxElevatorHeight(ButtonBox buttonBox, ElevatorArmSetpoint elevatorArmSetpoint) {
    this.buttonBox = buttonBox;
    this.setpoint = elevatorArmSetpoint;
    // Use requires() here to declare subsystem dependencies
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    switch (buttonBox.getScoringDirectionState()) {
      case Front:
        switch (buttonBox.getScoringArtifactState()) {
          case Ball:
            switch (buttonBox.getScoringDestinationState()) {
              case Rocket:
                switch (buttonBox.getPositionState()) {
                  case High:
                    //setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontTopCargoHeight, 45);
                    setpoint.setHeightAndAngle(Robot.jl.getHeight("FrontBallRocketHigh"), Robot.jl.getAngle("FrontBallRocketHigh"));
                    break;
                  case Medium:
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontMiddleCargoHeight, 0);
                    break;
                  case Low:
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontBottomCargoHeight, 0);
                    break;
                  case None:
                    break;
                }
                break;
              case CargoShip:
                setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontShipCargoHeight, 0);
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
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontTopHatchHeight, 0);
                    break;
                  case Medium:
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontMiddleHatchHeight, 0);
                    break;
                  case Low:
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontBottomHatchHeight, 0);
                    break;
                  case None:
                    //TODO: add in what should happen when none
                    break;
                }
                break;
              case CargoShip:
                setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontShipHatchHeight, 0);
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
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackTopCargoHeight, 180);
                    break;
                  case Medium:
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackMiddleCargoHeight, 180);
                    break;
                  case Low:
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackBottomCargoHeight, 180);
                    break;
                  case None:
                    break;
                }
                break;
              case CargoShip:
                setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackShipCargoHeight, 180);
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
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackTopHatchHeight, 180);
                    break;
                  case Medium:
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackMiddleHatchHeight, 180);
                    break;
                  case Low:
                    setpoint.setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackBottomHatchHeight, 180);
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
