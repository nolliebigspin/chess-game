package schach.model;

import java.util.List;

/**
 * Class King representing the chess piece king
 */
public class King extends Piece {

    /**
     * Constructor defining the initial position, color and board of the king piece
     * @param position the Square it will be placed on initially
     * @param isWhite true if white, false if black
     * @param board the board the Bishop will be placed on
     */
    public King(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
    }

    @Override
    public String print() {
        if (super.white) {
            return "\u2654";
        }
        else {
            return "\u265A";
        }
    }

    /**
     * Same method as in the piece interface, but sets boolean neverMoved to false
     * and checks for casteling
     * @param target is the target square where the piece is moved to
     */
    @Override
    public void move(Square target){
        for (Square square: legalNextSquares){
            if (square == target){
                if ((target.getDenotation().equals("c1") || target.getDenotation().equals("c8")) && castlingLongValid()){
                    rookCastling(true);
                }
                if ((target.getDenotation().equals("g1") || target.getDenotation().equals("g8")) && castlingShortValid()) {
                    rookCastling(false);
                }
                acceptMove(target);
                neverMoved = false;
                return;
            }
        }
        refuseMove();
    }

    /**
     * Updates the list of legal squares
     */
    @Override
    public void updateLegals() {
        legalNextSquares.clear();

        boolean oppositeIsWhite = !white;
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
        if (castlingLongValid()){
            Square bishopPos;
            if (white){
                bishopPos = board.squareByDenotation("c1");
            } else {
                bishopPos = board.squareByDenotation("c8");
            }
            legalNextSquares.add(bishopPos);
        }
        if (castlingShortValid()){
            Square knightPos;
            if (white){
                knightPos = board.squareByDenotation("g1");
            } else {
                knightPos = board.squareByDenotation("g8");
            }
            legalNextSquares.add(knightPos);
        }
        filterAttacked();

    }

    private void checkForward(int column, int row, boolean oppositeIsWhite) {
        if (row < 8) {
            Square nextSquare = board.getSquare(column, row + 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().white == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkBackward(int column, int row, boolean oppositeIsWhite) {
        if (row > 1) {
            Square nextSquare = board.getSquare(column, row - 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().white == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkLeft(int column, int row, boolean oppositeIsWhite) {
        if (column > 1) {
            Square nextSquare = board.getSquare(column - 1, row);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().white == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkRight(int column, int row, boolean oppositeIsWhite) {
        if (column < 8) {
            Square nextSquare = board.getSquare(column + 1, row);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().white == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkForwardRight(int column, int row, boolean oppositeIsWhite) {
        if (row < 8 && column < 8) {
            Square nextSquare = board.getSquare(column + 1, row + 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().white == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkBackwardRight(int column, int row, boolean oppositeIsWhite) {
        if (column < 8 && row > 1){
            Square nextSquare = board.getSquare(column + 1, row - 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().white == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkBackwardLeft(int column, int row, boolean oppositeIsWhite) {
        if (column > 1 && row > 1) {
            Square nextSquare = board.getSquare(column - 1, row - 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().white == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    private void checkForwardLeft(int column, int row, boolean oppositeIsWhite) {
        if (column > 1 && row < 8) {
            Square nextSquare = board.getSquare(column - 1, row + 1);
            if (!nextSquare.isOccupied()) {
                legalNextSquares.add(nextSquare);
            } else if (nextSquare.isOccupied() && nextSquare.getOccupier().white == oppositeIsWhite) {
                legalNextSquares.add(nextSquare);
            }
        }
    }

    /**
     * checks if castling on the long side (queen side) is legal
     * @return true if castling long is legal, false if not
     */
    private boolean castlingLongValid(){
        String rookPosition;
        String queenPosition;
        String bishopPosition;
        String knightPosition;
        if (white){
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
        // checks if rook square is occupied && if King was moved already
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
        return !board.isUnderAttack(queenPosition, !white) && !board.isUnderAttack(bishopPosition, !white);
    }

    /**
     * checks if castling on the short side (not queen side) is legal
     * @return true if castling short is legal, false if not
     */
    private boolean castlingShortValid(){
        String rookPosition;
        String bishopPosition;
        String knightPosition;
        if (white){
            rookPosition = "h1";
            bishopPosition = "f1";
            knightPosition = "g1";
        } else {
            rookPosition = "h8";
            bishopPosition = "f8";
            knightPosition = "g8";
        }
        Square rSquare = board.squareByDenotation(rookPosition);
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
        if (kSquare.isOccupied() || bSquare.isOccupied() ){
            return false;
        }
        // checks if squares where king has to move over are under attack
        return !board.isUnderAttack(knightPosition, !white) && !board.isUnderAttack(bishopPosition, !white);
    }

    /**
     * Moves the rook to its position during castling
     * @param isLongCastling true if its long castling, false if its short side castling
     */
    private void rookCastling(boolean isLongCastling){
        int startColumn;
        int targetColumn;
        int row;
        if (isLongCastling){
            startColumn = 1;
            targetColumn = 4;
        } else {
            startColumn = 8;
            targetColumn = 6;
        }
        if (white){
            row = 1;
        } else {
            row = 8;
        }
        Square rookStart = board.getSquare(startColumn, row);
        Square rookTarget = board.getSquare(targetColumn, row);
        Piece rook = rookStart.getOccupier();
        rook.acceptMove(rookTarget);
    }

    private void filterAttacked(){
        List<Square> attacked = board.attackedSquares(!white);
        for (Square square: attacked){
            legalNextSquares.remove(square);
        }
    }
}
