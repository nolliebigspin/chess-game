package schach;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import schach.view.HomeScreen;

public class GuiMain extends Application {
    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage primaryStage) {
        HomeScreen homeScreen = new HomeScreen();
        Scene scene = new Scene(homeScreen);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Chess Game");
        primaryStage.show();
    }
}
