package schach.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Pawn representing the chess piece peasant
 */
public class Pawn extends Piece {

    /**
     * boolean var that indicates wherever the pawn made the opener move where its jumping two rows ahead
     */
    private boolean twoSquareOpener;

    /**
     * Constructor defining the initial position, color and board of the pawn piece
     * @param position the Square it will be placed on initially
     * @param isWhite true if white, false if black
     * @param board the board the Bishop will be placed on
     */
    public Pawn(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
    }

    @Override
    public String print() {
        if (super.white) {
            return "\u2659";
        } else {
            return "\u265F";
        }
    }

    @Override
    public void move(Square target) {
        boolean inList = false;
        for (Square square: legalNextSquares){
            if (square == target){
                inList = true;
                break;
            }
        }
        if (inList && !board.getCheck().inCheckIfMoved(this, target)){
            acceptMove(target);
            twoSquareOpener = movingTwoSquares(target);
            neverMoved = false; //TODO replace in to acceptMove()
        } else {
            refuseMove();
        }
    }

    @Override
    protected void updateLegals() {
        legalNextSquares.clear();

        int column = position.getColumn();
        int row = position.getRow();
        if (row < 8 && row > 1) {
            checkAhead(column, row);
            checkUpRight(column, row);
            checkUpLeft(column, row);
        }

        if (board.getCheck().kingInCheck(white)){ //TODO: , board.attackedSquares(!isWhite)
            legalNextSquares = board.getCheck().legalsToResolveCheck(this);
            return;
        }
    }

    /**
     * checks wherever the pawn makes the two row opener move
     * @param target the square the pawn is supposed to be moved to
     * @return true if it is the two row opener move, false if not
     */
    protected boolean movingTwoSquares(Square target){
        if (!neverMoved){
            return false;
        }
        int secondRow = 4;
        if (!isWhite()){
            secondRow = 5;
        }
        if (target.getRow() == secondRow){
            return true;
        }
        return false;
    }

    /**
     * getter for the twoSquareOpener var
     * @return true if Pawn made the two row opener move, false if not
     */
    public boolean isTwoSquareOpener(){
        return this.twoSquareOpener;
    }

    /**
     * checks if starting move (2 squares ahead) and basic move (1 square ahead) are valid
     * if so adds it to list of valid squares
     * @param column integer representation of the column the pawn is currently occupying
     * @param row integer representation of the row the pawn is currently occupying
     */
    private void checkAhead(int column, int row) {
        int startingRow;
        int secondRow;
        int oneUp;

        if (white) {
            startingRow = 2;
            secondRow = 4;
            oneUp = 1;
        } else {
            startingRow = 7;
            secondRow = 5;
            oneUp = -1;
        }

        Square ahead = board.getSquare(column, row + oneUp);
        Square aheadTwice = board.getSquare(column, secondRow);

        if (row == startingRow && !ahead.isOccupied() && !aheadTwice.isOccupied()) {
            legalNextSquares.add(aheadTwice);
        }

        if (!ahead.isOccupied()) {
            legalNextSquares.add(ahead);
        }

    }

    /**
     * checks if attack move (one diagonally to the right) is valid
     * if so adds it to list of valid squares
     * @param column integer representation of the column the pawn is currently occupying
     * @param row integer representation of the row the pawn is currently occupying
     */
    private void checkUpRight(int column, int row) {
        int oneUp;
        int right;
        boolean oppositeIsWhite;

        if (white) {
            oneUp = 1;
            right = 1;
            oppositeIsWhite = false;
        } else {
            oneUp = -1;
            right = -1;
            oppositeIsWhite = true;
        }

        if (column < 8 && white || column > 1 && !white) {
            Square aheadRight = board.getSquare(column + right, row + oneUp);

            if (aheadRight.isOccupied() && aheadRight.getOccupier().white == oppositeIsWhite) {
                legalNextSquares.add(aheadRight);
            }
        }
    }

    /**
     * checks if attack move (one diagonally to the left) is valid
     * if so adds it to list of valid squares
     * @param column integer representation of the column the pawn is currently occupying
     * @param row integer representation of the row the pawn is currently occupying
     */
    private void checkUpLeft(int column, int row) {
        int oneUp;
        int left;
        boolean oppositeIsWhite;

        if (white) {
            oneUp = 1;
            left = -1;
            oppositeIsWhite = false;
        } else {
            oneUp = -1;
            left = 1;
            oppositeIsWhite = true;
        }

        if (column > 1 && white || column < 8 && !white) {
            Square aheadLeft = board.getSquare(column + left, row + oneUp);

            if (aheadLeft.isOccupied() && aheadLeft.getOccupier().white == oppositeIsWhite) {
                legalNextSquares.add(aheadLeft);
            }
        }
    }

    /**
     * Gets all squares the pawn can make a attack move to
     * @return list of attacked squares
     */
    public List<Square> getAttackedSquares(){
        List<Square> attacked = new ArrayList<>();
        int column = this.position.getColumn();
        int row = this.position.getRow();
        int plusOne = 1;
        if (!white){
            plusOne = - 1;
        }
        if (column > 1){
            attacked.add(board.getSquare(column - 1, row + plusOne ));
        }
        if (column < 8){
            attacked.add(board.getSquare(column + 1, row + plusOne));
        }
        return attacked;
    }

}
