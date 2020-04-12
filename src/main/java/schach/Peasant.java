package schach;

/**
 * Class Peasant representing the chess piece peasant
 */
public class Peasant extends Piece{

    public Peasant(Square position, boolean isWhite) {
        super(position, isWhite);
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
}
