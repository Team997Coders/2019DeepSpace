package frc.robot;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import edu.wpi.first.wpilibj.command.Command;

public class DeepspaceOperator2ConsoleUnitTest {
  @Test
  public void itSetsScoringArtifactStateToBallWhenClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Act
    console.clickScoringArtifactBallButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringArtifactStates.Ball, console.getScoringArtifactState());
  }

  @Test
  public void itSetsScoringArtifactStateToHatchWhenClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Act
    console.clickScoringArtifactHatchButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringArtifactStates.Hatch, console.getScoringArtifactState());
  }

  @Test
  public void itSetsScoringDestinationStateToRocketWhenClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Act
    console.clickScoringDestinationRocketButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringDestinationStates.Rocket, console.getScoringDestinationState());
  }

  @Test
  public void itSetsScoringDestinationStateToCargoShipWhenClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Act
    console.clickScoringDestinationCargoShipButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringDestinationStates.CargoShip, console.getScoringDestinationState());
  }

  @Test
  public void itSetsScoringDirectionStateToBackWhenClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Act
    console.clickScoringDirectionBackButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringDirectionStates.Back, console.getScoringDirectionState());
  }

  @Test
  public void itSetsScoringDirectionStateToFrontWhenClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Act
    console.clickScoringDirectionFrontButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringDirectionStates.Front, console.getScoringDirectionState());
  }

  @Test
  public void itSetsPositionToHighWhenClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Act
    console.clickHighPositionButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.PositionStates.High, console.getPositionState());
  }

  @Test
  public void itSetsPositionToMeduimWhenClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Act
    console.clickMediumPositionButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.PositionStates.Medium, console.getPositionState());
  }

  @Test
  public void itSetsPositionToLowWhenClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Act
    console.clickLowPositionButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.PositionStates.Low, console.getPositionState());
  }

  @Test
  public void itTogglesIntakeWhenClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Act
    console.clickIntakeButton();

    // Assert
    assertEquals(true, console.getIntakeState());
  }

  @Test
  public void itWillNotSetHighHeightWhenDestinationCargoShip() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
    console.clickScoringDestinationCargoShipButton();

    // Act
    console.clickHighPositionButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.PositionStates.None, console.getPositionState());
  }

  @Test
  public void itWillNotSetMediumHeightWhenDestinationCargoShip() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
    console.clickScoringDestinationCargoShipButton();

    // Act
    console.clickMediumPositionButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.PositionStates.None, console.getPositionState());
  }

  @Test
  public void itWillNotSetLowHeightWhenDestinationCargoShip() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
    console.clickScoringDestinationCargoShipButton();

    // Act
    console.clickLowPositionButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.PositionStates.None, console.getPositionState());
  }

  @Test
  public void itStartsActivatedCommandWhenActivateButtonClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
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
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringDirectionStates.Back, console.getScoringDirectionState());
    assertEquals(DeepspaceOperator2Console.ScoringDestinationStates.None, console.getScoringDestinationState());
    assertEquals(DeepspaceOperator2Console.ScoringArtifactStates.None, console.getScoringArtifactState());
    assertEquals(DeepspaceOperator2Console.PositionStates.None, console.getPositionState());
    assertEquals(false, console.getIntakeState());
  }
  
  @Test
  public void itStartsBCommandWhenBButtonClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
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
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
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
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
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
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
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
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
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
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
    console.clickHighPositionButton();

    // Act
    console.clickScoringDestinationCargoShipButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.PositionStates.None, console.getPositionState());
  }

  @Test
  public void itDoesNotAllowBackHatchRocket() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
    console.clickScoringDirectionBackButton();
    console.clickScoringArtifactHatchButton();
    console.clickScoringDestinationCargoShipButton();

    // Act
    console.clickScoringDestinationRocketButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringDestinationStates.CargoShip, console.getScoringDestinationState());
  }

  @Test
  public void itFlipsRocketToCargoShipWhenHatchSetAndBackSelected() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
    console.clickScoringArtifactHatchButton();
    console.clickScoringDestinationRocketButton();

    // Act
    console.clickScoringDirectionBackButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringDestinationStates.CargoShip, console.getScoringDestinationState());
  }

  @Test
  public void itFlipsRocketToCargoShipWhenBackSetAndHatchSelected() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
    console.clickScoringDirectionBackButton();
    console.clickScoringDestinationRocketButton();

    // Act
    console.clickScoringArtifactHatchButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringDestinationStates.CargoShip, console.getScoringDestinationState());
  }

  @Test
  public void itClearsStatesWhenCancelButtonClicked() {
    // Assemble
    DeepspaceOperator2Console console = new DeepspaceOperator2Console();
    console.clickScoringDirectionFrontButton();
    console.clickScoringArtifactHatchButton();
    console.clickScoringDestinationRocketButton();
    console.clickMediumPositionButton();
    console.clickIntakeButton();

    // Act
    console.clickCancelButton();

    // Assert
    assertEquals(DeepspaceOperator2Console.ScoringDestinationStates.None, console.getScoringDestinationState());
    assertEquals(DeepspaceOperator2Console.ScoringDirectionStates.None, console.getScoringDirectionState());
    assertEquals(DeepspaceOperator2Console.ScoringArtifactStates.None, console.getScoringArtifactState());
    assertEquals(DeepspaceOperator2Console.PositionStates.None, console.getPositionState());
    assertEquals(false, console.getIntakeState());
  }
}