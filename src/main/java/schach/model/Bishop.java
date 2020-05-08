package schach.model;

/**
 * Class Bishop representing the chess piece bishop
 */
public class Bishop extends Piece {

    /**
     * Constructor defining the position, color and board
     * @param position the Square the Bishop will be placed on initially
     * @param isWhite true if white, false if black
     * @param board the board the Bishop will be placed on
     */
    public Bishop(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
    }

    @Override
    public String print() {
        if (super.isWhite){
            return "\u2657";
        }
        else {
            return "\u265D";
        }
    }

    @Override
    public void updateLegals() {
        legalNextSquares.clear();

        int column = this.position.getColumn();
        int row = this.position.getRow();

        checkForwardRight(column, row);
        checkForwardLeft(column, row);
        checkBehindRight(column, row);
        checkBehindLeft(column,row);
    }

    /**
     * checks if squares diagonally (white: forward-right, black: backwards-left) are valid
     * if so adds them to list of valid squares
     * @param column current column position of the bishop
     * @param row current row position of the bishop
     */
    private void checkForwardRight(int column, int row){

        boolean oppositeIsWhite;

        if (isWhite){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }

        for (int i = 1; i <= 8 - column && i <= 8 - row; i++){
            Square nextForwardRight = board.getSquare(column + i, row + i);
            if (!(nextForwardRight.isOccupied())){
                legalNextSquares.add(nextForwardRight);
            } else if (nextForwardRight.isOccupied() && nextForwardRight.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(nextForwardRight);
                break;
            }
            else {
                break;
            }
        }

    }

    /**
     * checks if squares diagonally (white: forward-left, black: backwards-right) are valid
     * if so adds them to list of valid squares
     * @param column current column position of the bishop
     * @param row current row position of the bishop
     */
    private void checkForwardLeft(int column, int row){

        boolean oppositeIsWhite;

        if (isWhite){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }

        for (int i = 1; i < column && i <= 8 - row; i++){
            Square nextForwardLeft = board.getSquare(column - i, row + i);
            if (!(nextForwardLeft.isOccupied())){
                legalNextSquares.add(nextForwardLeft);
            } else if (nextForwardLeft.isOccupied() && nextForwardLeft.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(nextForwardLeft);
                break;
            }
            else {
                break;
            }
        }

    }

    /**
     * checks if squares diagonally (white: backwards-right, black: forward-left) are valid
     * if so adds them to list of valid squares
     * @param column current column position of the bishop
     * @param row current row position of the bsihop
     */
    private void checkBehindRight(int column, int row){

        boolean oppositeIsWhite;

        if (isWhite){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }

        for (int i = 1; i <= 8 - column && i < row; i++){
            Square nextBehindRight = board.getSquare(column + i, row - i);
            if (!(nextBehindRight.isOccupied())){
                legalNextSquares.add(nextBehindRight);
            } else if (nextBehindRight.isOccupied() && nextBehindRight.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(nextBehindRight);
                break;
            }
            else {
                break;
            }
        }

    }

    /**
     * checks if squares diagonally (white: backwards-left, black: forward-right) are valid
     * if so adds them to list of valid squares
     * @param column current column position of the bishop
     * @param row current row position of the bsihop
     */
    private void checkBehindLeft(int column, int row){

        boolean oppositeIsWhite;

        if (isWhite){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }

        for (int i = 1; i < column && i < row; i++){
            Square nextBehindLeft = board.getSquare(column - i, row - i);
            if (!(nextBehindLeft.isOccupied())){
                legalNextSquares.add(nextBehindLeft);
            } else if (nextBehindLeft.isOccupied() && nextBehindLeft.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(nextBehindLeft);
                break;
            }
            else {
                break;
            }
        }

    }


}

