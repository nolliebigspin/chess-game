package schach.view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ClockController {

    private final GridPane gridPane;

    private int timeWhite;
    private int timeBlack;

    private boolean whitesTurn;
    private boolean blacksTurn;

    /**
     * Constructor of the ClockController
     * @param basePane Pane where the clock is placed
     */
    public ClockController(Pane basePane) {
        this.gridPane = (GridPane) basePane.lookup("#chessClockBasePane");
        this.timeWhite = 600;
        this.timeBlack = 600;
        this.whitesTurn = false;
        this.blacksTurn = false;
        setWhitesTurn();
    }


    /**
     * Sets the turn to whites turn and starts the countdown-routine
     */
    public void setWhitesTurn() {
        whitesTurn = true;
        blacksTurn = false;
        countDownRoutine();
    }

    /**
     * Sets the turn to blacks turn and starts the countdown-routine
     */
    public void setBlacksTurn() {
        blacksTurn = true;
        whitesTurn = false;
        countDownRoutine();
    }

    /**
     * Getter for whites rest time
     * @return the left time of the white side
     */
    public int getTimeWhite() {
        return timeWhite;
    }

    /**
     * Getter for blacks rest time
     * @return the left time of the black side
     */
    public int getTimeBlack() {
        return timeBlack;
    }

    /**
     * This Method is the countdown routine. If its whites turn
     * it should count down the timeWhite and the other way round
     */
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

    /**
     * This method is used to convert the raw left time of a player to a nicer string.
     * Example: 598 --> 9:58
     * @param rawTimeLeft Raw input time that is left in seconds
     * @return String of left time with a colon between minutes and seconds
     */
    public String convertToString(int rawTimeLeft) {
        ArrayList<Integer> timeArray = new ArrayList<Integer>();

        int minutesLeft = rawTimeLeft / 60;
        int secondsLeft = rawTimeLeft % 60;

        timeArray.add(minutesLeft);
        timeArray.add(secondsLeft);

        return timeArray.get(0) + ":" + timeArray.get(1);
    }
}
