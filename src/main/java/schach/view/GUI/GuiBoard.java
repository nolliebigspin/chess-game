//package schach.view.GUI;

//public class GuiBoard {
//}
package schach.view;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.control.Control;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
public class GuiBoard extends Application  {
    GridPane root = new GridPane();
    final int size = 8;

    public void start(Stage primaryStage) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Rectangle square = new Rectangle();
                Color color;
                if ((row + col) % 2 == 0) color = Color.WHITE;
                else color = Color.BLACK;
                square.setFill(color);
                root.add(square, col, row);
                square.widthProperty().bind(root.widthProperty().divide(size));
                square.heightProperty().bind(root.heightProperty().divide(size));
            }
        }
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}