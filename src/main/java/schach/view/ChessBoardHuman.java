package schach.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import schach.model.Piece;
import schach.model.Square;

public class ChessBoardHuman extends ChessBoardController{

    private GameScreen gameScreen;

    /**
     * Constructor initializing fields, maps and event handler
     *
     * @param container the Pane that contains the Chessboard and all belonging Panes
     */
    public ChessBoardHuman(Pane container, GameScreen gameScreen) {
        super(container);
       this.gameScreen = gameScreen;
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
            String playerName = "";
            if(this.gameScreen.getPlayers().get(0).isActive())
                playerName = this.gameScreen.getPlayers().get(0).getName();
            else{
                playerName = this.gameScreen.getPlayers().get(1).getName();
            }
            this.gameScreen.getLastMoveController().saveMove(board,playerName,start.getDenotation()+"-"+target.getDenotation());
           this.togglePlayer();
            rotateGame();
        }

        printBoard();
        inMove = false;

        if (showIsCheck) {
            if (board.getCheck().kingInCheck(!whitesTurn)) {
                checkPane.setVisible(true);
            }
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

    private void togglePlayer(){
        if(this.gameScreen.getPlayers().get(0).isActive()){
            this.gameScreen.getPlayers().get(0).setActive(false);
            this.gameScreen.getPlayers().get(1).setActive(true);
        }else{
            this.gameScreen.getPlayers().get(0).setActive(true);
            this.gameScreen.getPlayers().get(1).setActive(false);
        }
    }
}
