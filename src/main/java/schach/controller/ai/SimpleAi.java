package schach.controller.ai;

import schach.controller.interfaces.AiInteface;
import schach.model.Board;
import schach.model.Piece;
import schach.model.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleAi extends AiInteface{


    /**
     * the constructor initializing the ai and fields
     *
     * @param board
     * @param isWhite
     */
    public SimpleAi(Board board, boolean isWhite) {
        super(board, isWhite);
    }

    public String getNextMove(){
        Move move = randomMove();
        return move.moveAsString();
    }

    public Move randomMove(){
        List<Move> aiMoves = getAiMoves();
        int i = new Random().nextInt(aiMoves.size());
        return aiMoves.get(i);
    }
}
