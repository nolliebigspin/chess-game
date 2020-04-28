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

    private boolean checkIsWhite() {
        boolean oppositeIsWhite;
        if (isWhite){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }
        return oppositeIsWhite;
    }

    private void checkForwardLeft(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() + 2, position.getRow() - 1);

        if (!nextSquare.isOccupied()) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkForwardRight(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() + 2, position.getRow() + 1);

        if (!nextSquare.isOccupied()) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkRightForward(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() + 1, position.getRow() + 2);

        if (!nextSquare.isOccupied()) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkRightBackward(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() - 1, position.getRow() + 2);

        if (!nextSquare.isOccupied()) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkBackwardRight(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() - 2, position.getRow() + 1);

        if (!nextSquare.isOccupied()) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkBackwardLeft(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() - 2, position.getRow() - 1);

        if (!nextSquare.isOccupied()) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkLeftBackward(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() - 1, position.getRow() - 2);

        if (!nextSquare.isOccupied()) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
            legalNextSquares.add(nextSquare);
        }
    }

    private void checkLeftForward(boolean oppositeIsWhite) {
        Square nextSquare = board.getSquare(position.getColumn() + 1, position.getRow() - 2);

        if (!nextSquare.isOccupied()) {
            legalNextSquares.add(nextSquare);
        } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
            legalNextSquares.add(nextSquare);
        }
    }
}
