package frc.robot.misc;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.misc.GearBox;

/**
 * Add your docs here.
 */
public class RoboMisc {
  
  // This creates the Talon and 2 slave Victors for our standard half of the
  // drivetrain
  public static GearBox standTalonSRXSetup(int talon, int victor1, int victor2, boolean reverse) {
    WPI_TalonSRX a = new WPI_TalonSRX(talon);
    WPI_VictorSPX b = new WPI_VictorSPX(victor1);
    WPI_VictorSPX c = new WPI_VictorSPX(victor2);

    b.follow(a);
    c.follow(a);

    a.setInverted(reverse);

    b.setInverted(reverse);
    c.setInverted(reverse);

    a.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    a.setSensorPhase(true);

    a.setNeutralMode(NeutralMode.Coast);

    /* set the peak, nominal outputs */
    a.configNominalOutputForward(0, 10);
    a.configNominalOutputReverse(0, 10);
    // a.configPeakOutputForward(1, 10); //Use for PB
    // a.configPeakOutputReverse(-1, 10); //Use for PB
    a.configPeakOutputForward(1, 10); // Use for extrasensitive CB
    a.configPeakOutputReverse(-1, 10); // Use for extrasensitive CB

    a.configPeakCurrentLimit(40, 10);
    a.configPeakCurrentDuration(100, 10);
    a.configContinuousCurrentLimit(30, 10);
    a.enableCurrentLimit(true);

    a.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 40, 10);
    a.config_kF(0, 0.1097, 10);
    // a.config_kP(0, 0.113333, 10);
    // a.config_kP(0, SmartDashboard.getNumber("P", 0), 10);
    // a.config_kI(0, 0, 10);
    // a.config_kI(0, SmartDashboard.getNumber("I", 0), 10);
    // a.config_kD(0, 0, 10);
    // a.config_kD(0, SmartDashboard.getNumber("D", 0), 10);

    new SensorCollection(a);

    a.setSafetyEnabled(false);
    b.setSafetyEnabled(false);
    c.setSafetyEnabled(false);

    return new GearBox(a, b, c);
  }

}
