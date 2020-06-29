package schach.model;

import java.util.ArrayList;
import java.util.List;

public class PieceState {

    private Piece piece;

    private int moveCount;

    private Square position;

    private Square prevPosition;

    private boolean neverMoved;

    private boolean twoSquareOpener;

    private List<PieceState> stateHistory;

    public PieceState(Piece piece, int moveCount){
        this.piece = piece;
        this.moveCount = moveCount;
        this.position = piece.getPosition();
        this.neverMoved = piece.isNeverMoved();
        stateHistory = new ArrayList<>();
        stateHistory.addAll(piece.getStateHistory());
        if (piece instanceof Pawn){
            Pawn p = (Pawn) piece;
            this.twoSquareOpener = p.isTwoSquareOpener();
        }
    }

    public Square getPosition(){
        return this.position;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void load(){
        position.setOccupier(piece);
        position.setOccupied(true);
        piece.setPosition(position);
        piece.setNeverMoved(neverMoved);
        piece.setStateHistory(stateHistory);
        if (piece instanceof Pawn){
            Pawn p = (Pawn) piece;
            p.setTwoSquareOpener(twoSquareOpener);
        }
    }
}
