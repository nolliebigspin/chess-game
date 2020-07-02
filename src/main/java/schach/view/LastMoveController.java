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
    private Label timeLabel;
    private Timeline timeline;
    private static final Integer STARTTIME = 15;
    private Integer timeSeconds = STARTTIME;
    ObservableList<Step> data = FXCollections.observableArrayList();
    ObservableList<Step> storedData = FXCollections.observableArrayList();

    private ListView historyList;
    private GameScreen gameScreen;


    public LastMoveController(Pane containerPane, GameScreen gameScreen){
        this.containerPane = containerPane;
        this.boards = new ArrayList<>();
        this.timeLabel = (Label)this.containerPane.lookup("#timeLabel");
        this.timeLabel.setText(timeSeconds.toString());

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

    private EventHandler<MouseEvent> listViewHandler(){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (historyList.getSelectionModel().getSelectedItem() != null){
                    gameScreen.setUndoDisabled(false);
                }
            }
        };
    }

    private EventHandler<ActionEvent>undoHandler(){
        return new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                System.out.println("Undo");
                data.add(new Step("Player","e2-e3","00:00"));
                if (timeline != null) {
                    timeline.stop();
                }
                timeSeconds = STARTTIME;
                timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(1),
                                new EventHandler() {
                                    @Override
                                    public void handle(Event event) {

                                    }

                                    // KeyFrame event handler
                                    public void handle(ActionEvent event) {
                                        timeSeconds--;
                                        // update timerLabel
                                        timeLabel.setText(
                                                timeSeconds.toString());
                                        if (timeSeconds <= 0) {
                                            timeline.stop();
                                        }
                                    }
                                }));
                timeline.playFromStart();
            }
        };
    }


    private EventHandler<ActionEvent>forwardHandler(){
        return new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                System.out.println("Forward");
                if(data.size()>0)
                data.remove(data.get(data.size()-1));
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


