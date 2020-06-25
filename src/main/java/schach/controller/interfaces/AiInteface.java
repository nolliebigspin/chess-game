package schach.controller.interfaces;

import schach.controller.ai.SimpleAi;
import schach.model.Board;

public class AiInteface {

    private SimpleAi ai;

    private boolean white;


    public AiInteface(Board board, boolean isWhite){
        this.white = isWhite;
        this.ai = new SimpleAi(board, white);
    }

    protected String getNextMove(){
        return ai.getNextMove();
    }
}
