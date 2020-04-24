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
        if (isWhite) {
            if (target.getRow() - position.getRow() == 0 || target.getColumn() - position.getColumn() == 0) {
                acceptMove(target);
            } else {
                refuseMove();
            }
        }

        if (!isWhite) {
            if (target.getRow() - position.getRow() == 0 || target.getColumn() - position.getColumn() == 0) {
                acceptMove(target);
            } else {
                refuseMove();
            }
        }
    }
}
