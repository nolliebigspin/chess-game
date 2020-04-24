package schach.model;

/**
 * Class Knight representing the chess piece knight
 */
public class Knight extends Piece {

    public Knight(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
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

    @Override
    public void updateLegals() {

    }

    @Override
    public void move(Square target) {

    }
}

