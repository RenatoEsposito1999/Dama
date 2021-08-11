// import java.awt.*; NON USATO
public class ConcreteCommand implements Command{
    private Piece receiver = null;
    //private Rectangle[][] rectangles;
    private CheckersTable checkTable;
    public ConcreteCommand(CheckersTable ct) {
        checkTable = ct;
    }

    @Override
    public void suggestion(Piece p) {
        checkTable.suggestion(p);
    }
    public void clear(){ checkTable.clearSuggestions();}
    public void move(Rectangle r, int i, int j ){ checkTable.move(r,i,j);}
}
