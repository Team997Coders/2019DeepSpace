/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.misc;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * remove this if you aren't using it
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
