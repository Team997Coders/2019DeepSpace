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
  // TODO: Put these as tunable values in RobotMap
  private double powerMotor = 0.8;
  private double noPowerMotor = -.5;
  private double normal = .1; //for double line seen
  private double straight = .35;
  // to here

  private final long gracePeriodTimeInMs;
  private long gracePeriodStartTimeInMs;
  private final LineDetector lineDetector;
  private final InfraredRangeFinder infraredRangeFinder;
  private boolean inGracePeriod;
  private final ButtonBox.ScoringDirectionStates scoringDirection;
  private final ButtonBox.ScoringDestinationStates scoringDestination;
  private final ButtonBox.ScoringArtifactStates scoringArtifact;
  private final DriveTrain driveTrain;

  public FollowLine(long gracePeriodTimeInMs) {
    this(Robot.frontLineDetector, 
      Robot.backLineDetector, 
      Robot.frontInfraredRangeFinder,
      Robot.backInfraredRangeFinder,
      Robot.driveTrain, 
      gracePeriodTimeInMs, 
      Robot.buttonBox.getScoringDirectionState(),
      Robot.buttonBox.getScoringDestinationState(),
      Robot.buttonBox.getScoringArtifactState());
  }

  public FollowLine(
      LineDetector frontLineDetector, 
      LineDetector backLineDetector, 
      InfraredRangeFinder frontInfraredRangeFinder,
      InfraredRangeFinder backInfraredRangeFinder,
      DriveTrain driveTrain, 
      long gracePeriodTimeInMs, 
      ButtonBox.ScoringDirectionStates scoringDirection,
      ButtonBox.ScoringDestinationStates scoringDestination,
      ButtonBox.ScoringArtifactStates scoringArtifact) {

    // Set member variables
    this.gracePeriodTimeInMs = gracePeriodTimeInMs;
    this.scoringDirection = scoringDirection;
    this.driveTrain = driveTrain;
    this.scoringDestination = scoringDestination;
    this.scoringArtifact = scoringArtifact;
    
    // Set up appropriate sensors based on our current scoring direction
    if(scoringDirection == ButtonBox.ScoringDirectionStates.Back){
      lineDetector = backLineDetector;
      infraredRangeFinder = backInfraredRangeFinder;
    } else {
      lineDetector = frontLineDetector;
      infraredRangeFinder = frontInfraredRangeFinder;
    }

    // Require subsystems
    requires(lineDetector);
    requires(driveTrain);
    requires(infraredRangeFinder);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {    
    inGracePeriod = false;
    driveTrain.setBrake();
    // If scoring from the back side, negate power values;
    if(scoringDirection == ButtonBox.ScoringDirectionStates.Back) {
      System.out.println("init new value");
      powerMotor = -powerMotor;
      noPowerMotor = -noPowerMotor;
      normal = -normal;
      straight = -straight;
    }
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
      driveTrain.setVolts(normal, powerMotor);
    } else if(lineDetector.rightCenterLineSeen()){
      driveTrain.setVolts(powerMotor, normal);
    } else if(lineDetector.leftLineSeen()){
      driveTrain.setVolts(noPowerMotor, powerMotor);
    } else if(lineDetector.rightLineSeen()){
      driveTrain.setVolts(powerMotor, noPowerMotor);
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
      return true;
    } else {
      return false;
    }
  }

  protected boolean isCloseToTarget() {
    if (scoringDirection == ButtonBox.ScoringDirectionStates.Back) {
      if (scoringDestination == ButtonBox.ScoringDestinationStates.Rocket) {
        if (scoringArtifact == ButtonBox.ScoringArtifactStates.Hatch) {
          throw new RuntimeException("Back/Hatch/Rocket is not valid!");
        } else if (scoringArtifact == ButtonBox.ScoringArtifactStates.Ball) {
          return (infraredRangeFinder.getRawValue() < RobotMap.Values.backInfraredSensorBallRocket);
        } else {
          throw new RuntimeException("Scoring artifact is unknown or must be set.");
        }
      } else if (scoringDestination == ButtonBox.ScoringDestinationStates.CargoShip) {
        if (scoringArtifact == ButtonBox.ScoringArtifactStates.Hatch){
          return (infraredRangeFinder.getRawValue() < RobotMap.Values.backInfraredSensorHatchCargoship);
        } else if (scoringArtifact == ButtonBox.ScoringArtifactStates.Ball) {
          return (infraredRangeFinder.getRawValue() < RobotMap.Values.backInfraredSensorBallCargoship);
        } else {
          throw new RuntimeException("Scoring artifact is unknown or must be set.");
        }
      } else {
        throw new RuntimeException("Scoring destination is unknown or must be set.");
      }
    } else if (scoringDirection == ButtonBox.ScoringDirectionStates.Front) {
      if (scoringDestination == ButtonBox.ScoringDestinationStates.Rocket) {
        if (scoringArtifact == ButtonBox.ScoringArtifactStates.Hatch) {
          return (infraredRangeFinder.getRawValue() < RobotMap.Values.frontInfraredSensorHatchRocket);
        } else if (scoringArtifact == ButtonBox.ScoringArtifactStates.Ball) {
          return(infraredRangeFinder.getRawValue() < RobotMap.Values.frontUltrasonicSensorBallCargoship);
        } else {
          throw new RuntimeException("Scoring artifact is unknown or must be set.");
        }
      } else if (scoringDestination == ButtonBox.ScoringDestinationStates.CargoShip) {
        if (scoringArtifact == ButtonBox.ScoringArtifactStates.Hatch) {
          return(infraredRangeFinder.getRawValue() < RobotMap.Values.frontInfraredSensorHatchCargoship);
        } else if (scoringArtifact == ButtonBox.ScoringArtifactStates.Ball) {
            return(infraredRangeFinder.getRawValue() < RobotMap.Values.frontUltrasonicSensorBallCargoship);
        } else {
          throw new RuntimeException("Scoring artifact is unknown or must be set.");
        }
      } else {
        throw new RuntimeException("Scoring destination is unknown or must be set.");
      }
    } else {
      throw new RuntimeException("Scoring direction is unknown or must be set.");
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