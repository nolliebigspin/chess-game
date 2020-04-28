package schach.model;

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

    /**
     * List of Squares that the Square could be moved to by a legal move
     */
    protected List<Square> legalNextSquares = new ArrayList<Square>();

    /**
     * Constructor of Piece
     * the square the piece is position on is marked and gets the Piece Object passed
     * @param position denotation of the square the piece will be initiated on
     * @param isWhite true if it is a piece of the white set, false if black
     * @param board the board the piece will be played on
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
     * checks if given square is in the list of valid squares
     * @param target Square the Piece should be moved to
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
        position.setOccupied(false);
        this.position = target;
        position.setOccupied(true);
        position.setOccupier(this);
        updateLegals();
    }

    /**
     * Refuses the move and print Error Message
     */
    protected void refuseMove(){
        System.out.println("!Move not allowed");
    }

    /**
     * debug Method
     * //TODO delete
     */
    public void printLegals(){
        for (int i = 0; i < legalNextSquares.size(); i++){
            System.out.println(legalNextSquares.get(i).getDenotation());
        }
    }
}
