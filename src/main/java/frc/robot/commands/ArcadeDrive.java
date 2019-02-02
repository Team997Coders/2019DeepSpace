package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ArcadeDrive extends Command {
  public ArcadeDrive() {
    requires(Robot.driveTrain);
  }

  @Override
  protected void initialize() {
    if (Robot.driveTrain.decell) {
      Robot.driveTrain.setBrake();
    } else {
      Robot.driveTrain.setCoast();
    }

    Robot.driveTrain.resetEncoders();
  }

  @Override
  protected void execute() {
    double left = Robot.oi.getLeftYAxis() + Robot.oi.getRightXAxis();
    double right = Robot.oi.getLeftYAxis() - Robot.oi.getRightXAxis();

    left = Robot.oi.deadBand(left, 0.005);
    right = Robot.oi.deadBand(right, 0.005);

    if (Robot.driveTrain.decell) {
      Robot.driveTrain.setVoltsDecel(left, right);
    } else {
      Robot.driveTrain.setVolts(left, right);
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.driveTrain.stopVolts();
  }

  @Override
  protected void interrupted() { end(); }
}
