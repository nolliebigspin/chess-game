package schach.view;

//import java.awt.Stroke;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Pair;
import schach.controller.interfaces.Input;
import schach.model.Board;

public class GuiMain extends Application {
	private String playerName1;
	private String playerName2;
	private Label[][] boardGame;
	private Board board;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		this.boardGame = new Label[9][9];
		primaryStage.setTitle("Odai GUI");
		primaryStage.setWidth(800);
		primaryStage.setHeight(800);
		primaryStage.setResizable(false);

		///////////////////////// Background //////////////////
		FileInputStream input = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\schach\\view\\GUI\\images\\background.jpeg");
		Image image = new Image(input);
		BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.SPACE,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

		Background background = new Background(backgroundimage);
///////////////////////////////////////////////////////

		Menu start = new Menu("Start");
		Menu newGame = new Menu("New Game");
		MenuItem loadGame = new MenuItem("Load Game");
		MenuItem exit = new MenuItem("Exit");
		start.getItems().add(newGame);
		start.getItems().add(loadGame);
		start.getItems().add(exit);
		MenuItem humanVSHumanMenu = new MenuItem("Human VS Human");
		MenuItem humanVSCPUMenu = new MenuItem("Human VS CPU");
		newGame.getItems().add(humanVSHumanMenu);
		newGame.getItems().add(humanVSCPUMenu);

		MenuBar mb = new MenuBar();
		Menu setting = new Menu("Setting");
		Menu language = new Menu("Change Language");
		MenuItem german = new MenuItem("German");
		MenuItem english = new MenuItem("English");
		setting.getItems().add(language);
		language.getItems().add(german);
		language.getItems().add(english);
		Menu changeColor = new Menu("Change Color");
		setting.getItems().add(changeColor);
		mb.getMenus().add(start);
		mb.getMenus().add(setting);
		GridPane boardPane = new GridPane();
		HBox boardBox = new HBox(5);
		StackPane root = new StackPane();
		/////////////////// century/////////////////////
		GridPane century = new GridPane();
//        century.setAlignment(Pos.CENTER_RIGHT);

		/////////////////////// Board Testing ///////////////.
		this.board = new Board();
		board.initLineUp();
		fillBoard(boardPane);
		fillCentury(century);
		Input input1 = new Input(board);
//		input1.inOutRoutine();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.getSquares()[i][j].isOccupied()) {
					System.out.println(
							board.getSquares()[i][j].getOccupier().print() + " in Pos: (" + i + ", " + j + ")");
				}
			}
		}

//	    boardBox.getChildren().addAll(boardPane,century);
		boardBox.getChildren().add(boardPane);
		boardBox.getChildren().add(century);
		boardPane.setPrefSize(800, 600);

		root.getStylesheets().add(GuiMain.class.getResource("GUI/Style/root.css").toExternalForm());
//      mb.getStylesheets().add(GuiMain.class.getResource("GUI/Style/root.css").toExternalForm());
		mb.getStyleClass().add("mb");
		mb.prefWidthProperty().bind(root.widthProperty().multiply(1));
//        boardPane.getStylesheets().add(GuiMain.class.getResource("GUI/Style/minu.css").toExternalForm());
		boardPane.getStyleClass().add("boardPane");
		root.setAlignment(Pos.CENTER_LEFT);
		VBox minuBox = new VBox(mb);
		HBox mainBox = new HBox(10, minuBox);
//        mb.getStylesheets().add(GuiMain.class.getResource("GUI/Style/minu.css").toExternalForm());
//        mainBox.getChildren().add(boardPane);
		mainBox.setMaxWidth(800);
		mainBox.setMaxHeight(800);
		boardPane.setMaxSize(800, 800);
//	    mainBox.setBackground(background);
		mainBox.setAlignment(Pos.CENTER_LEFT);
		boardPane.setGridLinesVisible(true);

		minuBox.getChildren().add(boardBox);
		root.getChildren().add(mainBox);

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

				// Request focus on the username field by default.
				Platform.runLater(() -> playerOne.requestFocus());

				// Convert the result to a username-password-pair when the login button is
				// clicked.

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
				ButtonType startButton = new ButtonType("Start", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(startButton, ButtonType.CANCEL);

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
//        		    System.out.println("Player1 = " + player.getKey() + ", Player2 = " + player.getValue());
					playerName1 = player.getKey();
					playerName2 = player.getValue();

					/////////////// Jaja /////////////////////////////////////////

					final int size = 8;
					for (int row = 0; row < size; row++) {
						for (int col = 0; col < size; col++) {
							Rectangle square = new Rectangle();
							Color color;
							if ((row + col) % 2 == 0)
								color = Color.WHITE;
							else
								color = Color.BLACK;
							square.setFill(color);
							boardPane.add(square, col, row);
							square.widthProperty().bind(boardPane.widthProperty().divide(size));
							square.heightProperty().bind(boardPane.heightProperty().divide(size));
						}
					}
					mainBox.setBackground(null);
//        	        primaryStage.setScene(new Scene(root, 600, 600));
//        	        primaryStage.show();
					//////////////////////////////////////////////////////////////
				});
			}
		});
		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Player1 = " + playerName1 + ", Player2 = " + playerName2);
				System.exit(2);

			}

		});
		///////////////////// test ///////////////////
