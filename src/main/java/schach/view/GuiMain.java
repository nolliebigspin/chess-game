package schach.view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

/**
 * Starts the JavaFX GUI and handles transitions between scenes as well as events that cant be implemented in the
 * controller classes
 */
public class GuiMain extends Application {

    private StartMenu startMenu;
    private Stage stage;

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
        URL url = Paths.get("src/main/resources/startMenu.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        stage = primaryStage;
        startMenu = (StartMenu) fxmlLoader.getController();
        startMenu.setGuiMain(this);
        initStartMenuHandler();
    }

    /**
     * Initializes EventHandler for the StartMenu which cant be initialized in StartMenu controller class
     */
    private void initStartMenuHandler(){
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

    /**
     * loads the main Game Screen as a scene in the stage and initializes a ChessBoardController Class to handles and
     * control events in the chess board pane
     * @param vsPlayer true if it's PvP, false if AI opponent
     * @param player1isWhite true if player1 chose white, false if player1 chose black
     * @param simpleAi true if AI is simple, false if AI should be Minmax
     * @throws Exception
     */
    public void loadGameScreen(Boolean vsPlayer, Boolean player1isWhite, Boolean simpleAi) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gameScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        GameScreen gameScreen = (GameScreen) fxmlLoader.getController();
        new ChessBoardController(gameScreen.getContainerPane());
        new LastMoveController(gameScreen.getControllerContainerPane());
        new TopMenuBarController((gameScreen.getTopMenuBar()));
    }
}
