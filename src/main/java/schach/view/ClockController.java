package schach.view;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClockController {

    private int timeWhite;
    private int timeBlack;

    private boolean whitesTurn;
    private boolean blacksTurn;
    private Thread pauseThread;

    /**
     * Constructor of the ClockController
     * @param basePane Pane where the clock is placed
     */
    public ClockController(Pane basePane) {
        GridPane clockPane = (GridPane) basePane.lookup("#chessClockBasePane");
        this.timeWhite = 600;
        this.timeBlack = 600;
        this.whitesTurn = true;
        this.blacksTurn = false;
        this.pauseThread = new Thread();
    }

    /**
     * Starts the countdown of chess clock for the first player (white)
     */
    public void start() {
        System.out.println("Clock started!");
        countDownRoutine();
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
            Thread delayThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(timeWhite != 0 && whitesTurn) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(convertToString(timeWhite));
                        timeWhite--;
                    }
                }
            });
            delayThread.start();

        } else if (blacksTurn) {
            Thread delayThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(timeBlack != 0 && blacksTurn) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(convertToString(timeBlack));
                        timeBlack--;
                    }
                }
            });
            delayThread.start();
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
