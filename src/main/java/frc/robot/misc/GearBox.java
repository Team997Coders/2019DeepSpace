package frc.robot.misc;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * Add your docs here.
 */
public class GearBox {

    public TalonSRX talon;
    public VictorSPX victor1, victor2;

    // Set some gearboxes
    public GearBox(TalonSRX talon, VictorSPX victor1, VictorSPX victor2) {
        this.talon = talon;
        this.victor1 = victor1;
        this.victor2 = victor2;
    }

}
