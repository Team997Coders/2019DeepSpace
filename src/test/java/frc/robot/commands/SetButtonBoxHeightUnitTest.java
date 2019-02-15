/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import frc.robot.buttonbox.*;

import frc.robot.RobotMap;
import frc.robot.subsystems.Elevator;

import org.junit.Test;

/**
 * Add your docs here.
 */
public class SetButtonBoxHeightUnitTest {
    @Test
    public void itSetsFrontBallRocketHigh(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.High);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorFrontTopCargoHeight);
    }


    public void itSetsFrontBallRocketMedium(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Medium);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorFrontMiddleCargoHeight);
    }


    public void itSetsFrontBallRocketLow(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorFrontBottomCargoHeight);
    }

    public void itSetsFrontBallCargoShip(){
        //Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorFrontShipCargoHeight);

    }






    public void itSetsFrontHatchRocketHigh(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.High);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorFrontTopHatchHeight);
    }


    public void itSetsFrontHatchRocketMedium(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Medium);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorFrontMiddleHatchHeight);
    }


    public void itSetsFrontHatchRocketLow(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorFrontBottomHatchHeight);
    }

    public void itSetsFrontHatchCargoShip(){
        //Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorFrontShipHatchHeight);

    }

    public void itSetsBackBallRocketHigh(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.High);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorBackTopCargoHeight);
    }


    public void itSetsBackBallRocketMedium(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Medium);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorBackMiddleCargoHeight);
    }


    public void itSetsBackBallRocketLow(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorBackBottomCargoHeight);
    }

    public void itSetsBackBallCargoShip(){
        //Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorBackShipCargoHeight);

    }






    public void itSetsBackHatchRocketHigh(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.High);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorBackTopHatchHeight);
    }


    public void itSetsBackHatchRocketMedium(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Medium);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorBackMiddleHatchHeight);
    }


    public void itSetsBackHatchRocketLow(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorBackBottomHatchHeight);
    }

    public void itSetsBackHatchCargoShip(){
        //Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        Elevator elevator = mock(Elevator.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(elevator, buttonBox);

        // Act
        dut.execute();

        // Assert
        verify(elevator, times(1)).SetPosition(RobotMap.ElevatorHeights.elevatorBackShipHatchHeight);

    }

}
