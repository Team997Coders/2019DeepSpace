package frc.robot.commands;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandChain extends Command {

  private int step = 0;

  private ArrayList<Command> commands = new ArrayList<>();

  public CommandChain() { }

  protected void addSeq(Command com) {
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
  protected void end() { System.out.println("Command Chain ended"); }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() { System.out.println("Command Chain interrupted"); end(); }
}
