package schach.view;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.ArrayList;

/**
 * This class is the controller class for the chess-clock
 */
public class ClockController {

    private int timeWhite;
    private int timeBlack;

    private boolean whitesTurn;
    private boolean blacksTurn;

    @FXML
    private Text timerTextWhite;
    private Text timerTextBlack;

    /**
     * Constructor of the ClockController
     * @param basePane Pane where the clock is placed
     */
    public ClockController(Pane basePane) {
        this.timerTextWhite = (Text) basePane.lookup("#whiteTimeChessClock");
        this.timerTextBlack = (Text) basePane.lookup("#blackTimeChessClock");
        this.timeWhite = 899;
        this.timeBlack = 899;
        this.whitesTurn = true;
        this.blacksTurn = false;
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
     * This Method is the countdown routine. If its whites turn
     * it should count down the timeWhite and the other way round
     */
    public void countDownRoutine() {
        if(whitesTurn) {
            Thread delayThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(timeWhite >= 0 && whitesTurn) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        timerTextWhite.setText(convertToString(timeWhite));
                        timeWhite--;
                        if(timeWhite == 0) {
                            handleGameOver();
                        }
                    }
                }
            });
            delayThread.start();

        } else if (blacksTurn) {
            Thread delayThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(timeBlack >= 0 && blacksTurn) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        timerTextBlack.setText(convertToString(timeBlack));
                        timeBlack--;
                        if(timeBlack == 0) {
                            handleGameOver();
                        }
                    }
                }
            });
            delayThread.start();
        }
    }

    /**
     * This Method calls the game-over if one player runs out of time
     */
    public void handleGameOver() {
        if (timeWhite <= 0) {
            // TODO
            System.out.println("Black wins the game!");
        } else if (timeBlack <= 0) {
            //TODO
            System.out.println("White wins the game!");
        }
    }

    /**
     * This method is used to convert the raw left time of a player to a nicer string.
     * Example: 598 --> 9:58
     * @param rawTimeLeft Raw input time that is left in seconds
     * @return String of left time with a colon between minutes and seconds
     */
    public String convertToString(int rawTimeLeft) {
        ArrayList<String> timeArray = new ArrayList<String>();

        int minutesLeft = rawTimeLeft / 60;
        int secondsLeft = rawTimeLeft % 60;

        timeArray.add(String.format("%02d", minutesLeft));
        timeArray.add(String.format("%02d", secondsLeft));

        return timeArray.get(0) + ":" + timeArray.get(1);
    }
}
