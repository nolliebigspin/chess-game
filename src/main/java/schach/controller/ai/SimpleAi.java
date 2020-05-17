package schach.controller.ai;

import schach.model.Board;
import schach.model.Piece;
import schach.model.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleAi {

    private Board board;

    private boolean white;

    private List<Move> aiMoves;

    private List<Piece> aiPieces;

    public SimpleAi(Board board, boolean isWhite){
        this.board = board;
        this.white = isWhite;
    }

    private void updateAiPieces(){
        aiPieces = board.allActivePieces(white);
    }

    private void possibleMoves(){
        List<Move> moves = new ArrayList<>();
        updateAiPieces();
        for (Piece piece: aiPieces){
            piece.updateLegals();
            List<Square> legals = piece.getLegalNextSquares();
            for (Square square: legals){
                moves.add(new Move(piece, square));
            }
        }
        this.aiMoves = moves;
    }

    public String getNextMove(){
        Move move = randomMove();
        return move.moveAsString();
    }

    public Move randomMove(){
        possibleMoves();
        int i = new Random().nextInt(aiMoves.size());
        return aiMoves.get(i);
    }

}
