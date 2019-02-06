/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.Robot;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class ScoreGamePiece extends Command {
  private boolean gamePieceType; // True = hatch false = cargo
  private boolean scoringSide; // back = true front = false
  private int elevatorHeight; // selects from array of heights
  
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
    if(scoringSide != Robot.arm.getArmSide()){
      if(Robot.elevator.GetPositon() < RobotMap.Values.armSwitchHeight) {
      Robot.elevator.SetPosition(RobotMap.Values.armSwitchHeight);
    }
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (scoringSide != Robot.arm.getArmSide() && Robot.elevator.GetPositon() >= RobotMap.Values.armSwitchHeight){
      
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
