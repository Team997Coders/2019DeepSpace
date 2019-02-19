package frc.robot.vision;
  /**
   * Custom exception to indicate that a target is not expected
   * where it should be.
   */
  public class TargetNotLockedException extends RuntimeException {
    public TargetNotLockedException () {}
    public TargetNotLockedException (String message) {
      super (message);
    }
    public TargetNotLockedException (Throwable cause) {
      super (cause);
    }
    public TargetNotLockedException (String message, Throwable cause) {
      super (message, cause);
    }
  }
