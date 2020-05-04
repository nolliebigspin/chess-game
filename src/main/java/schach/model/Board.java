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
     * Constructor, initializes the Square Matrix and the start Lineup
     */
    public Board() {
        initMatrix();
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
        Pawn wP1 = new Pawn(squareByDenotation("a2"), true, this);
        Pawn wP2 = new Pawn(squareByDenotation("b2"), true, this);
        Pawn wP3 = new Pawn(squareByDenotation("c2"), true, this);
        Pawn wP4 = new Pawn(squareByDenotation("d2"), true, this);
        Pawn wP5 = new Pawn(squareByDenotation("e2"), true, this);
        Pawn wP6 = new Pawn(squareByDenotation("f2"), true, this);
        Pawn wP7 = new Pawn(squareByDenotation("g2"), true, this);
        Pawn wP8 = new Pawn(squareByDenotation("h2"), true, this);

        Rook wR1 = new Rook(squareByDenotation("a1"), true, this);
        Rook wR2 = new Rook(squareByDenotation("h1"), true, this);

        Knight wN1 = new Knight(squareByDenotation("b1"), true, this);
        Knight wN2 = new Knight(squareByDenotation("g1"), true, this);

        Bishop wB1 = new Bishop(squareByDenotation("c1"), true, this);
        Bishop wB2 = new Bishop(squareByDenotation("f1"), true, this);

        Queen wQ = new Queen(squareByDenotation("d1"), true, this);
        King wK = new King(squareByDenotation("e1"), true, this);


        Pawn bP1 = new Pawn(squareByDenotation("a7"), false, this);
        Pawn bP2 = new Pawn(squareByDenotation("b7"), false, this);
        Pawn bP3 = new Pawn(squareByDenotation("c7"), false, this);
        Pawn bP4 = new Pawn(squareByDenotation("d7"), false, this);
        Pawn bP5 = new Pawn(squareByDenotation("e7"), false, this);
        Pawn bP6 = new Pawn(squareByDenotation("f7"), false, this);
        Pawn bP7 = new Pawn(squareByDenotation("g7"), false, this);
        Pawn bP8 = new Pawn(squareByDenotation("h7"), false, this);

        Rook bR1 = new Rook(squareByDenotation("a8"), false, this);
        Rook bR2 = new Rook(squareByDenotation("h8"), false, this);

        Knight bN1 = new Knight(squareByDenotation("b8"), false, this);
        Knight bN2 = new Knight(squareByDenotation("g8"), false, this);

        Bishop bB1 = new Bishop(squareByDenotation("c8"), false, this);
        Bishop bB2 = new Bishop(squareByDenotation("f8"), false, this);

        Queen bQ = new Queen(squareByDenotation("d8"), false, this);
        King bK = new King(squareByDenotation("e8"), false, this);


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
        Piece p;
        switch (piece){
            case "pawn":
                p = new Pawn(position, isWhite, this);
                break;
            case "rook":
                p = new Rook(position, isWhite, this);
                break;
            case "knight":
                p = new Knight(position, isWhite, this);
                break;
            case "bishop":
                p = new Bishop(position, isWhite, this);
                break;
            case "queen":
                p = new Queen(position, isWhite, this);
                break;
            case "king":
                p = new King(position, isWhite, this);
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
    // TODO Check if target square is already occupied
    public void movePiece(String startingPos, String targetPos) {
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
    }

    /**
     * Returns all pieces currently active on the board of a given color
     * @param isWhite true for all white pieces, false for all black pieces
     * @return List of all active pieces of a color
     */
    private ArrayList<Piece> allActivePieces(boolean isWhite){
        ArrayList<Piece> pieces = new ArrayList<>();
        for (Square[] squareArray: squareMatrix){
            for (Square square: squareArray){
                if (square.isOccupied() && square.getOccupier().isWhite == isWhite){
                    pieces.add(square.getOccupier());
                }
            }
        }
        return pieces;
    }

    /**
     * Lists all squares that are currently under attack by a color
     * @param isWhite true for all squares attacked by white, false for attacked by black
     * @return ArrayList of attacked squares
     */
    private ArrayList<Square> attackedSquares(boolean isWhite){
        ArrayList<Piece> allPieces = allActivePieces(isWhite);
        ArrayList<Square> attacked = new ArrayList<>();
        for (Piece piece: allPieces){
            piece.updateLegals();
            attacked.addAll(piece.getLegalSquares());
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
        ArrayList<Square> attacked = attackedSquares(oppositeIsWhite);
        for (Square square: attacked){
            if (square.getDenotation().equals(denotation)){
                return true;
            }
        }
        return false;
    }

    /**
     * debug
     * TODO delete
     */
    public void printAttacked() {
        System.out.println("Under attack by white:");
        ArrayList<Square> whites = attackedSquares(true);
        for (Square square : whites) {
            System.out.println(square.getDenotation());
        }
        System.out.println("\n Under attack by black:");
        ArrayList<Square> blacks = attackedSquares(false);
        for (Square square : blacks) {
            System.out.println(square.getDenotation());
        }
    }
}
