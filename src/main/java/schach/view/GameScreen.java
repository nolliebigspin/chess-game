package schach.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
    public Text checkWarning;

    private boolean allowMultipleSelect = true;
    private boolean showIsInCheck = true;
    private boolean turnBoard = true;

    private boolean vsPlayer;
    private boolean playerOneIsWhite;
    private boolean simpleAi;

    private GuiMain guimain;



    // Getter + Setter
    public void setGuiMain(GuiMain newGuiMan) {
        this.guimain = newGuiMan;
    }
    public boolean getIsInCheck() {
        return showIsInCheck;
    }

    public void setShowIsInCheck(boolean newBool) {
        this.showIsInCheck = newBool;
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

    public void handleBackToStartMenu(ActionEvent e) throws Exception {
        guimain.loadStartScreen();
    }

    /**
     * Toggles if the possible moves are shown or not
     * @param e describes the event of the mouse click for this event
     */
    public void handleShowPossibleMoves(ActionEvent e) {
        boardController.setPossibleMoves(((CheckMenuItem)e.getSource()).isSelected());
    }

    public void handleAllowMultipleSelect(ActionEvent e) {
        this.allowMultipleSelect = ((CheckMenuItem)e.getSource()).isSelected();
    }

    /**
     * Toggles warning that is shown if a player is in a check position
     * @param e describes the event of the mouse click for this event
     */
    public void handleIsInCheck(ActionEvent e) {
        boardController.setShowIsCheck(((CheckMenuItem)e.getSource()).isSelected());
    }

    /**
     * Toggles if the board should turn around or not after each move.
     * default: black on top and white on bottom
     * @param e describes the event of the mouse click for this event
     */
    public void handleTurnBoard(ActionEvent e) {
        this.turnBoard = ((CheckMenuItem)e.getSource()).isSelected();
        boardController.setRotate(turnBoard);
    }

}
