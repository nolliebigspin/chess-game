package schach.model;

/**
 * Class Queen representing the chess piece queen
 */
public class Queen extends Piece {

    public Queen(Square position, boolean isWhite) {
        super(position, isWhite);
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
}

