package schach.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import schach.model.Square;

/**
 * Class extending the ChessBoardController to implement concrete functions for the PvP game mode
 */
public class ChessBoardHuman extends ChessBoardController{

    /**
     * Constructor initializing fields, maps and event handler
     * @param container the Pane that contains the Chessboard and all belonging Panes
     */
    public ChessBoardHuman(Pane container, GameScreen gameScreen, String[] playerNames) {
        super(container, gameScreen,playerNames);
    }

    @Override
    protected void move(StackPane lastClickedPane) {
        checkPane.setVisible(false);
        Square start = toBeMoved.getPosition();
        Square target = paneToSquareMap.get(lastClickedPane);
        board.movePiece(start.getDenotation(), target.getDenotation());
        updateHistory();
        resetBackground();
        if (isPromotion(toBeMoved)){
            showPromotion(toBeMoved.isWhite());
        } else {
            rotateGame();
        }
        printBoard();
        gameScreen.getCemeteryController().updateCemetery(this);
        inMove = false;
        if (showIsCheck && board.getCheck().kingInCheck(!whitesTurn)) {
            checkPane.setVisible(true);
        }
        if (board.getCheck().isCheckMate(!whitesTurn)){
            gameOver();
        }
        whitesTurn = !whitesTurn;
        gameScreen.setForwardDisabled(true);
    }

    @Override
    protected void doPromotion(StackPane pane) {
        super.doPromotion(pane);
        resetBackground();
        rotateGame();
    }


    @Override
    public void undo(int index) {
        if (index == -1){
            return;
        }
        if (index == 0){
            whitesTurn = true;
        } else if (board.getStates().get(index).getLastMoved().isWhite()){
            whitesTurn = false;
        } else {
            whitesTurn = true;
        }
        board.loadState(index);
        printBoard();
        clearAndUpdateHistory();
        gameScreen.setUndoDisabled(true);
        gameScreen.setForwardDisabled(false);
    }

    @Override
    public void redo() {
        board.redo();
        printBoard();
        clearAndUpdateHistory();
        if (board.getStates().get(board.getStates().size() - 1).getLastMoved().isWhite()){
            whitesTurn = false;
        } else {
            whitesTurn = true;
        }
        gameScreen.setForwardDisabled(true);
    }
}