/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;
import frc.robot.RobotMap;


public class SwitchArmPostion extends Command {
  public int state;
  public double elevatorHeight;
  public SwitchArmPostion() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.arm);
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() { 
    elevatorHeight = Robot.elevator.GetPositon();
    state= Robot.arm.getLimit();
    if(elevatorHeight < RobotMap.Values.armSwitchHeight){
      Robot.elevator.SetPosition(RobotMap.Values.armSwitchHeight);
    }
    
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(state == 1){
      Robot.arm.setSpeed(.5);
    }
    else if (state == 2){
      Robot.arm.setSpeed(-.5);
    }
    else{
      Robot.arm.zeroArm();
    }
    Robot.arm.releaseBrake(); // This is hear so that the arm has power before
                              // realeasing the break and does not flop.
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Robot.arm.getLimit() != 0 && Robot.arm.getLimit() != state){
      return true;
    }
      else{
        return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.arm.setSpeed(0);
    Robot.arm.engageBrake();
    Robot.elevator.SetPosition(elevatorHeight);

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
