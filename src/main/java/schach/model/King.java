package schach.model;

/**
 * Class King representing the chess piece king
 */
public class King extends Piece {

    /**
     * Constructor defining the initial position, color and board of the king piece
     * @param position the Square it will be placed on initially
     * @param isWhite true if white, false if black
     * @param board the board the Bishop will be placed on
     */
    public King(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
    }

    @Override
    public String print() {
        if (super.isWhite) {
            return "\u2654";
        }
        else {
            return "\u265A";
        }
    }

    @Override
    public void updateLegals() {
        legalNextSquares.clear();
        boolean oppositeIsWhite = !isWhite;
        int row = position.getRow();
        int column = position.getColumn();

        checkRightLeft(column, row, oppositeIsWhite);

        checkForward(column, row, oppositeIsWhite);
        checkBackward(column, row, oppositeIsWhite);
        checkLeft(column, row, oppositeIsWhite);
        checkRight(column, row, oppositeIsWhite);
    }

    private void checkForward (int column, int row, boolean oppositeIsWhite) {
        if (row < 8) {
            Square nextSquare = board.getSquare(column, row + 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkBackward (int column, int row, boolean oppositeIsWhite) {
        if (row > 8) {
            Square nextSquare = board.getSquare(column, row - 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkLeft (int column, int row, boolean oppositeIsWhite) {
        if (column > 1) {
            Square nextSquare = board.getSquare(column - 1, row);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkRight (int column, int row, boolean oppositeIsWhite) {
        if (column < 8) {
            Square nextSquare = board.getSquare(column + 1, column);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkRightDiagonal(int column, int row, boolean oppositeIsWhite){
        if (column < 8 && row < 8){
            Square rightUp = board.getSquare(column + 1, row + 1);
            if (!rightUp.isOccupied()){
                legalNextSquares.add(rightUp);
            } else if(rightUp.isOccupied() && rightUp.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(rightUp);
            }
        }

        if (column < 8 && row > 1){
            Square rightDown = board.getSquare(column + 1, row - 1);
            if (!rightDown.isOccupied()){
                legalNextSquares.add(rightDown);
            } else if(rightDown.isOccupied() && rightDown.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(rightDown);
            }
        }
    }




}
