package schach.model;

/**
 * Class Queen representing the chess piece queen
 */
public class Queen extends Piece {

    /**
     * Constructor defining the initial position, color and board of the queen piece
     * @param position the Square it will be placed on initially
     * @param isWhite true if white, false if black
     * @param board the board the Bishop will be placed on
     */
    public Queen(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
    }

    @Override
    public String print() {
        if (super.isWhite) {
            return "\u2655";
        }
        else {
            return "\u265B";
        }
    }

    @Override
    public void updateLegals() {

    }

    @Override
    public void move(Square target) {

    }
}

