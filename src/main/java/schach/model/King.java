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

        checkUpDown(column, row, oppositeIsWhite);
        checkRightLeft(column, row, oppositeIsWhite);
    }

    private void checkUpDown(int column, int row, boolean oppositeIsWhite){

        if (row < 8){
            Square ahead = board.getSquare(column, row + 1);
            if (!ahead.isOccupied()){
                legalNextSquares.add(ahead);
            } else if(ahead.isOccupied() && ahead.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(ahead);
            }
        }

        if (row > 1){
            Square behind = board.getSquare(column, row - 1);
            if (!behind.isOccupied()){
                legalNextSquares.add(behind);
            } else if(behind.isOccupied() && behind.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(behind);
            }
        }
    }

    private void checkRightLeft(int column, int row, boolean oppositeIsWhite){

        if (column < 8){
            Square right = board.getSquare(column + 1, row);
            if (!right.isOccupied()){
                legalNextSquares.add(right);
            } else if(right.isOccupied() && right.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(right);
            }
        }

        if (column > 1){
            Square left = board.getSquare(column - 1, row);
            if (!left.isOccupied()){
                legalNextSquares.add(left);
            } else if(left.isOccupied() && left.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(left);
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
