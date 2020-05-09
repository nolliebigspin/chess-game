package schach.model;

import schach.model.*;

import java.util.ArrayList;
import java.util.List;

public class CheckRuler {

    Board board;

    Piece whiteKing;

    Piece blackKing;

    public CheckRuler(Board board){
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
        board.updateAllLegalSquares();
        List<Square> currentlyAttacked = board.attackedSquares(!movingPiece.isWhite());
        boolean inCheck;
        if (kingInCheck(movingPiece.isWhite(), currentlyAttacked)){
            inCheck = true;
        } else {
            inCheck = false;
        }
        movingPiece.undoMove();
        board.updateAllLegalSquares();
        return inCheck;
    }

    public boolean attackingKing(Piece attacker){
        Piece king;
        if (attacker.isWhite){
            king = blackKing;
        } else {
            king = whiteKing;
        }
        Square kingPos = king.getPosition();
        List<Square> legals;
        if (attacker instanceof Pawn){
            Pawn pawn = (Pawn) attacker;
            legals = pawn.getAttackedSquares();
        } else {
            legals = attacker.getLegalSquares();
        }
        if (legals.contains(kingPos)){
            return true;
        } else {
            return false;
        }
    }

    public List<Piece> attackersSettingCheck(boolean kingIsWhite){
        List<Piece> allEnemies = board.allActivePieces(!kingIsWhite);
        List<Piece> settingCheck = new ArrayList<>();
        for (Piece piece: allEnemies){
            if (attackingKing(piece)){
                settingCheck.add(piece);
            }
        }
        return  settingCheck;
    }

    /**
     * returns a list of squares that a given piece could move to to resolve a check situation
     * @param piece Piece that is checked for possible 'resolve squares'
     * @return List of squares that could resolve a check situation
     */
    public List<Square> legalsToResolveCheck(Piece piece){
        Piece attacker = attackersSettingCheck(piece.isWhite).get(0);
        List<Square> inBetweens = inBetweenSquares(piece.isWhite);
        List<Square> newLegals = new ArrayList<>();
        List<Square> legals = piece.getLegalSquares();
        if (piece.getLegalSquares().contains(attacker.getPosition())){
            newLegals.add(attacker.getPosition());
        }
        /**
        if (inBetweens.size() > 0){
            for (Square betweenSquare: inBetweens){
                if (legals.contains(betweenSquare)){
                    newLegals.add(betweenSquare);
                }
            }
        }
         */
        return newLegals;
    }

    public boolean isCheckMate(boolean kingIsWhite){
        Piece king = whiteKing;
        if (!kingIsWhite){
            king = blackKing;
        }
        boolean cantMove = (king.getLegalSquares().size() == 0);
        boolean doubleCheck = (attackersSettingCheck(kingIsWhite).size() > 1);
        System.out.println(cantMove);
        System.out.println(doubleCheck);
        for (Square square: king.getLegalSquares()){
            System.out.println(square.getDenotation());
        }
        if (!cantMove){
            return false;
        }
        if (doubleCheck && cantMove){
            return true;
        }
        //TODO imlement rest
        return false;
    }

    private boolean canResolveCheckByAttacking(Piece piece){
        Piece attacker = attackersSettingCheck(piece.isWhite).get(0);
        if (piece.getLegalSquares().contains(attacker.getPosition())){
            return true;
        } else {
            return false;
        }
    }

    private List<Square> resolveCheckByMoving(Piece piece){
        List<Square> legals = piece.getLegalSquares();
        List<Square> newLegas = new ArrayList<>();
        List<Square> betweens = inBetweenSquares(piece.isWhite);
        Piece attacker = attackersSettingCheck(piece.isWhite).get(0);
        if (attacker instanceof Pawn || attacker instanceof Knight || attacker instanceof King){
            return newLegas;
        } else if (betweens.size() != 0){
            for (Square betweenSquare: betweens){
                if (legals.contains(betweenSquare)){
                    newLegas.add(betweenSquare);
                }
            }
            return newLegas;
        }
        return newLegas;
    }

    /**
     * Returns squares that are in the line of the attacker and the king
     * @param kingIsWhite
     * @return
     */
    private List<Square> inBetweenSquares(boolean kingIsWhite){
        Piece attacker = attackersSettingCheck(kingIsWhite).get(0);
        if (attacker instanceof Rook){
            return inBetweenSquaresRook(kingIsWhite);
        }
        if (attacker instanceof Rook){
            return inBetweenSquaresRook(kingIsWhite);
        }
        return null;
    }

    private List<Square> inBetweenSquaresRook(boolean kingIsWhite){
        return null;
    }

    private void setLegalsNull(Piece piece){

    }

}
