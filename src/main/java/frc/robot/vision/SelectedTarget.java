package frc.robot.vision;

import edu.wpi.first.networktables.NetworkTable;

/**
 * Provides information about the hatch target that has been selected
 * for lockon. It reads the values from network tables. Values are 
 * written by the 2019HatchFindingVision/CameraVision project.
 */
public class SelectedTarget {
  public double rangeInInches;
  public double cameraAngleInDegrees;
  public double angleToTargetInDegrees;
   // Gives you a percentage in x and y you can use to apply to PID to move Camera
   // to get target in center of FOV
  public Point normalizedPointFromCenter;
  public boolean active;
  private final static String SELECTEDTARGETKEY = "SelectedTarget";
  private final static String RANGEININCHESKEY = "RangeInInches";
  private final static String CAMERAANGLEINDEGREES = "CameraAngleInDegrees";
  private final static String ANGLETOTARGETINDEGREES = "AngleToTargetInDegrees";
  private final static String NORMALIZEDPOINTFROMCENTER = "NormalizedPointFromCenter";
  private final static String ACTIVE = "Enabled";
  private final static String NORMALIZEDPOINTFROMCENTERX = "X";
  private final static String NORMALIZEDPOINTFROMCENTERY = "Y";

  /**
   * Pass in a network table to the constructor that this class will read from to
   * re-constitute state.
   * @param visionNetworkTable  Network table to read from.
   */
  public SelectedTarget(NetworkTable visionNetworkTable) {
    NetworkTable selectedTargetTable = visionNetworkTable.getSubTable(SELECTEDTARGETKEY);
    rangeInInches = selectedTargetTable.getEntry(RANGEININCHESKEY).getDouble(0);
    cameraAngleInDegrees = selectedTargetTable.getEntry(CAMERAANGLEINDEGREES).getDouble(0);
    angleToTargetInDegrees = selectedTargetTable.getEntry(ANGLETOTARGETINDEGREES).getDouble(0);
    active = selectedTargetTable.getEntry(ACTIVE).getBoolean(false);
    NetworkTable normalizedPointFromCenterTable = selectedTargetTable.getSubTable(NORMALIZEDPOINTFROMCENTER);
    double normalizedPointFromCenterX = normalizedPointFromCenterTable.getEntry(NORMALIZEDPOINTFROMCENTERX).getDouble(0);
    double normalizedPointFromCenterY = normalizedPointFromCenterTable.getEntry(NORMALIZEDPOINTFROMCENTERY).getDouble(0);
    normalizedPointFromCenter = new Point(normalizedPointFromCenterX, normalizedPointFromCenterY);
  }
}