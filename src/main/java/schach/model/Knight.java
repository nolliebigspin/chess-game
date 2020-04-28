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
        boolean oppositeIsWhite = checkIsWhite();
        legalNextSquares.clear();

        checkForwardLeft(oppositeIsWhite);
        checkForwardRight(oppositeIsWhite);
        checkRightForward(oppositeIsWhite);
        checkRightBackward(oppositeIsWhite);
        checkBackwardRight(oppositeIsWhite);
        checkBackwardLeft(oppositeIsWhite);
        checkLeftBackward(oppositeIsWhite);
        checkLeftForward(oppositeIsWhite);
    }

    /**
     * checks if color of opposite player is white
     * @return opposite isWhite
     */
    private boolean checkIsWhite() {
        boolean oppositeIsWhite;
        if (isWhite){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }
        return oppositeIsWhite;
    }

    /**
     * checks if a next Square is out of Board matrix
     * @param nextSquare the Square which is checked
     * @return boolean if Square is in bounds or out of bounds
     */
    private boolean checkIsOutOfBounds(Square nextSquare) {
        boolean isOutOfBounds = false;
        if (nextSquare.getRow() < 1 || nextSquare.getRow() > 8 || nextSquare.getColumn() < 1 || nextSquare.getColumn() > 8) {
            isOutOfBounds = true;
        }
        return isOutOfBounds;
    }

    /**
     * checks if the next Square ForwardLeft is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkForwardLeft(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() + 2, position.getRow() - 1);

        if (!nextSquare.isOccupied() && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        }
    }

    /**
     * checks if the next Square ForwardRight is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkForwardRight(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() + 2, position.getRow() + 1);

        if (!nextSquare.isOccupied() && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        }
    }

    /**
     * checks if the next Square RightForward is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkRightForward(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() + 1, position.getRow() + 2);

        if (!nextSquare.isOccupied() && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        }
    }

    /**
     * checks if the next Square RightBackward is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkRightBackward(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() - 1, position.getRow() + 2);

        if (!nextSquare.isOccupied() && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        }
    }

    /**
     * checks if the next Square BackwardRight is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkBackwardRight(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() - 2, position.getRow() + 1);

        if (!nextSquare.isOccupied() && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        }
    }

    /**
     * checks if the next Square BackwardLeft is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkBackwardLeft(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() - 2, position.getRow() - 1);

        if (!nextSquare.isOccupied() && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        }
    }

    /**
     * checks if the next Square LeftBackward is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkLeftBackward(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() - 1, position.getRow() - 2);

        if (!nextSquare.isOccupied() && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        }
    }

    /**
     * checks if the next Square LeftForward is reachable or if it's occupied by your own color
     * @param oppositeIsWhite if the color of the opponent is white or not
     */
    private void checkLeftForward(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() + 1, position.getRow() - 2);

        if (!nextSquare.isOccupied() && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite && !checkIsOutOfBounds(nextSquare)) {
            legalNextSquares.add(nextSquare);
        }
    }
}
