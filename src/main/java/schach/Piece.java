package schach;

/**
 * abstract class representing a chess piece
 */
public abstract class Piece {
    protected Square position;
    protected boolean isWhite;

    /**
     * Constructor of Piece
     * the square the piece is position on is marked and gets the Piece Object passed
     * @param position denotation of the square the piece will be initiated on
     * @param isWhite true if it is a piece of the white set, false if black
     */
    public Piece (Square position, boolean isWhite){
        this.position = position;
        this.isWhite = isWhite;
        position.setOccupied(true);
        position.setOccupier(this);
    }

    /**
     * getter for the square the piece is currently positioned on
     * @return the square the piece is occupying
     */
    public Square getPosition(){
        return this.position;
    }

    /**
     * abstract Method to print the piece
     * @return String that represents the piece
     */
    public abstract String print();

    /**
     * repositions the piece to a target square
     * @param target Square the piece is supposed to be moved to
     */
    public void move(Square target){
        position.setOccupied(false); // Old square is not occupied anymore (boolean), still has an occupier @todo remove occupier?

        this.position = target;
        position.setOccupied(true);
        position.setOccupier(this);
    }

    protected void acceptMove(Square target){
        position.setOccupied(false);
        this.position = target;
        position.setOccupied(true);
        position.setOccupier(this);
    }

}

