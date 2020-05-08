package schach.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Board representing the chess board
 */
public class Board {

    /**
     * Matrix of Squares representing the chess board
     */
    private Square[][] squareMatrix = new Square[8][8];

    /**
     * List of pieces that are beaten, no longer activ on board
     */
    private List<Piece> cemetery = new ArrayList<>();

    private CheckRuler checkRuler;

    /**
     * Constructor, initializes the Square Matrix and the start Lineup
     */
    public Board() {
        initMatrix();
    }

    public CheckRuler getCheckRuler(){
        return checkRuler;
    }

    /**
     * Initializing the matrix by creating Square typ objects
     */
    private void initMatrix(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                this.squareMatrix[i][j] = new Square(i+1, j+1);

            }
        }
    }

    /**
     * Initializes all pieces and places them on the board in the starting lineup
     */
    public void initLineUp(){
        new Pawn(squareByDenotation("a2"), true, this);
        new Pawn(squareByDenotation("b2"), true, this);
        new Pawn(squareByDenotation("c2"), true, this);
        new Pawn(squareByDenotation("d2"), true, this);
        new Pawn(squareByDenotation("e2"), true, this);
        new Pawn(squareByDenotation("f2"), true, this);
        new Pawn(squareByDenotation("g2"), true, this);
        new Pawn(squareByDenotation("h2"), true, this);

        new Rook(squareByDenotation("a1"), true, this);
        new Rook(squareByDenotation("h1"), true, this);

        new Knight(squareByDenotation("b1"), true, this);
        new Knight(squareByDenotation("g1"), true, this);

        new Bishop(squareByDenotation("c1"), true, this);
        new Bishop(squareByDenotation("f1"), true, this);

        new Queen(squareByDenotation("d1"), true, this);
        new King(squareByDenotation("e1"), true, this);


        new Pawn(squareByDenotation("a7"), false, this);
        new Pawn(squareByDenotation("b7"), false, this);
        new Pawn(squareByDenotation("c7"), false, this);
        new Pawn(squareByDenotation("d7"), false, this);
        new Pawn(squareByDenotation("e7"), false, this);
        new Pawn(squareByDenotation("f7"), false, this);
        new Pawn(squareByDenotation("g7"), false, this);
        new Pawn(squareByDenotation("h7"), false, this);

        new Rook(squareByDenotation("a8"), false, this);
        new Rook(squareByDenotation("h8"), false, this);

        new Knight(squareByDenotation("b8"), false, this);
        new Knight(squareByDenotation("g8"), false, this);

        new Bishop(squareByDenotation("c8"), false, this);
        new Bishop(squareByDenotation("f8"), false, this);

        new Queen(squareByDenotation("d8"), false, this);
        new King(squareByDenotation("e8"), false, this);

        //TODO initialize chckRuler somerwhere else, also if singlepieces are added (THER MUST BE KINGS)
        this.checkRuler = new CheckRuler(this);
    }

    /**
     * Adds a wanted piece to a given position to the board
     * @param piece the piece entered in lowercase
     * @param squareDenotation the denotation of the position square the piece should be placed on
     * @param isWhite true if added piece should be white, flase if black
     */
    public void addPiece(String piece, String squareDenotation, boolean isWhite){
        Square position = squareByDenotation(squareDenotation);
        if (position.isOccupied()){
            System.out.println("Position already occupied");
            return;
        }
        switch (piece){
            case "pawn":
                new Pawn(position, isWhite, this);
                break;
            case "rook":
                new Rook(position, isWhite, this);
                break;
            case "knight":
                new Knight(position, isWhite, this);
                break;
            case "bishop":
                new Bishop(position, isWhite, this);
                break;
            case "queen":
                new Queen(position, isWhite, this);
                break;
            case "king":
                new King(position, isWhite, this);
                break;
            default:
                System.out.println("no valid piece: " + piece);
        }
    }

    /**
     * prints the current setup of the board
     */
    public void printBoard() {
        int x = 8;
        for (int i = 7; i >= 0; i--){
            System.out.print(""+ x);
            for (int j = 0; j < 8; j++){
                System.out.print("\t");
                if (squareMatrix[j][i].isOccupied()){
                    System.out.print(squareMatrix[j][i].getOccupier().print());
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
            x--;
        }
        System.out.println("\t a \t b \t c \t d \t e \t f \t g \t h");
    }

    /**
     * converts input string denotation to the belonging Square-Object
     * @param denotation denotes a square on the chess board
     * @return the square indicated by the denotation
     */
    public Square squareByDenotation(String denotation){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (this.squareMatrix[i][j].getDenotation().equals(denotation)) {
                    return squareMatrix[i][j];
                }
            }
        }
        return null;
    }

    /**
     * getter for a Square of the SquareMatrix
     * @param column the integer value of the column of the wanted square
     * @param row the integer value of the row of the wanted square
     * @return Square that is represented by the given coordinates
     */
    public  Square getSquare(int column, int row){
        return squareMatrix[column-1][row-1];
    }

    /**
     * Moving a piece from a certain square to a certain square
     * @param startingPos denotation of the square which the piece to be moved is occupying
     * @param targetPos denotation of the square which the piece is supposed to be moved to
     */
    public void movePiece(String startingPos, String targetPos) {
        updateAllLegalSquares();
        if (!squareByDenotation(startingPos).isOccupied()) {
            System.out.println("!Invalid Move: No Piece found!");
            return;
        }
        if (squareByDenotation(targetPos).isOccupied()
                && squareByDenotation(startingPos).getOccupier().isWhite == squareByDenotation(targetPos).getOccupier().isWhite) {
            System.out.println("!Invalid Move: Square already occupied");
            return;
        }
        squareByDenotation(startingPos).getOccupier().move(squareByDenotation(targetPos));
        updateAllLegalSquares();
    }

    /**
     * Returns all pieces currently active on the board of a given color
     * @param isWhite true for all white pieces, false for all black pieces
     * @return List of all active pieces of a color
     */
    public List<Piece> allActivePieces(boolean isWhite){
        List<Piece> pieces = new ArrayList<>();
        for (Square[] squareArray: squareMatrix){
            for (Square square: squareArray){
                if (square.isOccupied() && square.getOccupier().isWhite == isWhite){
                    pieces.add(square.getOccupier());
                }
            }
        }
        return pieces;
    }

    public void updateAllLegalSquares(){
        List<Piece> pieces = allActivePieces(true);
        pieces.addAll(allActivePieces(false));
        for (Piece piece: pieces){
            piece.updateLegals();
        }
    }

    /**
     * Lists all squares that are currently under attack by a color
     * @param attackerIsWhite true for all squares attacked by white, false for attacked by black
     * @return ArrayList of attacked squares
     */
    public List<Square> attackedSquares(boolean attackerIsWhite){
        List<Piece> allPieces = allActivePieces(attackerIsWhite);
        List<Square> attacked = new ArrayList<>();
        for (Piece piece: allPieces){
            if (piece instanceof Pawn){
                Pawn pawn = (Pawn) piece;
                attacked.addAll(pawn.getAttackedSquares());
            } else {
                attacked.addAll(piece.getLegalSquares());
            }
        }
        return attacked;
    }

    /**
     * checks if a given square is under attack by a given color
     * @param denotation denotation of square which should be checked
     * @param oppositeIsWhite true if opposite color is white, false if black
     * @return true if square is under attack, false if not
     */
    public boolean isUnderAttack(String denotation, boolean oppositeIsWhite){
        List<Square> attacked = attackedSquares(oppositeIsWhite);
        for (Square square: attacked){
            if (square.getDenotation().equals(denotation)){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a given piece to the cemetery, the list of beaten pieces
     * @param piece that should be added
     */
    public void addToCemetery(Piece piece){
        cemetery.add(piece);
    }

    /**
     * prints the list of beaten piece, by printing every beaten pieces unicode
     */
    public void printBeaten(){
        if (cemetery.size() == 0){
            System.out.println("no pieces beaten yet");
        }
        for (Piece piece: cemetery){
            System.out.println(piece.print());
        }
    }

    /**
     * debug
     * TODO delete
     */
    public void printAttacked() {
        System.out.println("Under attack by white:");
        List<Square> whites = attackedSquares(true);
        for (Square square : whites) {
            System.out.println(square.getDenotation());
        }
        System.out.println("\n Under attack by black:");
        List<Square> blacks = attackedSquares(false);
        for (Square square : blacks) {
            System.out.println(square.getDenotation());
        }
    }

    /**
     * debug
     * TODO delete
     */
    public void undo(String denotation){
        Piece piece = squareByDenotation(denotation).getOccupier();
        piece.undoMove();
    }
}
