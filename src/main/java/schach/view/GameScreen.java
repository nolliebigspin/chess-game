package schach.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import schach.model.Player;

import java.util.List;

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
    public ListView historyList;
    public Button undo;
    public Button forward;

    // ChessClock
    public Button startButtonChessClock;


    private ChessBoardController boardController;
    private GuiMain guimain;

    private boolean vsPlayer;
    private boolean playerOneIsWhite;
    private boolean simpleAi;
    private ClockController clockController;

    private boolean allowMultipleSelect;
    private boolean showIsInCheck;
    private boolean turnBoard;
    private boolean displayPossibles;
    private LastMoveController lastMoveController;
    private CemeteryController cemeteryController;


    private List<Player> players;

    private String[] playerNames;


    /**
     * Setter for the GuiMain reference
     * @param newGuiMan the guiMain
     */
    public void setGuiMain(GuiMain newGuiMan) {
        this.guimain = newGuiMan;
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
            guimain.loadGameScreen(vsPlayer, playerOneIsWhite, simpleAi, players, playerNames);
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

    /**
     * Method to handle chess-clock start button
     */
    public void handleStartChessClock() {
        clockController.start();
    }

    /**
     * Method to handle white clicked the button on chess-clock
     */
    public void handlePressWhiteClock() {
        clockController.setBlacksTurn();
    }

    /**
     * Method to handle black clicked the button on chess-clock
     */
    public void handlePressBlackClock() {
        clockController.setWhitesTurn();
    }

    public void setLastMoveController(LastMoveController lastMoveController){
        this.lastMoveController = lastMoveController;
    }

    /**
     * Adds a string representing a move to the History List
     * @param unicode the unicode representing the piece
     * @param move String representing a move
     */
    public void addMoveToHistory(String unicode, String move){
        String line = unicode + "   " + move;
        historyList.getItems().add(line);
    }

    /**
     * Clears all List Elements of the History List
     */
    public void clearHistoryList(){
        historyList.getItems().clear();
    }

    /**
     * gets the index of the Item that is selected in the history list
     * @return index int
     */
    public int getSelectedMoveIndex(){
        return historyList.getItems().indexOf(historyList.getSelectionModel().getSelectedItem());
    }

    /**
     * Called via fxml, calls the undo() method in BoardController
     */
    public void undoButtonHandler(){
        boardController.undo(getSelectedMoveIndex());
    }

    /**
     * Disables or enables the undo button
     * @param disabled true if button should be disabled, false if enabled
     */
    public void setUndoDisabled(boolean disabled){
        undo.setDisable(disabled);
    }

    /**
     * Called via fxml, call the redo() method in BoardController
     */
    public void forwardButtonHandler(){
        boardController.redo();
    }

    /**
     * Disables or enables the forward/redo button
     * @param disabled true if button should be disabled, false if enabled
     */
    public void setForwardDisabled(boolean disabled){
        forward.setDisable(disabled);
    }

    /**
     * Setter fpr clock-controller
     * @param clockController instance of a clock-controller
     */
    public void setClockController(ClockController clockController) {
        this.clockController = clockController;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players){
        this.players = players;
    }
}