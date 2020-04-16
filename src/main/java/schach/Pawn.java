package schach;

/**
 * Class Pawn representing the chess piece peasant
 */
public class Pawn extends Piece{

    public Pawn(Square position, boolean isWhite) {
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

    /**@Override
    public void move(Square target){
        if (isWhite){
            if (target.get)
        }
    }*/
}
