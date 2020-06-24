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
    public ChessBoardHuman(Pane container) {
        super(container);
    }

    @Override
    protected void move(StackPane lastClickedPane) {
        checkPane.setVisible(false);
        Square start = toBeMoved.getPosition();
        Square target = paneToSquareMap.get(lastClickedPane);
        board.movePiece(start.getDenotation(), target.getDenotation());
        resetBackground();
        if (isPromotion(toBeMoved)){
            showPromotion(toBeMoved.isWhite());
        } else {
            rotateGame();
        }

        printBoard();
        inMove = false;
        if (showIsCheck && board.getCheck().kingInCheck(!whitesTurn)) {
            checkPane.setVisible(true);
        }
        if (board.getCheck().isCheckMate(!whitesTurn)){
            gameOver();
        }
        whitesTurn = !whitesTurn;
    }

    @Override
    protected void doPromotion(StackPane pane) {
        super.doPromotion(pane);
        resetBackground();
        rotateGame();
    }

}
