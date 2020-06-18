package schach.view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import schach.model.Board;
import schach.model.Piece;
import schach.model.Positioning;
import schach.model.Square;

import java.util.HashMap;
import java.util.Map;

public class ChessBoardController {

    private Pane container;
    private GridPane gridPane;
    private Boolean vsPlayer;
    private Boolean playerIsWhite;
    private Boolean simpleAi;
    private Board board;
    private Map<StackPane, Square> paneToSquareMap = new HashMap<>();
    private Map<Square, StackPane> squareToPaneMap = new HashMap<>();
    private StackPane lastClickedPane;

    public ChessBoardController(Pane container){
        this.container = container;
        for (Node node: container.getChildren()){
            if (node instanceof GridPane){
                this.gridPane = (GridPane) node;
            }
        }
        this.board = new Board();
        board.initLineUp();
        initHashMap();
        printBoard();
        initEventHandler();
    }

    public void initGameMode(Boolean vsPlayer, Boolean playerIsWhite, Boolean simpleAi){
        this.vsPlayer = vsPlayer;
        this.playerIsWhite = playerIsWhite;
        this.simpleAi = simpleAi;
    }

    private void initHashMap(){
        for (Node node: gridPane.getChildren()){
            if (node instanceof StackPane){
                StackPane pane = (StackPane) node;
                int col = gridPane.getColumnIndex(node);
                Square square = board.getSquare(gridPane.getColumnIndex(node) + 1, 8 - gridPane.getRowIndex(node));
                paneToSquareMap.put(pane, square);
            }
        }
        for (Map.Entry<StackPane, Square> entry: paneToSquareMap.entrySet()){
            squareToPaneMap.put(entry.getValue(), entry.getKey());
        }
    }

    private void initEventHandler(){
        for (Node node: gridPane.getChildren()){
            node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lastClickedPane = (StackPane) node;
                    paneClicked((StackPane) node);
                }
            });
        }
    }

    private void paneClicked(StackPane pane){
        Piece piece;
        Square square = paneToSquareMap.get(pane);
        System.out.println(square.getDenotation());
        if (square.isOccupied()){
            piece = square.getOccupier();
        }
    }

    private void printBoard(){
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
