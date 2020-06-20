package schach.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import schach.controller.ai.AiInterface;
import schach.controller.ai.MinMaxAi;
import schach.controller.ai.SimpleAi;
import schach.model.Square;

import java.util.concurrent.TimeUnit;

public class ChessBoardComputer extends ChessBoardController{

    private AiInterface ai;

    private boolean playerIsWhite;

    /**
     * Constructor initializing fields, maps and event handler
     * @param container the Pane that contains the Chessboard and all belonging Panes
     */
    public ChessBoardComputer(Pane container, boolean simpleAi, boolean playerIsWhite) {
        super(container);
        if (simpleAi){
            ai = new SimpleAi(board, !playerIsWhite);
        } else {
            ai = new MinMaxAi(board, !playerIsWhite);
        }
        this.playerIsWhite = playerIsWhite;
        if (!playerIsWhite){
            computerMove();
        }
    }

    private void computerMove(){
        disabledMouseOnBoard = true;
        String aiMove = ai.getNextMove();
        String[] squares = aiMove.split("-");
        board.movePiece(squares[0], squares[1]);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                printBoard();
                if (board.getCheck().isCheckMate(!whitesTurn)){
                    gameOver();
                }
                rotateGame();
            }
        });
        whitesTurn = !whitesTurn;
        disabledMouseOnBoard = false;
    }

    @Override
    protected void move(StackPane lastClickedPane){
        Square start = toBeMoved.getPosition();
        Square target = paneToSquareMap.get(lastClickedPane);
        board.movePiece(start.getDenotation(), target.getDenotation());
        if (isPromotion(toBeMoved)){
            showPromotion(toBeMoved.isWhite());
        }
        inMove = false;
        if (board.getCheck().isCheckMate(!whitesTurn)){
            gameOver();
        }
        whitesTurn = !whitesTurn;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        resetBackground();
                        printBoard();
                        rotateGame();
                    }
                });
                computerMove();
            }
        });
        thread.start();
    }

    @Override
    protected void showPromotion(boolean whiteProm) {
        disabledMouseOnBoard = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        printBoard();
                        squareToPaneMap.get(toBeMoved.getPosition()).setStyle(backgroundGreen);
                        GridPane promGrid = (GridPane) container.lookup("#promWhiteGrid");
                        if (!whiteProm){
                            promGrid = (GridPane) container.lookup("#promBlackGrid");
                        }
                        promGrid.setVisible(true);
                    }
                });

            }
        });
        thread.start();
    }

    private void promotionWaitForInput(){
        Boolean running = true;
        while (true){
            if (!disabledMouseOnBoard){
                running = false;
            }
        }
    }
}
