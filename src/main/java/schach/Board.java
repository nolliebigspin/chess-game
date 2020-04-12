package schach;

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
        //printMatrix();
    }

    /**
     * Initializing the matrix by creating Square typ objects and label them with the
     * typical chess denotation (a1, a2, ..., b1, b2,..)
     */
    private void initMatrix(){
        int x = 8;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                String denonation = ""  + resolveIntToChar(j+1) + x;
                this.squareMatrix[i][j] = new Square(denonation);
            }
            x--;
        }
    }

    /**
     * Method to help the initialization of the Matrix.
     * Converts a int to a char (0=a, 1=b, ..., 7=h)
     * @param i int to be converted
     * @return the converted char
     */
    private char resolveIntToChar(int i){
        switch (i){
            case 1:
                return 'a';
            case 2:
                return 'b';
            case 3:
                return 'c';
            case 4:
                return 'd';
            case 5:
                return 'e';
            case 6:
                return 'f';
            case 7:
                return 'g';
            case 8:
                return 'h';
            default:
                return 'x';
        }
    }

    /**
     * Initializes all pieces and places them on the board in the starting linup
     */
    public void initLineUp(){
        Peasant wP1 = new Peasant(getSquare("a2"), true);
        Peasant wP2 = new Peasant(getSquare("b2"), true);
        Peasant wP3 = new Peasant(getSquare("c2"), true);
        Peasant wP4 = new Peasant(getSquare("d2"), true);
        Peasant wP5 = new Peasant(getSquare("e2"), true);
        Peasant wP6 = new Peasant(getSquare("f2"), true);
        Peasant wP7 = new Peasant(getSquare("g2"), true);
        Peasant wP8 = new Peasant(getSquare("h2"), true);

        Rook wR1 = new Rook(getSquare("a1"), true);
        Rook wR2 = new Rook(getSquare("h1"), true);

        Knight wN1 = new Knight(getSquare("b1"), true);
        Knight wN2 = new Knight(getSquare("g1"), true);

        Bishop wB1 = new Bishop(getSquare("c1"), true);
        Bishop wB2 = new Bishop(getSquare("f1"), true);

        Queen wQ = new Queen(getSquare("d1"), true);
        King wK = new King(getSquare("e1"), true);


        Peasant bP1 = new Peasant(getSquare("a7"), false);
        Peasant bP2 = new Peasant(getSquare("b7"), false);
        Peasant bP3 = new Peasant(getSquare("c7"), false);
        Peasant bP4 = new Peasant(getSquare("d7"), false);
        Peasant bP5 = new Peasant(getSquare("e7"), false);
        Peasant bP6 = new Peasant(getSquare("f7"), false);
        Peasant bP7 = new Peasant(getSquare("g7"), false);
        Peasant bP8 = new Peasant(getSquare("h7"), false);

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
     * prints the matrix with the correct denotation
     * used for debugging
     * not used atm
     */
    public void printMatrix(){
        for (int i = 0; i<8; i++){
            for (int j = 0; j < 8; j++){
                System.out.print(squareMatrix[i][j].getDenotation() + " " ); // + " = " +  i + j + "| "
            }
            System.out.print("\n");
        }
    }

    /**
     * converts input string denotation to the belonging Square-Object
     * @param denotation denotes a square on the chess board
     * @return the square indicated by the denotation
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
    // @todo Check if target suqare is already occupied
    public void movePiece(String startingPos, String targetPos){
        if (getSquare(startingPos).isOccupied()){
            getSquare(startingPos).getOccupier().move(getSquare(targetPos));
        } else {
            System.out.println("No Piece found @" + getSquare(startingPos).getDenotation());
        }
        printBoard();
    }
}
