package schach.view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import schach.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoardController {

    private Pane container;
    private GridPane gridPane;
    private boolean vsPlayer;
    private boolean playerIsWhite;
    private boolean simpleAi;
    private Board board;
    private Map<StackPane, Square> paneToSquareMap = new HashMap<>();
    private Map<Square, StackPane> squareToPaneMap = new HashMap<>();
    private StackPane lastClickedPane;
    private boolean inMove;
    private Piece toBeMoved;
    private boolean whitesTurn;
    private boolean disabledMouseOnBoard;

    public ChessBoardController(Pane container){
        this.container = container;
        gridPane = (GridPane) container.lookup("#chessBoardGrid");
        this.board = new Board();
        board.initLineUp();
        this.inMove = false;
        this.whitesTurn = true;
        this.disabledMouseOnBoard = false;
        initHashMap();
        printBoard();
        initEventHandler();
    }

    public void initGameMode(boolean vsPlayer, boolean playerIsWhite, boolean simpleAi){
        this.vsPlayer = vsPlayer;
        this.playerIsWhite = playerIsWhite;
        this.simpleAi = simpleAi;
    }

    private void initHashMap(){
        for (Node node: gridPane.getChildren()){
            if (node instanceof StackPane){
                StackPane pane = (StackPane) node;
                int col = GridPane.getColumnIndex(node);
                Square square = board.getSquare(GridPane.getColumnIndex(node) + 1, 8 - GridPane.getRowIndex(node));
                paneToSquareMap.put(pane, square);
            }
        }
        for (Map.Entry<StackPane, Square> entry: paneToSquareMap.entrySet()){
            squareToPaneMap.put(entry.getValue(), entry.getKey());
        }
    }

    private void initEventHandler(){
        for (Node node: gridPane.getChildren()){
            if (node instanceof StackPane){
                node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!disabledMouseOnBoard){
                            lastClickedPane = (StackPane) node;
                            if (inMove){
                                checkForMove();
                            } else {
                                paneClicked((StackPane) node);
                            }
                        }
                    }
                });
            }
        }

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

    private void paneClicked(StackPane pane){
        resetBackground();
        pane.setStyle("-fx-background-color: blue;");
        Piece piece;
        Square square = paneToSquareMap.get(pane);
        if (square.isOccupied()){
            piece = square.getOccupier();
            if (correctTurn(piece)){
                colorLegals(piece);
                inMove = true;
                toBeMoved = piece;
            }
        }
    }

    private void checkForMove(){
        toBeMoved.updateLegals();
        List<Square> legals = toBeMoved.filteredLegals();
        if (legals.contains(paneToSquareMap.get(lastClickedPane))){
            move();
        } else {
            paneClicked(lastClickedPane);
        }
    }

    private void move(){
        Square start = toBeMoved.getPosition();
        Square target = paneToSquareMap.get(lastClickedPane);
        board.movePiece(start.getDenotation(), target.getDenotation());
        resetBackground();
        if (isPromotion(toBeMoved)){
            showPromotion(toBeMoved.isWhite());
        }
        printBoard();
        inMove = false;
        if (board.getCheck().isCheckMate(!whitesTurn)){
            gameOver();
        }
        whitesTurn = !whitesTurn;
    }

    private boolean isPromotion(Piece piece){
        if (!(piece instanceof Pawn)){
            return false;
        }
        Pawn pawn = (Pawn) piece;
        int finalRow = 8;
        if (!pawn.isWhite()){
            finalRow = 1;
        }
        if (pawn.getPosition().getRow() != finalRow){
            return false;
        }
        return true;
    }

    private void showPromotion(boolean whiteProm){
        disabledMouseOnBoard = true;
        squareToPaneMap.get(toBeMoved.getPosition()).setStyle("-fx-background-color: green;");
        GridPane promGrid = (GridPane) container.lookup("#promWhiteGrid");
        if (!whiteProm){
            promGrid = (GridPane) container.lookup("#promBlackGrid");
        }
        promGrid.setVisible(true);
    }

    private void doPromotion(StackPane pane){
        pane.setStyle("-fx-background-color: green;");
        ImageView imageView = null;
        for (Node node: pane.getChildren()){
            if (node instanceof ImageView){
                imageView = (ImageView) node;
            }
        }
        Image image = imageView.getImage();
        String imgName = image.getUrl().substring(112);
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

    private void printBoard(){
        for (Node node: gridPane.getChildren()){
            if (node instanceof StackPane){
                StackPane pane = (StackPane) node;
                pane.getChildren().clear();
            }
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

    private void resetBackground(){
        for (Node node: gridPane.getChildren()){
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

    private void colorLegals(Piece piece){
        piece.updateLegals();
        List<Square> legals = piece.filteredLegals();
        for (Square square: legals){
            if (piece instanceof Pawn && square.getColumn() != piece.getPosition().getColumn() && !square.isOccupied()){
                squareToPaneMap.get(board.getLastMoved().getPosition()).setStyle("-fx-background-color: red;");
                squareToPaneMap.get(square).setStyle("-fx-background-color: green;");
            } else if (square.isOccupied()){
                squareToPaneMap.get(square).setStyle("-fx-background-color: red;");
            } else {
                squareToPaneMap.get(square).setStyle("-fx-background-color: green;");
            }
        }
    }

    private boolean correctTurn(Piece clickedPiece){
        return clickedPiece.isWhite() == whitesTurn;
    }

    private void gameOver(){
        Pane overlay = (Pane) this.container.lookup("#gameOverOverlay");
        overlay.setVisible(true);
    }

    private void placeImageOnPane(String unicode, StackPane pane){
        Image img = unicodeToImage(unicode);
        ImageView imageView = new ImageView(img);
        pane.getChildren().add(imageView);
    }

    private Image unicodeToImage(String unicode){
        String path = unicodeToPath(unicode);
        return new Image(path);
    }

    private String unicodeToPath(String unicode){
        switch (unicode){
            case "\u2654":
                return "assets/whiteKing.png";
            case "\u265A":
                return "assets/blackKing.png";
            case "\u2655":
                return "assets/whiteQueen.png";
            case "\u265B":
                return "assets/blackQueen.png";
            case "\u2656":
                return "assets/whiteRook.png";
            case "\u265C":
                return "assets/blackRook.png";
            case "\u2657":
                return "assets/whiteBishop.png";
            case "\u265D":
                return "assets/blackBishop.png";
            case "\u2658":
                return "assets/whiteKnight.png";
            case "\u265E":
                return "assets/blackKnight.png";
            case "\u2659":
                return "assets/whitePawn.png";
            case "\u265F":
                return "assets/blackPawn.png";
        }
        return null;
    }

}
