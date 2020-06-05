package schach.controller.ai;

import schach.model.Board;

public class MinMaxAi extends AiInteface {

    /**
     * the constructor initializing the ai and fields
     * @param board
     * @param isWhite
     */
    public MinMaxAi(Board board, boolean isWhite) {
        super(board, isWhite);
    }

    @Override
    public String getNextMove(){
        BoardValueTree bvt = new BoardValueTree(board, white, null);
        return bvt.bestValuedMove(white, 2).moveAsString();
    }
}
