package schach.model;

/**
 * Class Board representing the chess board
 */
public class Board {

    /**
     * Matrix of Squares representing the chess board
     */
    private Square[][] squareMatrix = new Square[8][8];

    /**
     * Constructor, initializes the Square Matrix and the start Linup
     */
    public Board(){
        initMatrix();
        initLineUp();
    }

    /**
     * Initializing the matrix by creating Square typ objects and label them with the
     * typical chess denotation (a1, a2, ..., b1, b2,..)
     */
    private void initMatrix(){
        int x = 8;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                this.squareMatrix[i][j] = new Square(j+1, x);
            }
            x--;
        }
    }

    /**
     * Initializes all pieces and places them on the board in the starting linup
     */
    private void initLineUp(){
        Pawn wP1 = new Pawn(getSquare("a2"), true);
        Pawn wP2 = new Pawn(getSquare("b2"), true);
        Pawn wP3 = new Pawn(getSquare("c2"), true);
        Pawn wP4 = new Pawn(getSquare("d2"), true);
        Pawn wP5 = new Pawn(getSquare("e2"), true);
        Pawn wP6 = new Pawn(getSquare("f2"), true);
        Pawn wP7 = new Pawn(getSquare("g2"), true);
        Pawn wP8 = new Pawn(getSquare("h2"), true);

        Rook wR1 = new Rook(getSquare("a1"), true);
        Rook wR2 = new Rook(getSquare("h1"), true);

        Knight wN1 = new Knight(getSquare("b1"), true);
        Knight wN2 = new Knight(getSquare("g1"), true);

        Bishop wB1 = new Bishop(getSquare("c1"), true);
        Bishop wB2 = new Bishop(getSquare("f1"), true);

        Queen wQ = new Queen(getSquare("d1"), true);
        King wK = new King(getSquare("e1"), true);


        Pawn bP1 = new Pawn(getSquare("a7"), false);
        Pawn bP2 = new Pawn(getSquare("b7"), false);
        Pawn bP3 = new Pawn(getSquare("c7"), false);
        Pawn bP4 = new Pawn(getSquare("d7"), false);
        Pawn bP5 = new Pawn(getSquare("e7"), false);
        Pawn bP6 = new Pawn(getSquare("f7"), false);
        Pawn bP7 = new Pawn(getSquare("g7"), false);
        Pawn bP8 = new Pawn(getSquare("h7"), false);

        Rook bR1 = new Rook(getSquare("a8"), false);
        Rook bR2 = new Rook(getSquare("h8"), false);

        Knight bN1 = new Knight(getSquare("b8"), false);
        Knight bN2 = new Knight(getSquare("g8"), false);

        Bishop bB1 = new Bishop(getSquare("c8"), false);
        Bishop bB2 = new Bishop(getSquare("f8"), false);

        Queen bQ = new Queen(getSquare("d8"), false);
        King bK = new King(getSquare("e8"), false);


    }

    /**
     * prints the current setup of the board
     */
    public void printBoard(){
        int x = 8;
        for (int i = 0; i < 8; i++){
            System.out.print(""+ x);
            for (int j = 0; j < 8; j++){
                System.out.print("\t");
                if (squareMatrix[i][j].isOccupied()){
                    System.out.print(squareMatrix[i][j].getOccupier().print());
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
            x--;
        }
        System.out.println("\t a \t b \t c \t d \t e \t f \t g \t h");
    }

    /**
     * converts input string denotation to the belonging Square-Object
     * @param denotation denotes a square on the chess board
     * @return the square indicated by the denotation
     * // TODO: 13.04.2020 rename
     */
    public Square getSquare(String denotation){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (this.squareMatrix[i][j].getDenotation().equals(denotation)){
                    return squareMatrix[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Moving a piece from a certain square to a certain square
     * @param startingPos denotation of the square which the piece to be moved is occupying
     * @param targetPos denotation of the square which the piece is supposed to be moved to
     */
    // TODO Check if target square is already occupied
    public void movePiece(String startingPos, String targetPos) {
        if (!getSquare(startingPos).isOccupied()) {
            System.out.println("!Invalid Move: No Piece found!");
            return;
        }
        if (getSquare(targetPos).isOccupied()
                && getSquare(startingPos).getOccupier().isWhite == getSquare(targetPos).getOccupier().isWhite) {
            System.out.println("!Invalid Move: Square already occupied");
            return;
        }
        getSquare(startingPos).getOccupier().move(getSquare(targetPos));
    }

}
