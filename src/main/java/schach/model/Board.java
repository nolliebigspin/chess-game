package schach.model;

import schach.controller.Check;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Class Board representing the chess board
 */
public class Board {

    /**
     * Matrix of Squares representing the chess board
     */
    private Square[][] squareMatrix = new Square[8][8];

    /**
     * List of pieces that are beaten, no longer available on the board
     */
    private List<Piece> cemetery = new ArrayList<>();

    /**
     * Check class to check for check-situation on the board
     */
    private Check check;

    /**
     * Piece that was moved in the last move
     */
    private Piece lastMoved;

    /**
     * Indicates the number of moves already made
     */
    private int moveCount;

    /**
     * List that contains all the states the board was in
     */
    private List<BoardState> states;

    /**
     * The current state in before a earlier state will be loaded, used to redo the undo
     */
    private BoardState redoState;

    /**
     * Constructor, initializes the Square Matrix
     */
    public Board() {
        initMatrix();
        this.check = new Check(this);
        this.states = new ArrayList<>();
        this.moveCount = 0;
    }

    /**
     * Constructor, initializing the Square Matrix and places pieces given by a HashMap
     * @param positioning HashMap that represents the wanted Positioning
     */
    public Board(List<String> positioning){
        initMatrix();
        Positioning pos = new Positioning(this);
        pos.writePositioning(positioning);
        this.check = new Check(this);
        updateAllLegalSquares();
    }

    /**
     * Initializing the matrix by creating Square typ objects
     */
    public void initMatrix(){
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

        check.updateKings();

        updateAllLegalSquares();

        List<Piece> actives = allActivePieces(true);
        actives.addAll(allActivePieces(false));
        List<PieceState> pieceStates = new ArrayList<>();
        for (Piece piece: actives){
            pieceStates.add(new PieceState(piece, 0));
        }
        //creates the first state
        this.states.add(new BoardState(this, 0, pieceStates));
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
            System.out.println("The given position is either occupied or it's an invalid backward move!");
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
                System.out.print("no valid piece: " + piece);
        }
        check.updateKings();
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
        System.out.println("");
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
    public Square getSquare(int column, int row){
        return squareMatrix[column-1][row-1];
    }

    /**
     * Moving a piece from a certain square to a certain square
     * @param startingPos denotation of the square which the piece to be moved is occupying
     * @param targetPos denotation of the square which the piece is supposed to be moved to
     */
    public void movePiece(String startingPos, String targetPos) {
        updateAllLegalSquares();
        if (squareByDenotation(targetPos).isOccupied()
                && squareByDenotation(startingPos).getOccupier().white == squareByDenotation(targetPos).getOccupier().white) {
            System.out.println("!Move not allowed" + "(" + startingPos + "-" + targetPos + ")");
            return;
        }
        moveCount++;
        Piece p = squareByDenotation(startingPos).getOccupier();
        try {
            squareByDenotation(startingPos).getOccupier().move(squareByDenotation(targetPos));
        } catch (Exception e) {
            e.printStackTrace();
            printBoard();
            System.out.println(startingPos + "-" + targetPos);
        }

        updateAllLegalSquares();

        if (p.isValidMove()){
            List<PieceState> currentPieceStates = new ArrayList<>();
            try {
                currentPieceStates.addAll(states.get(states.size() - 1).getPieceStates());
            } catch (Exception e){
                currentPieceStates = new ArrayList<>();
            }
            states.add(new BoardState(this, moveCount, currentPieceStates, new PieceState(p, moveCount)));
        } else {
            moveCount--;
        }
        //updateAllLegalSquares();  //Necessary because king has to filter again, after all moved: TODO FIX! DONE?

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
                if (square.isOccupied() && square.getOccupier().white == isWhite){
                    pieces.add(square.getOccupier());
                }
            }
        }
        return pieces;
    }

    /**
     * Updates the list of legal squares of every active piece
     */
    public void updateAllLegalSquares(){
        List<Piece> pieces = allActivePieces(true);
        pieces.addAll(allActivePieces(false));
        for (Piece piece: pieces){
            piece.updateLegals();
        }
        for (Piece p : pieces){
            if (p instanceof King){
                p.updateLegals();
            }
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
                attacked.addAll(pawn.getAttackedSquaresAll());
            } else if (piece instanceof King){
                King king = (King) piece;
                attacked.addAll(king.getAttackedSquares());
            } else {
                attacked.addAll(piece.getLegalNextSquares());
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
     * Removes a given piece from the cemetry, the list of beaten pieces
     * @param piece that should be removed
     */
    public void removeFromCemetery(Piece piece){
        cemetery.remove(piece);
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
     * loads a state contained in the list of states of the board
     * @param index of the State in the list (0 is the initial (starting lineup) state)
     */
    public void loadState(int index){
        redoState = states.get(states.size() - 1);
        if (index < states.size()){
            BoardState state = states.get(index);
            state.load();
        }
        //clears the matrix - removes all pieces not connected correctly to its position square
        for (Square[] squareArray: squareMatrix){
            for (Square square: squareArray){
                if (square.isOccupied() && square != square.getOccupier().getPosition()){
                    square.setOccupied(false);
                    square.setOccupier(null);
                }
            }
        }
    }

    /**
     * redoes the last move undone by a loadState() call
     */
    public void redo(){
        redoState.load();
        //clears the matrix - removes all pieces not connected correctly to its position square
        for (Square[] squareArray: squareMatrix){
            for (Square square: squareArray){
                if (square.isOccupied() && square != square.getOccupier().getPosition()){
                    square.setOccupied(false);
                    square.setOccupier(null);
                }
            }
        }
        printBoard();
    }

    public List<Piece> getCemetery() {
        return cemetery;
    }

    /**
     * Getter for the Check
     * @return the check
     */
    public Check getCheck(){
        return check;
    }

    /**
     * getter to return the last moved piece
     * @return piece that was moved in the last move
     */
    public Piece getLastMoved(){
        return lastMoved;
    }

    /**
     * setter for the last moved piece
     * @param piece the piece that should be set as the last moved piece
     */
    public void setLastMoved(Piece piece){
        this.lastMoved = piece;
    }

    /**
     * gets the current positioning of the pieces on the board
     * @return Map containing the Piece and the belonging square both as Strings
     */
    public List<String> getPositioning(){
        Positioning pos = new Positioning(this);
        return pos.readPositioning();
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }


    public List<BoardState> getStates() {
        return states;
    }

    public void setStates(List<BoardState> states) {
        this.states.clear();
        this.states.addAll(states);
    }

    public void setCemetery(List<Piece> cemetery) {
        this.cemetery.clear();
        this.cemetery.addAll(cemetery);
    }
}
