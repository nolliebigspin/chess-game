package schach.controller;

/**
 * Multiplayer Class
 * @author Odai
 *
 */
public class Multiplayer {

    /**
     * Multiplayer Mode: true/false
     * When is true: Human X Human
     * false: Human X CPU
     */
    private boolean muliplayerMode;


    /**
     * if true: white Turn else black
     */
    private boolean whiteTurn;

    /**
     * Constructor for Multiplayer
     * @param playerMode as boolean Value
     */
    public Multiplayer(boolean playerMode) {
        this.setMuliplayerMode(playerMode);
    }

    /**
     * If Human vc Human, it will return true
     * If Human vc CPU, it will return false
     * @return {@link isMultiplayerMode}
     */
    public boolean isMuliplayerMode() {
        return muliplayerMode;
    }

    /**
     * setter for multiplayer Mode
     * @param muliplayerMode {@value Boolean}
     */
    public void setMuliplayerMode(boolean muliplayerMode) {
        this.muliplayerMode = muliplayerMode;
    }

    /**
     * Reverse the player
     */
    public void switchPlayer() {
        this.whiteTurn = !this.whiteTurn;
    }

    /**
     * This Function will accept an integer Value: the even numbers refers to White Player
     * the odd numbers refers to black player
     * @param movementCount {@value Integer}
     * @return isWhite as boolean
     */
    public boolean isWhiteTurn(int movementCount) {
        boolean whiteTurn = false;
        if(movementCount %2 ==0) {
            whiteTurn = true;
        }
        return whiteTurn;
    }

}

