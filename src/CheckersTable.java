import javax.swing.*;
import java.lang.Exception;
import java.awt.*;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Singleton
//Command Receiver
public class CheckersTable {
    private static CheckersTable Instance;

    private final int N_ROWS, N_COLS, DIM_RECT;
    private Player p1, p2;
    private Piece pToMove;

    private JFrame frame;
    private JPanel panel;

    private static Rectangle[][] rectangles;

    private List<Point> pointsListToClear = new ArrayList<Point>();

    /*-------------------------------------------------------------------------------------------------
            p1 = new Player(Color.green,playerName);
            p2 = new Player(Color.red, playerName2);
            inizializeWindow();
     ---------------------------------------------------------------------------------------------------*/
    //Da modificare, invece delle stringe nome player passiamo gli oggetti player instanziati nel main (con eventuali interfacce etc.)
    private CheckersTable(final int N_ROWS, final int N_COLS, final int DIM) {
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
        DIM_RECT = DIM;
    }

    //Singleton
    public static synchronized CheckersTable getInstance(final int N, final int C, final int DIM) {
        if (Instance == null)
            Instance = new CheckersTable(N, C, DIM);
        return Instance;
    }

    //get the existing instance without specifying unnecessary parameters
    public static synchronized CheckersTable getInstance() throws Exception {
        if (Instance == null)
            throw new Exception("ISTANCE NULL. Please use method: public static synchronized CheckersTable getInstance(final int n, final int c, final int dim)");
        return Instance;
    }

