package schach.controller;

import schach.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Check contains methods and information to search for and keep control of check- and checkmate situations
 */
public class Check {

    /**
     * the board the game is played on
     */
    Board board;

    /**
     * the piece representing the white king on the board
     */
    Piece whiteKing;

    /**
     * the piece representing the black king on the board
     */
    Piece blackKing;

    /**
     * Constructor initializing the fields
     * @param board the board
     */
    public Check(Board board){
        this.board = board;
        this.whiteKing = searchKing(true);
        this.blackKing = searchKing(false);
    }

    /**
     * Searches for Kings on the board and saves them in the class fields
     */
    public void updateKings(){
        this.whiteKing = searchKing(true);
        this.blackKing = searchKing(false);
    }

    /**
     * Searches the king of a given color on the board
     * @param isWhite true if wanted king is white, false if black
     * @return Piece King of wanted color, null if no king of given color found
     */
    protected Piece searchKing(boolean isWhite){
        List<Piece> pieces = board.allActivePieces(isWhite);
        for (Piece piece: pieces){
            if (piece instanceof King){
                return piece;
            }
        }
        return null;
    }

    /**
     * Checks if king of a given color is attacked in a given situation
     * @param isWhite true if king is white, false if black
     * //@param currentlyAttackedSquares List of squares that are under attack in the given situation
     * @return true if king is in check, false if not
     * TODO resolve parameter (does it work that way?)
     */
    public boolean kingInCheck(boolean isWhite/**, List<Square> currentlyAttackedSquares*/){
        List<Square> currentlyAttackedSquares = board.attackedSquares(!isWhite);
        Piece king = blackKing;
        if (isWhite){
            king = whiteKing;
        }
        return currentlyAttackedSquares.contains(king.getPosition());
    }

    /**
     * Checks wherever the own king would be in a check-situation
     * if a given piece would be moved to a given target square
     * @param movingPiece the piece that would be moved
     * @param target the square the piece would be moved to
     * @return true if Piece would allow the king to be in a check-situatio, false if move is safe for king
     */
    public boolean inCheckIfMoved(Piece movingPiece, Square target){
        Piece realLastMoved = board.getLastMoved();
        movingPiece.acceptMove(target);
        board.updateAllLegalSquares();
        //List<Square> currentlyAttacked = board.attackedSquares(!movingPiece.getIsWhite());
        boolean inCheck = kingInCheck(movingPiece.isWhite()/**TODO delete: , currentlyAttacked*/);
        movingPiece.undoMove();
        board.setLastMoved(realLastMoved);
        board.updateAllLegalSquares();
        return inCheck;
    }

    /**
     * Checks if a given piece is attaking the king of the opposite color
     * @param attacker the piece that should be checked
     * @return true if piece is attacking the king, false if not
     */
    public boolean attackingKing(Piece attacker){
        Piece king;
        if (attacker.isWhite()){
            king = blackKing;
        } else {
            king = whiteKing;
        }
        Square kingPos = king.getPosition();
        List<Square> legals;
        if (attacker instanceof Pawn){
            Pawn pawn = (Pawn) attacker;
            legals = pawn.getAttackedSquaresAll();
        } else {
            legals = attacker.getLegalNextSquares();
        }
        return legals.contains(kingPos);

    }

    /**
     * Lists all pieces attacking the king of given color
     * @param kingIsWhite true if king is white, false if black
     * @return ArrayList of Pieces attacking the king
     */
    public List<Piece> attackersSettingCheck(boolean kingIsWhite){
        List<Piece> allEnemies = board.allActivePieces(!kingIsWhite);
        List<Piece> settingCheck = new ArrayList<>();
        for (Piece piece: allEnemies){
            if (attackingKing(piece)){
                settingCheck.add(piece);
            }
        }
        return settingCheck;
    }

