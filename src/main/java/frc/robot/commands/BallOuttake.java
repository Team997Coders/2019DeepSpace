package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

import frc.robot.subsystems.BallManipulator;

public class BallOuttake extends Command {
  public BallOuttake() {
    requires(Robot.ballManipulator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

import frc.robot.RobotMap;
import frc.robot.subsystems.DriveTrain;

public class ArcadeDrive extends Command {

  public ArcadeDrive() {
    requires(Robot.driveTrain);
    System.out.println("Arcade Init");
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

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    Robot.ballManipulator.ballOuttake();

    double left = Robot.oi.getLeftYAxis() + Robot.oi.getRightXAxis();
    double right = Robot.oi.getLeftYAxis() - Robot.oi.getRightXAxis();

    if (Robot.driveTrain.decell) {
      Robot.driveTrain.setVoltsDecel(left, right);
    } else {
      Robot.driveTrain.setVolts(left, right);
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
    Robot.ballManipulator.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
