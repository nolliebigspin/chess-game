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
     * @return if human vs AI or human vs human
     */
    public boolean getMuliplayerMode() {
        return muliplayerMode;
    }

    /**
     * setter for multiplayer Mode
     * @param muliplayerMode sets the multiplayer Mode
     */
    public void setMuliplayerMode(boolean muliplayerMode) {
        this.muliplayerMode = muliplayerMode;
    }

    /**
     * This Function will accept an integer Value: the even numbers refers to White Player
     * the odd numbers refers to black player
     * @param movementCount counter for plays
     * @return isWhite as boolean
     */
    public boolean isWhiteTurn(int movementCount) {
        boolean whiteTurn = false;
        if(movementCount % 2 == 0) {
            whiteTurn = true;
        }
        return whiteTurn;
    }

}

