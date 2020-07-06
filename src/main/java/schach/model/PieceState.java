package schach.model;

/**
 * Class that represents/saves the state of piece at certain point
 */
public class PieceState {

    private Piece piece;

    private Square position;

    private boolean neverMoved;

    private boolean twoSquareOpener;

    /**
     * Constructor initializing the state by defining the fields
     * @param piece the belonging piece to the state
     * @param moveCount the int value counting the moves
     */
    public PieceState(Piece piece, int moveCount){
        this.piece = piece;
        this.position = piece.getPosition();
        this.neverMoved = piece.isNeverMoved();
        if (piece instanceof Pawn){
            Pawn p = (Pawn) piece;
            this.twoSquareOpener = p.isTwoSquareOpener();
        }
    }

    /**
     * loads the state - repositions the piece to the square it was occupying when the state was created
     * sets fields to the value the state saved them in
     */
    public void load(){
        position.setOccupier(piece);
        position.setOccupied(true);
        piece.setPosition(position);
        piece.setNeverMoved(neverMoved);
        if (piece instanceof Pawn){
            Pawn p = (Pawn) piece;
            p.setTwoSquareOpener(twoSquareOpener);
        }
    }

    public Square getPosition(){
        return this.position;
    }

    public Piece getPiece() {
        return piece;
    }
}
