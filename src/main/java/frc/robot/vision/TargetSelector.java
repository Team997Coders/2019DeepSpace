package frc.robot.vision;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;

public class TargetSelector {
  private ArrayList<CameraControlStateMachine.Trigger> validTriggers;
  private final NetworkTable visionNetworkTable;

  public TargetSelector(NetworkTable visionNetworkTable) {
    this.visionNetworkTable = visionNetworkTable;
    this.validTriggers = new ArrayList<CameraControlStateMachine.Trigger>();
  }

  protected void getValidTriggers() {
    validTriggers.clear();
    String[] triggerStrings = visionNetworkTable.getEntry("SelectableTargetTriggers").getStringArray(new String[] {});
    for (String triggerString : triggerStrings) {
      CameraControlStateMachine.Trigger trigger = Enum.valueOf(CameraControlStateMachine.Trigger.class, triggerString);
      validTriggers.add(trigger);
    }
  }

  public boolean isTriggerValid(CameraControlStateMachine.Trigger trigger) {
    return validTriggers.contains(trigger);
  }
}