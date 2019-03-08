/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Add your docs here.
 */
public class PathManager {

  private static PathManager instance = null;

  public boolean loaded = false;

  public Queue<String> pathnames;
  public static ArrayList<MotionProfile> profiles;

  public Thread[] daemons;
  private int finishedCount = 0;
  private int daemonCount = 1;

  public static PathManager getInstance() {
    if (instance == null) {
      instance = new PathManager();
    }
    return instance;
  }

  /**
   * Takes hard coded pathnames and runs daemons
   */
  private PathManager() {
    // Load pathnames in the queue
    pathnames = new LinkedList<String>();
    profiles = new ArrayList<MotionProfile>();
    pathnames.add("Hab1MiddleToShipRight");
    //pathnames.add("ShipRightToLoadingStationRight");
    pathnames.add("LoadingStationRightToCargoCenterLeft");

    //daemons = new Thread[daemonCount];
    for (int i = 0; i < daemonCount; i++) {
      //daemons[i] = new Thread(this::loadPath);
    }

    for (int i = 0; i < daemonCount; i++) {
      //daemons[i].start();
    }
  }

  public MotionProfile getProfile(String name) {
    for (int i = 0; i < profiles.size(); i++) {
      if (profiles.get(i).name.equals(name)) {
        return profiles.get(i);
      }
    }
    return null;
  }

  /**
   * Loads pathname from queue, creates path, then stores it in the list.
   */
  void loadPath() {

    MotionProfile mp;

    String pathname;
    boolean run = true;
    while (run) {

      pathname = pathnames.poll();
      
      if (pathname == null) {
        run = false;
      } else {
        System.out.println(pathname);
        try {
          mp = new MotionProfile(pathname);
          profiles.add(mp);
        } catch (Exception e) {
          System.out.println("\n\nLoading profile '" + pathname + "' has failed.'\n\n");
          e.printStackTrace();
        }
      }
    }

    if (daemonCount - 1 <= finishedCount) {
      loaded = true;
    }

    finishedCount++;
  }

}
