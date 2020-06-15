package schach;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class StartMenu {

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

    public StartMenu(){
        vsPlayer = true;
        player1isWhite = true;
//        playerName1.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                setPlayerName1();
//                System.out.println("as");
//            }
//        });
    }

    public void setToggleComputer(){
        togglePlayer.setSelected(false);
        vsPlayer = false;
        playerName2.setVisible(false);
    }

    public void setTogglePlayer(){
        toggleComputer.setSelected(false);
        vsPlayer = true;
        playerName2.setVisible(true);
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
        player1 = playerName1.getText();
        playerNameColor.setText(player1);
    }



    public void start(){

    }
}
