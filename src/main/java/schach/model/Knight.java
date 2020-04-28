package schach.model;

/**
 * Class Knight representing the chess piece knight
 */
public class Knight extends Piece {

    /**
     * Constructor defining the initial position, color and board of the knight piece
     * @param position the Square it will be placed on initially
     * @param isWhite true if white, false if black
     * @param board the board the Bishop will be placed on
     */
    public Knight(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
    }

    @Override
    public String print() {
        if (super.isWhite) {
            return "\u2658";
        }
        else {
            return "\u265E";
        }
    }

    @Override
    public void updateLegals() {
        checkForwardLeft();
        checkForwardRight();
        checkRightForward();
        checkRightBackward();
        checkBackwardRight();
        checkBackwardLeft();
        checkLeftBackward();
        checkLeftForward();
    }

    private void checkForwardLeft() {

    }

    private void checkForwardRight() {

    }

    private void checkRightForward() {

    }

    private void checkRightBackward() {

    }

    private void checkBackwardRight() {

    }

    private void checkBackwardLeft() {

    }

    private void checkLeftBackward() {

    }

    private void checkLeftForward() {

    }
}
