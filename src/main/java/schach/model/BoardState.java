package schach.model;

import java.util.ArrayList;
import java.util.List;

public class BoardState {

    private Board board;

    private int stateCount;

    private List<Piece> activePieces;

    private Piece lastMoved;

    private Square lastMovedStartPos = null;

    private Square lastMovedTargetPos = null;

    private List<BoardState> stateHistory;

    public BoardState(Board board, int stateCount){
        this.board = board;
        this.stateCount = stateCount;
        this.activePieces = board.allActivePieces(true);
        this.activePieces.addAll(board.allActivePieces(false));
        stateHistory = new ArrayList<>();
        stateHistory.addAll(board.getStates());
        stateHistory.add(this);
        this.lastMoved = board.getLastMoved();
        if (lastMoved != null){
            this.lastMovedStartPos = lastMoved.getPreviousPos();
            this.lastMovedTargetPos = lastMoved.getPosition();
        }
    }

    public void load(){
        for (Piece piece: activePieces){
            PieceState pieceState = getState(piece);
            if (pieceState != null){
                pieceState.load();
            }
        }
        board.setLastMoved(lastMoved);
        board.setStates(stateHistory);
        board.setMoveCount(stateCount);
        board.setStates(stateHistory);
    }

    private PieceState getState(Piece piece) {
        if (piece.getStateHistory().size() == 1){
            return null;
        }
        List<PieceState> states = piece.getStateHistory();
        for (PieceState state : states) {
            if (state.getMoveCount() == stateCount) {
                return state;
            }
        }
        for (int i = stateCount - 1; i >= 0; i--) {
            for (PieceState state : states) {
                if (state.getMoveCount() == i) {
                    return state;
                }
            }
        }
        return null;
    }

    public void print(){
        String sym;
        String way = "";
        if (lastMoved == null){
            sym = "null";
            way = "";
        } else {
            sym = lastMoved.print();
            if (lastMovedTargetPos != null  && lastMovedStartPos != null){
                way = "" + lastMovedStartPos.getDenotation() + "-" + lastMovedTargetPos.getDenotation();
            }
        }
        System.out.println("Nr.: " + stateCount + " | Piece: " + sym + " | " + way);
    }
}
