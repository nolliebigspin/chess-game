package schach.controller.ai;

import schach.model.Board;

import java.util.List;
import java.util.Random;

/**
 * a simple ai that just randomly picks a possible move as the next move
 */
public class SimpleAi extends AiInterface {

    /**
     * the constructor initializing the fields
     * @param board the ai will be active on
     * @param isWhite true if ai plays white pieces, false if ai plays black pieces
     */
    public SimpleAi(Board board, boolean isWhite) {
        super(board, isWhite);
    }

    /**
     * calls random move and returns the string representing the move
     * @return syntactically correct Move represented as a string
     */
    @Override
    public String getNextMove(){
        Move move = randomMove();
        return move.moveAsString();
    }

    /**
     * randomly generates a move and returns it
     * @return a randomly generated move the ai can execute
     */
    private Move randomMove(){
        List<Move> aiMoves = getAiMoves();
        int i = new Random().nextInt(aiMoves.size());
        return aiMoves.get(i);
    }
}
