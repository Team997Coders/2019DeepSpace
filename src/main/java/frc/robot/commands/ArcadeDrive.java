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
    Robot.driveTrain.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
