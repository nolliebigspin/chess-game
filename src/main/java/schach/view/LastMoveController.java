package schach.view;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


/**
 * Class to control the list of moves on the gui
 */
public class LastMoveController {

    private TableColumn playerColumn;
    private TableColumn moveCollumn;
    private TableColumn timeCollumn;

    private ListView historyList;
    private GameScreen gameScreen;
    private ChessBoardController boardController;

    /**
     * Constructor initializing the fields
     * @param containerPane the Pane containing all gui elements
     * @param gameScreen the fxml controller for the scene
     */
    public LastMoveController(Pane containerPane, GameScreen gameScreen){
        this.historyList = (ListView) containerPane.lookup("#historyList");
        this.gameScreen = gameScreen;
        this.historyList.setOnMouseClicked(listViewHandler());
        this.playerColumn =new TableColumn("Player");
        this.moveCollumn =new TableColumn("Move");
        this.timeCollumn =new TableColumn("Timer");
        this.playerColumn.setCellValueFactory(
                new PropertyValueFactory<>("playerName"));
        this.moveCollumn.setCellValueFactory(
                new PropertyValueFactory<>("move"));
        this.timeCollumn.setCellValueFactory(
                new PropertyValueFactory<>("time"));

    }

    public void setBoardController(ChessBoardController boardController) {
        this.boardController = boardController;
    }

    private EventHandler<MouseEvent> listViewHandler(){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (boardController instanceof ChessBoardHuman){
                    if (historyList.getSelectionModel().getSelectedItem() != null){
                        gameScreen.setUndoDisabled(false);

                    }
                } else {
                    ChessBoardComputer boardComputer = (ChessBoardComputer) boardController;
                    if (boardComputer.isPlayerIsWhite() && historyList.getSelectionModel().getSelectedIndex() % 2 == 1 ||
                            !boardComputer.isPlayerIsWhite() && historyList.getSelectionModel().getSelectedIndex() % 2 == 0){
                        System.out.println(historyList.getSelectionModel().getSelectedItem());
                        historyList.getSelectionModel().select(-1);
                        gameScreen.setUndoDisabled(true);
                    } else if (historyList.getSelectionModel().getSelectedItem() != null) {
                        gameScreen.setUndoDisabled(false);
                    }
                }

            }
        };
    }










}