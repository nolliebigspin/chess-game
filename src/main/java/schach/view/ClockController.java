package schach.view;

public class ClockController {

    private int timeWhite = 600;
    private int timeBlack = 600;

    private boolean whitesTurn = false;
    private boolean blacksTurn = false;

    public void setWhiteTurn() {
        whitesTurn = true;
        blacksTurn = false;
    }

    public void setBlacksTurn() {
        blacksTurn = true;
        whitesTurn = false;
    }

    public int getTimeWhite() {
        return timeWhite;
    }

    public int getTimeBlack() {
        return timeBlack;
    }

    public void countDown() {
        if(whitesTurn) {
            while(timeWhite != 0 && whitesTurn) {
                try {
                    timeWhite--;
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (timeBlack != 0 && blacksTurn) {
            try {
                timeBlack--;
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
