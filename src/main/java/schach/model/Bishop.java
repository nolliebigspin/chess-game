package schach.model;

/**
 * Class Bishop representing the chess piece bishop
 */
public class Bishop extends Piece {

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

    }

    @Override
    public void move(Square target) {

    }
}

