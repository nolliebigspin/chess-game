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
        if (neverMoved){
            checkCastelingLong();
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

    private  void checkCastelingLong(){
        Square rSquare;
        Square qSquare;
        Square bSquare;
        Square kSquare;
        if (isWhite){
            rSquare = board.squareByDenotation("a1");
            kSquare = board.squareByDenotation("b1");
            bSquare = board.squareByDenotation("c1");
            qSquare = board.squareByDenotation("d1");
        } else {
            rSquare = board.squareByDenotation("a8");
            kSquare = board.squareByDenotation("b8");
            bSquare = board.squareByDenotation("c8");
            qSquare = board.squareByDenotation("d8");
        }
        if (!rSquare.isOccupied()){
            return;
        }
        if (!(rSquare.getOccupier() instanceof Rook)){
            return;
        }
        Rook rook = (Rook) rSquare.getOccupier();
        if (!rook.isNeverMoved()){
            return;
        }
        if (kSquare.isOccupied() || bSquare.isOccupied() || qSquare.isOccupied()){
            return;
        }


    }
}
