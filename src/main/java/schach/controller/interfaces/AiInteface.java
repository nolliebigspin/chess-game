package schach.controller.interfaces;

import schach.model.Board;

public class AiInteface {

    private Board board;

    private boolean white;

    private String nextMove;

    public AiInteface(Board board, boolean isWhite){
        this.board = board;
        this.white = isWhite;
    }

    protected String getNextMove(){
        return nextMove;
    }
}
