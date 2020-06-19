package schach.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    public void handleButtonRestart() {
        //
    }

    public void handleGiveUp(ActionEvent actionEvent) {
        //
    }

    public void handleSave(ActionEvent actionEvent) {
        //
    }

    public void handleOpenStartMenu(ActionEvent actionEvent) {
        //
    }

    public void handleOpenManual(ActionEvent actionEvent) {
        //
    }

    public void handleAbout(ActionEvent actionEvent) {
        //
    }
}
