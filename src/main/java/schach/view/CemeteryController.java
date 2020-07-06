package schach.view;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


import java.util.ArrayList;
import java.util.List;


/**
 * Class to control the cemetery output on gui
 */
public class CemeteryController {

    private final GridPane gridPane;

    private List<Label> deadPieces;

    /**
     * Constructor initializing the fields
     * @param containerPane
     */
    public CemeteryController(Pane containerPane){
        this.deadPieces = new ArrayList<>();
        this.gridPane = (GridPane) containerPane.lookup("#cemeteryGridPane");
        this.initialize();
    }

    private void initialize(){
        this.deadPieces = new ArrayList<>();
        for(int i =0; i<32; i++){
            Label l = new Label();
            l.setPrefHeight(30);
            l.setPrefWidth(95);
            l.setAlignment(Pos.CENTER);
            l.setDisable(true);
            this.deadPieces.add(l);
        }
        int c = 0;
        for(int i =0; i<4; i++){
            for(int j=0;j<8;j++){
                this.gridPane.add(this.deadPieces.get(c),j,i);
                c++;
            }
        }
    }

    /**
     * updates the cemetery output on the gui
     * @param chessBoardController
     */
    public void updateCemetery(ChessBoardController chessBoardController){
        for(int i = 0; i<chessBoardController.board.getCemetery().size();i++){
            Image image = chessBoardController.unicodeToImage(chessBoardController.board.getCemetery().get(i));
            ImageView im = new ImageView(image);
            im.setFitHeight(30);
            im.setFitWidth(50);
            this.deadPieces.get(i).setFont(new Font("Arial", 30));
            this.deadPieces.get(i).setStyle("-fx-border-style: solid;-fx-border-radius: 10;");
            if(chessBoardController.board.getCemetery().get(i).isWhite()){
                this.deadPieces.get(i).setStyle("-fx-background-color: black;-fx-text-fill: white;");

            }else{
                this.deadPieces.get(i).setStyle("-fx-background-color: white;-fx-text-fill: black;");
            }
            //this.deadPieces.get(i).setText(chessBoardController.board.getCemetery().get(i).print());
            this.deadPieces.get(i).setGraphic(im);
        }


    }

}