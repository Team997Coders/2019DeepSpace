package frc.robot.vision.commands;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import frc.robot.buttonbox.ButtonBox;
import frc.robot.subsystems.DriveTrain;
import frc.robot.vision.CameraControlStateMachine;
import frc.robot.vision.SelectedTarget;
import frc.robot.vision.commands.TurnParallelToTarget;

public class TurnParallelToTargetUnitTest {
    @Test
    public void itSteersRightWhenCameraAngleGreaterThan0FacingFront() {
        // Assemble
        CameraControlStateMachine camera = mock(CameraControlStateMachine.class);
        SelectedTarget selectedTarget = mock(SelectedTarget.class);
        DriveTrain driveTrain = mock(DriveTrain.class);
        ButtonBox buttonBox = mock(ButtonBox.class);
        // TODO: Chuck, fix me please!
        when(selectedTarget.cameraAngleInDegrees).thenReturn(30D);
        when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
        when(camera.getSelectedTarget()).thenReturn(selectedTarget);
        TurnParallelToTarget turnParallelToTarget = new TurnParallelToTarget(camera, driveTrain, buttonBox);

        // Act
        turnParallelToTarget.execute();

        // Assert
        verify(driveTrain, times(1)).setVolts(.5,-.5);
    }
}