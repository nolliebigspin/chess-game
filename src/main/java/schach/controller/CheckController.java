package schach.controller;

import schach.model.*;

import java.util.List;

public class CheckController {

    Board board;

    Piece whiteKing;

    Piece blackKing;

    public CheckController(Board board){
        this.board = board;
        this.whiteKing = searchKing(true);
        this.blackKing = searchKing(false);
    }

    private Piece searchKing(boolean isWhite){
        List<Piece> pieces = board.allActivePieces(isWhite);
        for (Piece piece: pieces){
            if (piece instanceof King){
                return piece;
            }
        }
        return null;
    }

    public boolean kingInCheck(boolean isWhite, List<Square> currentlyAttackedSquares){
        Piece king;
        if (isWhite){
            king = whiteKing;
        } else {
            king = blackKing;
        }
        if (currentlyAttackedSquares.contains(king.getPosition())){
            return true;
        } else {
            return false;
        }
    }

    public boolean inCheckIfMoved(Piece movingPiece, Square target){
        Square startingPos = movingPiece.getPosition();
        movingPiece.acceptMove(target);
        List<Square> currentlyAttacked = board.attackedSquares(!movingPiece.isWhite());
        boolean inCheck;
        if (kingInCheck(movingPiece.isWhite(), currentlyAttacked)){
            inCheck = true;
        } else {
            inCheck = false;
        }
        movingPiece.undoMove();
        return  inCheck;
    }
}
