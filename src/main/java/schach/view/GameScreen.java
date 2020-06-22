package schach.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Controller for gameScreen.fxml
 */
public class GameScreen {

    @FXML
    public GridPane gridPane;
    @FXML
    public Pane boardContainerPane;
    @FXML
    public Pane controllerContainerPane;
    @FXML
    public MenuBar topMenuBar;

    private boolean showPossibleMoves = false;
    private boolean allowMultipleSelect = false;
    private boolean showIsInCheck = false;
    private boolean turnBoard = true;

    private boolean vsPlayer;
    private boolean playerOneIsWhite;
    private boolean simpleAi;

    private GuiMain guimain;

    public void setGuiMain(GuiMain newGuiMan) {
        this.guimain = newGuiMan;
    }

    public void InitGameMode(boolean newVsPlayer, boolean newPlayerOneIsWhite, boolean newSimpleAi) {
        this.vsPlayer = newVsPlayer;
        this.playerOneIsWhite = newPlayerOneIsWhite;
        this.simpleAi = newSimpleAi;
    }

    ChessBoardController boardController;

    /**
     * getter for the pane containing the gridPane representing the chess board
     * @return the container pane
     */
    public Pane getContainerPane(){
        return this.boardContainerPane;
    }

    public Pane getControllerContainerPane(){
        return this.controllerContainerPane;
    }

    public void setBoardController(ChessBoardController boardController){
        this.boardController = boardController;
    }

    //TopMenuBar button handling
    public void handleButtonRestart() throws Exception {
        guimain.loadGameScreen(vsPlayer, playerOneIsWhite, simpleAi);
    }


    public void handleBackToStartMenu(ActionEvent e) {
        //
    }

    public void handleOpenManual(ActionEvent e) {
        //
    }

    public void handleShowPossibleMoves(ActionEvent e) {
        this.showPossibleMoves = ((CheckMenuItem)e.getSource()).isSelected();
    }

    public void handleAllowMultipleSelect(ActionEvent e) {
        this.allowMultipleSelect = ((CheckMenuItem)e.getSource()).isSelected();
    }

    public void handleIsInCheck(ActionEvent e) {
        this.showIsInCheck = ((CheckMenuItem)e.getSource()).isSelected();
    }

    public void handleTurnBoard(ActionEvent e) {
        this.turnBoard = ((CheckMenuItem)e.getSource()).isSelected();
        boardController.setRotate(turnBoard);
    }

}
