package schach.model;

/**
 * Class Rook representing the chess piece rook
 */
public class Rook extends Piece {

    /**
     * Constructor defining the initial position, color and board of the rook piece
     * @param position the Square it will be placed on initially
     * @param isWhite true if white, false if black
     * @param board the board the Bishop will be placed on
     */
    public Rook(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
    }

    @Override
    public String print() {
        if (super.white) {
            return "R";
        }
        else {
            return "r";
        }
    }

    @Override
    public void updateLegals() {
        legalNextSquares.clear();

        checkForward();
        checkBackward();
        checkRight();
        checkLeft();

        if (board.getCheck().kingInCheck(white)){ //TODO: , board.attackedSquares(!isWhite)
            legalNextSquares = board.getCheck().legalsToResolveCheck(this);
            return;
        }
    }

    /**
     * checks which squares ahead (for white, black: behind) the rook can be legally moved to
     * and adds them to the list of valid squares
     */
    private void checkForward(){

        boolean oppositeIsWhite;

        if (white){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }

        for (int i = 1 ; i <= 8 - position.getRow(); i++){
            Square nextForward = board.getSquare(position.getColumn(), position.getRow() + i);
            if (!(nextForward.isOccupied())){
                legalNextSquares.add(nextForward);
            } else if (nextForward.isOccupied() && nextForward.getOccupier().white == oppositeIsWhite){
                legalNextSquares.add(nextForward);
                break;
            }
            else {
                break;
            }
        }
    }

    /**
     * checks which squares behind (for white, black: ahead) the rook can be legally moved to
     * and adds them to the list of valid squares
     */
    private void checkBackward(){

        boolean oppositeIsWhite;

        if (white){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }

        for (int i = 1; i < position.getRow(); i++){
            Square nextBackward = board.getSquare(position.getColumn(), position.getRow() - i);
            if (!(nextBackward.isOccupied())){
                legalNextSquares.add(nextBackward);
            }
            else if (nextBackward.isOccupied() && nextBackward.getOccupier().white == oppositeIsWhite){
                legalNextSquares.add(nextBackward);
                break;
            }
            else {
                break;
            }
        }
    }

    /**
     * checks which squares to the right (for white, black: to the left) the rook can be legally moved to
     * and adds them to the list of valid squares
     */
    private void checkRight(){

        boolean oppositeIsWhite;

        if (white){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }

        for (int i = 1; i <= 8 - position.getColumn(); i++){
            Square nextRight = board.getSquare(position.getColumn() + i, position.getRow());
            if (!(nextRight.isOccupied())){
                legalNextSquares.add(nextRight);
            }
            else if (nextRight.isOccupied() && nextRight.getOccupier().white == oppositeIsWhite){
                legalNextSquares.add(nextRight);
                break;
            }
            else {
                break;
            }
        }
    }

    /**
     * checks which squares to the left (for white, black: to the right) the rook can be legally moved to
     * and adds them to the list of valid squares
     */
    private void checkLeft(){

        boolean oppositeIsWhite;

        if (white){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }

        //right (black), left (white)
        for (int i = 1; i < position.getColumn(); i++){
            Square nextLeft = board.getSquare(position.getColumn() - i, position.getRow());
            if (!(nextLeft.isOccupied())){
                legalNextSquares.add(nextLeft);
            }
            else if (nextLeft.isOccupied() && nextLeft.getOccupier().white == oppositeIsWhite){
                legalNextSquares.add(nextLeft);
                break;
            }
            else {
                break;
            }
        }
    }

}
