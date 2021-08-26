import javax.swing.*;
import java.awt.*;


public abstract class Piece extends JComponent {
   private final Color color;
   protected int points;
   private ImagePiece objIMG;
   private final int DIM_IMG;
   private Point coord;
   private Player owner;
   
   protected final CheckersTable TABLE;
   private static Point posAfterMove = new Point(); //free position on which to show suggestion 
   
   public Piece(Color c, int p, Player ply) throws Exception{
      color = c;
      points = p;
      owner = ply;
      addMouseListener(owner);
      objIMG = new ImagePiece(getPathIMG());
      TABLE = CheckersTable.getInstance();
      DIM_IMG = Box.DIM_BOX - 10; //Dimension of a Box - margin
      objIMG.setImg(ImageFunctions.scale(objIMG.getImg(), DIM_IMG, DIM_IMG));
      setPreferredSize(new Dimension(DIM_IMG, DIM_IMG));
      coord = new Point();
   }

   protected abstract String getPathIMG();

   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g.drawImage(objIMG.getImg(), 0, 0,null);
   }

 

   //return values: 2 (deve mangiare) -  0(si può muovere o meno (non verificato!!))
   protected int showSuggestions(int direction){
      final int GOLEFT_COL = coord.y - 1, GORIGHT_COL = coord.y + 1;
      int esito_left, esito_right;
      
      posAfterMove.x = direction;
      esito_left = checkMove(posAfterMove.x, GOLEFT_COL);

      if (esito_left == 0 || esito_left == 1){ // Se a sinistra non si può mangiare
         esito_right = checkMove(posAfterMove.x, GORIGHT_COL); //Vedo se a destra posso muovermi o mangiare
         if (esito_right == 2){ //DEVO mangiare a DESTRA
            posAfterMove.x = setRowonEat(posAfterMove);
            TABLE.showFreeBox(posAfterMove.x, posAfterMove.y);
            return 2;
         }
         else{ //Se nemmeno a destra si può mangiare allora..
            if (esito_left == 0) //Se posso muovermi a sinistra
               TABLE.showFreeBox(posAfterMove.x, GOLEFT_COL);   
            if (esito_right == 0) //Se posso muovermi a destra
               TABLE.showFreeBox(posAfterMove.x, GORIGHT_COL); 
         }
      }
      else if (esito_left == 2){ //DEVO mangiare a SINISTRA
         posAfterMove.x = setRowonEat(posAfterMove);
         TABLE.showFreeBox(posAfterMove.x, posAfterMove.y);
         return 2;
      }
      return 0;
  }
  
   //Indica la colonna da colorare quando occorre mangiare
   protected int setColonEat(int col){
      return (col > coord.y) ? coord.y + 2 : coord.y - 2;
   }
      
   // Indica la riga da colorare quando occorre mangiare
   //Position indica il rettangolo nel quale si trova il pezzo da mangiare.
   protected int setRowonEat(Point position){
      return (coord.x - position.x > 0) ? coord.x - 2 : coord.x + 2; 
   }

   protected int setRowbyColor(){
      return (getColor() == Color.red) ? coord.x - 1 : coord.x + 1;
   }

   protected int checkMove(int row, final int COL_DIRECTION){
      Point boxToAnalize = new Point(row, COL_DIRECTION); //First Box to analyze
      int r = enemyPiece_inBox(boxToAnalize);
      if(r == 2 && !arcTryEatArch(boxToAnalize)){ //if piece is in first box && piece is not a Wizard trying eat enemy Wizard
         //Enemy piece found but I don't know if I can eat because I have to check if SECONDBOX BEHIND boxToAnalize is free
         Point second_boxToAnalize = new Point(setRowonEat(boxToAnalize), setColonEat(boxToAnalize.y));
         posAfterMove.y = second_boxToAnalize.y; //save new position to Move (for suggestion)
         return (enemyPiece_inBox(second_boxToAnalize) == 0) ? 2 : 1; // 2: second box is FREE and I MUST EAT piece in first box
      }                                                                // 1: second box is NOT FREE, so I cannot eat first box or move 
      else 
         return (r == 0) ? 0 : 1; // 0: FIRST box is free so I can move.
   }                             // 1: FIRST box is not free, so I cannot eat first box or move 
   
   //returns true if an Wizard try eat another Wizard, else returns false
   private boolean arcTryEatArch(Point position) {
      Box box = TABLE.getBoxfromList(position.x, position.y);
      String enemy_pieceClass = box.getPiece().getClass().toString();
      String pieceClass = getClass().toString();
      
      return pieceClass.equals("class Wizard") && enemy_pieceClass.equals("class Wizard");
   }

   protected int enemyPiece_inBox(Point position){
      if (TABLE.illegalMove(position.x) || TABLE.illegalMove(position.y))
         return 1; //Piece cannot go over the table (ex: for 6x6 matrix piece cannot go in -1,-1 or 6,6: only 0 to 5)

      Box box = TABLE.getBoxfromList(position.x, position.y);
      if(box.HasPiece()){ // if there is a piece
         Piece piece = box.getPiece();
         boolean enemy_piece = ! getColor().equals(piece.getColor());
         return (enemy_piece) ? 2 : 1; // 2: enemy piece, maybe I can eat it
      }                                      // 1: my piece,  I cannot eat it
      else
         return 0; // rectangle is free
   }

   // Getters and Setters methods..

   public void setCoord(int x, int y){
      coord.x = x;
      coord.y = y;
   }

   public Point getCoord() {
      return coord;
   }

   protected Color getColor(){
      return color;
   }

   public Piece getPtoMove(){
      return this;
   }

   public Player getOwner(){
      return owner;
   }

   public int getPoints(){
      return points;
   }

}
