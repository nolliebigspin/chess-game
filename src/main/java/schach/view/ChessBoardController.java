package schach.view;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import schach.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ChessBoardController {

    /**
     * the Pane containing the BoardGrid and other needed FXML objects
     */
    protected Pane container;

    /**
     * the GridPane representing the chess board, containing and arranging the StackPanes representing the Squares
     */
    private GridPane boardGridPane;

    /**
     * the Board containing the logic
     */
    protected Board board;

    /**
     * HashMap assigning every Square to a StackPane
     */
    protected Map<StackPane, Square> paneToSquareMap = new HashMap<>();

    /**
     * HashMap assigning every StackPane to a Square
     */
    protected Map<Square, StackPane> squareToPaneMap = new HashMap<>();

    /**
     * boolean determining if a valid Piece was already clicked, so the next click will be a move
     */
    protected boolean inMove;

    /**
     * Valid Piece that was last clicked and can be moved
     */
    protected Piece toBeMoved;

    /**
     * boolean determining whose turn it is
     */
    protected boolean whitesTurn;

    /**
     * boolean determining if clicks on the BoardGrid will be ignored
     */
    protected boolean disabledMouseOnBoard;

    /**
     * String to save space, PMD forced me to do that :/
     */
    protected String backgroundGreen = "-fx-background-color: green;";

    protected boolean rotate;
    protected boolean possibleMoves = true;
    protected boolean showIsCheck = true;
    protected boolean multipleSelect = true;
    Text checkPane;

    /**
     * Constructor initializing fields, maps and event handler
     * @param container the Pane that contains the Chessboard and all belonging Panes
     */
    public ChessBoardController(Pane container){
        this.container = container;
        boardGridPane = (GridPane) container.lookup("#chessBoardGrid");
        this.board = new Board();
        board.initLineUp();
        this.inMove = false;
        this.whitesTurn = true;
        this.disabledMouseOnBoard = false;
        this.rotate = true;
        initHashMap();
        printBoard();
        initEventHandler();
        Node parentCheck = container.getParent();
        checkPane = (Text) (parentCheck.lookup("#checkWarning"));
    }

    /**
     * Setter to toggle if possible moves are shown or not
     * @param newValue new bool value if possible moves should be shown or not
     */
    public void setPossibleMoves(boolean newValue) {
        this.possibleMoves = newValue;
    }

    /**
     * Sets the information to visible if a playyer is in check
     * @param newBool boolean if any player is in check
     */
    public void setShowIsCheck(boolean newBool) {
        this.showIsCheck = newBool;
    }

    public void setMultipleSelect(boolean multipleSelect){
        this.multipleSelect = multipleSelect;
        resetBackground();
        inMove = false;
    }

    /**
     * initializes the two hashmaps assigning Squares to StackPane and vice versa
     */
    private void initHashMap(){
        for (Node node: boardGridPane.getChildren()){
            if (node instanceof StackPane){
                StackPane pane = (StackPane) node;
                Square square = board.getSquare(GridPane.getColumnIndex(node) + 1, 8 - GridPane.getRowIndex(node));
                paneToSquareMap.put(pane, square);
            }
        }
        for (Map.Entry<StackPane, Square> entry: paneToSquareMap.entrySet()){
            squareToPaneMap.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * initializes the event handler for the stackpanes on the board and for promotion grids
     */
    private void initEventHandler(){
        //Eventhandler for every stackpane representing the squares
        for (Node node: boardGridPane.getChildren()){
            if (node instanceof StackPane){
                node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!disabledMouseOnBoard){
                            if (inMove){
                                checkForMove((StackPane) node);
                            } else {
                                paneClicked((StackPane) node);
                            }
                        }
                    }
                });
            }
        }

        //Eventhandler for all StackPanes in the PromotionGrids
        GridPane promWhiteGrid = (GridPane) container.lookup("#promWhiteGrid");
        GridPane promBlackGrid = (GridPane) container.lookup("#promBlackGrid");
        List<Node> promGridChildren = new ArrayList<>();
        promGridChildren.addAll(promWhiteGrid.getChildren());
        promGridChildren.addAll(promBlackGrid.getChildren());
        for (Node node: promGridChildren){
            if (node instanceof StackPane){
                node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        doPromotion((StackPane) node);
                    }
                });
            }
        }

    }

    /**
     * Called if a Pane was clicked and there is not valid piece chosen
     * Colors the pane and checks if there is a valid piece on the pane
     * @param pane the pane that was clicked on
     */
    private void paneClicked(StackPane pane){
        resetBackground();
        pane.setStyle("-fx-background-color: blue;");
        Piece piece;
        Square square = paneToSquareMap.get(pane);
        if (square.isOccupied()){
            piece = square.getOccupier();
            if (correctTurn(piece)){
                if (possibleMoves) {
                    colorLegals(piece);
                }
                inMove = true;
                toBeMoved = piece;
            }
        }
    }

    /**
     * Called if a Pane was clicked and there is a valid piece chosen: inMove
     * Checks if the last clicked pane represents a legal square the toBeMoved piece can be moved to
     * if so calls move method, otherwise calls paneClicked method
     */
    private void checkForMove(StackPane lastClickedPane){
        toBeMoved.updateLegals();
        List<Square> legals = toBeMoved.filteredLegals();
        if (legals.contains(paneToSquareMap.get(lastClickedPane))){
            move(lastClickedPane);
        } else if (multipleSelect) {
            paneClicked(lastClickedPane);
        }
    }

    /**
     * calls the board.movePiece method and calls the print method
     * checks for promotion and checkmate
     */
    protected abstract void move(StackPane lastClickedPane);

    /**
     * Checks if promotion is valid
     * @param piece the promotion should be checked for
     * @return true if promotion will executed, false if no promotion possible
     */
    protected boolean isPromotion(Piece piece){
        if (!(piece instanceof Pawn)){
            return false;
        }
        Pawn pawn = (Pawn) piece;
        int finalRow = 8;
        if (!pawn.isWhite()){
            finalRow = 1;
        }
        return pawn.getPosition().getRow() == finalRow;
    }

    /**
     * displays the PromotionGrid and disables the mouse events on the board grid
     * @param whiteProm true if promotion for white, false if promotion for black
     */
    protected void showPromotion(boolean whiteProm){
        disabledMouseOnBoard = true;
        squareToPaneMap.get(toBeMoved.getPosition()).setStyle(backgroundGreen);
        GridPane promGrid = (GridPane) container.lookup("#promWhiteGrid");
        if (!whiteProm){
            promGrid = (GridPane) container.lookup("#promBlackGrid");
        }
        promGrid.setVisible(true);
    }

    /**
     * called if mouse clicked on pane in the promotion grid
     * Executes the promotion by getting the pathname of the image displayed in the pane and converting it to a
     * pawn.doPromotion call
     * @param pane the Pane in a Promotion grid clicked on
     */
    protected void doPromotion(StackPane pane){
        ImageView imageView = null;
        for (Node node: pane.getChildren()){
            if (node instanceof ImageView){
                imageView = (ImageView) node;
            }
        }
        Image image = imageView.getImage();
        String[] urls = image.getUrl().split("/");
        String imgName = urls[urls.length - 1];
        String prom = imgName.substring(5, 6);
        if (prom.equals("K")){
            prom = "N";
        }
        Pawn pawn = (Pawn) toBeMoved;
        pawn.doPromotion(prom);

        pane.setStyle("-fx-background-color: grey;");
        if (!pawn.isWhite()){
            pane.setStyle("-fx-background-color: white;");
        }
        pane.getParent().setVisible(false);
        disabledMouseOnBoard = false;
        printBoard();
    }

    /**
     * 'Draws' the Chess Board by getting the positioning of the board, iterating over all active piece and
     * displaying the belonging image in the belonging StackPane
     */
    protected void printBoard(){
        for (Node node: boardGridPane.getChildren()){
            if (node instanceof StackPane){
                StackPane pane = (StackPane) node;
                pane.getChildren().clear();
            }
        }
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(board.allActivePieces(true));
        allPieces.addAll(board.allActivePieces(false));
        for (Piece piece: allPieces){

        }
        Positioning positioning = new Positioning(board);
        positioning.readPositioning();
        Map<Square, String> pos = positioning.getPositioningMap();
        for (Map.Entry<Square, String> entry: pos.entrySet()){
            String unicode = entry.getValue();
            Square square = entry.getKey();
            StackPane pane = squareToPaneMap.get(square);
            placeImageOnPane(unicode, pane);
        }
    }

    /**
     * Places image of a wanted piece on a StackPane via a ImageView
     * @param unicode the unicode of the piece that should be displayed as a image
     * @param pane the pane the image will be place on
     */
    private void placeImageOnPane(String unicode, StackPane pane){
        Image img = unicodeToImage(unicode);
        ImageView imageView = new ImageView(img);
        imageView.setRotate(boardGridPane.getRotate());
        pane.getChildren().add(imageView);
    }

    /**
     * 'Converts' a unicode of a piece to the belonging image
     * @param unicode the unicode representing the piece
     * @return image displaying the piece
     */
    private Image unicodeToImage(String unicode){
        String path = null;
        switch (unicode){
            case "\u2654":
                path = "assets/whiteKing.png";
                break;
            case "\u265A":
                path = "assets/blackKing.png";
                break;
            case "\u2655":
                path = "assets/whiteQueen.png";
                break;
            case "\u265B":
                path = "assets/blackQueen.png";
                break;
            case "\u2656":
                path = "assets/whiteRook.png";
                break;
            case "\u265C":
                path = "assets/blackRook.png";
                break;
            case "\u2657":
                path = "assets/whiteBishop.png";
                break;
            case "\u265D":
                path = "assets/blackBishop.png";
                break;
            case "\u2658":
                path = "assets/whiteKnight.png";
                break;
            case "\u265E":
                path = "assets/blackKnight.png";
                break;
            case "\u2659":
                path = "assets/whitePawn.png";
                break;
            case "\u265F":
                path = "assets/blackPawn.png";
                break;
        }
        return new Image(path);
    }

    /**
     * Resets the background color of all StackPanes in the BoardGrid to grey/white
     */
    protected void resetBackground(){
        for (Node node: boardGridPane.getChildren()){
            if (node instanceof StackPane){
                int col = GridPane.getColumnIndex(node);
                int row = GridPane.getRowIndex(node);
                if (col % 2 == row % 2){
                    node.setStyle("-fx-background-color: white;");
                } else {
                    node.setStyle("-fx-background-color: grey;");
                }
            }
        }
    }

    /**
     * Colors the legalSquares of a given piece
     * @param piece Piece the legal squares should be colored
     */
    private void colorLegals(Piece piece){
        piece.updateLegals();
        List<Square> legals = piece.filteredLegals();
        for (Square square: legals){
            if (piece instanceof Pawn && square.getColumn() != piece.getPosition().getColumn() && !square.isOccupied()){
                squareToPaneMap.get(board.getLastMoved().getPosition()).setStyle("-fx-background-color: red;");
                squareToPaneMap.get(square).setStyle(backgroundGreen);
            } else if (square.isOccupied()){
                squareToPaneMap.get(square).setStyle("-fx-background-color: red;");
            } else {
                squareToPaneMap.get(square).setStyle(backgroundGreen);
            }
        }
    }

    public void rotateGame(){
        if (!rotate){
            return;
        }
        Thread rotationThread = new Thread(new Runnable() {
            Duration duration = Duration.millis(500);
            @Override
            public void run() {
                disabledMouseOnBoard = true;
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ScaleTransition downsizeTransition = new ScaleTransition(duration, boardGridPane);
                        ScaleTransition enlargeTransition = new ScaleTransition(duration, boardGridPane);
                        RotateTransition rotateTransition = new RotateTransition(duration, boardGridPane);
                        downsizeTransition.setByX(-0.2);
                        downsizeTransition.setByY(-0.2);
                        enlargeTransition.setByX(0.2);
                        enlargeTransition.setByY(0.2);
                        rotateTransition.setByAngle(180);
                        List<RotateTransition> imageTransitions = new ArrayList<>();
                        List<ImageView> imageViews = new ArrayList<>();
                        for (Node node: boardGridPane.getChildren()){
                            if (node instanceof StackPane){
                                StackPane stackPane = (StackPane) node;
                                for (Node innerNode: stackPane.getChildren()){
                                    if (innerNode instanceof ImageView){
                                        ImageView view = (ImageView) innerNode;
                                        imageViews.add(view);
                                    }
                                }
                            }
                        }
                        for (ImageView imageView: imageViews){
                            RotateTransition imgRoation = new RotateTransition(duration, imageView);
                            imgRoation.setByAngle(180);
                            imageTransitions.add(imgRoation);
                        }
                        
                        downsizeTransition.play();
                        downsizeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                rotateTransition.play();
                                for (RotateTransition transition: imageTransitions){
                                    transition.play();
                                }
                            }
                        });
                        rotateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                enlargeTransition.play();
                            }
                        });
                        enlargeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                disabledMouseOnBoard = false;
                            }
                        });
                    }
                });

            }
        });
        rotationThread.start();
    }

    /**
     * checks if its the right turn
     * @param clickedPiece piece that was clicked on
     * @return true if clicked piece can be moved, false if its not the turn of the color of the piece
     */
    protected boolean correctTurn(Piece clickedPiece){
        return clickedPiece.isWhite() == whitesTurn;
    }

    /**
     * Called if Checkmate
     * displays overlay with game over message
     */
    protected void gameOver(){
        Pane overlay = (Pane) this.container.lookup("#gameOverOverlay");
        overlay.setVisible(true);
    }

    public void setRotate(Boolean rotate){
        this.rotate = rotate;
        if (!rotate){
            boardGridPane.setRotate(0);
            for (Node node: boardGridPane.getChildren()){
                if (node instanceof StackPane){
                    StackPane stackPane = (StackPane) node;
                    for (Node innerNode: stackPane.getChildren()){
                        if (innerNode instanceof ImageView){
                            ImageView view = (ImageView) innerNode;
                            view.setRotate(0);
                        }
                    }
                }
            }
        } else if (!whitesTurn){
            boardGridPane.setRotate(180);
            for (Node node: boardGridPane.getChildren()){
                if (node instanceof StackPane){
                    StackPane stackPane = (StackPane) node;
                    for (Node innerNode: stackPane.getChildren()){
                        if (innerNode instanceof ImageView){
                            ImageView view = (ImageView) innerNode;
                            view.setRotate(180);
                        }
                    }
                }
            }
        }
    }
}
