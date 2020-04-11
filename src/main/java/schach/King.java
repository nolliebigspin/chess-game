package schach;

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
}
