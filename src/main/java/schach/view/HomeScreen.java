package schach.view;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.LabeledSkinBase;
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
	private VBox content;

	Boolean canMove;
	private Board board;
	private final Label[][] labels = new Label[8][8];
	private final Map<Label, Square> labelMap = new HashMap<Label, Square>();

	public HomeScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			this.getStylesheets().add(HomeScreen.class.getResource("root.css").toExternalForm());
			startGame();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	private void showDialogHuman() {
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
		// Convert the result to a username-password-pair when the login button is clicked.
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
			startGame();
		});
	}

	@FXML
	private void showDialogCPU() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("New Game");
		dialog.setHeaderText("Add Player");
		dialog.getDialogPane().setStyle("-fx-background-color: #feffef;");
		// Set the icon (must be included in the project).
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
			playerOneName = username.getKey();
			RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
			playerColor = selectedRadioButton.getText();
			this.multiplayer = false;
			startGame();
		});
	}

	private void startGame() {
		this.board = new Board();
		board.initMatrix();
		board.initLineUp();
		board.updateAllLegalSquares();
		HBox boardBox = new HBox();
		this.boardPane = new GridPane();
		this.boardPane.setPrefSize(600, 600);
		reset();
		boardBox.getChildren().add(boardPane);
		content.setPrefSize(700, 500);
		/// Last Move section
		HBox lastMove = new HBox();
		lastMove.setPrefSize(100, 100);
		Label l = new Label("Last Move");
		lastMove.getChildren().add(l);
		content.getChildren().addAll(boardBox, lastMove);
	}

public ArrayList<Label> list = new ArrayList<Label>();
    private final Map<Label,Square> map = new HashMap<Label,Square>();
	private final Map<Square,Label> mapTwo = new HashMap<Square,Label>();
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

					Label squareTile = new Label("     " + (board.getSquares()[col][row].getOccupier().print()));
					squareTile.prefHeightProperty().bind(boardPane.heightProperty().divide(8));
					squareTile.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
					// color
					squareTile.setBackground(new Background(new BackgroundFill(squareColor, null, Insets.EMPTY)));
					boardPane.add(squareTile, col, row);
					//map.put( squareTile,new Square(col, row));
                    map.put(squareTile, board.getSquare(col + 1,7 - row + 1));
					mapTwo.put(board.getSquare(col + 1,7 - row + 1),squareTile);
					// Add listener
					int finalCol = col;
					int finalRow = row;
					final Label temp = labels[col][row];
					list.add(squareTile);
					squareTile.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							Square ts = board.getSquares()[finalCol][7 - finalRow];
							final Piece occupier = ts.getOccupier();

							final List<Square> legalSquareList = occupier.filteredLegals();
							ArrayList<Label> legalLabelList = getLegalLabelList(legalSquareList);
							colorizeLegalNextSquares(squareTile, legalLabelList);

                            Square shit = (Square) map.get(squareTile);
                            System.out.println(squareTile);
                            System.out.println(shit);
                            if(shit.isOccupied()) {
                                System.out.println("hallo" + shit.getOccupier().print() + shit.getOccupier().isWhite());
                            }
							//Highlight possible moves if turned on
							for (int i = 0; i < occupier.filteredLegals().size(); i++) {
								System.out.println("can move to:" + occupier.filteredLegals().get(i).getColumn() + ", " + occupier.filteredLegals().get(i).getRow());
							}
						}
					});
				} else {
					Label temp = labels[col][row];
					Label squareTile = new Label();
					squareTile.setContentDisplay(ContentDisplay.CENTER);
					squareTile.prefHeightProperty().bind(boardPane.heightProperty().divide(8));
					squareTile.prefWidthProperty().bind(boardPane.widthProperty().divide(8));
					squareTile.setBackground(new Background(new BackgroundFill(squareColor, null, Insets.EMPTY)));
					boardPane.add(squareTile, col, row);
					int finalCol = col;
					int finalRow = row;
                    GridPane.setFillWidth(squareTile,true);
                    GridPane.setFillHeight(squareTile, true);
					squareTile.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							Square ts = board.getSquares()[finalCol][7 - finalRow];
							//System.out.println(ts);
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

				}
			}
		}
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
	 * @param nextLabels List of labels that are legal next squares
	 */
	private void colorizeLegalNextSquares(Label clicked, ArrayList<Label> nextLabels) {
		Color clickedColor = Color.RED;
		Color nextColor = Color.GREEN;
		clicked.setBackground(new Background(new BackgroundFill(clickedColor, null, Insets.EMPTY)));
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
}
