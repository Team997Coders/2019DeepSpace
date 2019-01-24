package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToAngle extends Command {
	private double angSetpoint;
	private double minError = 2;
	private double initYaw = -999; 
	private double integral = 0;
	private double previousError = 0;

    public DriveToAngle(double _ang) {
    	requires(Robot.driveTrain);
    	angSetpoint = _ang;
    } 

    protected void initialize() {
    	initYaw = Robot.driveTrain.getAngle();
    	Robot.driveTrain.setBrakeMode();
    	System.out.println("PDriveAngle - Init PAngle" + initYaw);
    }
    
    public double yawCorrect() {
    	// Calculate full PID
    	// pfactor = (P × error) + (I × ∑error) + (D × δerrorδt)
    	double error = this.piderror();
    	// Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
    	this.integral += (error * .02);
    	// Derivative is change in error over time
    	double derivative = (error - this.previousError) / .02;
        this.previousError = error;
        return 
        	(Robot.driveTrain.DriveAngleP * error) + 
        	(Robot.driveTrain.DriveAngleI * this.integral) + 
        	(Robot.driveTrain.DriveAngleD * derivative);
    }

    protected void execute() {
    	// calculate yaw correction
    	double yawcorrect = this.yawCorrect();
    	Robot.driveTrain.setVolts(Util.clamp(yawcorrect, -1, 1), Util.clamp(-yawcorrect, -1, 1)); 
    	// Debug information to be placed on the smart dashboard.
    	SmartDashboard.putNumber("PDriveToAngle: Angle Error", piderror());
    	SmartDashboard.putNumber("PDriveToAngle: Theta Angle Correction", yawcorrect);
    	SmartDashboard.putBoolean("PDriveToAngle: On Angle Target", onTarget());
    	SmartDashboard.putNumber("PDriveToAngle: Init Angle Yaw", initYaw);
    }

    private double piderror() {
    	return initYaw + angSetpoint - Robot.driveTrain.getAngle();
    }
    
    private boolean onTarget() {
    	return Math.abs(piderror()) < minError;
    }

    protected boolean isFinished() {
        return onTarget();   
    }

    protected void end() {
    	Robot.driveTrain.setVolts(0, 0);
    	System.out.println("PDriveAngle - PAngle End");
    }

    protected void interrupted() {
    	end();
    }
}