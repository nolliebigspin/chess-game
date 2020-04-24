package schach.model;

/**
 * Class Bishop representing the chess piece bishop
 */
public class Bishop extends Piece {

    public Bishop(Square position, boolean isWhite) {
        super(position, isWhite);
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
}

