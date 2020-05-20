package schach.controller.interfaces;

import schach.controller.ai.Move;
import schach.controller.ai.SimpleAi;
import schach.model.Board;
import schach.model.Piece;
import schach.model.Square;

import java.util.ArrayList;
import java.util.List;

public class AiInteface {

    private Board board;

    private SimpleAi ai;

    private boolean white;

    private String nextMove;

    public AiInteface(Board board, boolean isWhite){
        this.board = board;
        this.white = isWhite;
        this.ai = new SimpleAi(this);
    }

    protected String getNextMove(){
        return ai.getNextMove();
    }

    protected List<Piece> getAiPices(){
        return board.allActivePieces(white);
    }

    protected List<Piece> getEnemyPieces(){
        return board.allActivePieces(!white);
    }

    public List<Move> getAiMoves(){
        List<Move> moves = new ArrayList<>();
        List<Piece> aiPieces = getAiPices();
        for (Piece piece: aiPieces){
            piece.updateLegals();
            List<Square> legals = piece.getLegalNextSquares();
            for (Square square: legals){
                moves.add(new Move(piece, square));
            }
        }
        return moves;
    }

    protected List<Move> getEnemyMoves(){
        List<Move> moves = new ArrayList<>();
        List<Piece> enemyPieces = getEnemyPieces();
        for (Piece piece: enemyPieces){
            piece.updateLegals();
            List<Square> legals = piece.getLegalNextSquares();
            for (Square square: legals){
                moves.add(new Move(piece, square));
            }
        }
        return moves;
    }
}
