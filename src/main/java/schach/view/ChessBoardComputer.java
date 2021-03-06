package schach.view;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import schach.controller.ai.AiInterface;
import schach.controller.ai.MinMaxAi;
import schach.controller.ai.SimpleAi;
import schach.model.Piece;
import schach.model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Class extending the ChessBoardController containing concrete implementations for vsAi game mode
 */
public class ChessBoardComputer extends ChessBoardController{

    private AiInterface ai;

    private boolean inPromotion;

    private List<Boolean> promHistory = new ArrayList<>();

    private boolean playerIsWhite;

    private boolean inFirstMove;

    private GameScreen gameScreen;

    /**
     * Constructor initializing fields, maps and event handler
     * @param container the Pane that contains the Chessboard and all belonging Panes
     */
    public ChessBoardComputer(Pane container, GameScreen gameScreen, String[] playerNames, boolean simpleAi, boolean playerIsWhite) {
        super(container, gameScreen, playerNames);
        this.gameScreen = gameScreen;
        this.playerIsWhite = playerIsWhite;
        if (simpleAi){
            ai = new SimpleAi(board, !playerIsWhite);
        } else {
            ai = new MinMaxAi(board, !playerIsWhite);
        }
        inPromotion = false;
        promHistory.add(0, false);
        promHistory.add(1, false);
        inFirstMove = true;
        if (!playerIsWhite){
            Thread computerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    computerMove();
                }
            });
            computerThread.start();
        }
    }

    private void computerMove(){
        //if player made promotion, short delay before rotation
        if (promHistory.get(1)){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //runLater to address JavaFX application Thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                resetBackground();
                printBoard();
                if (board.getCheck().isCheckMate(!playerIsWhite)){
                    return;
                }
                if(!inFirstMove || inFirstMove && playerIsWhite){
                    rotateGame();
                }
            }
        });
        disabledMouseOnBoard = true;
        //short delay for simpleAi in before ai makes a move
        /*if (ai instanceof SimpleAi){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        //generates ai move, can take a while: reason for the extra thread
        /*Task<String> moveTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                return ai.getNextMove();
            }
        };*/
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String aiMove = ai.getNextMove();
        String[] squares = aiMove.split("-");
        board.movePiece(squares[0], squares[1]);
        //runLater to address JavaFX application Thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                checkPane.setVisible(false);
                printBoard();
                updateHistory();
                gameScreen.getCemeteryController().updateCemetery();
                if (board.getCheck().isCheckMate(!whitesTurn)){
                    gameOver();
                }
            }
        });
        //runLater to address JavaFX application Thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rotateGame();
                clock.setWhitesTurn();
            }
        });
        whitesTurn = !whitesTurn;

        disabledMouseOnBoard = false;
        inFirstMove = false;
    }

    @Override
    protected void move(StackPane lastClickedPane){
        checkPane.setVisible(false);
        Square start = toBeMoved.getPosition();
        Square target = paneToSquareMap.get(lastClickedPane);
        board.movePiece(start.getDenotation(), target.getDenotation());
        updateHistory();
        if (isPromotion(toBeMoved)){
            showPromotion(toBeMoved.isWhite());
        } else {
            promHistory.add(0, false);
        }
        gameScreen.getCemeteryController().updateCemetery();
        inMove = false;
        if (showIsCheck && board.getCheck().kingInCheck(!whitesTurn)) {
            checkPane.setVisible(true);
        }
        if (board.getCheck().isCheckMate(!whitesTurn)){
            gameOver();
        }
        whitesTurn = !whitesTurn;
        //extra Thread that executes the move by the ai, otherwise would block the JavaFX Thread and freeze the gui
        Thread computerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                computerMove();
            }
        });
        //Thread will not be started if the players in a promotion pick currently
        if (!inPromotion && !promHistory.get(0)){
            computerThread.start();
        }
        //If the extra Thread is not running (so inPromotion): redraws the gui (otherwise done in the extra Thread)
        //TODO simplify/connect the two if statements
        if (!computerThread.isAlive()){
            printBoard();
        }
        gameScreen.setForwardDisabled(true);
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
        resetBackground();
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

    @Override
    protected boolean correctTurn(Piece clickedPiece) {
        return clickedPiece.isWhite() == whitesTurn && clickedPiece.isWhite() == playerIsWhite;
    }

    @Override
    public void undo(int index) {
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
        gameScreen.setForwardDisabled(true);
    }

    public boolean isPlayerIsWhite() {
        return playerIsWhite;
    }
}