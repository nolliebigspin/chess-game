package schach.view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import schach.model.Constants;
import schach.model.Player;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Starts the JavaFX GUI and handles transitions between scenes as well as events that cant be implemented in the
 * controller classes
 */
public class GuiMain extends Application {

    private StartMenu startMenu;
    private Stage stage;
    private Scene gameScreenContainerPane;

    /**
     * main method, starts the gui
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Starts the JavaFX GUI by creating new stage and sets the StartMenu as a scene
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        //URL url = Paths.get("src/main/resources/startMenu.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/startMenu.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("Language", Constants.LANGUAGE));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1150);
        primaryStage.setMinHeight(795);
        primaryStage.setResizable(true);
        primaryStage.show();
        stage = primaryStage;
        startMenu = (StartMenu) fxmlLoader.getController();
        startMenu.setGuiMain(this);
        initStartMenuHandler();
    }

    /**
     * Initializes EventHandler for the StartMenu which cant be initialized in StartMenu controller class
     */
    private void initStartMenuHandler(){
        double initWidth = 1150;
        double initHeight = 795;
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double factor = stage.getWidth()/initWidth;
                setStageHeight(initHeight*(stage.getWidth()/initWidth));
            }
        });
        startMenu.getPlayerName1().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                startMenu.setPlayerName1();
            }
        });

        startMenu.getPlayerName2().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                startMenu.setPlayerName2();
            }
        });
    }

    // initialize the game screen listener for window resizing
    private void initGameScreenListener(Pane container){
        double initWidth = 1150;
        double initHeight = 795;
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double factor = stage.getWidth()/initWidth;
                setStageHeight(initHeight*(stage.getWidth()/initWidth));
                setMenuBarWidth();
                centerChessBoard(factor);
                centerLastMoves(factor);
                centerCemetery(factor);


            }
        });
    }

    // this method will align the chessBoard pane accordingly when the window is resized
    private void centerChessBoard(double ratio){
        Pane chessBoardPane = (Pane) gameScreenContainerPane.lookup("#boardContainerPane");
        double initXpos = 287;
        chessBoardPane.setLayoutX(initXpos*ratio);
    }
    // this method will align the lastMove pane accordingly when the window is resized
    private void centerLastMoves(double ratio){
        Pane chessBoardPane = (Pane) gameScreenContainerPane.lookup("#controllerContainerPane");
        double initXpos = 834;
        chessBoardPane.setLayoutX(initXpos*ratio);
    }

    // this method will align the cemetery pane accordingly when the window is resized
    private void centerCemetery(double ratio){
        Pane chessBoardPane = (Pane) gameScreenContainerPane.lookup("#cemeteryPane");
        double initXpos = 287;
        chessBoardPane.setLayoutX(initXpos*ratio);

    }

    // this method will align the menu bar accordingly when the window is resized
    private void setMenuBarWidth(){
        MenuBar menuBar = (MenuBar) gameScreenContainerPane.lookup("#topMenuBar");
        menuBar.setPrefWidth(stage.getWidth());
    }

    private void setStageWidth(double width){
        stage.setWidth(width);
    }

    private void setStageHeight(double height){
        stage.setHeight(height);
    }

    /**
     * loads the main Game Screen as a scene in the stage and initializes a ChessBoardController Class to handles and
     * control events in the chess board pane
     * @param vsPlayer true if it's PvP, false if AI opponent
     * @param player1isWhite true if player1 chose white, false if player1 chose black
     * @param simpleAi true if AI is simple, false if AI should be Minmax
     * @throws Exception
     */
    public void loadGameScreen(Boolean vsPlayer, Boolean player1isWhite, Boolean simpleAi, List<Player> players, String[] playerNames ) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gameScreen.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("Language", Constants.LANGUAGE));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setX(100);
        stage.setY(50);
        GameScreen gameScreen = (GameScreen) fxmlLoader.getController();
        gameScreen.setGuiMain(this);
        gameScreen.setPlayers(players);
        gameScreen.InitGameMode(vsPlayer, player1isWhite, simpleAi);
        Pane boardPane = gameScreen.getContainerPane();
        this.gameScreenContainerPane = scene;
        ChessBoardController boardController;
        //new LastMoveController(gameScreen.getControllerContainerPane());
        LastMoveController lastMoveController = new LastMoveController(gameScreen.getControllerContainerPane(), gameScreen);
        ClockController clockController = new ClockController((Pane) scene.lookup("#chessClockBasePane"));
        gameScreen.setLastMoveController(lastMoveController);
        gameScreen.setCemeteryController(new CemeteryController(gameScreen.getCemeteryPane()));
        gameScreen.setClockController(clockController);
        if (vsPlayer){
            boardController = new ChessBoardHuman(boardPane, gameScreen, playerNames);
        } else {
            boardController = new ChessBoardComputer(boardPane, gameScreen, playerNames, simpleAi, player1isWhite);
        }
        lastMoveController.setBoardController(boardController);
        gameScreen.setBoardController(boardController);
        initGameScreenListener(new Pane());
        boardController.setClock(clockController);
    }

    /**
     * loads the gameScreen scene in the stage
     * @throws Exception
     */
    public void loadStartScreen() throws Exception {
        //URL url = Paths.get("src/main/resources/startMenu.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/startMenu.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("Language", Constants.LANGUAGE));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(100);
        stage.setY(50);
        stage.show();
        stage.setResizable(true);
        startMenu = (StartMenu) fxmlLoader.getController();
        startMenu.setGuiMain(this);
        initStartMenuHandler();
    }
}