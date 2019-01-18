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

        //output = arm.readEncoder(true, 3.1);
        //output = arm.readEncoder(true, 3.7);
        double output = 0;
        try {

            File f = new File("/home/n30b4rt/Desktop/BOI.txt");
        
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(("Init Test:\n").getBytes());
            output = arm.readEncoder(true, 4.4);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 4.9);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 0.5);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 2.0);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 3.2);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 4.5);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 0.5);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 2.0);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 3.2);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 4.5);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 0.5);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 2.0);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            output = arm.readEncoder(true, 2.5);
            fos.write((String.valueOf(bugAvoidence(output) + "\n")).getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        output = bugAvoidence(output);

        arm.close();
        //assertTrue(true);
        assertTrue("Output is " + output, (output == 13.5));
    }

    private double bugAvoidence(double a) {
        return ((double)((int)(a * 10000))) / 10000;
    }

}