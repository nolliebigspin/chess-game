package schach.model;

/**
 * abstract class representing a chess piece
 */
public abstract class Piece {

    /**
     * The Square the Piece is currently occupying
     */
    protected Square position;

    /**
     * Boolean to indicate the color of the Piece
     */
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
     * abstract Method to move the Piece
     * @param target Square the Piece should be moved to
     */
    public abstract void move(Square target);

    /**
     * allows the move to the target square
     * updates the position square of the Piece
     * @param target Square the Piece will be moved to
     */
    protected void acceptMove(Square target){
        position.setOccupied(false);
        this.position = target;
        position.setOccupied(true);
        position.setOccupier(this);

    }

    /**
     * Refuses the move and print Error Message
     */
    protected void refuseMove(){
        System.out.println("!Move not allowed");
    }

}

