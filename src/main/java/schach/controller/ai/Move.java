package schach.controller.ai;

import schach.model.Piece;
import schach.model.Square;

/**
 * Class that represents a possible move
 */
public class Move {

    /**
     * the piece that will be moved by this move
     */
    private Piece piece;

    /**
     * the square the piece is occupying in before the move
     */
    private Square startingPos;

    /**
     * the square the piece is occupying after the move
     */
    private Square targetPos;


    /**
     * constructor that initializes the fields
     * @param piece the piece that will be moved
     * @param targetPos the square the piece will be moved to
     */
    public Move(Piece piece, Square targetPos){
        this.piece = piece;
        this.startingPos = piece.getPosition();
        this.targetPos = targetPos;
    }

    /**
     * Generates a string that represents the move and can be read by the Input classes (correct syntax)
     * @return String representing the move, with the right syntax to be read by the Input classes
     */
    public String moveAsString(){
        return startingPos.getDenotation() + "-" + targetPos.getDenotation();
    }


    /**
     * getter for the moved piece
     * @return the piece that will be moved
     */
    public Piece getPiece(){
        return piece;
    }

}
