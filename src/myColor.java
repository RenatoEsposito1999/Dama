import java.awt.*;

import jdk.jfr.Unsigned;
public class myColor {
    //Return the color according to position
    public static Color getColor(int i, int j){
        Boolean quadrato_giocabile = (i%2 == 0 && j%2 != 0 || j%2 == 0 && i%2 != 0);
        return  (quadrato_giocabile) ? Color.darkGray : Color.white;
    }
}
