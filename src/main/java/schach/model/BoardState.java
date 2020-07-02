package schach.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents/saves the state of the board to a certain point in time
 */
public class BoardState {

    private Board board;

    private int stateCount;

    private List<Piece> activePieces;

    private Piece lastMoved;

    private List<BoardState> stateHistory;

    private List<PieceState> pieceStates;

    private List<Piece> cemetery;

    /**
     * Constructor initializing the state by declaring the fields
     * @param board the belonging board to the state
     * @param stateCount the int value counting the moves
     * @param pieceStates List of all PieceStates of all Pieces existing at the time the BoardState is initialized
     */
    public BoardState(Board board, int stateCount, List<PieceState> pieceStates){
        initState(board, stateCount, pieceStates);
    }

    /**
     * Constructor initializing the state by declaring the fields
     * @param board the belonging board to the state
     * @param stateCount the int value counting the moves
     * @param pieceStates List of all PieceStates of all Pieces existing at the time the BoardState is initialized
     */
    public BoardState(Board board, int stateCount, List<PieceState> pieceStates, PieceState newPieceState){
        initState(board, stateCount, pieceStates);
        this.pieceStates.add(newPieceState);
    }

    private void initState(Board board, int stateCount, List<PieceState> pieceStates){
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
        this.pieceStates = new ArrayList<>();
        this.pieceStates.addAll(pieceStates);
    }

    //TODO filter not moved pieces
    /**
     * loads this state - for all pieces (excepting ones on cemetery) loads the last state of the piece that was existing
     * when the Board state was initialized
     */
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

    public Piece getLastMoved() {
        return lastMoved;
    }

    public int getStateCount() {
        return stateCount;
    }
}
