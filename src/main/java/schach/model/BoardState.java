package schach.model;

import java.util.List;

public class BoardState {

    private Board board;

    private int stateCount;

    private List<Piece> activePieces;

    private Piece lastMoved;

    private Square lastMovedStartPos = null;

    private Square lastMovedTargetPos = null;

    public BoardState(Board board, int stateCount){
        this.stateCount = stateCount;
        this.activePieces = board.allActivePieces(true);
        this.activePieces.addAll(board.allActivePieces(false));
        this.lastMoved = board.getLastMoved();
        if (lastMoved != null){
            this.lastMovedStartPos = lastMoved.getPreviousPos();
            this.lastMovedTargetPos = lastMoved.getPosition();
        }
    }

    public void load(){
        for (Piece piece: activePieces){
            getState(piece).load();
        }
        board.setLastMoved(lastMoved);

    }

    private PieceState getState(Piece piece) {
        List<PieceState> states = piece.getStateHistory();
        for (PieceState state : states) {
            if (state.getMoveCount() == stateCount) {
                return state;
            }
        }
        for (int i = stateCount; i >= 0; i--) {
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
