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
    private static PositionStates positionState;
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

    public enum PositionStates {
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

    public static PositionStates getPositionState() {
        return positionState;
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
        positionState = PositionStates.None;
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
        positionState = PositionStates.None;
        intakeState = false;
    }

    /**
     * Set the robot startup state. Robot will start in the back facing position, so
     * set scoring direction to back.
     */
    public static void setRobotState(ScoringDirectionStates _direction, ScoringArtifactStates _artifact,
            ScoringDestinationStates _destination, PositionStates _position) {
        scoringDirectionState = _direction;
        scoringArtifactState = _artifact;
        scoringDestinationState = _destination;
        positionState = _position;
        intakeState = false;
    }

}
