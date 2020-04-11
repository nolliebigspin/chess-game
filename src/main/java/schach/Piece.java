package schach;

public abstract class Piece {
    protected Square position;
    protected boolean isWhite;

    public Piece (Square position, boolean isWhite){
        this.position = position;
        this.isWhite = isWhite;
        position.setOccupied(true);
        position.setOccupier(this);
    }

    public Square getPosition(){
        return this.position;
    }

    public abstract String print();

    public void move(Square target){
        position.setOccupied(false); // Old square is not occupied anymore (boolean), still has an occupier @todo remove occupier?

        this.position = target;
        position.setOccupied(true);
        position.setOccupier(this);
    }


    //public abstract void move(Square target);
}

