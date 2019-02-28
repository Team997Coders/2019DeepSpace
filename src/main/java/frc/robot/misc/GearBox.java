package frc.robot.misc;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * remove this if you aren't using it
 */
public class GearBox {

    public WPI_TalonSRX talon;
    public WPI_VictorSPX victor1, victor2;

    // Set some gearboxes
    public GearBox(WPI_TalonSRX talon, WPI_VictorSPX victor1, WPI_VictorSPX victor2) {
        this.talon = talon;
        this.victor1 = victor1;
        this.victor2 = victor2;
    }

}
