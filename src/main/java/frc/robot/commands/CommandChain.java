/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;

public class CommandChain extends Command {

  private int step = 0;

  private ArrayList<Command> commands;

  public CommandChain() {
    commands = new ArrayList<>();
  }

  public void addCommand(Command com) {
    commands.add(com);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (commands.get(step).isCompleted()) {
      step++;
    } else if (!commands.get(step).isRunning()) {
      commands.get(step).start();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return step >= commands.size();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() { }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() { System.out.println("Command Chain interrupted"); end(); }
}
