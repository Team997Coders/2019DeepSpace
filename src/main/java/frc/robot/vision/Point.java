package frc.robot.vision;

/**
 * A small representation of a point so that we don't have to drag
 * in opencv to this project.
 */
public class Point {
  public double x;
  public double y;

  public Point() {
    x = 0;
    y = 0;
  }

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
}