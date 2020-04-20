package schach.model;

/**
 * Class King representing the chess piece king
 */
public class King extends Piece {
    public King(Square position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public String print() {
        if (super.isWhite){
            return "\u2654";
        }
        else{
            return "\u265A";
        }
    }

    @Override
    public void move(Square target) {

    }
}
