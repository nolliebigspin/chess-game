package schach.view;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import schach.controller.ai.AiInterface;
import schach.controller.ai.MinMaxAi;
import schach.controller.ai.SimpleAi;
import schach.model.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ChessBoardComputer extends ChessBoardController{

    private AiInterface ai;

    private boolean playerIsWhite;

    private boolean inPromotion;

    private List<Boolean> promHistory = new ArrayList<>();

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
        inPromotion = false;
        promHistory.add(0, false);
        promHistory.add(1, false);
    }

    private void computerMove(){
        if (ai instanceof SimpleAi && promHistory.get(1)){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    protected void move(StackPane lastClickedPane){
        Square start = toBeMoved.getPosition();
        Square target = paneToSquareMap.get(lastClickedPane);
        board.movePiece(start.getDenotation(), target.getDenotation());
        if (isPromotion(toBeMoved)){
            showPromotion(toBeMoved.isWhite());
        } else {
            promHistory.add(0, false);
        }
        inMove = false;
        if (board.getCheck().isCheckMate(!whitesTurn)){
            gameOver();
        }
        whitesTurn = !whitesTurn;
        Thread computerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                computerMove();
            }
        });
        if (!inPromotion && !promHistory.get(0)){
            computerThread.start();
        }
        if (!computerThread.isAlive()){
            resetBackground();
            printBoard();
        }
    }

    @Override
    protected void showPromotion(boolean whiteProm) {
        inPromotion = true;
        promHistory.add(0, true);
        disabledMouseOnBoard = true;
        printBoard();
        squareToPaneMap.get(toBeMoved.getPosition()).setStyle(backgroundGreen);
        GridPane promGrid = (GridPane) container.lookup("#promWhiteGrid");
        if (!whiteProm){
            promGrid = (GridPane) container.lookup("#promBlackGrid");
        }
        promGrid.setVisible(true);
    }

    @Override
    protected void doPromotion(StackPane pane) {
        super.doPromotion(pane);
        Thread computerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                computerMove();
            }
        });
        inPromotion = false;
        promHistory.add(0, false);
        computerThread.start();
    }
}
