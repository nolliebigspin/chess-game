package schach.model;

/**
 * Class Knight representing the chess piece knight
 */
public class Knight extends Piece {

    public Knight(Square position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public String print() {
        if (super.isWhite) {
            return "\u2658";
        }
        else {
            return "\u265E";
        }
    }
}

