package schach.view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class GuiMain extends Application {

    private StartMenu startMenu;
    private schach.view.GameScreen gameScreen;
    private Stage stage;

    public static void main(String[] args) {
        launch();
    }
    
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
        startMenu.setPrimaryStage(primaryStage);
        startMenu.setGuiMain(this);
        initStartMenuHandler();
    }

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

    public void loadGameScreen(Boolean vsPlayer, Boolean player1isWhite, Boolean simpleAi) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gameScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        GameScreen gameScreen = (GameScreen) fxmlLoader.getController();
        ChessBoardController chessBoardController = new ChessBoardController(gameScreen.getContainerPane());
    }
}
