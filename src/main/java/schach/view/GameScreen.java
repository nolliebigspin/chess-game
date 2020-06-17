package schach.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import schach.model.Board;
import schach.model.Square;

import java.util.HashMap;

public class GameScreen {

    @FXML
    public GridPane gridPane;

    private Boolean vsPlayer;
    private Boolean playerIsWhite;
    private Boolean simpleAi;

    private Board board;
    private HashMap<StackPane, Square> paneToSquare;

    public GameScreen(){
        this.board = new Board();
        //initHashMap();
    }

    private void initHashMap(){
        for (Node node: gridPane.getChildren()){
            if (node instanceof StackPane){
                StackPane pane = (StackPane) node;
                Square square = board.getSquare(gridPane.getColumnIndex(node) + 1, 8 - gridPane.getRowIndex(node));
                paneToSquare.put(pane, square);
            }
        }
    }

    private StackPane getStackPaneFromGrid(int col, int row){
        for (Node node: gridPane.getChildren()){
            if (gridPane.getColumnIndex(node) == col && gridPane.getRowIndex(node) == row){
                return (StackPane) node;
            }
        }
        return null;
    }

    public void initGameMode(Boolean vsPlayer, Boolean playerIsWhite, Boolean simpleAi){
        this.vsPlayer = vsPlayer;
        this.playerIsWhite = playerIsWhite;
        this.simpleAi = simpleAi;
    }
}
