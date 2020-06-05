package schach.view;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import schach.model.Square;
import schach.controller.interfaces.Input;
import schach.model.Board;
import schach.model.Piece;


public class HomeScreen extends Pane {
	
	@FXML
	private VBox menuBox;

	private String playerOneName;
	private String PlayerTwoName;
	private String playerColor;
	private boolean multiplayer;
	private GridPane boardPane;



	@FXML
	private VBox image;




	@FXML
	private VBox content;
	
	public HomeScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
//	    	FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setRoot(this);
	    	fxmlLoader.setController(this);
	    	fxmlLoader.load();
	    	this.getStylesheets().add(HomeScreen.class.getResource("root.css").toExternalForm());
//	    	mainContent = new VBox(this.menuBox);
//	    	mainContent.getChildren().add(this.menuBox);
	    	///////////// test
	    	////// background
	    	// image ex
//	    	FileInputStream input = new FileInputStream(
//					System.getProperty("user.dir") + "\\src\\main\\resources\\schach\\view\\background.jpeg");
////					
//			Image image = new Image(input);
//			BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.SPACE,
//					BackgroundPosition.DEFAULT,
//					new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
//
//			Background background = new Background(backgroundimage);
//			this.image.setBackground(background);
	    	//////////end
	    	startGame();
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
		dialog.getDialogPane().setStyle("-fx-background-color: #edfffc;");
		// Set the icon (must be included in the project).
		// Set the button types.
		ButtonType startButton = new ButtonType("Start", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(startButton, ButtonType.CANCEL);
		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setStyle("-fx-text-fill: #f0ffef;");
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
		dialog.getDialogPane().setStyle("-fx-background-color: #feffef;");
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

		board.initMatrix();
		board.initLineUp();
		board.updateAllLegalSquares();
		HBox boardBox = new HBox();
		this.boardPane = new GridPane();
		this.boardPane.setPrefSize(600, 600);
		updateBoard(board);
		boardBox.getChildren().add(boardPane);
		
		content.setPrefSize(700, 500);
		/// Last Move section
		HBox lastMove = new HBox();
		lastMove.setPrefSize(100, 100);
		Label l = new Label("Last Move");
		lastMove.getChildren().add(l);
//		content.getChildren().add(lastMove);
		content.getChildren().addAll(boardBox,lastMove);
//		content.setPrefSize(600, 600);
//		mainContent.
//		mainContent.setAlignment(Pos.BOTTOM_LEFT);
	}











	public static Map<Integer, Image> wFigures;
	static {
		wFigures = new HashMap<Integer, Image>();
		wFigures.put(0, new Image(HomeScreen.class.getResource("wp.png").toExternalForm(), false));
		wFigures.put(1, new Image(HomeScreen.class.getResource("wr.png").toExternalForm(), false));
		wFigures.put(2, new Image(HomeScreen.class.getResource("wb.png").toExternalForm(), false));
		wFigures.put(3, new Image(HomeScreen.class.getResource("wki.png").toExternalForm(), false));
		wFigures.put(4, new Image(HomeScreen.class.getResource("wq.png").toExternalForm(), false));
		wFigures.put(5, new Image(HomeScreen.class.getResource("wq.png").toExternalForm(), false));
	}

	public static Map<Integer, Image> bFigure;
		static {
			bFigure = new HashMap<Integer, Image>();
			bFigure.put(0, new Image(HomeScreen.class.getResource("bp.png").toExternalForm(), false));
			bFigure.put(1, new Image(HomeScreen.class.getResource("br.png").toExternalForm(), false));
			bFigure.put(2, new Image(HomeScreen.class.getResource("bb.png").toExternalForm(), false));
			bFigure.put(3, new Image(HomeScreen.class.getResource("bk.png").toExternalForm(), false));
			bFigure.put(4, new Image(HomeScreen.class.getResource("bq.png").toExternalForm(), false));
			bFigure.put(5, new Image(HomeScreen.class.getResource("bki.png").toExternalForm(), false));

		}
































	private void updateBoard(Board board) {
		GridPane root = new GridPane();
		final int size = 8;
		//Square[][] squareMatrix1 = new Square[8][8];


		Color c;

	for (int col= 0; col < 8; col++) {
		for (int row = 0; row < 8;row++) {
			if ((row + col) % 2 == 0) {
				c = Color.BLACK;
			} else {
				c = Color.GREY;
			}
			//if (row == 8) {
	//			if(board.getSquares()[col][row].isOccupied()) {
	//				Label b = new Label();
	//				b.setTextFill(Color.web("#ff0000", 0.8));
	//				b.setContentDisplay(ContentDisplay.LEFT);
	//				b.setAlignment(Pos.CENTER);
	//				b.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
	//				b.setBackground(new Background(new BackgroundFill(Color.rgb(75, 75, 75), null, Insets.EMPTY)));
	//				boardPane.add(b, col, row);
	//			}

	//		 else {

//					 System.out.println(board.squareByDenotation(alfa[col]+String.valueOf(row)));
//						if (board.getSquares()[col - 1][row].isOccupied()) {
				if (board.getSquares()[col][row].isOccupied()) {
//							Label b = new Label("  " + board.getSquares()[col - 1][row].getOccupier().print());
					Label b = new Label("     " + (board.getSquares()[col][row].getOccupier().print()));
					b.setContentDisplay(ContentDisplay.CENTER);
					b.prefHeightProperty().bind(boardPane.heightProperty().divide(8));
					b.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
					/// color
					b.setBackground(new Background(new BackgroundFill(c, null, Insets.EMPTY)));
					boardPane.add(b, col, row);
					int position[] = {col, row};
					///////////// Add listener
					int finalCol = col;
					int finalRow = row;
					b.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {

//									b.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, Insets.EMPTY)));
							Alert alert = new Alert(AlertType.INFORMATION);
//							current piece 		board.getSquares()[position[0] - 1][position[1]].getOccupier().print();
							//Square s = board.getSquare(finalCol, finalRow).getOccupier();
							Piece p = board.getSquares()[finalCol][7-finalRow].getOccupier();
							System.out.println(p.print() + " White: " + p.isWhite());
							for (int i = 0; i < p.getLegalNextSquares().size(); i++) {
								System.out.println("can move to:" + p.getLegalNextSquares().get(i).getColumn() + ", " + p.getLegalNextSquares().get(i).getRow());
							}
							alert.setContentText(board.getSquares()[finalCol][7-finalRow].getOccupier().print());
							alert.showAndWait().ifPresent(rs -> {
								if (rs == ButtonType.OK) {
									System.out.println("Pressed OK.");
								}
							});
						}
					});


				}else {
					Label b = new Label();
					b.setContentDisplay(ContentDisplay.CENTER);
					b.prefHeightProperty().bind(boardPane.heightProperty().divide(8));
					b.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
					b.setBackground(new Background(new BackgroundFill(c, null, Insets.EMPTY)));
					boardPane.add(b, col, row);
				}
			/// else {
			//	Label b = new Label([row]);
			//	b.setContentDisplay(ContentDisplay.RIGHT);
			//	b.setAlignment(Pos.CENTER_LEFT);
			//	b.prefHeightProperty().bind(boardPane.heightProperty().divide(9));
			//	b.setBackground(new Background(new BackgroundFill(Color.rgb(75,75,75), null, Insets.EMPTY)));
			//	boardPane.add(b, col, row);

			//}

//					b.setAlignment(Pos.CENTER_LEFT);


		}
	}
}

}
