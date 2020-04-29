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
    }

    private void checkUpDown(int column, int row, boolean oppositeIsWhite){
        if (row <= 8){
            Square toTheRight = board.getSquare(column, row);
            if (toTheRight.isOccupied())
            }
        }
    }



}
