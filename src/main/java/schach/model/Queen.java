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

        legalNextSquares.clear();
        boolean oppositeWhite = !isWhite;
        int column = position.getColumn();
        int row = position.getRow();

        checkForwards(column, row, oppositeWhite);
        checkBackwards(column, row, oppositeWhite);
        checkRight(column, row, oppositeWhite);
        checkLeft(column, row, oppositeWhite);
        checkForwardsRight(column, row, oppositeWhite);
        checkForwardsLeft(column, row, oppositeWhite);
        checkBackwardsRight(column, row, oppositeWhite);
        checkBackwardsLeft(column, row, oppositeWhite);

        if (board.getCheck().kingInCheck(isWhite)){ //TODO: , board.attackedSquares(!isWhite)
            legalNextSquares = board.getCheck().legalsToResolveCheck(this);
            return;
        }
    }

    /**
     * Check which squares ahead (black: behind) the queen could occupy by a legal move
     * and adds the squares to the list of legal squares
     * @param column the current column position of the queen
     * @param row the current row position of the queen
     * @param oppositeIsWhite true if this queen is black, false if this queen is white
     */
    private void checkForwards(int column, int row, boolean oppositeIsWhite){
        for (int i = 1 ; i <= 8 - row; i++){
            Square nextForward = board.getSquare(column, row + i);
            if (!(nextForward.isOccupied())){
                legalNextSquares.add(nextForward);
            } else if (nextForward.isOccupied() && nextForward.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(nextForward);
                break;
            }
            else {
                break;
            }
        }
    }

    /**
     * Check which squares behind (black: ahead) the queen could occupy by a legal move
     * and adds the squares to the list of legal squares
     * @param column the current column position of the queen
     * @param row the current row position of the queen
     * @param oppositeIsWhite true if this queen is black, false if this queen is white
     */
    private void checkBackwards(int column, int row, boolean oppositeIsWhite){
        for (int i = 1; i < row; i++){
            Square nextBackward = board.getSquare(column, row - i);
            if (!(nextBackward.isOccupied())){
                legalNextSquares.add(nextBackward);
            }
            else if (nextBackward.isOccupied() && nextBackward.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(nextBackward);
                break;
            }
            else {
                break;
            }
        }
    }

    /**
     * Check which squares to the left (black: right) the queen could occupy by a legal move
     * and adds the squares to the list of legal squares
     * @param column the current column position of the queen
     * @param row the current row position of the queen
     * @param oppositeIsWhite true if this queen is black, false if this queen is white
     */
    private void checkLeft(int column, int row, boolean oppositeIsWhite){
        for (int i = 1; i < column; i++){
            Square nextLeft = board.getSquare(column - i, row);
            if (!(nextLeft.isOccupied())){
                legalNextSquares.add(nextLeft);
            }
            else if (nextLeft.isOccupied() && nextLeft.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(nextLeft);
                break;
            }
            else {
                break;
            }
        }
    }

    /**
     * Check which squares to the right (black: left) the queen could occupy by a legal move
     * and adds the squares to the list of legal squares
     * @param column the current column position of the queen
     * @param row the current row position of the queen
     * @param oppositeIsWhite true if this queen is black, false if this queen is white
     */
    private void checkRight(int column, int row, boolean oppositeIsWhite){
        for (int i = 1; i <= 8 - column; i++){
            Square nextRight = board.getSquare(column + i, row);
            if (!(nextRight.isOccupied())){
                legalNextSquares.add(nextRight);
            }
            else if (nextRight.isOccupied() && nextRight.getOccupier().isWhite == oppositeIsWhite){
                legalNextSquares.add(nextRight);
                break;
            }
            else {
                break;
            }
        }
    }

    /**
     * Check which squares diagonally forwards to the right (black: backwards-left) the queen could occupy by a legal move
     * and adds the squares to the list of legal squares
     * @param column the current column position of the queen
     * @param row the current row position of the queen
     * @param oppositeIsWhite true if this queen is black, false if this queen is white
     */
    private void checkForwardsRight(int column, int row, boolean oppositeIsWhite){
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
     * Check which squares diagonally forwards to the left (black: backwards-right) the queen could occupy by a legal move
     * and adds the squares to the list of legal squares
     * @param column the current column position of the queen
     * @param row the current row position of the queen
     * @param oppositeIsWhite true if this queen is black, false if this queen is white
     */
    private void checkForwardsLeft(int column, int row, boolean oppositeIsWhite){
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
     * Check which squares diagonally backwards to the right (black: forwards-left) the queen could occupy by a legal move
     * and adds the squares to the list of legal squares
     * @param column the current column position of the queen
     * @param row the current row position of the queen
     * @param oppositeIsWhite true if this queen is black, false if this queen is white
     */
    private void checkBackwardsRight(int column, int row, boolean oppositeIsWhite){
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
     * Check which squares diagonally backwards to the left (black: forwards-right) the queen could occupy by a legal move
     * and adds the squares to the list of legal squares
     * @param column the current column position of the queen
     * @param row the current row position of the queen
     * @param oppositeIsWhite true if this queen is black, false if this queen is white
     */
    private void checkBackwardsLeft(int column, int row, boolean oppositeIsWhite){
        for (int i = 1; i < column && i < row; i++){
            Square nextBehindRight = board.getSquare(column - i, row - i);
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

}

