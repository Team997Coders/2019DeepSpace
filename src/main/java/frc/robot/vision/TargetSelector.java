package frc.robot.vision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTable;

public class TargetSelector {
  private Point slewPoint;
  private final Map<CameraControlStateMachine.Trigger, Point> buttonToPointMap;
  private final Map<String, Point> identifierToPointMap;
  private final Map<CameraControlStateMachine.Trigger, String> buttonToIdentifierMap;
  private final NetworkTable visionNetworkTable;

  public TargetSelector(NetworkTable visionNetworkTable) {
    buttonToPointMap = new HashMap<CameraControlStateMachine.Trigger, Point>();
    identifierToPointMap = new HashMap<String, Point>();
    buttonToIdentifierMap = new HashMap<>();
    this.visionNetworkTable = visionNetworkTable;
  }

  public Point getSlewPoint() {
    return slewPoint;
  }

  public void clearSlewPoint() {
    slewPoint = null;
  }

  public boolean isSlewPointSet() {
    return slewPoint != null;
  }

  protected ArrayList<Point> getTargetCenterPoints() {
    // Call network tables to get points
    // TODO
    throw new RuntimeException("Implement me please!");
  }

  public void setSlewPoint(CameraControlStateMachine.Trigger trigger) {
    // Set the point to slew to
    slewPoint = buttonToPointMap.get(trigger);
  }

  public boolean isSlewPointDefined(CameraControlStateMachine.Trigger trigger) {
    return buttonToPointMap.get(trigger) != null;
  }

  public void mapButtonsToTargetCenterPoints() {
    ArrayList<Point> centerPoints = getTargetCenterPoints();
    buttonToPointMap.clear();
    identifierToPointMap.clear();
    int buttonIndex = 0;
    for(Point point : centerPoints) {
      switch (buttonIndex) {
        case 0:
          buttonToPointMap.put(CameraControlStateMachine.Trigger.AButton, point);
          identifierToPointMap.put(buttonToIdentifierMap.get(CameraControlStateMachine.Trigger.AButton), point);
          break;
        case 1:
          buttonToPointMap.put(CameraControlStateMachine.Trigger.BButton, point);
          identifierToPointMap.put(buttonToIdentifierMap.get(CameraControlStateMachine.Trigger.BButton), point);
          break;
        case 2:
          buttonToPointMap.put(CameraControlStateMachine.Trigger.XButton, point);
          identifierToPointMap.put(buttonToIdentifierMap.get(CameraControlStateMachine.Trigger.XButton), point);
          break;
        case 3:
          buttonToPointMap.put(CameraControlStateMachine.Trigger.YButton, point);
          identifierToPointMap.put(buttonToIdentifierMap.get(CameraControlStateMachine.Trigger.YButton), point);
          break;
        default:
          // TODO: Do nothing for now...should the HUD say "more targets" and provide a way to get to them?
          // Or is 4 targets enough?
      }
      buttonIndex +=1;
    }
  }
}