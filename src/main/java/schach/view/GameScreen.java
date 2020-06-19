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

    private boolean showPossibleMoves = false;
    private boolean allowMultipleSelect = false;
    private boolean showIsInCheck = false;

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

    public void handleShowPossibleMoves(ActionEvent e) {
        this.showPossibleMoves = ((CheckMenuItem)e.getSource()).isSelected();
        System.out.println(showPossibleMoves);
    }

    public void handleAllowMultipleSelect(ActionEvent e) {
        this.allowMultipleSelect = ((CheckMenuItem)e.getSource()).isSelected();
        System.out.println(allowMultipleSelect);
    }

    public void handleIsInCheck(ActionEvent e) {
        this.showIsInCheck = ((CheckMenuItem)e.getSource()).isSelected();
        System.out.println(showIsInCheck);
    }
}
