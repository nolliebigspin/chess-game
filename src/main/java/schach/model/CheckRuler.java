package schach.model;

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
        if (inBetweens.size() > 0){
            for (Square betweenSquare: inBetweens){
                if (legals.contains(betweenSquare)){
                    newLegals.add(betweenSquare);
                }
            }
        }
        return newLegals;
    }

    public boolean isCheckMate(boolean kingIsWhite){
        Piece king = whiteKing;
        if (!kingIsWhite){
            king = blackKing;
        }
        boolean cantMove = (king.getLegalSquares().size() == 0);
        boolean doubleCheck = (attackersSettingCheck(kingIsWhite).size() > 1);
        boolean onlyKing = (board.allActivePieces(kingIsWhite).size() == 1);
        boolean noOneCanHelp = noOneCanHelp(kingIsWhite);
        if (!cantMove){
            return false;
        }
        if (doubleCheck && cantMove){
            return true;
        }
        if (cantMove && onlyKing){
            return true;
        }
        if (cantMove && noOneCanHelp){
            return true;
        }
        return false;
    }

    private boolean noOneCanHelp(boolean isWhite){
        List<Piece> pieces = board.allActivePieces(isWhite);
        for (Piece p: pieces){
            if (!p.getLegalSquares().isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns squares that are in the line of the attacker and the king
     * @param kingIsWhite
     * @return
     */
    private List<Square> inBetweenSquares(boolean kingIsWhite){
        Piece attacker = attackersSettingCheck(kingIsWhite).get(0);
        Piece king = whiteKing;
        if (!kingIsWhite){
            king = blackKing;
        }
        if (attacker instanceof Rook){
            return inBetweenSquaresRook(attacker, king);
        } else if (attacker instanceof Bishop){
            return inBetweenSquaresBishop(attacker, king);
        } else if (attacker instanceof Queen){
            return inBetweenSquaresQueen(attacker, king);
        }
        return null;
    }

    private List<Square> inBetweenSquaresRook(Piece attacker, Piece king){
        List<Square> list = new ArrayList<>();
        Square attackPos = attacker.getPosition();
        Square kingPos = king.getPosition();
        int rowDif = kingPos.getRow() - attackPos.getRow();
        int colDif = kingPos.getColumn() - attackPos.getColumn();
        // adding if in same column
        if (colDif == 0){
            int difference = Math.abs(rowDif);
            if (rowDif < 0){
                for (int i = 1; i < difference; i++){
                    Square square = board.getSquare(kingPos.getColumn(), kingPos.getRow() + i);
                    list.add(square);
                }
            } else if (rowDif > 0){
                for (int i = 1; i < difference; i++){
                    Square square = board.getSquare(kingPos.getColumn(), kingPos.getRow() - i);
                    list.add(square);
                }
            }
        }
        //adding if in same row
        else if (rowDif == 0){
            int difference = Math.abs(colDif);
            if (colDif < 0){
                for (int i = 1; i < difference; i++){
                    Square square = board.getSquare(kingPos.getColumn() + i, kingPos.getRow());
                    list.add(square);
                }
            } else if (colDif > 0){
                for (int i = 1; i < difference; i++){
                    Square square = board.getSquare(kingPos.getColumn() - i, kingPos.getRow() );
                    list.add(square);
                }
            }
        }
        return list;
    }

    private List<Square> inBetweenSquaresBishop(Piece attacker, Piece king){
        List<Square> list = new ArrayList<>();
        Square attackPos = attacker.getPosition();
        Square kingPos = king.getPosition();
        int rowDif = kingPos.getRow() - attackPos.getRow();
        int colDif = kingPos.getColumn() - attackPos.getColumn();
        int difference = Math.abs(colDif);
        if (colDif < 0 && rowDif < 0){
            for (int i = 1; i < difference; i++){
                Square square = board.getSquare(kingPos.getColumn() + i, kingPos.getRow() + i);
                list.add(square);
            }
        }
        else if (colDif < 0 && rowDif > 0){
            for (int i = 1; i < difference; i++){
                Square square = board.getSquare(kingPos.getColumn() + i, kingPos.getRow() - i);
                list.add(square);
            }
        }
        else if (colDif > 0 && rowDif > 0){
            for (int i = 1; i < difference; i++){
                Square square = board.getSquare(kingPos.getColumn() - i, kingPos.getRow() - i);
                list.add(square);
            }
        }
        else if (colDif > 0 && rowDif < 0){
            for (int i = 1; i < difference; i++){
                Square square = board.getSquare(kingPos.getColumn() - i, kingPos.getRow() + i);
                list.add(square);
            }
        }
        return list;
    }

    private List<Square> inBetweenSquaresQueen(Piece attacker, Piece king){
        List<Square> list = new ArrayList<>();
        Square attackPos = attacker.getPosition();
        Square kingPos = king.getPosition();
        int rowDif = kingPos.getRow() - attackPos.getRow();
        int colDif = kingPos.getColumn() - attackPos.getColumn();
        int difference = Math.abs(colDif);
        if (colDif < 0 && rowDif < 0){
            for (int i = 1; i < difference; i++){
                Square square = board.getSquare(kingPos.getColumn() + i, kingPos.getRow() + i);
                list.add(square);
            }
        }
        else if (colDif < 0 && rowDif > 0){
            for (int i = 1; i < difference; i++){
                Square square = board.getSquare(kingPos.getColumn() + i, kingPos.getRow() - i);
                list.add(square);
            }
        }
        else if (colDif > 0 && rowDif > 0){
            for (int i = 1; i < difference; i++){
                Square square = board.getSquare(kingPos.getColumn() - i, kingPos.getRow() - i);
                list.add(square);
            }
        }
        else if (colDif > 0 && rowDif < 0){
            for (int i = 1; i < difference; i++){
                Square square = board.getSquare(kingPos.getColumn() - i, kingPos.getRow() + i);
                list.add(square);
            }
        }
        else if (colDif == 0){
            int difference1 = Math.abs(rowDif);
            if (rowDif < 0){
                for (int i = 1; i < difference1; i++){
                    Square square = board.getSquare(kingPos.getColumn(), kingPos.getRow() + i);
                    list.add(square);
                }
            } else if (rowDif > 0){
                for (int i = 1; i < difference1; i++){
                    Square square = board.getSquare(kingPos.getColumn(), kingPos.getRow() - i);
                    list.add(square);
                }
            }
        }
        //adding if in same row
        else if (rowDif == 0){
            int difference1 = Math.abs(colDif);
            if (colDif < 0){
                for (int i = 1; i < difference1; i++){
                    Square square = board.getSquare(kingPos.getColumn() + i, kingPos.getRow());
                    list.add(square);
                }
            } else if (colDif > 0){
                for (int i = 1; i < difference1; i++){
                    Square square = board.getSquare(kingPos.getColumn() - i, kingPos.getRow() );
                    list.add(square);
                }
            }
        }
        return list;
    }


}
