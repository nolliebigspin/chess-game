package schach.view;


import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import schach.model.Piece;

import java.util.List;


/**
 * Class to control the cemetery output on gui
 */
public class CemeteryController {

    private final GridPane gridPane;

    private ChessBoardController boardController;


    /**
     * Constructor initializing the fields
     * @param containerPane
     */
    public CemeteryController(Pane containerPane, ChessBoardController boardController){
        this.boardController = boardController;
        this.gridPane = (GridPane) containerPane.lookup("#cemeteryGridPane");
    }

    /**
     * updates the cemetery output on the gui
     * @param chessBoardController
     */
    public void updateCemetery(ChessBoardController chessBoardController){
        for (Node node: gridPane.getChildren()){
            if (node instanceof StackPane){
                ((StackPane) node).getChildren().clear();
            }
        }
        List<Piece> cemetery = boardController.getBoard().getCemetery();
        int i = 0;
        for (Node node: gridPane.getChildren()){
            if (node instanceof StackPane){
                StackPane pane = (StackPane) node;
                try {
                    boardController.placeImageOnPane(cemetery.get(i), pane, true);
                    i++;
                } catch (Exception e) {
                    return;
                }
            }
        }
    }

}