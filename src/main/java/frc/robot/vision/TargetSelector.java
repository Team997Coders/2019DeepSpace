package frc.robot.vision;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;

/**
 * Obtains a list of valid target selector triggers.
 * This data is fetched from network tables and is generated
 * by the CameraVision application. It is used to feed the
 * CameraControlStateMachine so that it knows what buttons are
 * valid for selecting targets.
 */
public class TargetSelector {
  private ArrayList<CameraControlStateMachine.Trigger> validTriggers;
  private final NetworkTable visionNetworkTable;

  public TargetSelector(NetworkTable visionNetworkTable) {
    this.visionNetworkTable = visionNetworkTable;
    this.validTriggers = new ArrayList<CameraControlStateMachine.Trigger>();
  }

  /**
   * Fetches valid triggers from network tables. Call isTriggerValid to test.
   */
  protected void getValidTriggers() {
    validTriggers.clear();
    String[] triggerStrings = visionNetworkTable.getEntry("SelectableTargetTriggers").getStringArray(new String[] {});
    for (String triggerString : triggerStrings) {
      CameraControlStateMachine.Trigger trigger = Enum.valueOf(CameraControlStateMachine.Trigger.class, triggerString);
      validTriggers.add(trigger);
    }
  }

  /**
   * Call getValidTriggers to load valid triggers. This will test if one is valid.
   * 
   * @param trigger   The trigger to test to see if it is valid. Usually will yield
   *                  AButton, BButton, etc.
   * @return          True if it is valid.
   */
  public boolean isTriggerValid(CameraControlStateMachine.Trigger trigger) {
    return validTriggers.contains(trigger);
  }
}