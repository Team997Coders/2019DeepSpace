/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.data;

/**
 * Add your docs here.
 */
public class RobotState {
    private static ScoringDirectionStates scoringDirectionState;
    private static ScoringArtifactStates scoringArtifactState;
    private static ScoringDestinationStates scoringDestinationState;
    private static LiftPositionStates liftPositionState;
    private static boolean intakeState;

    public enum ScoringDirectionStates {
        None, Front, Back
    }

    public enum ScoringArtifactStates {
        None, Ball, Hatch
    }

    public enum ScoringDestinationStates {
        None, CargoShip, Rocket
    }

    public enum LiftPositionStates {
        None, High, Medium, Low
    }

    public static ScoringDestinationStates getScoringDestinationState() {
        return scoringDestinationState;
    }

    public static ScoringDirectionStates getScoringDirectionState() {
        return scoringDirectionState;
    }

    public static ScoringArtifactStates getScoringArtifactState() {
        return scoringArtifactState;
    }

    public static LiftPositionStates getPositionState() {
        return liftPositionState;
    }

    public static boolean getIntakeState() {
        return intakeState;
    }

    /**
     * Set the cancel state. All settings are sent to none.
     */
    public void setCancelState() {
        scoringDirectionState = ScoringDirectionStates.None;
        scoringArtifactState = ScoringArtifactStates.None;
        scoringDestinationState = ScoringDestinationStates.None;
        liftPositionState = LiftPositionStates.None;
        intakeState = false;
    }

    /**
     * Set the robot startup state. Robot will start in the back facing position, so
     * set scoring direction to back.
     */
    public void setStartupState() {
        scoringDirectionState = ScoringDirectionStates.Back;
        scoringArtifactState = ScoringArtifactStates.Hatch;
        scoringDestinationState = ScoringDestinationStates.CargoShip;
        liftPositionState = LiftPositionStates.None;
        intakeState = false;
    }

    /**
     * Set the robot startup state. Robot will start in the back facing position, so
     * set scoring direction to back.
     */
    public static void setRobotState(ScoringDirectionStates _direction, ScoringArtifactStates _artifact,
            ScoringDestinationStates _destination, LiftPositionStates _position) {
        scoringDirectionState = _direction;
        scoringArtifactState = _artifact;
        scoringDestinationState = _destination;
        liftPositionState = _position;
        intakeState = false;
    }

}
