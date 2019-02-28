/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command does basically what 2 other commands do
 */
public class ScoreGamePiece extends Command {
  private boolean gamePieceType; // True = hatch | false = cargo
  private boolean scoringSide; // back = true | front = false
  private int elevatorHeight; // height in ticks, scoringHeightLogic should take care of actually getting this.
  private int tolerance= 10;
  
  public ScoreGamePiece(boolean gamePieceType,boolean scoringSide, int elevatorHeight) {
    requires(Robot.arm);
    requires(Robot.elevator);
    this.elevatorHeight = elevatorHeight;
    this.gamePieceType = gamePieceType;
    this.scoringSide= scoringSide;

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (scoringSide == true) {
      Robot.arm.SetPostion(RobotMap.Values.armBackLimit);
    } else {
      Robot.arm.SetPostion(RobotMap.Values.armFrontLimit);
    }
    Robot.arm.releaseBrake();
    if (scoringSide == Robot.arm.getArmSide() || elevatorHeight > Robot.elevator.GetPosition()) {
      Robot.elevator.SetPosition(elevatorHeight);
    }
    Robot.arm.UpdateF();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Math.abs(Robot.elevator.GetPosition()-elevatorHeight) < tolerance && Robot.arm.getArmSide() == scoringSide){
      return true;
    }else{
      return false;
    }
  }

  // Called once after isFinished returns true=
  @Override
  protected void end() {
    Robot.arm.engageBrake();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
