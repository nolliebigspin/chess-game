package schach.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiMain extends Application {
    public static void main(String[] args) {
    	
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Odai GUI");
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        ///////////////////////// Background //////////////////
        FileInputStream input = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\schach\\view\\GUI\\images\\background.jpeg");
                Image image = new Image(input);
                BackgroundImage backgroundimage = new BackgroundImage(image,  
                        BackgroundRepeat.NO_REPEAT,  
                        BackgroundRepeat.SPACE,  
                        BackgroundPosition.DEFAULT,  
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)); 
                
                Background background = new Background(backgroundimage);
///////////////////////////////////////////////////////
        Button btn = new Button();
        btn.setText("Start");
//        btn.setScaleX(2);
        Menu game = new Menu("Game"); 
        MenuItem game1 = new MenuItem("Human VS Human"); 
        MenuItem game2 = new MenuItem("Human VS CPU"); 
        game.getItems().add(game1); 
        game.getItems().add(game2);
        MenuBar mb = new MenuBar();
        Menu setting = new Menu("Setting");
        Menu setting1 = new Menu("Change Language");
        MenuItem german = new MenuItem("German");
        MenuItem english = new MenuItem("English");
        setting.getItems().add(setting1);
        setting1.getItems().add(german);
        setting1.getItems().add(english);
        Menu changeColor = new Menu("Change Color");
        setting.getItems().add(changeColor);
        mb.getMenus().add(game); 
        mb.getMenus().add(setting);
        VBox vb = new VBox(mb);
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        StackPane root = new StackPane();
        HBox hbox = new HBox(300,vb, btn);
        hbox.setMaxWidth(600);
        hbox.setMaxHeight(600);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setBackground(background);
        root.getChildren().add(hbox);
//        root.getChildren().add(vb);
//        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
