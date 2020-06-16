package schach;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

public class GuiMain extends Application {
    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        GuiHandler guiHandler = new GuiHandler();
        Scene scene = new Scene(guiHandler);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
