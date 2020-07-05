package schach.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import schach.model.Board;

import java.util.ArrayList;

public class LastMoveController {

    private Pane containerPane;
    private TableView<Step> lastMoveTable;
    private ArrayList<Board> boards;
    private TableColumn playerColumn;
    private TableColumn moveCollumn;
    private TableColumn timeCollumn;
    private Timeline timeline;
    private static final Integer STARTTIME = 15;
    private Integer timeSeconds = STARTTIME;
    ObservableList<Step> data = FXCollections.observableArrayList();
    ObservableList<Step> storedData = FXCollections.observableArrayList();

    private ListView historyList;
    private GameScreen gameScreen;
    private ChessBoardController boardController;


    public LastMoveController(Pane containerPane, GameScreen gameScreen){
        this.containerPane = containerPane;
        this.boards = new ArrayList<>();



        this.historyList = (ListView) containerPane.lookup("#historyList");
        this.gameScreen = gameScreen;
        this.historyList.setOnMouseClicked(listViewHandler());

        this.lastMoveTable = (TableView)this.containerPane.lookup("#lasMoveTable");
        Button undo = (Button)this.containerPane.lookup("#undo");
        Button forward = (Button)this.containerPane.lookup("#forward");
        //undo.setOnAction(undoHandler());
        //forward.setOnAction(forwardHandler());
        this.playerColumn =new TableColumn("Player");
        this.moveCollumn =new TableColumn("Move");
        this.timeCollumn =new TableColumn("Timer");
        this.playerColumn.setCellValueFactory(
                new PropertyValueFactory<>("playerName"));
        this.moveCollumn.setCellValueFactory(
                new PropertyValueFactory<>("move"));
        this.timeCollumn.setCellValueFactory(
                new PropertyValueFactory<>("time"));
        this.lastMoveTable.getColumns().addAll(this.playerColumn,this.moveCollumn, this.timeCollumn);
        this.lastMoveTable.setItems(this.data);

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






    public void saveMove(Board board,String playerName, String move){
        Step step = new Step(playerName, move, "00,00");
        this.data.add(step);
        this.boards.add(board);
    }


    public static class Step {

        private final SimpleStringProperty playerName;
        private final SimpleStringProperty move;
        private final SimpleStringProperty time;
        private Step(String playerName,  String  move, String time) {
            this.playerName = new SimpleStringProperty(playerName);
            this.move = new SimpleStringProperty(move);
            this.time = new SimpleStringProperty(time);
        }

        public String getPlayerName() {
            return playerName.get();
        }

        public void setPlayerName(String name) {
            playerName.set(name);
        }

        public String getMove() {
            return move.get();
        }

        public void setMove(String step) {
            move.set(step);
        }

        public void setTime(String t){
            time.set(t);
        }

        public String getTime(){
            return time.get();
        }

    }



}