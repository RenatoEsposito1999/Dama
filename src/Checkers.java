import java.awt.*;

public class Checkers extends Piece{
    
    public Checkers(Color c, String path_name) throws Exception{
        super(c, 5, path_name);
    }

    @Override
    public int showSuggestions(int direction){
        int oppositeDirection;
        boolean showMoreSuggestions = super.showSuggestions(direction) != 2; //vero se non trova da mangiare
        if(showMoreSuggestions){
            TABLE.clearSuggestions(); // se non deve mangiare verso direction pulisco i suggerimenti e se non mangia neanche veros oppositeDirection 
            //cerco di nuovo i suggerimenti verso direction
            oppositeDirection = (getColor().equals(Color.red)) ? getCoord().x + 1 : getCoord().x - 1;
             if (super.showSuggestions(oppositeDirection) != 2){
                super.showSuggestions(direction);
             }
        }
        return 1;
    }

}
