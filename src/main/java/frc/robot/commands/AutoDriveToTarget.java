/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.vision.CameraControlStateMachine;
import frc.robot.vision.SelectedTarget;
import frc.robot.vision.TargetNotLockedException;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utils;
import frc.robot.RobotMap;

/**
 * The pièces de résistance<p>
 * Hunter, work your magic...<p>
 * This command will start when the driver pushes the
 * start driving button on the vision HUD.<p>
 * This maybe should be named AutoDriveToTargetUnderVisionControl...because
 * we will probably have a command group called AutoDriveToTarget that combines
 * this command with LineFollowing.<p>
 * I'll keep going...probably another called ScoreNow which moves manipulators
 * and auto drives.
 */
public class AutoDriveToTarget extends Command {
  private final DriveTrain driveTrain;
  private boolean weMadeIt;

  private double distSetpoint;
	private double minError = 3;
	public Timer timer = new Timer();
	private double lastTime = 0;
	private double lastVoltage = 0;
	private double deltaT = 0;
	private double speed = 0.25;
	private double initYaw = -999;
	private double Ktheta = 0.02;
	private double integral, previous_error = 0;
	private double targetAngle;

  public AutoDriveToTarget() {
    this(Robot.driveTrain);
  }

  public AutoDriveToTarget(DriveTrain driveTrain) {
    this.driveTrain = driveTrain;
    requires(driveTrain);
  }
   
    // Called just before this Command runs the first time
    protected void initialize() {
    	lastVoltage = 0;
    	Robot.driveTrain.resetEncoders();
    	Robot.driveTrain.setBrake();
    	initYaw = Robot.driveTrain.getGyroAngle();
    	this.previous_error = this.piderror();
    	timer.reset();
    	timer.start();
		System.out.println("(PDTD-INIT) OMG, I got initialized!!! :O");
		System.out.println("targetAngle: " + targetAngle);
    	lastTime = 0;
    }
    
    // current algorithm assumes that we are starting
    // from a stop
    private double linearAccel(double input) {
    	double Klin = 0.8;
    	double deltaT = timer.get() - lastTime;
    	lastTime = timer.get();
    	
    	double Volts = lastVoltage + Klin * (deltaT);
    	if (Volts > input) {
    		Volts = input;
    	}
    	lastVoltage = Volts;
    	return Volts;
    }

    
    public double pFactor() {
    	// Calculate full PID
    	// pfactor = (P × error) + (I × ∑error) + (D × δerrorδt)
    	double error = this.piderror();
    	// Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
    	this.integral += (error * .02);
    	// Derivative is change in error over time
    	double derivative = (error - this.previous_error) / .02;
        this.previous_error = error;
        return 
        	(RobotMap.Values.driveDistanceP * error) + 
        	(RobotMap.Values.driveDistanceI * this.integral) + 
        	(RobotMap.Values.driveDistanceD * derivative);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		// compute the pid P value
		//System.out.println("Im driving");
		try {
			SelectedTarget selectedTarget = Robot.cameraControlStateMachine.getSelectedTarget();
			distSetpoint = (selectedTarget.rangeInInches- 24) * (RobotMap.Values.protobotTickPerFoot/12) ;
			targetAngle = selectedTarget.angleToTargetInDegrees;
			System.out.println("distance setpoint" + distSetpoint);
			System.out.println("target angle" + targetAngle);


			/*
			  selectedTarget contains: 
			  rangeInInches
			  cameraAngleInDegrees - camermount pan angle relative to robot, -90 to 90, with 0 being center
			  angleToTargetInDegrees - robot's angle to target, from target's POV, -90 to 90, with 0 being perpenticular to target
			*/
		  } catch (TargetNotLockedException e) {
			weMadeIt = false;
		  }
    	double pfactor = speed * Utils.clamp(this.pFactor(), -1, 1);
    	double pfactor2 = linearAccel(pfactor);
    	double deltaTheta = Robot.driveTrain.getGyroAngle() - initYaw;
    	deltaT = timer.get() - lastTime;
    	lastTime = timer.get();

    	// calculate yaw correction
    	double yawcorrect = deltaTheta * Ktheta;
    	
    	// set the output voltage
    	Robot.driveTrain.setVolts(-(pfactor2 + yawcorrect), -(pfactor2 - yawcorrect)); //TODO check these signs...
    	//Robot.driveTrain.SetVoltssetVolts(-pfactor, -pfactor); //without yaw correction, accel

    	// Debug information to be placed on the smart dashboard.
    	SmartDashboard.putNumber("Setpoint", distSetpoint);
    	SmartDashboard.putNumber("Encoder Distance", this.encoderDistance());
    	//SmartDashboard.putNumber("Encoder Rate", Robot.driveTrain.getEncoderRate());
    	SmartDashboard.putNumber("Distance Error", piderror());
    	SmartDashboard.putNumber("K-P factor", pfactor);
    	SmartDashboard.putNumber("K-P factor Accel", pfactor2);
    	SmartDashboard.putNumber("deltaT", deltaT);
    	SmartDashboard.putNumber("Theta Correction", yawcorrect);
    	SmartDashboard.putBoolean("On Target", onTarget());
    	SmartDashboard.putNumber("NavX Heading", Robot.driveTrain.getGyroAngle());
    	SmartDashboard.putNumber("Init Yaw", initYaw);
    }

    private double piderror() {
    	// shouldn't we average this out between both of the encoders?
    	return distSetpoint - this.encoderDistance();
    }
    
    private double encoderDistance() {
    	return (Robot.driveTrain.leftEncoderTicks() + Robot.driveTrain.rightEncoderTicks()) / 2;
    }
    
    private boolean onTarget() {
    	return piderror() < minError;
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.driveTrain.leftEncoderVelocity() <= 0 && 
    			Robot.driveTrain.rightEncoderVelocity() <= 0 &&
    			onTarget()) {
    		System.out.println("(PDTD-ISFINISHED) PDTD ended with isFinished!");
    		 return onTarget();
    	} else {
    		/*if (Robot.collector.getAvgLeftVoltage() > RobotMap.Values.autoIRthreshold ||
    			Robot.collector.getAvgRightVoltage() > RobotMap.Values.autoIRthreshold) {
    			return true;
    		} else {
    			return false;
    		}*/
    		return false;
    	}
    	
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	System.out.println(this.encoderDistance());
    	System.out.println("PDrive End");
    	timer.stop();
    	Robot.driveTrain.setVolts(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	System.out.println("(PDTD-INTERRUPTED) I got interrupted!! D:");
    }
  }
  

  