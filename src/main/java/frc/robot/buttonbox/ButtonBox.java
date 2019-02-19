package frc.robot.buttonbox;

import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.buttonbox.commands.HighHeight;

/**
 * This class implements the Deepspace operator 2 custom console
 * for running on the roborio. Hook wpilibj joystick whenPressed
 * methods to the appropriate click methods below. Wpilibj whenPressed
 * methods will get called in response to custom HID firmware running
 * on an embedded device, plugged in to the driver station, which
 * mimics a joystick. The driver station will do what it always does
 * with joystick feedback and send it along to the robot.
 */
public class ButtonBox {
  private ScoringDirectionStates scoringDirectionState;
  private ScoringArtifactStates scoringArtifactState;
  private ScoringDestinationStates scoringDestinationState;
  private PositionStates positionState;
  private boolean intakeState;
  private Command activateClickedCommand;
  private Command visionAClickedCommand;
  private Command visionBClickedCommand;
  private Command visionXClickedCommand;
  private Command visionLeftClickedCommand;
  private Command visionRightClickedCommand;
  private Command visionCenterClickedCommand;

  /**
   * NOTE: You must wire up whenClicked event handlers independent of
   * this constructor.
   */
  public ButtonBox() {
    setStartupState();
  }

  /**
   * Click event handler for activating arm movement.
   */
  public void clickActivateButton() {
    activateClickedCommand.start();
  }

  /**
   * Click event handler for setting the cancel state.
   */
  public void clickCancelButton() {
    setCancelState();
  }

  /**
   * Click event handler for setting intake.
   */
  public void clickIntakeButton(){
    this.intakeState = !intakeState;
  }

  /**
   * Click event handler for setting high lift position.
   * This will not set for the cargo ship.
   */
  public void clickHighPositionButton(){
    if (this.scoringDestinationState != ScoringDestinationStates.CargoShip) {
      this.positionState = PositionStates.High;
    if(getScoringArtifactState() == ScoringArtifactStates.Ball &&
        getScoringDestinationState() == ScoringDestinationStates.Rocket &&
         getScoringDirectionState() == ScoringDirectionStates.Front){
      positionState = PositionStates.Medium;
      }
    }
  }

  /**
   * Click event handler for setting medium lift position.
   * This will not set for the cargo ship.
   */
  public void clickMediumPositionButton(){
    if (this.scoringDestinationState != ScoringDestinationStates.CargoShip) {
      this.positionState = PositionStates.Medium;
    }
  }

  /**
   * Click event handler for setting low lift position.
   * This will not set for the cargo ship.
   */
  public void clickLowPositionButton(){
    if (this.scoringDestinationState != ScoringDestinationStates.CargoShip) {
      this.positionState = PositionStates.Low;
    }
  }

  /**
   * Click event handler for setting the scoring artifact to ball.
   */
  public void clickScoringArtifactBallButton() {
    this.scoringArtifactState = ScoringArtifactStates.Ball;
    if(getPositionState() == PositionStates.High &&
        getScoringDestinationState() == ScoringDestinationStates.Rocket &&
         getScoringDirectionState() == ScoringDirectionStates.Front){
      positionState = PositionStates.Medium;
    }
  }

  /**
   * Click event handler for setting the scoring artifact to hatch.
   * Back/Hatch/Rocket is not allowed, so set cargoship as destination
   * if this combination is found.
   */
  public void clickScoringArtifactHatchButton() {
    this.scoringArtifactState = ScoringArtifactStates.Hatch;
    // If back is selected and destination is rocket, set cargoship destination.
    if (scoringDirectionState == ScoringDirectionStates.Back &&
        scoringDestinationState == ScoringDestinationStates.Rocket) {
      clickScoringDestinationCargoShipButton();
    }
  }

  /**
   * Click event handler for setting scoring direction to back.
   * Back/Hatch/Rocket is not allowed, so set cargoship destination 
   * if this combination found.
   */
  public void clickScoringDirectionBackButton() {
    this.scoringDirectionState = ScoringDirectionStates.Back;
    // If hatch is selected and destination is rocket, clear destination.
    // This combination is not allowed.
    if (scoringArtifactState == ScoringArtifactStates.Hatch && 
        scoringDestinationState == ScoringDestinationStates.Rocket) {
      clickScoringDestinationCargoShipButton();
    }
  }

  /**
   * Click event handler for setting scoring direction to front.
   */
  public void clickScoringDirectionFrontButton() {
    this.scoringDirectionState = ScoringDirectionStates.Front;
    if(getScoringArtifactState() == ScoringArtifactStates.Ball &&
        getScoringDestinationState() == ScoringDestinationStates.Rocket &&
         getPositionState() == PositionStates.High){
      positionState = PositionStates.Medium;
    }
  }

  /**
   * Click event handler for setting scoring destination to rocket.
   * Back/Hatch/Rocket is not a valid combination, so cargoship will
   * get set under that condition.
   */
  public void clickScoringDestinationRocketButton() {
    if (scoringDirectionState == ScoringDirectionStates.Back &&
        scoringArtifactState == ScoringArtifactStates.Hatch) {
      clickScoringDestinationCargoShipButton();
    } else {
      this.scoringDestinationState = ScoringDestinationStates.Rocket;
    }if(getScoringArtifactState() == ScoringArtifactStates.Ball &&
    getPositionState() == PositionStates.High &&
     getScoringDirectionState() == ScoringDirectionStates.Front){
  positionState = PositionStates.Medium;
    }
  }

