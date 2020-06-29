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

    private List<PieceState> pieceStates;

    private List<Piece> cemetery;

    public BoardState(Board board, int stateCount, List<PieceState> pieceStates){
        this.board = board;
        this.stateCount = stateCount;
        this.activePieces = board.allActivePieces(true);
        this.activePieces.addAll(board.allActivePieces(false));
        stateHistory = new ArrayList<>();
        stateHistory.addAll(board.getStates());
        stateHistory.add(this);

        cemetery = new ArrayList<>();
        cemetery.addAll(board.getCemetery());

        this.lastMoved = board.getLastMoved();
        if (lastMoved != null){
            this.lastMovedStartPos = lastMoved.getPreviousPos();
            this.lastMovedTargetPos = lastMoved.getPosition();
        }
        this.pieceStates = new ArrayList<>();
        this.pieceStates.addAll(pieceStates);
    }

    public BoardState(Board board, int stateCount, List<PieceState> pieceStates, PieceState newPieceState){
        this.board = board;
        this.stateCount = stateCount;
        this.activePieces = board.allActivePieces(true);
        this.activePieces.addAll(board.allActivePieces(false));
        stateHistory = new ArrayList<>();
        stateHistory.addAll(board.getStates());
        stateHistory.add(this);

        cemetery = new ArrayList<>();
        cemetery.addAll(board.getCemetery());

        this.lastMoved = board.getLastMoved();
        if (lastMoved != null){
            this.lastMovedStartPos = lastMoved.getPreviousPos();
            this.lastMovedTargetPos = lastMoved.getPosition();
        }
        this.pieceStates = new ArrayList<>();
        this.pieceStates.addAll(pieceStates);
        this.pieceStates.add(newPieceState);
    }

    //TODO filter not moved pieces
    public void load(){
        List<Piece> loadPieces = new ArrayList<>();
        loadPieces.addAll(cemetery);
        for (int i = pieceStates.size()-1; i >= 0; i--){
            PieceState pieceState = pieceStates.get(i);
            if (!loadPieces.contains(pieceState.getPiece())){
                pieceState.load();
                loadPieces.add(pieceState.getPiece());
            }
        }
        board.setLastMoved(lastMoved);
        board.setStates(stateHistory);
        board.setMoveCount(stateCount);
        board.setCemetery(cemetery);
    }

    public List<PieceState> getPieceStates() {
        return pieceStates;
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
