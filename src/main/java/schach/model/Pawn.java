package schach.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Pawn representing the chess piece peasant
 */
public class Pawn extends Piece{

    public Pawn(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
        updateLegals();
    }

    @Override
    public String print() {
        if (super.isWhite){
            return "\u2659";
        }
        else{
            return "\u265F";
        }
    }

    @Override
    protected void updateLegals() {
        legalNextSquares.clear();
        //column integer value of square the Pawn is currently occupying
        int column = position.getColumn();

        //row integer value of square the Pawn is currently occupying
        int row = position.getRow();

        int startingRow;
        int secondRow;
        int oneUp;
        int right;
        int left;
        boolean oppositeIsWhite;

        if (isWhite){
            startingRow = 2;
            secondRow = 4;
            oneUp = 1;
            right = 1;
            left = -1;
            oppositeIsWhite = false;
        }
        else {
            startingRow = 7;
            secondRow = 5;
            oneUp = -1;
            right = -1;
            left = 1;
            oppositeIsWhite = true;
        }

        if (row < 8){

            Square ahead = board.getSquare(column, row + oneUp);
            Square aheadTwice = board.getSquare(column, secondRow);

            if (row == startingRow && !ahead.isOccupied() && !aheadTwice.isOccupied()){
                legalNextSquares.add(aheadTwice);
            }

            if (!ahead.isOccupied()){
                legalNextSquares.add(ahead);
            }

            if ((column > 1 && isWhite) || (column < 8 && !isWhite)){
                Square aheadLeft = board.getSquare(column + left, row + oneUp);

                if (aheadLeft.isOccupied() && aheadLeft.getOccupier().isWhite == oppositeIsWhite){
                    legalNextSquares.add(aheadLeft);
                }
            }

            if ((column < 8 && isWhite) || (column > 1 && !isWhite)){
                Square aheadRight = board.getSquare(column + right, row + oneUp);

                if (aheadRight.isOccupied() && aheadRight.getOccupier().isWhite == oppositeIsWhite) {
                    legalNextSquares.add(aheadRight);
                }
            }
        }

    }

}
