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
        if (super.white) {
            return "\u2658";
        }
        else {
            return "\u265E";
        }
    }

    @Override
    public void updateLegals() {
        legalNextSquares.clear();

        int row = position.getRow();
        int column = position.getColumn();
        checkForwardLeft(column, row);
        checkForwardRight(column, row);
        checkRightForward(column, row);
        checkRightBackward(column, row);
        checkBackwardRight(column, row);
        checkBackwardLeft(column, row);
        checkLeftBackward(column, row);
        checkLeftForward(column, row);

        if (board.getCheck().kingInCheck(white)){ //TODO: , board.attackedSquares(!isWhite)
            legalNextSquares = board.getCheck().legalsToResolveCheck(this);
            return;
        }
    }

    private void checkForwardLeft(int column, int row) {
        if (column > 1 && row < 7) {
            Square nextSquare = board.getSquare(column - 1, row + 2);
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkForwardRight(int column, int row) {
        if (column < 8 && row < 7) {
            Square nextSquare = board.getSquare(column + 1, row + 2);
            legalNextSquares.add(nextSquare);
        }
    }

    /**
     * checks if the next Square RightForward is reachable or if it's occupied by your own color
     */
    private void checkRightForward(int column, int row) {
        if (column < 7 && row < 8) {
            Square nextSquare = board.getSquare(column + 2, row + 1);
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkRightBackward(int column, int row ) {
        if (column < 7 && row > 1) {
            Square nextSquare = board.getSquare(column + 2, row - 1);
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkBackwardRight(int column, int row) {
        if (column  < 8 && row > 2) {
            Square nextSquare = board.getSquare(column + 1, row - 2);
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkBackwardLeft(int column, int row) {
        if (column > 1 && row > 2) {
            Square nextSquare = board.getSquare(column - 1, row - 2);
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkLeftBackward(int column, int row) {
        if (column > 2 && row > 1) {
            Square nextSquare = board.getSquare(column - 2, row - 1);
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkLeftForward(int column, int row) {
        if (column > 2 && row < 8) {
            Square nextSquare = board.getSquare(column - 2, row + 1);
            legalNextSquares.add(nextSquare);
        }
    }
}
