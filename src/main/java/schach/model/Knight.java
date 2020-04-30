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
        legalNextSquares.clear();
        boolean oppositeIsWhite = !isWhite;
        int row = position.getRow();
        int column = position.getColumn();

        checkForwardLeft(column, row, oppositeIsWhite);
        checkForwardRight(column, row, oppositeIsWhite);
        checkRightForward(column, row, oppositeIsWhite);
        checkRightBackward(column, row, oppositeIsWhite);
        checkBackwardRight(column, row, oppositeIsWhite);
        checkBackwardLeft(column, row, oppositeIsWhite);
        checkLeftBackward(column, row, oppositeIsWhite);
        checkLeftForward(column, row, oppositeIsWhite);
    }

    /**
     * checks if the next Square ForwardLeft is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkForwardLeft(int column, int row, boolean oppositeIsWhite) {
        if (column > 1 && row < 7) {
            Square nextSquare = board.getSquare(column - 1, row + 2);

            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    /**
     * checks if the next Square ForwardRight is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkForwardRight(int column, int row, boolean oppositeIsWhite) {
        if (column < 8 && row < 7) {
            Square nextSquare = board.getSquare(column + 1, row + 2);

            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    /**
     * checks if the next Square RightForward is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkRightForward(int column, int row, boolean oppositeIsWhite) {
        if (column < 7 && row < 8) {
            Square nextSquare = board.getSquare(column + 2, row + 1);

            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    /**
     * checks if the next Square RightBackward is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkRightBackward(int column, int row, boolean oppositeIsWhite) {
        if (column < 7 && row > 1) {
            Square nextSquare = board.getSquare(column + 2, row - 1);

            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    /**
     * checks if the next Square BackwardRight is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkBackwardRight(int column, int row, boolean oppositeIsWhite) {
        if (column  < 8 && row > 2) {
            Square nextSquare = board.getSquare(column + 1, row - 2);

            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    /**
     * checks if the next Square BackwardLeft is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkBackwardLeft(int column, int row, boolean oppositeIsWhite) {
        if (column > 1 && row > 2) {
            Square nextSquare = board.getSquare(column - 1, row - 2);

            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }
    /**
     * checks if the next Square LeftBackward is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkLeftBackward(int column, int row, boolean oppositeIsWhite) {
        if (column > 2 && row > 1) {
            Square nextSquare = board.getSquare(column - 2, row - 1);

            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    /**
     * checks if the next Square LeftForward is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkLeftForward(int column, int row, boolean oppositeIsWhite) {
        if (column > 2 && row < 8) {
            Square nextSquare = board.getSquare(column - 2, row + 1);

            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }
}
