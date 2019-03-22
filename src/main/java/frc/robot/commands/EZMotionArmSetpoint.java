package frc.robot.commands;

import frc.robot.TrapezoidalMotion;
import frc.robot.Robot;

public class EZMotionArmSetpoint extends Command {

  private double distance = 0;
  private double dirMod = 1;

  private double lastPos = 0;

  private TrapezoidalMotion t;

  public EZMotionArmSetpoint(double position) {

    requires(Robot.arm);

    distance = position -  Robot.arm.readEncoder();
    if (distance < 0) {
      dirMod = -1;
      distance *= -1;
    }
  }

  @Override
  protected void initialize() {
    t = new TrapezoidalMotion(accel, maxVelo);
    t.init(distance);
  }

  @Override
  protected void execute() {
    lastPos = t.getPosition();
    Robot.arm.setPosition(Robot.readEncoder() + (lastPos * dirMod));
  }

  @Override
  protected boolean isFinished() {
    return (lastPos == distance);
  }

  @Override
  protected void end() {
    Robot.arm.engageBrake();
    Scheduler.getInstance().add(new LockArm());
  }

  @Override
  protected void interrupted() {
    end();
  }

}
