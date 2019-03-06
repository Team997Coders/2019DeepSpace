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
/*
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
*/

    @Test
    public void itSetsFrontBallRocketMedium(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Medium);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontMiddleCargoHeight, 0);
    }

    @Test
    public void itSetsFrontBallRocketLow(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontBottomCargoHeight, 0);
    }

    @Test
    public void itSetsFrontBallCargoShip(){
        //Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontShipCargoHeight, 0);

    }

    @Test
    public void itSetsFrontHatchRocketHigh(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.High);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontTopHatchHeight, 0);
    }

    @Test
    public void itSetsFrontHatchRocketMedium(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Medium);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontMiddleHatchHeight, 0);
    }

    @Test
    public void itSetsFrontHatchRocketLow(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontBottomHatchHeight, 0);
    }

    @Test
    public void itSetsFrontHatchCargoShip(){
        //Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorFrontShipHatchHeight, 0);

    }

    @Test
    public void itSetsBackBallRocketHigh(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.High);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackTopCargoHeight, 0);
    }


    @Test
    public void itSetsBackBallRocketMedium(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Medium);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackMiddleCargoHeight, 0);
    }


    @Test
    public void itSetsBackBallRocketLow(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackBottomCargoHeight, 0);
    }

    @Test
    public void itSetsBackBallCargoShip(){
        //Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Ball);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.CargoShip);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackShipCargoHeight, 0);

    }

    @Test
    public void itSetsBackHatchRocketHigh(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.High);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackTopHatchHeight, 0);
    }


    @Test
    public void itSetsBackHatchRocketMedium(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Medium);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackMiddleHatchHeight, 0);
    }


    @Test
    public void itSetsBackHatchRocketLow(){
        // Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackBottomHatchHeight, 0);
    }

    @Test
    public void itSetsBackHatchCargoShip(){
        //Assemble
        ButtonBox buttonBox = mock(ButtonBox.class);
        ElevatorArmSetpoint setpoint = mock(ElevatorArmSetpoint.class);

        when(buttonBox.getPositionState()).thenReturn(ButtonBox.PositionStates.Low);
        when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
        when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.CargoShip);
        SetButtonBoxElevatorHeight dut = new SetButtonBoxElevatorHeight(buttonBox, setpoint);

        // Act
        dut.initialize();

        // Assert
        verify(setpoint, times(1)).setHeightAndAngle(RobotMap.ElevatorHeights.elevatorBackShipHatchHeight, 100);
    }

}