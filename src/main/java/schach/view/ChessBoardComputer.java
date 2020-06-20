package schach.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                resetBackground();
                printBoard();
                rotateGame();
            }
        });
        disabledMouseOnBoard = true;
        if (ai instanceof SimpleAi){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
            }
        });
        if (ai instanceof SimpleAi){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rotateGame();
            }
        });
        whitesTurn = !whitesTurn;
        disabledMouseOnBoard = false;
    }

    Thread computerThread;

    @Override
    protected void move(StackPane lastClickedPane){
        /*Task<Void> runComputer = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        resetBackground();
                        printBoard();
                        rotateGame();
                    }
                });
                computerMove();
                return null;
            }
        };*/
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
        computerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                computerMove();
            }
        });
        computerThread.start();
    }

    @Override
    protected void showPromotion(boolean whiteProm) {
        disabledMouseOnBoard = true;
        printBoard();
        squareToPaneMap.get(toBeMoved.getPosition()).setStyle(backgroundGreen);
        GridPane promGrid = (GridPane) container.lookup("#promWhiteGrid");
        if (!whiteProm){
            promGrid = (GridPane) container.lookup("#promBlackGrid");
        }
        promGrid.setVisible(true);
        promotionWaitForInput();
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
