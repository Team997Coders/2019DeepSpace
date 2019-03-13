/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.buttonbox.ButtonBox;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.InfraredRangeFinder;
import frc.robot.subsystems.LineDetector;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Follow a line on the floor and stop when range is close
 * to target.
 */
public class FollowLine extends Command {
  private final long gracePeriodTimeInMs;
  private long gracePeriodStartTimeInMs;
  private LineDetector lineDetector;

  //private final LineDetector backLineDetector;
  private final LineDetector frontLineDetector;
  private InfraredRangeFinder infraredRangeFinder;
  private final InfraredRangeFinder backInfraredRangeFinder;
  private final InfraredRangeFinder frontInfraredRangeFinder;
  private boolean inGracePeriod;
  private ButtonBox buttonBox;
  private final DriveTrain driveTrain;
  private double powerMotor;
  private double noPowerMotor;
  private double normal;
  private double straight;

  public FollowLine(long gracePeriodTimeInMs) {    
    this(Robot.frontLineDetector, 
     // Robot.backLineDetector, 
      Robot.frontInfraredRangeFinder,
      Robot.backInfraredRangeFinder,
      Robot.driveTrain, 
      gracePeriodTimeInMs, 
      Robot.buttonBox);
  }

 


  public FollowLine(
      LineDetector frontLineDetector, 
      //LineDetector backLineDetector, 
      InfraredRangeFinder frontInfraredRangeFinder,
      InfraredRangeFinder backInfraredRangeFinder,
      DriveTrain driveTrain, 
      long gracePeriodTimeInMs, 
      ButtonBox buttonBox) {

    // Set member variables
    this.gracePeriodTimeInMs = gracePeriodTimeInMs;
    this.buttonBox = buttonBox;
    this.driveTrain = driveTrain;
    this.frontInfraredRangeFinder = frontInfraredRangeFinder;
    this.backInfraredRangeFinder = backInfraredRangeFinder;
    this.frontLineDetector = frontLineDetector;
  //  this.backLineDetector = backLineDetector;

    // Require subsystems
    requires(frontInfraredRangeFinder);
    requires(backInfraredRangeFinder);
    requires(frontLineDetector);
  //  requires(backLineDetector);
    requires(driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {    
    inGracePeriod = false;
    driveTrain.setBrake();
    // If scoring from the back side, negate power values;
    if(buttonBox.getScoringDirectionState() == ButtonBox.ScoringDirectionStates.Back) {
      this.noPowerMotor = -RobotMap.Values.noPowerMotor;
      this.normal = -RobotMap.Values.normal;
      this.straight = -RobotMap.Values.straight;
      this.powerMotor = -RobotMap.Values.powerMotor;
    } else {
      this.noPowerMotor = RobotMap.Values.noPowerMotor;
      this.normal = RobotMap.Values.normal;
      this.straight = RobotMap.Values.straight;
      this.powerMotor = RobotMap.Values.powerMotor;
    }
    
    // Set up appropriate sensors based on our current scoring direction
    /*
    if(buttonBox.getScoringDirectionState() == ButtonBox.ScoringDirectionStates.Back) {
      lineDetector = backLineDetector;
      infraredRangeFinder = backInfraredRangeFinder;
    } else {
      lineDetector = frontLineDetector;
      infraredRangeFinder = frontInfraredRangeFinder;
    }
    */
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(lineDetector.noLineSeen() && !inGracePeriod){
      inGracePeriod = true;
      gracePeriodStartTimeInMs = System.currentTimeMillis();
      driveTrain.setVolts(straight, straight);
    } else if(lineDetector.noLineSeen() && inGracePeriod) {
      driveTrain.setVolts(straight, straight);
    } else if (lineDetector.anyLineSeen() && inGracePeriod) {
      driveTrain.setBrake();
      inGracePeriod = false;
    } else if (lineDetector.leftCenterLineSeen()){
      driveTrain.setVolts(powerMotor, normal);
    } else if(lineDetector.rightCenterLineSeen()){
      driveTrain.setVolts(normal, powerMotor);
    } else if(lineDetector.leftLineSeen()){
      driveTrain.setVolts(powerMotor, noPowerMotor);
    } else if(lineDetector.rightLineSeen()){
      driveTrain.setVolts(noPowerMotor, powerMotor);
    } else if(lineDetector.centerLineSeen()){
      driveTrain.setVolts(straight, straight);
    } else {
      // Because we have a grace period, which should catch this
      // contition above, go ahead and keep driving straight.
      // If we set this to zero, then the grace period will stop working.
      driveTrain.setVolts(straight, straight);
    }
  }

  protected boolean gracePeriodExpired() {
    return (System.currentTimeMillis() > (gracePeriodStartTimeInMs + gracePeriodTimeInMs));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
   if (isCloseToTarget()) {
      return true;
    } else if (inGracePeriod && gracePeriodExpired()) {
      driveTrain.setBrake();
      return true;
    } else {
      return false;
    }
  }

  protected boolean isCloseToTarget() {
    try {/*
    if (buttonBox.getScoringDirectionState() == ButtonBox.ScoringDirectionStates.Back) {
      if (buttonBox.getScoringDestinationState() == ButtonBox.ScoringDestinationStates.Rocket) {
        if (buttonBox.getScoringArtifactState() == ButtonBox.ScoringArtifactStates.Hatch) {
          throw new RuntimeException("Back/Hatch/Rocket is not valid!");
        } else if (buttonBox.getScoringArtifactState() == ButtonBox.ScoringArtifactStates.Ball) {
          return (infraredRangeFinder.getRawValue() > RobotMap.Values.backInfraredSensorBallRocket);
        } else {
          throw new RuntimeException("Scoring artifact is unknown or must be set.");
        }
      } else if (buttonBox.getScoringDestinationState() == ButtonBox.ScoringDestinationStates.CargoShip) {
        if (buttonBox.getScoringArtifactState() == ButtonBox.ScoringArtifactStates.Hatch){
          return (infraredRangeFinder.getRawValue() > RobotMap.Values.backInfraredSensorHatchCargoship);
        } else if (buttonBox.getScoringArtifactState() == ButtonBox.ScoringArtifactStates.Ball) {
          return (infraredRangeFinder.getRawValue() > RobotMap.Values.backInfraredSensorBallCargoship);
        } else {
          throw new RuntimeException("Scoring artifact is unknown or must be set.");
        }
      } else {
        throw new RuntimeException("Scoring destination is unknown or must be set.");
      }
    }*/
    if (buttonBox.getScoringDirectionState() == ButtonBox.ScoringDirectionStates.Front) {
      if (buttonBox.getScoringDestinationState() == ButtonBox.ScoringDestinationStates.Rocket) {
        if (buttonBox.getScoringArtifactState() == ButtonBox.ScoringArtifactStates.Hatch) {
          return (infraredRangeFinder.getRawValue() > RobotMap.Values.frontInfraredSensorHatchRocket);
        } else if (buttonBox.getScoringArtifactState() == ButtonBox.ScoringArtifactStates.Ball) {
          return(infraredRangeFinder.getRawValue() > RobotMap.Values.frontUltrasonicSensorBallCargoship);
        } else {
          throw new RuntimeException("Scoring artifact is unknown or must be set.");
        }
      } else if (buttonBox.getScoringDestinationState() == ButtonBox.ScoringDestinationStates.CargoShip) {
        if (buttonBox.getScoringArtifactState() == ButtonBox.ScoringArtifactStates.Hatch) {
          return(infraredRangeFinder.getRawValue() > RobotMap.Values.frontInfraredSensorHatchCargoship);
        } else if (buttonBox.getScoringArtifactState() == ButtonBox.ScoringArtifactStates.Ball) {
            return(infraredRangeFinder.getRawValue() > RobotMap.Values.frontUltrasonicSensorBallCargoship);
        } else {
          throw new RuntimeException("Scoring artifact is unknown or must be set.");
        }
      } else {
        throw new RuntimeException("Scoring destination is unknown or must be set.");
      }
    } else {
      throw new RuntimeException("Scoring direction is unknown or must be set.");
    }
    } catch (Exception e) {
      e.printStackTrace();
      return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    driveTrain.setVolts(0,0);
    driveTrain.setCoast();
  }

  @Override
  protected void interrupted() {
    end();
  }
}