    /**
     * returns a list of squares that a given piece could move to to resolve a check situation
     * @param piece Piece that is checked for possible 'resolve squares'
     * @return List of squares that could resolve a check situation
     */
    public List<Square> legalsToResolveCheck(Piece piece){
        Piece attacker = attackersSettingCheck(piece.isWhite()).get(0);
        List<Square> inBetweens = inBetweenSquares(piece.isWhite());
        List<Square> newLegals = new ArrayList<>();
        List<Square> legals = piece.getLegalNextSquares();
        if (piece instanceof Pawn){
            Pawn pawn = (Pawn) piece;
            legals.addAll(pawn.getAttackedSquaresAttacked());
            int oneUp = 1;
            if (!pawn.isWhite()){
                oneUp = -1;
            }
            if (!pawn.checkEnPassantLeft().isEmpty()){
                newLegals.add(board.getSquare(piece.getPosition().getColumn() - 1, piece.getPosition().getRow() + oneUp));
            } else if (!pawn.checkEnPassantRight().isEmpty()){
                newLegals.add(board.getSquare(piece.getPosition().getColumn() + 1, piece.getPosition().getRow() + oneUp));
            }
        }
        if (piece.getLegalNextSquares().contains(attacker.getPosition())){
            newLegals.add(attacker.getPosition());
        }
        if (!inBetweens.isEmpty()){
            for (Square betweenSquare: inBetweens){
                if (legals.contains(betweenSquare)){
                    newLegals.add(betweenSquare);
                }
            }
        }
        return newLegals;
    }

    /**
     * Checks if the King of a given color is 'checkmate'
     * @param kingIsWhite true if king is white, false if king is black
     * @return true if king is checkmate, false if not
     */
    public boolean isCheckMate(boolean kingIsWhite){
        Piece king = whiteKing;
        if (!kingIsWhite){
            king = blackKing;
        }
        List<Square> legals = king.getLegalNextSquares();
        List<Piece> pieces = board.allActivePieces(kingIsWhite);
        for (Piece piece: pieces){
            legals.remove(piece.getPosition());
        }
        boolean cantMove = legals.size() == 0;
        boolean doubleCheck = attackersSettingCheck(kingIsWhite).size() > 1;
        boolean onlyKing = board.allActivePieces(kingIsWhite).size() == 1;
        boolean noOneCanHelp = noOneCanHelp(kingIsWhite);
        if (!cantMove){
            return false;
        } else if (doubleCheck || onlyKing || noOneCanHelp){
            return true;
        }
        return false;
    }

    /**
     * Checks if any piece the given color could resolve the checksituation
     * @param isWhite if pieces are white, false if black
     * @return true if no pieces can help/resolve the check-situation, false if pices could resolve
     */
    private boolean noOneCanHelp(boolean isWhite){
        List<Piece> pieces = board.allActivePieces(isWhite);
        for (Piece p: pieces){
            if (!p.getLegalNextSquares().isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns squares that are in the line of the attacker and the king
     * @param kingIsWhite if attacked King is white, false if black
     * @return List of squares that are in between, empty list if none found
     */
    protected List<Square> inBetweenSquares(boolean kingIsWhite){
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
        return new ArrayList<>();
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
            } else { //rowDif > 0
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
            } else {// colDif > 0
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
        if (colDif < 0){
            if (rowDif < 0){
                for (int i = 1; i < difference; i++){
                    Square square = board.getSquare(kingPos.getColumn() + i, kingPos.getRow() + i);
                    list.add(square);
                }
            } else { //rowDif > 0
                for (int i = 1; i < difference; i++){
                    Square square = board.getSquare(kingPos.getColumn() + i, kingPos.getRow() - i);
                    list.add(square);
                }
            }
        } else { //colDif > 0
            if (rowDif > 0){
                for (int i = 1; i < difference; i++){
                    Square square = board.getSquare(kingPos.getColumn() - i, kingPos.getRow() - i);
                    list.add(square);
                }
            } else { //rowDif < 0
                for (int i = 1; i < difference; i++){
                    Square square = board.getSquare(kingPos.getColumn() - i, kingPos.getRow() + i);
                    list.add(square);
                }
            }
        }
        return list;
    }

    private List<Square> inBetweenSquaresQueen(Piece attacker, Piece king){
        List<Square> list = inBetweenSquaresRook(attacker, king);
        if (list.isEmpty()){
            list.addAll(inBetweenSquaresBishop(attacker, king));
        }
        return list;
        /**
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
         */
    }


}
