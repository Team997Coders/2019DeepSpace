package test.frc.robot.subsystems;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

import org.junit.*;

import frc.robot.subsystems.Arm;

public class EncoderUnitTest {

    @Test
    public void TestEncoderOutput() {
        Arm arm = new Arm();

        //arm.readEncoder(3.1);
        //arm.readEncoder(3.7);
        double output = arm.readEncoder(4.4);
        arm.readEncoder(4.9);
        arm.readEncoder(0.5);
        arm.readEncoder(2.0);
        arm.readEncoder(3.2);
        arm.readEncoder(4.5);
        //output = arm.readEncoder(0.2);

        File f = new File("/home/n30b4rt/Desktop/BOI.txt");
        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write((int)(output * 10));
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        output = ((double)((int)(output * 10))) / 10;

        arm.close();
        //assertTrue(true);
        assertTrue("Output is " + output, (output == 0.4));
    }

}