/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Logger {

  private BufferedWriter writer;
  private boolean logging = true;
  private final String loggerBoolean = "Logging";
  private static Logger instance;
  private String fileName = "";
  private final String SDFileName = "File Name: ";
  private long startTime = 0;
  private double timeSinceStart;
  DriverStation ds;

  private int max = 0;

  private String path;

  public static Logger getInstance() {
    if (instance == null) {
      instance = new Logger();
    }
    return instance;
  }

  private Logger() {
    this.ds = DriverStation.getInstance();
    SmartDashboard.putBoolean(this.loggerBoolean, this.logging);
    this.logging = SmartDashboard.getBoolean(this.loggerBoolean, false);
    SmartDashboard.putString(this.SDFileName, this.fileName);
    this.fileName = SmartDashboard.getString(SDFileName, "NO NAME");
    File f = new File("/home/lvuser/spartanlogs");
    if (!f.exists()) {
      f.mkdir();
    }

    File[] files = new File("/spartanlogs").listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isFile()) {
          System.out.println(file.getName());
          try {
            int index = Integer.parseInt(file.getName().split("_")[0]);
            if (index > max) {
              max = index;
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    } else {
      max = 0;
    }
  }

  public void openFile() {
    if ((this.wantToLog() || this.ds.isFMSAttached())) {
      try {
        path = this.getPath();
        this.writer = new BufferedWriter(new FileWriter(path));
        this.writer.write("time,voltage,current,left_ticks,right_ticks,left_velocity,right_velocity, pf_left, pf_right, heading, desired_heading, turn, left_drive, right_drive");
        this.writer.newLine();
        this.startTime = System.currentTimeMillis();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private String getPath() {
    this.fileName = SmartDashboard.getString(SDFileName, "NO NAME");
    if (this.ds.isFMSAttached()) {
      return String.format("/home/lvuser/spartanlogs/%d_%s_%d_log.txt", ++this.max, this.ds.getAlliance().name(),
          this.ds.getLocation());
    } else if (this.fileName != null) {
      return String.format("/home/lvuser/spartanlogs/%d_%s.txt", ++this.max, this.fileName);
    } else {
      return String.format("/home/lvuser/spartanlogs/%d_log.txt", ++this.max);
    }
  }

  public void logAll() {
    if (this.wantToLog()) {
      try {
        this.timeSinceStart = (System.currentTimeMillis() - this.startTime) / 1000.0;

        this.writer.write(String.format("%.3f", this.timeSinceStart));
        this.writer.write(String.format(",%.3f", Robot.pdp.getVoltage()));
        this.writer.write(String.format(",%.3f", Robot.pdp.getTotalCurrent()));
        this.writer.write(String.format(",%.3f", Robot.driveTrain.leftEncoderTicks()));
        this.writer.write(String.format(",%.3f", Robot.driveTrain.rightEncoderTicks()));
        this.writer.write(String.format(",%.3f", Robot.driveTrain.leftEncoderVelocity()));
        this.writer.write(String.format(",%.3f", Robot.driveTrain.rightEncoderVelocity()));
        this.writer.write(String.format(",%.3f", Robot.motionProfile.left_speed));
        this.writer.write(String.format(",%.3f", Robot.motionProfile.right_speed));
        this.writer.write(String.format(",%.3f", Robot.motionProfile.heading));
        this.writer.write(String.format(",%.3f", Robot.motionProfile.desired_heading));
        this.writer.write(String.format(",%.3f", Robot.motionProfile.turn));
        this.writer.write(String.format(",%.3f", Robot.motionProfile.left_drive));
        this.writer.write(String.format(",%.3f", Robot.motionProfile.right_drive));


        this.writer.newLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean wantToLog() {
    this.logging = SmartDashboard.getBoolean(this.loggerBoolean, true);
    return this.logging;
  }

  public void close() {
    if (this.wantToLog()) {
      if (this.writer != null) {
        try {
          this.writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}