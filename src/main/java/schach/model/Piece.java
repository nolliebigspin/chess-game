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
     * Square the piece was occupying in before a move
     */
    private Square previousPos;

    /**
     * Piece that got beaten by the move of this piece
     */
    private Piece beatenPiece;

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
        boolean inList = false;
        for (Square square: legalNextSquares){
            if (square == target){
                inList = true;
                break;
            }
        }
        if (inList && !board.getCheck().inCheckIfMoved(this, target)){
            acceptMove(target);
        } else {
            refuseMove();
        }
    }

    /**
     * allows the move to the target square
     * updates the position square of the Piece
     * @param target Square the Piece will be moved to
     */
    public void acceptMove(Square target){
        previousPos = this.position;
        if (target.isOccupied() && target.getOccupier().isWhite != isWhite){
            beatenPiece = target.getOccupier();
            board.addToCemetery(target.getOccupier());
        }
        position.setOccupied(false);
        position.setOccupier(null);
        this.position = target;
        position.setOccupied(true);
        position.setOccupier(this);
        //updateLegals(); //TODO maybe delete, redundant?
    }

    /**
     * Refuses invalid moves and prints Error Message
     */
    protected void refuseMove(){
        System.out.println("!Move is invalid");
    }

    /**
     * getter for list of legal squares
     * @return the list of legal squares
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
            System.out.println(square.getDenotation());
        }
    }

    /**
     * getter for color of the piece
     * @return true if white, false if black
     */
    public boolean isWhite(){
        return isWhite;
    }

    /**
     * Undoes the last move of the piece
     */
    public void undoMove(){
        Square newPos = position;
        position.setOccupied(false);
        position.setOccupier(null);
        this.position = previousPos;
        position.setOccupied(true);
        position.setOccupier(this);
        if (beatenPiece != null){
            newPos.setOccupied(true);
            newPos.setOccupier(beatenPiece);
        }
    }
}
