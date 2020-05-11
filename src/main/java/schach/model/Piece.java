package schach.model;

import schach.controller.Input;
import schach.model.Queen;
import schach.model.Rook;
import schach.model.Bishop;
import schach.model.Knight;

import java.util.ArrayList;
import java.util.List;

/**
 * abstract class representing a chess piece
 */
public abstract class Piece {

    /**
     * The Square the Piece is currently occupying
     */
    protected Square position;

    /**
     * Boolean to indicate the color of the Piece
     */
    protected boolean isWhite;

    /**
     * Board that the piece are on
     */
    protected Board board;

    public List<Square> getLegalNextSquares() {
        return legalNextSquares;
    }

    /**
     * List of Squares that the Square could be moved to by a legal move
     */
    protected List<Square> legalNextSquares = new ArrayList<Square>();

    /**
     * Constructor of Piece
     * the square where the piece is placed, marked and gets the Piece Object passed
     * @param position denotation of the square where the piece will be initiated on
     * @param isWhite true if it is a piece of the white set, false if black
     * @param board the board where the piece will be played on
     */
    public Piece (Square position, boolean isWhite, Board board){
        this.position = position;
        this.isWhite = isWhite;
        this.board = board;
        position.setOccupied(true);
        position.setOccupier(this);
    }

    /**
     * getter for the square the piece is currently positioned on
     * @return the square the piece is occupying
     */
    public Square getPosition(){
        return this.position;
    }

    /**
     * abstract Method to print the piece
     * @return String that represents the piece
     */
    public abstract String print();

    /**
     * abstract Method to update the List of legal Squares
     */
    protected abstract void updateLegals();

    /**
     * abstract Method to move the Piece
     * checks if a given square is in the list of valid squares
     * @param target is the target square where the piece is moved to
     */
    public void move(Square target){
        updateLegals();
        for (Square square: legalNextSquares){
            if (square == target){
                acceptMove(target);
                return;
            }
        }
        refuseMove();
    }

    /**
     * allows the move to the target square
     * updates the position square of the Piece
     * @param target Square the Piece will be moved to
     */
    protected void acceptMove(Square target){
        if (target.isOccupied() && target.getOccupier().isWhite != isWhite){
            board.addToCemetery(target.getOccupier());
        }
        position.setOccupied(false);
        this.position = target;
        position.setOccupied(true);
        position.setOccupier(this);
        updateLegals();
    }

    /**
     * Refuses invalid moves and prints Error Message
     */
    protected void refuseMove(){
        System.out.println("!Move is invalid");
    }

    /**
     * getter for list of legal squares
     * @return
     */
    public List<Square> getLegalSquares(){
        return legalNextSquares;
    }

    /**
     * debug Method
     * //TODO delete
     */
    public void printLegals(){
        for (Square square: legalNextSquares){
            System.out.print(square.getDenotation());
        }
    }

    /**
     * This method does the promotion for a pawn
     * @param prom String for Piece which the pawn promotes to
     * @param pos Position on Board where the promoted pawn stands
     */
    public void doPromotion(String prom, Square pos) {
        if (this.isWhite && pos.getRow() != 8 || !(this instanceof Pawn)) {
            return;
        } else if (!this.isWhite && pos.getRow() != 1 || !(this instanceof Pawn)) {
            return;
        }
        switch (prom) {
            case "Q":
                Queen queenProm = new Queen(pos, this.isWhite, this.board);
                break;
            case "R":
                Rook rookProm = new Rook(pos, this.isWhite, this.board);
                break;
            case "B":
                Bishop bishopProm = new Bishop(pos, this.isWhite, this.board);
                break;
            case "N":
                Knight knightProm = new Knight(pos, this.isWhite, this.board);
                break;
            default:
        }
    }
}
