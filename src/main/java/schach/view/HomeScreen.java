package schach.view;
import java.io.FileInputStream;
import java.util.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import schach.model.Square;
import schach.model.Board;
import schach.model.Piece;
public class HomeScreen extends Pane {

	@FXML
	public GridPane grid;
	private Label light;
	private CheckBox highlight;
	private String playerOneName;
	private String PlayerTwoName;
	private String playerColor;
	private Boolean multiplayer;
	private GridPane boardPane;
	@FXML
	private VBox menuBox;
	@FXML
	private VBox image;
	@FXML
	private HBox content;
	@FXML
	private HBox cemetery;
	private int currentMove;
	boolean canMove;
	private Board board;
	private final Label[][] labels = new Label[8][8];
	private final Map<Label, Square> labelMap = new HashMap<Label, Square>();
	public ArrayList<Label> list = new ArrayList<Label>();
	private final Map<Label,Square> map = new HashMap<Label,Square>();
	private final Map<Square,Label> mapTwo = new HashMap<Square,Label>();
	private Label lastClickedOn;
	private Square lastClicked;
	private List<Square> clickedList = new ArrayList<>();
	private int clickCounter = 0;

	private ArrayList<Board> moves;
	private VBox lastMobeTableBox;

	public HomeScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			this.getStylesheets().add(HomeScreen.class.getResource("root.css").toExternalForm());
			this.cemetery.setVisible(false);
			FileInputStream input = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\schach\\view\\GUI\\images\\background.jpeg");
			Image image = new Image(input);
			BackgroundImage backgroundimage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT
					,BackgroundRepeat.SPACE,BackgroundPosition.DEFAULT,
					new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,true,true,true, true));
			Background background = new Background(backgroundimage);
			this.setBackground(background);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	private void Human() {
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
		Node loginButton = dialog.getDialogPane().lookupButton(startButton);
		loginButton.setDisable(true);
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
		Platform.runLater(() -> playerOne.requestFocus());
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == startButton) {
				return new Pair<>(playerOne.getText(), playerTwo.getText());
			}
			return null;
		});
		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(player -> {
			playerOneName = player.getKey();
			PlayerTwoName = player.getValue();
			this.multiplayer = true;
			this.content.getChildren().clear();
			startGame();
		});
	}

	@FXML
	private void AI() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("New Game");
		dialog.setHeaderText("Add Player");
		dialog.getDialogPane().setStyle("-fx-background-color: #feffef;");
		// Set the button types.
		ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
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
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);
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
			playerOneName = username.getKey();
			RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
			playerColor = selectedRadioButton.getText();
			this.multiplayer = false;
			this.content.getChildren().clear();
			startGame();
		});
	}

	private void startGame() {
		this.moves = new ArrayList<Board>();
		this.setBackground(null);
		this.setStyle("-fx-base: rgb(10, 10, 10);-fx-background: rgba(53, 64, 35, 0.2);");
		this.cemetery.setVisible(true);
		this.board = new Board();
		board.initMatrix();
		board.initLineUp();
		board.updateAllLegalSquares();
		HBox boardBox = new HBox();
		boardBox.setPrefSize(500,500);
		this.boardPane = new GridPane();
		this.boardPane.setPrefSize(500, 500);
		//updateBoard();
		this.boardPane.setPrefSize(600, 600);
		boardBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				clickCounter++;
				int x = (int) mouseEvent.getX();
				int y = (int) mouseEvent.getY();
				System.out.println(x + " " +y);
				Square s = resolveCoordinatesToSquare(x, y);
				lastClicked = s;
				clickedList.add(s);
				if (clickedList.get(0).isOccupied()){
					if (clickCounter == 2){
						move(clickedList.get(0));
					}
				}
			}
		});
		print();
		boardBox.getChildren().add(boardPane);
		VBox lastMove = new VBox();
		lastMove.setPrefSize(250,500);
		lastMove.getStyleClass().add("lastMove");
		lastMove.setAlignment(Pos.TOP_CENTER);
		this.lastMobeTableBox = new VBox();
		this.lastMobeTableBox.setPrefSize(250,500);
		this.lastMobeTableBox.getStyleClass().add("lastMoveTable");
		Label l = new Label("Last Move");
		lastMove.getChildren().add(l);
		lastMove.getChildren().add(this.lastMobeTableBox);
		l.getStyleClass().add("lastMove-label");
		l.setBackground(new Background(new BackgroundFill(Color.GREEN, null, Insets.EMPTY)));
		updateLastMove();
		content.setSpacing(10);
		content.getChildren().addAll(boardBox, lastMove);
	}


	private void move(Square clicked){
		Piece piece = clicked.getOccupier();
		Square start = clicked;
		Square target = clickedList.get(1);
		piece.updateLegals();
		List<Square> legals = piece.filteredLegals();
		if (legals.contains(target)){
			board.movePiece(start.getDenotation(), target.getDenotation());
		}
		print();
		clickCounter = 0;
		clickedList.clear();
	}


	private Square resolveCoordinatesToSquare(int x, int y){
		int xCoordinate = 1;
		int yCoordinate = 1;
		for (int i = 60; i <= 60*8; i+=60){
			if (x < i){
				break;
			}
			xCoordinate++;
		}
		for (int i = 60; i <= 60*8; i+=60){
			if (y < i){
				break;
			}
			yCoordinate++;
		}
		System.out.println(xCoordinate + " " + yCoordinate);
		return board.getSquare(xCoordinate, 9 - yCoordinate );
	}



	private void print(){
		Color squareColor;

		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {
				if ((row + col) % 2 == 0) {
					squareColor = Color.BLACK;
				} else {
					squareColor = Color.GREY;
				}
				if (board.getSquares()[col][row].isOccupied()) {

					Label squareTile = new Label("    " + (board.getSquare(col+1,row+1).getOccupier().print()));
					squareTile.prefHeightProperty().bind(boardPane.heightProperty().divide(8));
					squareTile.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
					// color
					squareTile.setBackground(new Background(new BackgroundFill(squareColor, null, Insets.EMPTY)));
					boardPane.add(squareTile, col, 9 - row);
					//map.put( squareTile,new Square(col, row));
					list.add(squareTile);
					map.put(squareTile, board.getSquare(col + 1, row + 1));
					mapTwo.put(board.getSquare(col + 1, row + 1),squareTile);
					// Add listener
					int finalCol = col;
					int finalRow = row;
					final Label temp = labels[col][row];

				} else {
					Label squareTile = new Label();
					squareTile.setContentDisplay(ContentDisplay.CENTER);
					squareTile.prefHeightProperty().bind(boardPane.heightProperty().divide(8));
					squareTile.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
					squareTile.setBackground(new Background(new BackgroundFill(squareColor, null, Insets.EMPTY)));
					boardPane.add(squareTile, col, 9-row);
					int finalCol = col;
					int finalRow = row;
					GridPane.setFillWidth(squareTile,true);
					GridPane.setFillHeight(squareTile, true);
					map.put( squareTile,board.getSquare(col + 1,  row + 1));
					mapTwo.put(board.getSquare(col + 1, row + 1), squareTile);
					list.add(squareTile);


				}
			}
		}
	}



	private void reset() {
		Color squareColor;

		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {
				if ((row + col) % 2 == 0) {
					squareColor = Color.BLACK;
				} else {
					squareColor = Color.GREY;
				}
				if (board.getSquares()[col][row].isOccupied()) {
					Label squareTile = new Label("     " + (board.getSquare(col+1,row+1).getOccupier().print()));
					squareTile.prefHeightProperty().bind(boardPane.heightProperty().divide(8));
					squareTile.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
					// color
					squareTile.setBackground(new Background(new BackgroundFill(squareColor, null, Insets.EMPTY)));
					boardPane.add(squareTile, col, 9 - row);
					list.add(squareTile);
                    map.put(squareTile, board.getSquare(col + 1,7 - row + 1));
					mapTwo.put(board.getSquare(col + 1,7 - row + 1),squareTile);
					// Add listener
					int finalCol = col;
					int finalRow = row;
					final Label temp = labels[col][row];
					squareTile.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							lastClickedOn = squareTile;
							Square ts = board.getSquares()[finalCol][7 - finalRow];
							final Piece occupier = ts.getOccupier();
							final List<Square> legalSquareList = occupier.filteredLegals();
							//Highlight legal moves
							ArrayList<Label> legalLabelList = getLegalLabelList(legalSquareList);
							//colorizeLegalNextSquares(squareTile, legalLabelList);
							Square place = (Square) map.get(squareTile);
                            System.out.println(squareTile);
                            System.out.println(place);
                            //White movement
                            if(place.isOccupied()&& place.getOccupier().isWhite() && currentMove% 2 == 0){
                            }
                            //Black movement
                            if(place.isOccupied() &&! place.getOccupier().isWhite() && currentMove% 2 != 0){
                            }

						}
					});
				} else {
					Label squareTile = new Label();
					squareTile.setContentDisplay(ContentDisplay.CENTER);
					squareTile.prefHeightProperty().bind(boardPane.heightProperty().divide(8));
					squareTile.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
					squareTile.setBackground(new Background(new BackgroundFill(squareColor, null, Insets.EMPTY)));
					boardPane.add(squareTile, col, 9-row);
					int finalCol = col;
					int finalRow = row;
					GridPane.setFillWidth(squareTile,true);
					GridPane.setFillHeight(squareTile, true);
					squareTile.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							lastClickedOn = squareTile;
							Square ts = board.getSquares()[finalCol][7 - finalRow];
							squareTile.setBackground(new Background(new BackgroundFill(Color.RED, null, Insets.EMPTY)));
                            Square shit = (Square) map.get(squareTile);
                            System.out.println(squareTile);
                            System.out.println(shit);
                            if(shit.isOccupied()) {
                                System.out.println("hallo" + shit.getOccupier());
                            }

						}
					});
					map.put( squareTile,board.getSquare(col + 1, 7 - row + 1));
					mapTwo.put(board.getSquare(col + 1, 7 - row + 1), squareTile);
					list.add(squareTile);


				}
			}
		}
	}


	private void updateLastMove(){
		TableView table = new TableView();
		TableColumn playerColum = new TableColumn("Player");
		TableColumn move = new TableColumn("Move");
//		move.setMinWidth(125);
		playerColum.setMinWidth(125);
		move.setMinWidth(125);
		table.getColumns().addAll(playerColum,move);
		table.getItems().addAll(new Label("White"), new Label("e2-e3"));
		this.lastMobeTableBox.getChildren().add(table);
	}

	/**
	 * Returns an ArrayList with all Labels of a givent square List
	 * @param squareList List of squares
	 * @return List of Squares filled with an emty string if its not occupied or with the occupier that is printed
	 * on the board
	 */
	public ArrayList<Label> getLegalLabelList(List<Square> squareList) {
		ArrayList<Label> labelList = new ArrayList<Label>();

		for (int i = 0; i < squareList.size(); i++) {
			Square s = squareList.get(i);
			labelList.add(mapTwo.get(s));
		}
		return labelList;
	}

	/**
	 * This method will color the actually clicked Square to red and the legal next squares to green
	 * @param clicked Label that is clicked
	 * @param nextSquare List of labels that are legal next squares
	 */
	private void colorizeLegalNextSquares(Label clicked, List<Square> nextSquare) {
		Color clickedColor = Color.RED;
		Color nextColor = Color.GREEN;
		clicked.setBackground(new Background(new BackgroundFill(clickedColor, null, Insets.EMPTY)));
		List <Label> nextLabels = new ArrayList<>();
		for (Square square: nextSquare){
			nextLabels.add(mapTwo.get(square));
		}
		for (int i = 0; i < nextLabels.size(); i++) {
			nextLabels.get(i).setBackground(new Background(new BackgroundFill(nextColor, null, Insets.EMPTY)));
		}
	}

	/**
	 * Method to decolorize all labels on the board
	 */
	private void resetColorizedLabels() {
		Color squareColor;

		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {
				if ((row + col) % 2 == 0) {
					squareColor = Color.BLACK;
				} else {
					squareColor = Color.GREY;
				}

				if (board.getSquares()[col][row].isOccupied()) {

					Label squareTile = new Label("     " + (board.getSquares()[col][row].getOccupier().print()));
					squareTile.prefHeightProperty().bind(boardPane.heightProperty().divide(8));
					squareTile.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
					// color
					squareTile.setBackground(new Background(new BackgroundFill(squareColor, null, Insets.EMPTY)));
					boardPane.add(squareTile, col, row);

					map.put(squareTile, board.getSquare(col + 1,7 - row + 1));
					mapTwo.put(board.getSquare(col + 1,7 - row + 1),squareTile);
					list.add(squareTile);
				} else {
					Label squareTile = new Label();
					squareTile.setContentDisplay(ContentDisplay.CENTER);
					squareTile.prefHeightProperty().bind(boardPane.heightProperty().divide(8));
					squareTile.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
					squareTile.setBackground(new Background(new BackgroundFill(squareColor, null, Insets.EMPTY)));
					boardPane.add(squareTile, col, row);
					GridPane.setFillWidth(squareTile,true);
					GridPane.setFillHeight(squareTile, true);

					map.put( squareTile,board.getSquare(col + 1, 7 - row + 1));
					mapTwo.put(board.getSquare(col + 1, 7 - row + 1), squareTile);

				}
			}
		}
	}

	/**
	 *
	 * @param title Title of the alert window
	 * @param text Messagetext of the alert window
	 * @param withExitButton Boolean if alert window should have an exit button
	 */
	public void showAlert(String title, String text, boolean withExitButton) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(text);

		ButtonType buttonOK = new ButtonType("Ok");

		alert.showAndWait().ifPresent(res -> {
			if (res == buttonOK) {
				System.out.println("Ok.");
			}
			if (withExitButton) {
				ButtonType buttonExit = new ButtonType("Exit");
				if (res == buttonExit) {
					System.out.println("Exit");
					Platform.exit();
					System.exit(0);
				}
			}
		});
	}
}