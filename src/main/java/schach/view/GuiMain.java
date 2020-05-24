package schach.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;


import javafx.util.Pair;

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
        btn.setText("Sweng Praktikum");
//        btn.setScaleX(2);
        Menu game = new Menu("Start");
        Menu newGame = new Menu("New Game");
        MenuItem loadGame = new MenuItem("Load Game");
        MenuItem exit = new MenuItem("Exit");
        game.getItems().add(newGame);
        game.getItems().add(loadGame);
        game.getItems().add(exit);
        MenuItem humanVSHumanMenu = new MenuItem("Human VS Human");
        MenuItem humanVSCPUMenu = new MenuItem("Human VS CPU");
        newGame.getItems().add(humanVSHumanMenu);
        newGame.getItems().add(humanVSCPUMenu);
        humanVSCPUMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("New Game");
                dialog.setHeaderText("Add Player");

                // Set the icon (must be included in the project).
//        		dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

                // Set the button types.
                ButtonType loginButtonType = new ButtonType("Start", ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                // Create the username and password labels and fields.
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField playerOne = new TextField();
                playerOne.setPromptText("Player Name");



                grid.add(new Label("Player Name:"), 0, 0);
                grid.add(playerOne, 1, 0);



                // Enable/Disable login button depending on whether a username was entered.
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(true);

                // Do some validation (using the Java 8 lambda syntax).
                playerOne.textProperty().addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(newValue.trim().isEmpty());
                });

                dialog.getDialogPane().setContent(grid);

                // Request focus on the username field by default.
                Platform.runLater(() -> playerOne.requestFocus());

                // Convert the result to a username-password-pair when the login button is clicked.


                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(usernamePassword -> {
//        		    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
                });
            }
        });
        humanVSHumanMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Create the custom dialog.
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("New Game");
                dialog.setHeaderText("Add Players");

                // Set the icon (must be included in the project).
//        		dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

                // Set the button types.
                ButtonType loginButtonType = new ButtonType("Start", ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                // Create the username and password labels and fields.
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField playerOne = new TextField();
                playerOne.setPromptText("Player One");
                TextField playerTwo = new TextField();
                playerTwo.setPromptText("Player Two");

                grid.add(new Label("Player 1:"), 0, 0);
                grid.add(playerOne, 1, 0);
                grid.add(new Label("Player 2:"), 0, 1);
                grid.add(playerTwo, 1, 1);

                // Enable/Disable login button depending on whether a username was entered.
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(true);

                // Do some validation (using the Java 8 lambda syntax).
                playerOne.textProperty().addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(newValue.trim().isEmpty());
                });

                dialog.getDialogPane().setContent(grid);

                // Request focus on the username field by default.
                Platform.runLater(() -> playerOne.requestFocus());

                // Convert the result to a username-password-pair when the login button is clicked.
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        return new Pair<>(playerOne.getText(), playerTwo.getText());
                    }
                    return null;
                });

                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(usernamePassword -> {
                    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
                });
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                System.exit(2);

            }

        });
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