package schach.controller.ai;

import schach.model.Piece;
import schach.model.Square;

public class Move {

    private Piece piece;

    private Square startingPos;

    private Square targetPos;

    private boolean attacking;

    private Piece attackedPiece;

    public Move(Piece piece, Square targetPos){
        this.piece = piece;
        this.startingPos = piece.getPosition();
        this.targetPos = targetPos;
        this.attacking = checkForAttacking();
    }

    public String moveAsString(){
        return startingPos.getDenotation() + "-" + targetPos.getDenotation();
    }

    private boolean checkForAttacking(){
        if (targetPos.isOccupied()){
            this.attackedPiece = targetPos.getOccupier();
            return true;
        }
        else {
            return false;
        }
    }

    public Piece getPiece(){
        return piece;
    }

    public boolean isAttacking(){
        return attacking;
    }

    public Piece getAttackedPiece() {
        return attackedPiece;
    }
}