  /**
   * Click event handler for setting the scoring destination to cargo ship.
   * There are no height settings for the cargo ship so they get cleared.
   */
  public void clickScoringDestinationCargoShipButton() {
    this.scoringDestinationState = ScoringDestinationStates.CargoShip;
    // There are no height positions for the cargo ship.
    this.positionState = PositionStates.None;
  }

  /**
   * Click event handler for the vision A button.
   */
  public void clickVisionAButton() {
    visionAClickedCommand.start();
  }

  /**
   * Click event handler for the vision B button.
   */
  public void clickVisionBButton() {
    visionBClickedCommand.start();
  }

  /**
   * Click event handler for the vision X button.
   */
  public void clickVisionXButton() {
    visionXClickedCommand.start();
  }

  /**
   * Click event handler for the vision pan left button.
   */
  public void clickVisionLeftButton() {
    visionLeftClickedCommand.start();
  }

  /**
   * Click event handler for the vision pan right button.
   */
  public void clickVisionRightButton() {
  visionRightClickedCommand.start();
  }

  /**
   * Click event handler for the vision center button.
   */
  public void clickVisionCenterButton() {
    visionCenterClickedCommand.start();
  }

  public ScoringDestinationStates getScoringDestinationState() {
    return scoringDestinationState;
  }

  public ScoringDirectionStates getScoringDirectionState() {
    return scoringDirectionState;
  }

  public ScoringArtifactStates getScoringArtifactState() {
    return scoringArtifactState;
  }

  public PositionStates getPositionState() {
    return positionState;
  }

  public boolean getIntakeState() {
    return intakeState;
  }

  /**
   * Set the cancel state. All settings are sent to none.
   */
  private void setCancelState() {
    scoringDirectionState = ScoringDirectionStates.None;
    scoringArtifactState = ScoringArtifactStates.None;
    scoringDestinationState = ScoringDestinationStates.None;
    positionState = PositionStates.None;
    intakeState = false;
  }

  /**
   * Set the robot startup state. Robot will start in the
   * back facing position, so set scoring direction to back.
   */
  private void setStartupState() {
    scoringDirectionState = ScoringDirectionStates.Back;
    scoringArtifactState = ScoringArtifactStates.None;
    scoringDestinationState = ScoringDestinationStates.None;
    positionState = PositionStates.None;
    intakeState = false;
  }

  /**
   * Set up the command that will be called when the arm
   * is to be activated.
   *  
   * @param activateClickedCommand  The command to start.
   */
  public void whenActivateClicked(Command activateClickedCommand) {
    this.activateClickedCommand = activateClickedCommand;
  }

  /**
   * Set up the command that will be called when the vision A
   * button is pressed.
   * 
   * @param visionAClickedCommand The command to start.
   */
  public void whenVisionAClicked(Command visionAClickedCommand) {
    this.visionAClickedCommand = visionAClickedCommand;
  }

  /**
   * Set up the command that will be called when the vision B
   * button is pressed.
   * 
   * @param visionBClickedCommand The command to start.
   */
  public void whenVisionBClicked(Command visionBClickedCommand) {
    this.visionBClickedCommand = visionBClickedCommand;
  }

  /**
   * Set up the command that will be called when the vision X
   * button is pressed.
   * 
   * @param visionXClickedCommand The command to start.
   */
  public void whenVisionXClicked(Command visionXClickedCommand) {
    this.visionXClickedCommand = visionXClickedCommand;
  }

  /**
   * Set up the command that will be called when the vision pan left
   * button is pressed.
   * 
   * @param visionLeftClickedCommand The command to start.
   */
  public void whenVisionLeftClicked(Command visionLeftClickedCommand) {
    this.visionLeftClickedCommand = visionLeftClickedCommand;
  }

  /**
   * Set up the command that will be called when the vision pan right
   * button is pressed.
   * 
   * @param visionRightClickedCommand The command to start.
   */
  public void whenVisionRightClicked(Command visionRightClickedCommand) {
    this.visionRightClickedCommand = visionRightClickedCommand;
  }

  /**
   * Set up the command that will be called when the vision center
   * button is pressed.
   * 
   * @param visionCenterClickedCommand The command to start.
   */
  public void whenVisionCenterClicked(Command visionCenterClickedCommand) {
    this.visionCenterClickedCommand = visionCenterClickedCommand;
  }

  public enum ScoringDirectionStates {
    None,
    Front,
    Back
  }

  public enum ScoringArtifactStates {
    None,
    Ball,
    Hatch
  }

  public enum ScoringDestinationStates {
    None,
    CargoShip,
    Rocket
  }

  public enum PositionStates {
    None,
    High,
    Medium,
    Low
  }

  public void updateSmartDashboard() {
    SmartDashboard.putString("Scoring Direction", getScoringDirectionState().toString());    
    SmartDashboard.putString("Scoring Destination", getScoringDestinationState().toString());    
    SmartDashboard.putString("Scoring Artifact", getScoringArtifactState().toString());    
    SmartDashboard.putString("Height Position", getPositionState().toString());    
  }
}