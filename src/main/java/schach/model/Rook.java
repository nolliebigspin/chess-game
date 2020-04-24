package schach.model;

/**
 * Class Rook representing the chess piece rook
 */
public class Rook extends Piece {

    public Rook(Square position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public String print() {
        if (super.isWhite) {
            return "\u2656";
        }
        else {
            return "\u265C";
        }
    }

    @Override
    public void move(Square target) {

    }
}
