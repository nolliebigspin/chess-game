package schach.view;


import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import schach.model.Board;


public class HomeScreen extends Pane {
	
	  @FXML
	  private MenuBar menuBar;
	
	@FXML
	private GridPane gameGrid;
	private String playerOneName;
	private String PlayerTwoName;
	private String playerColor;
	private boolean multiplayer;
	private GridPane boardPane;
//	@FXML
//	private HBox mainContent;
	public HomeScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUI/Components/HomeScreen.fxml"));
	    	fxmlLoader.setRoot(this);
	    	fxmlLoader.setController(this);
	    	fxmlLoader.load();
	    	this.getStylesheets().add(HomeScreen.class.getResource("GUI/Style/root.css").toExternalForm());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@FXML
	private void showDialogHuman(){  	
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("New Game");
		dialog.setHeaderText("Add Players");
		dialog.getDialogPane().setStyle("-fx-background-color: orange;");
		// Set the icon (must be included in the project).
		// Set the button types.
		ButtonType startButton = new ButtonType("Start", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(startButton, ButtonType.CANCEL);
		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setStyle("-fx-text-fill: orange;");
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
		Node loginButton = dialog.getDialogPane().lookupButton(startButton);
		loginButton.setDisable(true);
		// Do some validation (using the Java 8 lambda syntax).
		playerOne.textProperty().addListener((observable, oldValue, newValue) -> {
			playerTwo.textProperty().addListener((observable1, oldValue1, newValue1) -> {
				loginButton.setDisable(newValue.trim().isEmpty() || newValue1.trim().isEmpty());
			});
		});
		playerTwo.textProperty().addListener((observable, oldValue, newValue) -> {
			playerOne.textProperty().addListener((observable1, oldValue1, newValue1) -> {
				loginButton.setDisable(newValue.trim().isEmpty() || newValue1.trim().isEmpty());
			});
		});
		dialog.getDialogPane().setContent(grid);
		// Request focus on the username field by default.
		Platform.runLater(() -> playerOne.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == startButton) {
				return new Pair<>(playerOne.getText(), playerTwo.getText());
			}
			return null;
		});
		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(player -> {
//		    System.out.println("Player1 = " + player.getKey() + ", Player2 = " + player.getValue());
			playerOneName = player.getKey();
			PlayerTwoName = player.getValue();
			this.multiplayer = true;
			startGame();
		});
	}
	
	@FXML
	private void showDialogCPU(){
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("New Game");
		dialog.setHeaderText("Add Player");
		dialog.getDialogPane().setStyle("-fx-background-color: orange;");
		// Set the icon (must be included in the project).
//		dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		TextField playerOne = new TextField();
		playerOne.setPromptText("Player Name");
		ToggleGroup group = new ToggleGroup();
		RadioButton white = new RadioButton("White");
		RadioButton black = new RadioButton("Black");
		black.setToggleGroup(group);
		white.setToggleGroup(group);
		grid.add(new Label("Player Name:"), 0, 0);
		grid.add(playerOne, 1, 0);
		grid.add(new Label("Play With:"), 0, 1);
		grid.add(white, 1, 1);
		grid.add(black, 1, 2);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		playerOne.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});
		dialog.getDialogPane().setContent(grid);
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(playerOne.getText(), null);
			}
			return null;
		});
		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(username -> {
//			System.out.println("Done");
			playerOneName = username.getKey();
			RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
			playerColor = selectedRadioButton.getText();
//			System.out.println(selectedRadioButton.getText());
			this.multiplayer = false;
			startGame();
		});
	}
	
	private void startGame() {
		Board board = new Board();
		board.initLineUp();
		HBox boardBox = new HBox(5);
		this.boardPane = new GridPane();
		updateBoard(board);
//		boardBox.getChildren().add(boardPane);
//		mainContent.getChildren().add(boardBox);
	}
	
	private void updateBoard(Board board) {
		Color c;
		String[] alfa = { "", "A", "B", "C", "D", "E", "F", "G", "H" };
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if ((row + col) % 2 == 0) {
					c = Color.WHITE;
				} else {
					c = Color.BLACK;
				}
				if (row == 8) {
					if(col != 0) {
						Label b = new Label("  " + alfa[col]);
					b.setTextFill(Color.web("#ff0000", 0.8));
					b.setContentDisplay(ContentDisplay.RIGHT);
					b.setAlignment(Pos.CENTER_LEFT);
					b.prefWidthProperty().bind(boardPane.widthProperty().divide(9));
					b.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), null, Insets.EMPTY)));
					boardPane.add(b, col, row);
					}
					
				} else if (col != 0){

					 
						if (board.getSquares()[col - 1][row].isOccupied()) {
							Label b = new Label("  " + board.getSquares()[col - 1][row].getOccupier().print());
							b.setContentDisplay(ContentDisplay.CENTER);
							b.prefHeightProperty().bind(boardPane.heightProperty().divide(9));
							b.prefWidthProperty().bind(boardPane.widthProperty().divide(9));
							b.setBackground(new Background(new BackgroundFill(c, null, Insets.EMPTY)));
							boardPane.add(b, col, row);
						}else {
							Label b = new Label();
							b.setContentDisplay(ContentDisplay.CENTER);
							b.prefHeightProperty().bind(boardPane.heightProperty().divide(9));
							b.prefWidthProperty().bind(boardPane.widthProperty().divide(9));
							b.setBackground(new Background(new BackgroundFill(c, null, Insets.EMPTY)));
							boardPane.add(b, col, row);
						}
					} else {
						Label b = new Label(String.valueOf(8 - row));
						b.setContentDisplay(ContentDisplay.RIGHT);
						b.setAlignment(Pos.CENTER_LEFT);
						b.prefHeightProperty().bind(boardPane.heightProperty().divide(9));
						b.setBackground(new Background(new BackgroundFill(Color.rgb(100,100,100), null, Insets.EMPTY)));
						boardPane.add(b, col, row);

					}

//					b.setAlignment(Pos.CENTER_LEFT);

				
			}
		}
	}
	
}