    //Create and set the main Frame
    private void createFrame() {
        frame = new JFrame("Dama");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(N_ROWS * DIM_RECT, N_COLS * DIM_RECT);
        frame.setBackground(Color.white);
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    //Create and set the Panel that  will contain all the elements of the game
    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(N_ROWS, N_COLS, 0, 0));
        panel.setPreferredSize(new Dimension(N_ROWS * DIM_RECT, N_COLS * DIM_RECT));
        panel.setBackground(Color.gray);
    }


    private void inizializeWindow() throws Exception {
        createFrame();
        createPanel();

        //Create and Set an array of game cells (Rectangle type) with and without pieces
        rectangles = Rectangle.createRectangles(N_ROWS, N_COLS, DIM_RECT, p1, p2);

        //Add rect to Table
        for (Rectangle[] row : rectangles)
            for (Rectangle rect : row)
                panel.add(rect);

        frame.add(panel);
        frame.setVisible(true);
    }

    protected void startGame(Player p1, Player p2) throws Exception {
        this.p1 = p1;
        this.p2 = p2;
        inizializeWindow();
    }

    //Shows the moves allowed to click on a piece
    protected void suggestion(Piece p) {
        pToMove = p; //pToMove is a istance variable which stores piece to be moved
        Point coordpToMove = pToMove.getCoord();
        String classSelectedPiece = pToMove.getClass().toString();
        Color colorSelectedPiece = pToMove.getColor();

        Point pieceToeat_Coord;

        switch (classSelectedPiece){
            case "class Pawn":
            pieceToeat_Coord = (colorSelectedPiece == Color.red) ? showSuggestion(coordpToMove.x - 1, coordpToMove.y) : showSuggestion(coordpToMove.x + 1, coordpToMove.y);
                if (pieceToeat_Coord != null)   //if there a piece to eat, then show suggestion
                    //showEatSuggestion(pieceToeat_Coord.x, pieceToeat_Coord.y);
                break;
            case "class Archer":
                /* DEAD CODE: NON CI VA MAI. (ERRORE SEGNALATO DA VSCODE)  if (p == null) { } */ 
                System.out.println("CASA ARCHER: Vediamo cosa fare per l'arciere");
                break;

            case "class Dama":
                System.out.println("CASE DAMA: Vediamo cosa fare per la DAMA");
                break;

            default:
             System.out.println("CASE DEFAULT: vediamo se lasciarlo.");
        }
    }


    //Show (paint the rect) the suggestions on the game's table: Return Point when can eat the piece.
    //I_row è la riga già incrementata in base alla direzione che dipende dal colore
    //j rappresenta la posizione del pezzo selezionato (i_row - 1, j)
    private Point showSuggestion(int i_row, int j) {
        Piece tmp;
        int j_coordPieceToEat = -1;
        int j_notPieceToEat = -1; // È la cella accanto (ovviamente giocabile) alla pedina che vuole mangiare una compagna
        int left = j - 1, right = j + 1;
        boolean EATFIND = false;
        //System.out.println("i_row = " + i_row + " j = " + j);
        if (i_row >= 0 && i_row < N_ROWS) {
            System.out.println("1");
            if (left >= 0 && right < N_COLS) {
                System.out.println("2");
            // YOU ARE IN A FULL FIELD AND YOU CAN MOVE RIGHT OR LEFT OBVIOUSLY IT IS TO BE CHECKED
                if (!rectangles[i_row][left].getHasPiece() && !rectangles[i_row][right].getHasPiece()){
                    System.out.println("3");
                    //Left and Right are free, can move.
                    showFreeRectangle(i_row,left);
                    showFreeRectangle(i_row,right);
                }
                else {
                    System.out.println("4");
                    j_coordPieceToEat = rectangles[i_row][left].getHasPiece() ? left : right;
                    tmp = (Piece)rectangles[i_row][j_coordPieceToEat].getComponent(0);
                    if (tmp.getColor() == pToMove.getColor() ) { //Are the same player's Pieces
                        j_notPieceToEat = !rectangles[i_row][left].getHasPiece() ? left : right;
                        System.out.println("Sono ugualiii " + j_coordPieceToEat);
                        if(!rectangles[i_row][j_notPieceToEat].getHasPiece())
                            showFreeRectangle(i_row, j_notPieceToEat);
                        else{
                            System.out.println(j_notPieceToEat + " "  + i_row);
                            tmp = (Piece)rectangles[i_row][j_notPieceToEat].getComponent(0);
                            if ( tmp.getColor() != pToMove.getColor() && !rectangles[i_row + 1][j_notPieceToEat + 1].getHasPiece()) {
                                System.out.println("Mangio");
                                j_coordPieceToEat = j_notPieceToEat;
                                EATFIND = true;
                            }
                            else
                                return null;

                        }

                    }
                    else
                        EATFIND = true;
                }
            }
            else if (left >= 0) {
                System.out.println("5");
                // YOU ARE ON THE RIGHT EDGE SO YOU MOVE ONLY TO THE LEFT
                if (!rectangles[i_row][left].getHasPiece())
                    showFreeRectangle(i_row,left);
                else{
                    System.out.println("6");
                    j_coordPieceToEat = left;
                    tmp = (Piece)rectangles[i_row][j_coordPieceToEat].getComponent(0);
                    if (tmp.getColor() == pToMove.getColor()) { //Are the same player's Pieces
                        j_notPieceToEat = right;
                        showFreeRectangle(i_row, j_notPieceToEat);
                    }
                    else
                    EATFIND = true;
                }
            }
            else {
                System.out.println("7");
                if (!rectangles[i_row][right].getHasPiece())
                    showFreeRectangle(i_row,right);
                else{
                    j_coordPieceToEat = right;
                    tmp = (Piece)rectangles[i_row][j_coordPieceToEat].getComponent(0);
                    if (tmp.getColor() == pToMove.getColor()) { //Are the same player's Pieces
                        j_notPieceToEat = left;
                        showFreeRectangle(i_row, j_notPieceToEat);
                    }
                    else
                        EATFIND = true;
                }
            }
        }

        if (EATFIND)
            return new Point(i_row,j_coordPieceToEat);

        return null;
    }

    //Show Eat Suggestion
    /*private void showEatSuggestion(int i_pToEat, int j_pToEat) {
        int i_pToMove = pToMove.getCoord().x;
        int j_pToMove = pToMove.getCoord().y;
        Piece tmp;
        boolean isRed;
        int i,j;//indici per mangiare
        int i_nwSuggestion, j_nwSuggestion; // se non posso mangiare controllo se la cella opposta è libera
        int k; // se la cella opposta è libera controllo se posso mangiare quella. Se mangio mi trovo sulla cella K
        //if you have to move to the right diagonal. For example: if you are red and piece to eat is right side, coordinates are (i = x - 2, j = y - 2)
        isRed = (pToMove.getColor() == Color.red);
        if (isRed){
            i = i_pToMove - 2;
            i_nwSuggestion = i_pToMove - 1;
        }
        else {
            i = i_pToMove + 2;
            i_nwSuggestion = i_pToMove + 1;
        }
        //Check the diagonal's direction
        if (j_pToMove < j_pToEat){
            j = j_pToMove + 2;
            j_nwSuggestion = j_pToMove - 1;
            k = j_nwSuggestion - 1;
        }
        else
        {
            j = j_pToMove - 2;
            j_nwSuggestion = j_pToMove + 1;
            k = j_nwSuggestion + 1;
        }


        if (!rectangles[i][j].getHasPiece()) //if rectangle(i,j) is free then you can move your piece here
            showFreeRectangle(i, j);
        else{
                if (!rectangles[i_nwSuggestion][j_nwSuggestion].getHasPiece()){
                    showFreeRectangle(i_nwSuggestion,j_nwSuggestion);
                }
                else {
                    System.out.println("In teoria devo mangiare quello a dx" + i + k);
                    tmp = (Piece)rectangles[i_nwSuggestion][j_nwSuggestion].getComponent(0);
                    if (!rectangles[i][k].getHasPiece() && tmp.getColor() != pToMove.getColor())
                        showFreeRectangle(i,k);
                }
        }
    }*/


    protected void showFreeRectangle(int row, int col){
            rectangles[row][col].setColor(Color.cyan);
            rectangles[row][col].repaint();
            pointsListToClear.add(new Point(row, col));
    }

    //This function move pToMove into destRectangle
    public void move(Rectangle destRectangle, int i, int j) {
        Rectangle srcRectangle = rectangles[pToMove.getCoord().x][pToMove.getCoord().y]; //this rectangle contain the piece to be moved
        Component component_pToMove = srcRectangle.getComponent(0); // first component with index 0
        pToMove.setCoord(i, j);

        //Add piece to move in new rectagle
        destRectangle.add(component_pToMove);
        destRectangle.setHasPiece(true); // now rectangles has a piece.
        destRectangle.repaint();

        //remove the old piece from the previous rectangle
        srcRectangle.removeAll(); //remove all components by panel Rectangle (but it only has 1 component with index 0)
        srcRectangle.setHasPiece(false);
        srcRectangle.repaint();
    }

    public void clearSuggestions(){
        for (Point pointToClear : pointsListToClear){
            rectangles[pointToClear.x][pointToClear.y].setColor(Color.darkGray);
            rectangles[pointToClear.x][pointToClear.y].repaint();
        }
        pointsListToClear.clear();
    }

    //Dobbiamo passare solo la riga corretta
    private boolean checkLeft(int i, int j){
        return (rectangles[i][j - 1].getHasPiece()) ? true : false;
    }

    //Dobbiamo passare solo la riga corretta
    private boolean checkRight(int i, int j){
        return (rectangles[i][j + 1].getHasPiece()) ? true : false;
    }

    private boolean checkColors(Color a, Color b){
        return (a == b) ? true : false;
    }
}
