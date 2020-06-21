package schach.view;

import javafx.fxml.FXML;
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
    public Button button;

    ChessBoardController boardController;
    boolean rotation;

    public GameScreen(){
        this.rotation = true;
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

    public void setBoardController(ChessBoardController boardController){
        this.boardController = boardController;
    }

    public void setRotation(){
        this.rotation = !rotation;
        boardController.setRotate(rotation);
    }

}