//		final int size = 9;
//		Color c;
//		String[] alfa = { "", "A", "B", "C", "D", "E", "F", "G", "H" };
//		for (int row = 0; row < 9; row++) {
////	    	if(row%2 ==0) {
////	    		c = Color.WHITE;
////	    	}else {
////	    		c = Color.BLACK;
////	    	}
////	    	Box hb = new Box();
////            	hb.setBackground(new Background(new BackgroundFill(c, null,Insets.EMPTY)));
////            	boardPane.add(hb,row, row);
////            	hb.prefHeightProperty().bind(boardPane.heightProperty().divide(9));
////            	hb.prefWidthProperty().bind(boardPane.widthProperty().divide(9));
////            	hb.
//			for (int col = 0; col < 9; col++) {
//				if (col == 0) {
//					if (row != 8) {
//
//						Label b = new Label(String.valueOf(8 - row));
//						b.setContentDisplay(ContentDisplay.RIGHT);
//						b.setAlignment(Pos.CENTER_LEFT);
//						b.prefHeightProperty().bind(boardPane.heightProperty().divide(9));
//						b.setBackground(new Background(new BackgroundFill(Color.rgb(100,100,100), null, Insets.EMPTY)));
//						boardPane.add(b, col, row);
////						b.setTextFill(Color.web("#ff0000", 0.8));
//					}
//
//				} else if (row == 8) {
//					Label b = new Label("  "+alfa[col]);
//					b.setTextFill(Color.web("#ff0000", 0.8));
//					b.setContentDisplay(ContentDisplay.RIGHT);
//					b.setAlignment(Pos.CENTER_LEFT);
////					b.prefHeightProperty().bind(boardPane.heightProperty().divide(9));
//					b.prefWidthProperty().bind(boardPane.widthProperty().divide(9));
//					b.setBackground(new Background(new BackgroundFill(Color.rgb(100,100,100), null, Insets.EMPTY)));
//					boardPane.add(b, col, row);
//					
//				} else {
//					if ((row + col) % 2 == 0) {
//						c = Color.WHITE;
//					} else {
//						c = Color.BLACK;
//					}
//					Label b = new Label();
//					b.setAlignment(Pos.CENTER_LEFT);
//					b.prefHeightProperty().bind(boardPane.heightProperty().divide(9));
//					b.prefWidthProperty().bind(boardPane.widthProperty().divide(9));
//					b.setBackground(new Background(new BackgroundFill(c, null, Insets.EMPTY)));
//					boardPane.add(b, col, row);
//				}
//
////                boardPane.getChildren().add(square);
//			}
//		}
		////////////////////////////////////////////////
//        final int size = 9;
//	    for (int row = 0; row < 9; row++) {
//            for (int col = 0; col < 9; col++) {
//            	if(col == 0) {
//            		Rectangle square = new Rectangle();
//            		square.setWidth(20);
//            		square.setFill(Color.GREEN);
//            		boardPane.add(square, col, row);
////            		square.widthProperty().bind(boardPane.widthProperty().divide(size));
//            		square.heightProperty().bind(boardPane.heightProperty().divide(9));
//            	}else if(row == 8) {
//            		Rectangle square = new Rectangle();
//            		square.setHeight(20);
//            		square.setFill(Color.GREEN);
//            		boardPane.add(square, col, row);
////            		square.widthProperty().bind(boardPane.widthProperty().divide(size));
//            		square.widthProperty().bind(boardPane.widthProperty().divide(9));
//            	}else {
//            		Rectangle square = new Rectangle();
//            		Color color;
//            		if ((row + col) % 2 == 0) color = Color.WHITE;
//            		else color = Color.BLACK;
//            		square.setFill(color);
//            		boardPane.add(square, col, row);
//            		square.widthProperty().bind(boardPane.widthProperty().divide(9));
//            		square.heightProperty().bind(boardPane.heightProperty().divide(9));
//            	}
//                
////                boardPane.getChildren().add(square);
//            }
//        }
		///////////////////////////////////////////////

		primaryStage.setScene(new Scene(root, 800, 800));
		primaryStage.show();
	}

	private void fillBoard(GridPane boardPane) {
		Color c;
		String[] alfa = { "", "A", "B", "C", "D", "E", "F", "G", "H" };

		for (int row = 0; row < 9; row++) {
//			Label b1 = new Label(String.valueOf(8 - row));
//			b1.setContentDisplay(ContentDisplay.RIGHT);
//			b1.setAlignment(Pos.CENTER_LEFT);
//			b1.prefHeightProperty().bind(boardPane.heightProperty().divide(9));
//			b1.setBackground(new Background(new BackgroundFill(Color.rgb(100,100,100), null, Insets.EMPTY)));
//			boardPane.add(b1, 0, row);
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

	private void fillCentury(GridPane century) {
		century.setPrefSize(500, 300);
		century.getStyleClass().add("century");
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 4; col++) {
				Rectangle square = new Rectangle();
//                square.getStyleClass().add("centuryCell");
				Color color;
				square.setStrokeType(StrokeType.INSIDE);
				square.setStroke(Color.BLACK);
				square.setStrokeLineJoin(StrokeLineJoin.ROUND);
				square.setStrokeWidth(0.5);
				if (row < 4)
					color = Color.WHITE;
				else
					color = Color.GRAY;
				square.setStrokeWidth(1);
				square.setFill(color);
				century.add(square, col, row);
				square.widthProperty().bind(century.widthProperty().divide(4));
				square.setHeight(30);
			}
		}
	}

}
