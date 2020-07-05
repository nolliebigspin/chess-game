package schach.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import schach.model.Player;

import java.util.ArrayList;

/**
 * Controller for startMenu.fxml
 */
public class StartMenu {

    @FXML
    public Pane aiContainerPane;
    @FXML
    public ToggleButton toggleButtonSimple;
    @FXML
    public ToggleButton toggleButtonMinMax;
    @FXML
    public Button startButton;
    @FXML
    private ToggleButton toggleComputer;
    @FXML
    private ToggleButton togglePlayer;
    @FXML
    private TextField playerName1;
    @FXML
    private TextField playerName2;
    @FXML
    private ToggleButton toggleButtonWhite;
    @FXML
    private ToggleButton toggleButtonBlack;
    @FXML
    private Label playerNameColor;

    private Boolean vsPlayer;
    private String player1;
    private String player2;
    private Boolean player1isWhite;
    private Boolean simpleAi;
    private GuiMain guiMain;
    private ArrayList<Player> players;
    /**
     * Constructor setting Game Mode Booleans true
     */
    public StartMenu(){
        vsPlayer = true;
        player1isWhite = true;
        simpleAi = true;
        this.players = new ArrayList<>();
        player1 = "Player 1";
        player2 = "Player 2";
    }

    /**
     * If toggle button 'toggleComputer' clicked
     */
    public void setToggleComputer(){
        togglePlayer.setSelected(false);
        vsPlayer = false;
        playerName2.setVisible(false);
        aiContainerPane.setVisible(true);
        player2 = "CPU";
    }

    /**
     * If toggle button 'togglePlayer' clicked
     */
    public void setTogglePlayer(){
        toggleComputer.setSelected(false);
        vsPlayer = true;
        playerName2.setVisible(true);
        aiContainerPane.setVisible(false);
        player2 = "Player 2";
    }

    /**
     * If toggle button 'toggleButtonWhite' clicked:
     */
    public void setToggleButtonWhite(){
        toggleButtonBlack.setSelected(false);
        player1isWhite = true;
    }

    /**
     * If toggle button 'toggleButtonWhite' clicked:
     */
    public void setToggleButtonBlack(){
        toggleButtonWhite.setSelected(false);
        player1isWhite = false;
    }

    /**
     * updates name of player 1
     * called by eventhandler in GuiMain
     */
    public void setPlayerName1(){
        player1 = cutPlayerName(true);
        if (player1.length() == 0){
            player1 = "Player 1";
        }
    //    playerNameColor.setText(player1);
    }

    /**
     * updates name of player 2
     * called by eventHandler in GuiMain
     */
    public void setPlayerName2(){
        player2 = cutPlayerName(false);
        if (player2.length() == 0){
            player2 = "Player 2";
        }
    }

    /**
     * controls String length of Name input and limits name string if necessary
     * @param isPlayer1 true if name of player 1 should be controlled, false if name of player 2
     * @return cut version of the name
     */
    private String cutPlayerName(Boolean isPlayer1){
        String name = playerName1.getText();
        if(!isPlayer1){
            name = playerName2.getText();
        }
        String newName = name;
        if (name.length() > 12){
            newName = name.substring(0, 12);
            if (isPlayer1){
                playerName1.setText(newName);
            } else {
                playerName2.setText(newName);
            }
        }
        return newName;
    }

    /**
     * If toggle button 'toggleButtonSimple' clicked:
     */
    public void setToggleButtonSimple(){
        toggleButtonMinMax.setSelected(false);
        simpleAi = true;
    }

    /**
     * If toggle button 'toggleButtonMinMax' clicked:
     */
    public void setToggleButtonMinMax(){
        toggleButtonSimple.setSelected(false);
        simpleAi = false;
    }

    /**
     * getter for name of player 1
     * @return name of player 1
     */
    public TextField getPlayerName1(){
        return playerName1;
    }

    /**
     * getter for name of player 2
     * @return name of player 2
     */
    public TextField getPlayerName2() {
        return playerName2;
    }

    /**
     * setter to initialize the guiMain
     * @param guiMain
     */
    public void setGuiMain(GuiMain guiMain){
        this.guiMain = guiMain;
    }

    /**
     * Called if 'start' button clicked
     * loads the main game screen via guiMain
     * @throws Exception
     */
    public void start() throws Exception {
        Player playerOne;
        Player playerTwo;
        if(this.player1isWhite){
            playerOne = new Player(this.player1, "White");
            if(this.vsPlayer)
            playerTwo = new Player(this.player2, "Black");
            else {
                playerTwo = new Player("CPU", "Black");
            }
        }else{
            playerOne = new Player(this.player1, "Black");
            if(this.vsPlayer)
            playerTwo = new Player(this.player2, "White");
            else{
                playerTwo = new Player("CPU", "White");
            }
        }
        this.players.add(playerOne);
        this.players.add(playerTwo);
        //
        String[] playerNames = {player1, player2};
        guiMain.loadGameScreen(vsPlayer, player1isWhite, simpleAi, this.players, playerNames);
    }


}
