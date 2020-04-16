package schach;

/**
 * Class Square representing a chess square on the chess board
 */
public class Square {

    /**
     * Indicates if there is a piece occupying the square
     */
    private boolean occupied;

    /**
     * Denotation of the square (e1, h6, c4, ...)
     */
    private String denotation;

    /**
     * The currently occupying piece
     */
    private Piece occupier;

    /**
     * Integer representation of the column the square is part of (a=1, b=2, ...)
     */
    private int column;

    /**
     * Integer representation of the row the square is part of
     */
    private int row;

    /**
     * Constructor using the row and column to instantiate the denotation
     * @param column
     * @param row
     */
    public Square(int column, int row){
        this.column = column;
        this.row = row;
        this.denotation = "" + resolveIntToChar(column) + row;
    }

    /**
     * Converts a int to a char (0=a, 1=b, ..., 7=h)
     * @param i int to be converted
     * @return the converted char
     */
    private char resolveIntToChar(int i){
        switch (i){
            case 1:
                return 'a';
            case 2:
                return 'b';
            case 3:
                return 'c';
            case 4:
                return 'd';
            case 5:
                return 'e';
            case 6:
                return 'f';
            case 7:
                return 'g';
            case 8:
                return 'h';
            default:
                return 'x';
        }
    }

    /**
     * setter for boolean occupied
     * @param occupied true if square is set to be occupied, false if not
     */
    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }

    /**
     * getter for boolean occupied
     * @return true if square is currently occupied, false if not
     */
    public boolean isOccupied(){
        return this.occupied;
    }

    /**
     * getter for the denotation of square
     * @return denonation of the square
     */
    public String getDenotation(){
        return this.denotation;
    }

    /**
     * setter to set a piece as a occupier
     * @param occupier the occupier piece to be placed on the square
     */
    public void setOccupier(Piece occupier){
        this.occupier = occupier;
    }

    /**
     * getter to get the currently occupying piece
     * @return the currently occupying piece
     */
    public Piece getOccupier(){
        return this.occupier;
    }

    public int getColumn(){
        return this.column;
    }

    public int getRow(){
        return this.row;
    }

}
