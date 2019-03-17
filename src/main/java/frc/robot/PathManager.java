/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Add your docs here.
 */
public class PathManager {

  private static PathManager instance = null;

  private Queue<String> pathnames;
  private ArrayList<MotionProfile> profiles;

  private Thread[] daemons;
  private final int daemonCount = 2;

  /**
   * Implement the singleton pattern
   */
  public static PathManager getInstance() {
    if (instance == null) {
      instance = new PathManager();
    }
    return instance;
  }

  /**
   * Returns true once all paths are loaded
   */
  public boolean isLoaded() {
    for (int i = 0; i < daemonCount; i++) {
      if (daemons[i].isAlive()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Takes hard coded pathnames and runs daemons
   */
  private PathManager() {
    // Load pathnames in the queue
    pathnames = new ConcurrentLinkedQueue<String>();
    profiles = new ArrayList<MotionProfile>();
    pathnames.add("Hab1MiddleToShipRight");
    //pathnames.add("ShipRightToLoadingStationRight");
    pathnames.add("LoadingStationRightToCargoCenterLeft");

    daemons = new Thread[daemonCount];
    for (int i = 0; i < daemonCount; i++) {
      daemons[i] = new Thread(this::loadPath);
      daemons[i].start();
    }
  }

  /**
   * Given a profile name, gets you back the loaded profile.
   */
  public MotionProfile getProfile(String name) {
    if (isLoaded()) {
      for (int i = 0; i < profiles.size(); i++) {
        if (profiles.get(i).name.equals(name)) {
          return profiles.get(i);
        }
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
    Lock lock = new ReentrantLock();

    while (run) {

      pathname = pathnames.poll();
      
      if (pathname == null) {
        run = false;
      } else {
        System.out.println(pathname);
        try {
          mp = new MotionProfile(pathname);
          lock.lock();
          profiles.add(mp);
          lock.unlock();
        } catch (Exception e) {
          System.out.println("\n\nLoading profile '" + pathname + "' has failed.'\n\n");
          e.printStackTrace();
        }
      }
    }
  }
}
