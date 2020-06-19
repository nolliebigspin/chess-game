package schach.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
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

    private boolean showPossibleMoves;
    private boolean allowMultipleSelect;
    private boolean showIsInCheck;

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

    //TopMenuBar button handling
    public void handleButtonRestart() throws Exception {
        guimain.loadGameScreen(vsPlayer, playerOneIsWhite, simpleAi);
    }

    public void handleGiveUp(ActionEvent actionEvent) {
        //
    }

    public void handleOpenStartMenu(ActionEvent actionEvent) {
        //
    }

    public void handleOpenManual(ActionEvent actionEvent) {
        //
    }

    public void handleTurnBoard(ActionEvent actionEvent) {
        //
    }

    public void handleShowPossibleMoves(ActionEvent actionEvent) {
        if (((CheckMenuItem)actionEvent.getSource()).isSelected()) {

        }
    }

    public void handleAllowMultipleSelect(ActionEvent actionEvent) {
        //
    }

    public void handleIsInCheck(ActionEvent actionEvent) {
        //
    }
}
