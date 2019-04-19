package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    double front = Robot.oi.getLeftYAxis();
    double turn = Robot.oi.getRightXAxis();
    SmartDashboard.putNumber("Drive/Left Y", front);
    SmartDashboard.putNumber("Drive/Turn", turn);

    //Robot.driveTrain.setVolts(front + turn, front - turn);

    Robot.driveTrain.setRampArcadeVolts(front, 0.8 * turn);

    /*if (Robot.driveTrain.decell) {
      Robot.driveTrain.setRampArcadeVolts(front, turn);
    } else {
      Robot.driveTrain.setVolts(front + turn, front - turn);
    }*/
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
  protected void interrupted() {
    end();
  }
}