package schach.view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.time.Clock;

public class ClockController {

    private final GridPane gridPane;

    private int timeWhite;
    private int timeBlack;

    private boolean whitesTurn;
    private boolean blacksTurn;

    public ClockController(Pane basePane) {
        this.gridPane = (GridPane) basePane.lookup("#chessClockBasePane");
        this.timeWhite = 600;
        this.timeBlack = 600;
        this.whitesTurn = false;
        this.blacksTurn = false;
        toggleWhitesTurn();
    }

    public void toggleWhitesTurn() {
        whitesTurn = true;
        blacksTurn = false;
        countDownRoutine();
    }

    public void toggleBlacksTurn() {
        blacksTurn = true;
        whitesTurn = false;
        countDownRoutine();
    }

    public int getTimeWhite() {
        return timeWhite;
    }

    public int getTimeBlack() {
        return timeBlack;
    }

    public void countDownRoutine() {
        if(whitesTurn) {
            while(timeWhite != 0 && whitesTurn) {
                try {
                    this.timeWhite--;
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (blacksTurn) {
            while(timeBlack != 0 && blacksTurn) {
                try {
                    this.timeBlack--;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
