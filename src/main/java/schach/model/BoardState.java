package schach.model;

import java.util.List;

public class BoardState {

    private Board board;

    private int stateCount;

    private List<Piece> activePieces;

    public BoardState(Board board, int stateCount){
        this.stateCount = stateCount;
        this.activePieces = board.allActivePieces(true);
        this.activePieces.addAll(board.allActivePieces(false));
    }

    public void load(){
        for (Piece piece: activePieces){
            getState(piece).load();
        }
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
}
