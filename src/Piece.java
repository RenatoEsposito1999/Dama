import javax.swing.*;
import java.awt.*;

public abstract class Piece extends JPanel {
   protected final Color color;
   protected  int points;
   protected Image objIMG;

   public Piece(Color c, int p, String imgName){
      color = c;
      points = p;
      objIMG = new Image(imgName);
   }

   protected Image getImg(){
      return objIMG;
   }
}
