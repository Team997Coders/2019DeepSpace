/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Class for controller the LimeLight
 */
public class LimeLight {

  private NetworkTable table;

  public LimeLight() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public void setDouble(String entry, double value) {
    table.getEntry(entry).setDouble(value);
  }

  public double getDouble(String entry, double defaultValue) {
    return table.getEntry(entry).getDouble(defaultValue);
  }

  public interface LimeLightValue {
    public int getValue();
  }

  public enum CameraState implements LimeLightValue {
    VisionProccessing(0), DriverStation(1);

    int value;
    CameraState(int value) {
      this.value = value;
    }

    @Override
    public int getValue() { return value; }
  }

  public enum LEDState implements LimeLightValue {
    PipelinePreference(0), ForceOff(1), ForceBlink(2), ForceOn(3);

    int value;
    LEDState(int value) {
      this.value = value;
    }

    @Override
    public int getValue() { return value; }
  }

  public enum SnapshotMode implements LimeLightValue {
    StopTakingSnapshots(0), TakeSnapshots(1);

    int value;
    SnapshotMode(int value) {
      this.value = value;
    }

    @Override
    public int getValue() { return value; }
  }

}
