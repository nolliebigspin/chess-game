package schach;

public class Rook extends Piece {

    public Rook(Square position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public String print() {
        if (super.isWhite){
            return "\u2656";
        }
        else{
            return "\u265C";
        }
    }
}

