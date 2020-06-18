package schach.controller.interfaces;

import schach.controller.ai.SimpleAi;
import schach.model.Board;

public class AiInteface {

    private Board board;

    private SimpleAi ai;

    private boolean white;

    private String nextMove;

    public AiInteface(Board board, boolean isWhite){
        this.board = board;
        this.white = isWhite;
        this.ai = new SimpleAi(board, white);
    }

    protected String getNextMove(){
        return ai.getNextMove();
    }
}
