package schach.controller.ai;

import schach.model.Piece;
import schach.model.Square;

public class Move {

    private Piece piece;

    private Square startingPos;

    private Square targetPos;

    public Move(Piece piece, Square targetPos){
        this.piece = piece;
        this.startingPos = piece.getPosition();
        this.targetPos = targetPos;
    }

    public String moveAsString(){
        return startingPos.getDenotation() + "-" + targetPos.getDenotation();
    }
}
