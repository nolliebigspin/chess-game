package schach.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import schach.model.Board;
import schach.model.Positioning;
import schach.model.Square;

import java.util.HashMap;
import java.util.Map;

public class GameScreen {

    @FXML
    public GridPane gridPane;
    @FXML
    public Pane boardContainerPane;


    public Pane getContainerPane(){
        return this.boardContainerPane;
    }

}
