package frc.robot.buttonbox;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import edu.wpi.first.wpilibj.command.Command;

public class ButtonBoxUnitTest {
  @Test
  public void itSetsScoringArtifactStateToBallWhenClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();

    // Act
    console.clickScoringArtifactBallButton();

    // Assert
    assertEquals(ButtonBox.ScoringArtifactStates.Ball, console.getScoringArtifactState());
  }

  @Test
  public void itSetsScoringArtifactStateToHatchWhenClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();

    // Act
    console.clickScoringArtifactHatchButton();

    // Assert
    assertEquals(ButtonBox.ScoringArtifactStates.Hatch, console.getScoringArtifactState());
  }

  @Test
  public void itSetsScoringDestinationStateToRocketWhenClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringDirectionFrontButton();

    // Act
    console.clickScoringDestinationRocketButton();

    // Assert
    assertEquals(ButtonBox.ScoringDestinationStates.Rocket, console.getScoringDestinationState());
  }

  @Test
  public void itSetsScoringDestinationStateToCargoShipWhenClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();

    // Act
    console.clickScoringDestinationCargoShipButton();

    // Assert
    assertEquals(ButtonBox.ScoringDestinationStates.CargoShip, console.getScoringDestinationState());
  }

  @Test
  public void itSetsScoringDirectionStateToBackWhenClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();

    // Act
    console.clickScoringDirectionBackButton();

    // Assert
    assertEquals(ButtonBox.ScoringDirectionStates.Back, console.getScoringDirectionState());
  }

  @Test
  public void itSetsScoringDirectionStateToFrontWhenClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();

    // Act
    console.clickScoringDirectionFrontButton();

    // Assert
    assertEquals(ButtonBox.ScoringDirectionStates.Front, console.getScoringDirectionState());
  }

  @Test
  public void itSetsPositionToHighWhenClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringDirectionFrontButton();
    console.clickScoringDestinationRocketButton();

    // Act
    console.clickHighPositionButton();

    // Assert
    assertEquals(ButtonBox.PositionStates.High, console.getPositionState());
  }

  @Test
  public void itSetsPositionToMeduimWhenClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringDirectionFrontButton();
    console.clickScoringDestinationRocketButton();

    // Act
    console.clickMediumPositionButton();

    // Assert
    assertEquals(ButtonBox.PositionStates.Medium, console.getPositionState());
  }

  @Test
  public void itSetsPositionToLowWhenClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringDirectionFrontButton();
    console.clickScoringDestinationRocketButton();

    // Act
    console.clickLowPositionButton();

    // Assert
    assertEquals(ButtonBox.PositionStates.Low, console.getPositionState());
  }

  @Test
  public void itTogglesIntakeWhenClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();

    // Act
    console.clickIntakeButton();

    // Assert
    assertEquals(true, console.getIntakeState());
  }

  @Test
  public void itWillNotSetHighHeightWhenDestinationCargoShip() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringDestinationCargoShipButton();

    // Act
    console.clickHighPositionButton();

    // Assert
    assertEquals(ButtonBox.PositionStates.None, console.getPositionState());
  }

  @Test
  public void itWillNotSetMediumHeightWhenDestinationCargoShip() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringDestinationCargoShipButton();

    // Act
    console.clickMediumPositionButton();

    // Assert
    assertEquals(ButtonBox.PositionStates.None, console.getPositionState());
  }

  @Test
  public void itWillNotSetLowHeightWhenDestinationCargoShip() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringDestinationCargoShipButton();

    // Act
    console.clickLowPositionButton();

    // Assert
    assertEquals(ButtonBox.PositionStates.None, console.getPositionState());
  }

  @Test
  public void itStartsActivatedCommandWhenActivateButtonClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    Command activateCommand = mock(Command.class);
    console.whenActivateClicked(activateCommand);

    // Act
    console.clickActivateButton();

    // Assert
    verify(activateCommand, times(1)).start();
  }

  @Test
  public void itSetsStateCorrectlyWhenInitialized() {
    // Assemble
    ButtonBox console = new ButtonBox();

    // Assert
    assertEquals(ButtonBox.ScoringDirectionStates.Back, console.getScoringDirectionState());
    assertEquals(ButtonBox.ScoringDestinationStates.CargoShip, console.getScoringDestinationState());
    assertEquals(ButtonBox.ScoringArtifactStates.Hatch, console.getScoringArtifactState());
    assertEquals(ButtonBox.PositionStates.None, console.getPositionState());
    assertEquals(false, console.getIntakeState());
  }
  
  @Test
  public void itStartsBCommandWhenBButtonClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    Command visionBClickedCommand = mock(Command.class);
    console.whenVisionBClicked(visionBClickedCommand);

    // Act
    console.clickVisionBButton();

    // Assert
    verify(visionBClickedCommand, times(1)).start();
  }

  @Test
  public void itStartsXCommandWhenXButtonClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    Command visionXClickedCommand = mock(Command.class);
    console.whenVisionXClicked(visionXClickedCommand);

    // Act
    console.clickVisionXButton();

    // Assert
    verify(visionXClickedCommand, times(1)).start();
  }

  @Test
  public void itStartsLeftCommandWhenLeftButtonClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    Command visionLeftClickedCommand = mock(Command.class);
    console.whenVisionLeftClicked(visionLeftClickedCommand);

    // Act
    console.clickVisionLeftButton();

    // Assert
    verify(visionLeftClickedCommand, times(1)).start();
  }

  @Test
  public void itStartsRightCommandWhenRightButtonClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    Command visionRightClickedCommand = mock(Command.class);
    console.whenVisionRightClicked(visionRightClickedCommand);

    // Act
    console.clickVisionRightButton();

    // Assert
    verify(visionRightClickedCommand, times(1)).start();
  }

  @Test
  public void itStartsCenterCommandWhenCenterButtonClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    Command visionCenterClickedCommand = mock(Command.class);
    console.whenVisionCenterClicked(visionCenterClickedCommand);

    // Act
    console.clickVisionCenterButton();

    // Assert
    verify(visionCenterClickedCommand, times(1)).start();
  }

  @Test
  public void itClearsHeightWhenCargoShipButtonClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickHighPositionButton();

    // Act
    console.clickScoringDestinationCargoShipButton();

    // Assert
    assertEquals(ButtonBox.PositionStates.None, console.getPositionState());
  }

  @Test
  public void itDoesNotAllowBackHatchRocket() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringDirectionBackButton();
    console.clickScoringArtifactHatchButton();
    console.clickScoringDestinationCargoShipButton();

    // Act
    console.clickScoringDestinationRocketButton();

    // Assert
    assertEquals(ButtonBox.ScoringDestinationStates.CargoShip, console.getScoringDestinationState());
  }

  @Test
  public void itFlipsRocketToCargoShipWhenHatchSetAndBackSelected() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringArtifactHatchButton();
    console.clickScoringDestinationRocketButton();

    // Act
    console.clickScoringDirectionBackButton();

    // Assert
    assertEquals(ButtonBox.ScoringDestinationStates.CargoShip, console.getScoringDestinationState());
  }

  @Test
  public void itFlipsRocketToCargoShipWhenBackSetAndHatchSelected() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringDirectionBackButton();
    console.clickScoringDestinationRocketButton();

    // Act
    console.clickScoringArtifactHatchButton();

    // Assert
    assertEquals(ButtonBox.ScoringDestinationStates.CargoShip, console.getScoringDestinationState());
  }

  @Test
  public void itClearsStatesWhenCancelButtonClicked() {
    // Assemble
    ButtonBox console = new ButtonBox();
    console.clickScoringDirectionFrontButton();
    console.clickScoringArtifactHatchButton();
    console.clickScoringDestinationRocketButton();
    console.clickMediumPositionButton();
    console.clickIntakeButton();

    // Act
    console.clickCancelButton();

    // Assert
    assertEquals(ButtonBox.ScoringDestinationStates.None, console.getScoringDestinationState());
    assertEquals(ButtonBox.ScoringDirectionStates.None, console.getScoringDirectionState());
    assertEquals(ButtonBox.ScoringArtifactStates.None, console.getScoringArtifactState());
    assertEquals(ButtonBox.PositionStates.None, console.getPositionState());
    assertEquals(false, console.getIntakeState());
  }
}