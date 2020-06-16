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

public class GuiMain extends Application {

    private StartMenu startMenu;
    //private GameScreen gameScreen;

    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = Paths.get("src/main/java/schach/view/startMenu.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        startMenu = (StartMenu) fxmlLoader.getController();
        startMenu.setPrimaryStage(primaryStage);
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

    /*private void setGameScreen(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }*/
}
