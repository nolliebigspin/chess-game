package schach.controller.ai;

import schach.model.Board;
import schach.model.BoardState;

/**
 * AI that generates its next move by using the min max algorithm
 */
public class MinMaxAi extends AiInterface {

    /**
     * the constructor initializing the fields
     * @param board the ai will be active on
     * @param isWhite true if ai plays white pieces, false if ai plays black pieces
     */
    public MinMaxAi(Board board, boolean isWhite) {
        super(board, isWhite);
    }

    /**
     * generates move by creating a new tree structure of next moves and using the minmax algorithm
     * @return syntactically correct Move represented as a string
     */
    @Override
    public String getNextMove(){
        BoardValueNode bvt = new BoardValueNode(board, white, null);
        String move = bvt.bestValuedMove(white, 1).moveAsString();
        this.nextMove = move;
        return move;
    }
}
