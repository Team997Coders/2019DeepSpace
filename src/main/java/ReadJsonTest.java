
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.parser.ParseException;

import frc.robot.JsonLoader;
 
public class ReadJsonTest
{
    public static JsonLoader jl;
    public static void main(String[] args)
    {
        try {
            jl = new JsonLoader();
            System.out.println("FrontBallRocketLow:");
            System.out.println("Side: "+jl.getSide("FrontBallRocketLow"));
            System.out.println("Side: "+jl.getHeight("FrontBallRocketLow"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}