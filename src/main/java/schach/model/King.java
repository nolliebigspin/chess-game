package schach.model;

/**
 * Class King representing the chess piece king
 */
public class King extends Piece {

    /**
     * indicates if king is still in its starting position
     */
    private boolean neverMoved;

    /**
     * Constructor defining the initial position, color and board of the king piece
     * @param position the Square it will be placed on initially
     * @param isWhite true if white, false if black
     * @param board the board the Bishop will be placed on
     */
    public King(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
        this.neverMoved = true;
    }

    @Override
    public String print() {
        if (super.isWhite) {
            return "\u2654";
        }
        else {
            return "\u265A";
        }
    }

    /**
     * Same method as in the piece interface, but sets boolean neverMoved to false
     * @param target is the target square where the piece is moved to
     */
    @Override
    public void move(Square target){
        updateLegals();
        for (Square square: legalNextSquares){
            if (square == target){
                acceptMove(target);
                neverMoved = false;
                return;
            }
        }
        refuseMove();
    }

    @Override
    /**
     * allows the move to the target square
     * updates the position square of the Piece
     * @param target Square the Piece will be moved to
     */
    protected void acceptMove(Square target){
        if ((target.getDenotation().equals("c1") || target.getDenotation().equals("c8")) && castelingLongValid()){
            rookCasteling(true);
        }
        position.setOccupied(false);
        this.position = target;
        position.setOccupied(true);
        position.setOccupier(this);
        updateLegals();
    }

    @Override
    public void updateLegals() {
        legalNextSquares.clear();
        boolean oppositeIsWhite = !isWhite;
        int row = position.getRow();
        int column = position.getColumn();

        checkForward(column, row, oppositeIsWhite);
        checkBackward(column, row, oppositeIsWhite);
        checkLeft(column, row, oppositeIsWhite);
        checkRight(column, row, oppositeIsWhite);
        checkForwardRight(column, row, oppositeIsWhite);
        checkBackwardRight(column, row, oppositeIsWhite);
        checkBackwardLeft(column, row, oppositeIsWhite);
        checkForwardLeft(column, row, oppositeIsWhite);
        if (castelingLongValid()){
            Square bishopPos;
            if (isWhite){
                bishopPos = board.squareByDenotation("c1");
            } else {
                bishopPos = board.squareByDenotation("c8");
            }
            legalNextSquares.add(bishopPos);
        }
    }

    private void checkForward(int column, int row, boolean oppositeIsWhite) {
        if (row < 8) {
            Square nextSquare = board.getSquare(column, row + 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkBackward(int column, int row, boolean oppositeIsWhite) {
        if (row > 8) {
            Square nextSquare = board.getSquare(column, row - 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkLeft(int column, int row, boolean oppositeIsWhite) {
        if (column > 1) {
            Square nextSquare = board.getSquare(column - 1, row);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkRight(int column, int row, boolean oppositeIsWhite) {
        if (column < 8) {
            Square nextSquare = board.getSquare(column + 1, row);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkForwardRight(int column, int row, boolean oppositeIsWhite) {
        if (row < 8 && column < 8) {
            Square nextSquare = board.getSquare(column + 1, row + 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkBackwardRight(int column, int row, boolean oppositeIsWhite) {
        if (column < 8 && row > 1){
            Square nextSquare = board.getSquare(column + 1, row - 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkBackwardLeft(int column, int row, boolean oppositeIsWhite) {
        if (column > 1 && row > 1) {
            Square nextSquare = board.getSquare(column - 1, row - 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkForwardLeft(int column, int row, boolean oppositeIsWhite) {
        if (column > 1 && row < 8) {
            Square nextSquare = board.getSquare(column - 1, row + 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().isWhite == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private boolean castelingLongValid(){
        String rookPosition;
        String queenPosition;
        String bishopPosition;
        String knightPosition;
        if (isWhite){
            rookPosition = "a1";
            queenPosition = "d1";
            bishopPosition = "c1";
            knightPosition = "b1";
        } else {
            rookPosition = "a8";
            queenPosition = "d8";
            bishopPosition = "c8";
            knightPosition = "b8";
        }
        Square rSquare = board.squareByDenotation(rookPosition);
        Square qSquare = board.squareByDenotation(queenPosition);
        Square bSquare = board.squareByDenotation(bishopPosition);
        Square kSquare = board.squareByDenotation(knightPosition);
        // checks if rook square is occupied
        if (!rSquare.isOccupied() && !neverMoved){
            return false;
        }
        // checks if piece on rook square is rook
        if (!(rSquare.getOccupier() instanceof Rook)){
            return false;
        }
        Rook rook = (Rook) rSquare.getOccupier();
        // checks if rook was never moved
        if (!rook.isNeverMoved()){
            return false;
        }
        // checks if squares in between are occupied
        if (kSquare.isOccupied() || bSquare.isOccupied() || qSquare.isOccupied()){
            return false;
        }
        // checks if squares where king has to move over are under attack
        if (board.isUnderAttack(queenPosition, !isWhite) || board.isUnderAttack(bishopPosition, !isWhite)){
            return false;
        }
        return true;
    }

    private void rookCasteling(boolean isLongCasteling){
        int startColumn;
        int targetColumn;
        int row;
        if (isLongCasteling){
            startColumn = 1;
            targetColumn = 4;
        } else {
            startColumn = 8;
            targetColumn = 6;
        }
        if (isWhite){
            row = 1;
        } else {
            row = 8;
        }
        Square rookStart = board.getSquare(startColumn, row);
        Square rookTarget = board.getSquare(targetColumn, row);
        Piece rook = rookStart.getOccupier();
        rook.acceptMove(rookTarget);
    }
}
