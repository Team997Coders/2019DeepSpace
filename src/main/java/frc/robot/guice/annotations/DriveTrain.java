package frc.robot.guice.annotations;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Define binding annotations for DriveTrain subsystem
 * 
 * @see https://github.com/google/guice/wiki/BindingAnnotations
 */
public class DriveTrain {
  // TODO: These definitions probably should not follow the naming
  // of the motor controllers, because we really don't care what
  // controllers are being used for these annotations anyway. I'm not
  // sure yet what controllers, for example, will be on deepspace bot
  // and it seems silly to have to change these. Maybe call them
  // LeftDriveTrainMotor1, etc?
  @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
  public @interface LeftTalon {}

  @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
  public @interface RightTalon {}

  @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
  public @interface LeftVictor1 {}

  @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
  public @interface RightVictor1 {}

  @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
  public @interface LeftVictor2 {}

  @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
  public @interface RightVictor2 {}

  @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
  public @interface LeftTalonSensorCollection {}

  @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
  public @interface RightTalonSensorCollection {}

  @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
  public @interface DriveTrainStyle {}
}
