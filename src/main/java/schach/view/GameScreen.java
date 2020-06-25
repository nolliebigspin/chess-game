package schach.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import schach.model.Player;

import java.util.ArrayList;

/**
 * Controller for gameScreen.fxml
 */
public class GameScreen {

    @FXML
    public Pane boardContainerPane;
    public Pane controllerContainerPane;
    public MenuBar topMenuBar;
    public Pane cemeteryPane;
    public Text checkWarning;

    private ChessBoardController boardController;
    private GuiMain guimain;

    private boolean vsPlayer;
    private boolean playerOneIsWhite;
    private boolean simpleAi;

    private boolean allowMultipleSelect;
    private boolean showIsInCheck;
    private boolean turnBoard;
    private boolean displayPossibles;
    private LastMoveController lastMoveController;
    private CemeteryController cemeteryController;


    private ArrayList<Player> players;


    /**
     * Setter for the GuiMain reference
     * @param newGuiMan the guiMain
     */
    public void setGuiMain(GuiMain newGuiMan) {
        this.guimain = newGuiMan;
    }
    public boolean getIsInCheck() {
        return showIsInCheck;
    }

    /**
     * Constructor initializing settings to true
     */
    public GameScreen(){
        this.allowMultipleSelect = true;
        this.showIsInCheck = true;
        this.turnBoard = true;
        displayPossibles = true;
    }

    /**
     * initializing the game mode options
     * @param newVsPlayer true if game mode pvp, false if opponent is ai
     * @param newPlayerOneIsWhite true if player 1 is white, false if player 1 is black (Player 2 = Ai if player picked Ai as a Opponent)
     * @param newSimpleAi true if Ai is simple, false if Ai is minmax AI
     */
    public void InitGameMode(boolean newVsPlayer, boolean newPlayerOneIsWhite, boolean newSimpleAi) {
        this.vsPlayer = newVsPlayer;
        this.playerOneIsWhite = newPlayerOneIsWhite;
        this.simpleAi = newSimpleAi;
    }

    /**
     * Setter for the ChessBoardController reference
     * @param boardController the ChessBoardController
     */
    public void setBoardController(ChessBoardController boardController){
        this.boardController = boardController;
    }

    /**
     * getter for the pane containing the gridPane representing the chess board
     * @return the container pane
     */
    public Pane getContainerPane(){
        return this.boardContainerPane;
    }

    public Pane getCemeteryPane(){
        return this.cemeteryPane;
    }

    /**
     * getter for Pane containing the some other things?!
     * @return
     */
    public Pane getControllerContainerPane(){
        return this.controllerContainerPane;
    }


    /**
     * Handler for the Restart Button in the Menu Bar
     */
    public void handleButtonRestart(){
        try {
            guimain.loadGameScreen(vsPlayer, playerOneIsWhite, simpleAi, players);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handler for the Back to Startmenu Button in the Menu Bar
     */
    public void handleBackToStartMenu(){
        try {
            guimain.loadStartScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Toggles if the possible moves are shown or not
     * @param e describes the event of the mouse click for this event
     */
    public void handleShowPossibleMoves(ActionEvent e) {
        this.displayPossibles = ((CheckMenuItem)e.getSource()).isSelected();
        boardController.setPossibleMoves(displayPossibles);
    }

    /**
     * Toggles if player is allowed to switch piece after already chosen a piece
     * @param e describes the event of the mouse click for this event
     */
    public void handleAllowMultipleSelect(ActionEvent e) {
        this.allowMultipleSelect = ((CheckMenuItem)e.getSource()).isSelected();
        boardController.setMultipleSelect(allowMultipleSelect);
    }

    /**
     * Toggles warning that is shown if a player is in a check position
     * @param e describes the event of the mouse click for this event
     */
    public void handleIsInCheck(ActionEvent e) {
        this.showIsInCheck = ((CheckMenuItem)e.getSource()).isSelected();
        boardController.setShowIsCheck(showIsInCheck);
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

    public void setLastMoveController(LastMoveController lastMoveController){
        this.lastMoveController = lastMoveController;
    }

    public void setCemeteryController(CemeteryController cemeteryController){this.cemeteryController = cemeteryController;}

    public LastMoveController getLastMoveController(){
        return this.lastMoveController;
    }

    public CemeteryController getCemeteryController(){
        return this.cemeteryController;
    }

    public GuiMain getGuimain(){
        return this.guimain;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }
}