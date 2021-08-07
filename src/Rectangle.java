import javax.swing.*;

import java.awt.*;
public  class Rectangle extends JPanel{
    private final int x,y,wid,hei;
    private Color color;
    private boolean hasPiece = false;

    public Rectangle(int x, int y, int wid, int hei){
        this.x = x;
        this.y = y;
        this.wid = wid;
        this.hei = hei;
    }

//This function create and Set an array of game cells (Rectangle type) and return it to the CheckersTable
    public static Rectangle[][] createRectangles(int N_ROWS, int N_COLS, int DIM_RECT, Player p1, Player p2){
        Rectangle[][] rectangles = new Rectangle[N_ROWS][N_COLS];
        Color color_rect;
            for (int i = 0; i < N_ROWS; i++)
                for (int j = 0; j < N_COLS; j++){
                    //Color the rectangles according to their position
                    /* AGGIORNAMENTO 07.08.2021 LUCA: La condizione qui scritta viene fatta anche in getColor, per cui getColor ritorna sempre darkGray
                    color_rect = (i % 2 == 0 && j % 2 != 0 || j % 2 == 0 && i % 2 != 0) ? myColor.getColor(i, j) : Color.white;
                    */
                    //CORREZIONE:
                    color_rect = myColor.getColor(i, j);

                    rectangles[i][j] = new Rectangle(0, 0, DIM_RECT, DIM_RECT);
                    rectangles[i][j].color = color_rect;
                }
        
        addPieces(rectangles, N_ROWS, N_COLS, p1,p2);
        return rectangles;
    }

    
    private static void addPieces(Rectangle[][] rectangles , int N_ROWS, int N_COLS, Player p1, Player p2){
        //Used to create game's pieces
        ConcreteFactoryM factory = new ConcreteFactoryM();
        Color pieceColor;
        String typePiece;
        Piece piece;
        for (int i = 0; i < N_ROWS; i++)
            for (int j = 0; j < N_COLS; j++){
                //Add Pieces in the correct position
                if ( (i < N_COLS/2 - 1 || i > N_COLS/2 ) && rectangles[i][j].color == Color.darkGray){
                    typePiece = ((i == 0 && j == N_ROWS-1) || (i == N_COLS-1 && j == 0 )) ? "archer": "pawn";
                    pieceColor = (i<3) ? Color.green : Color.red;
                    piece = factory.factoryMethod(typePiece, pieceColor);
                    
                    //Assignment of the player based on the color of the pawn
                    //Forse non serve. Basterebbe passargli un new Player a caso.
                    //Il controllo va fatto in base ai colori dei pezzi e del player
                    //Probabilmente sarà una features o un bug o non serve a niente.
                    //Questo if serve perché un player potrebbe cliccaere su un pezzo d icolore diverso dal suo
                    //Quindi otterremo il mouselistener del pezzo selezionato e lo confrontiamo con il colore del pezzo
                    // Tutto questo nella funzione player che invoca il click.
                    piece.addMouseListener((pieceColor == Color.red) ? p1 : p2);
                    rectangles[i][j].add(piece, BorderLayout.CENTER);
                    rectangles[i][j].hasPiece = true;
                    piece.setCoord(i,j);
                }
            }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(x,y,wid,hei);
        g.setColor(color);
        g.fillRect(x,y,wid,hei);
    }


    public boolean getHasPiece(){
        return hasPiece;
    }

    public void setHasPiece(boolean x){
        hasPiece = x;
    }

    public void setColor(Color c){
        color = c;
    }

}
