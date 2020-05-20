package schach.controller.ai;

import schach.controller.interfaces.AiInteface;
import schach.model.Board;
import schach.model.Piece;
import schach.model.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleAi {

    private AiInteface aiInteface;

    public SimpleAi(AiInteface aiInterface){
        this.aiInteface = aiInterface;
    }

    public String getNextMove(){
        Move move = randomMove();
        return move.moveAsString();
    }

    public Move randomMove(){
        List<Move> aiMoves = aiInteface.getAiMoves();
        int i = new Random().nextInt(aiMoves.size());
        return aiMoves.get(i);
    }

}
