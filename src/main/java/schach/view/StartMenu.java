package schach.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

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

    private Stage primaryStage;
    private Boolean vsPlayer;
    private String player1;
    private String player2;
    private Boolean player1isWhite;
    private Boolean simpleAi;
    private GuiMain guiMain;

    public StartMenu(){
        vsPlayer = true;
        player1isWhite = true;
        simpleAi = true;
    }

    public void setToggleComputer(){
        togglePlayer.setSelected(false);
        vsPlayer = false;
        playerName2.setVisible(false);
        aiContainerPane.setVisible(true);
    }

    public void setTogglePlayer(){
        toggleComputer.setSelected(false);
        vsPlayer = true;
        playerName2.setVisible(true);
        aiContainerPane.setVisible(false);
    }

    public void setToggleButtonWhite(){
        toggleButtonBlack.setSelected(false);
        player1isWhite = true;
    }

    public void setToggleButtonBlack(){
        toggleButtonWhite.setSelected(false);
        player1isWhite = false;
    }

    public void setPlayerName1(){
        player1 = cutPlayerName(true);
        if (player1.length() == 0){
            player1 = "Player 1";
        }
        playerNameColor.setText(player1);
    }

    public void setPlayerName2(){
        player2 = cutPlayerName(false);
        if (player2.length() == 0){
            player2 = "Player 2";
        }
    }

    public void setToggleButtonSimple(){
        toggleButtonMinMax.setSelected(false);
        simpleAi = true;
    }

    public void setToggleButtonMinMax(){
        toggleButtonSimple.setSelected(false);
        simpleAi = false;
    }

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

    public TextField getPlayerName1(){
        return playerName1;
    }

    public TextField getPlayerName2() {
        return playerName2;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void setGuiMain(GuiMain guiMain){
        this.guiMain = guiMain;
    }

    public void start() throws Exception {
        guiMain.loadGameScreen(vsPlayer, player1isWhite, simpleAi);
    }


}
