
import java.io.FileNotFoundException;
import java.io.IOException;

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
            System.out.println("Height: "+jl.getHeight("FrontBallRocketLow"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Object low = jl.cargo_order.get(0);
        double height = jl.getHeight((String) low);
        double angle = jl.getAngle((String) low);
        System.out.println("First Element in Cargo Array: "+low+", height="+height+", angle="+angle);
    }
}