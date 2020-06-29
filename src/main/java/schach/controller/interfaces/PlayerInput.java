package schach.controller.interfaces;

import schach.model.Board;
import schach.model.Piece;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Class that handles all the input by the player
 */
public class PlayerInput {

    /**
     * Board the game is played on
     */
    private Board board;

    /**
     * the color this player picked, true for white, false for black
     */
    private boolean white;

    /**
     * the move wanted by the player checked for right syntax
     */
    private String move;

    private boolean inUndo;

    /**
     * Constructor initializing the board and white fields
     * @param board the board the games is played on
     * @param isWhite true if this player picked white, false if picked black
     */
    public PlayerInput(Board board, boolean isWhite){
        this.board = board;
        this.white = isWhite;
        inUndo = false;
    }

    /**
     * Routine that reads the user input and processes it
     * @return a string that is a syntactical correct move
     */
    protected String inputRoutine(){
        String in = readInput();
        if (inputIsMove(in)){
            return move;
        } else {
            return inputRoutine();
        }
    }

    /**
     * method that process the input string and checks if input was a syntactical correct move input
     * @param in the input string that should be processed / checked
     * @return true if input string is a correct move, false if not
     */
    protected boolean inputIsMove(String in){
        if (in.equals("beaten")){
            board.printBeaten();
            return false;
        } else if (in.equals("undo")){
            board.loadState(board.getStates().size() - 3);
            inUndo = true;
            return false;
        } else if (in.equals("redo")){
            if (inUndo){
                board.redo();
            }
            return false;
        } else if (in.equals("help")){
            System.out.println(helpOut());
            return false;
        } else if (validMoveInput(in)) {
            if (pieceIsRightColor(in)){
                this.move = in;
                inUndo = false;
                return true;
            }
        } else {
            System.out.println("no valid input given - please enter 'help' for further info");
            return false;
        }
        return false;
    }

    /**
     * reads the input and returns it
     * @return the string of the input line
     */
    private String readInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your move: ");
        return scanner.nextLine();
    }

    /**
     * returns a help message which include instruction
     * @return help message
     */
    private String helpOut(){
        return "enter 'beaten' to show all beaten Pieces  \n" +
                "enter a move by the following format: 'e2-a5' \n" +
                "if you want a promotion, enter move like: a7-a8B";
    }

    /**
     * checks wherever the player wants to move a piece by its own color
     * @param move the move the player wants to do
     * @return true if moving piece is the same color as the one the player picked, false if not
     */
    private boolean pieceIsRightColor(String move){
        Piece piece = board.squareByDenotation(move.substring(0,2)).getOccupier();
        if (piece == null){
            return false;
        }
        if (piece.isWhite() == white){
            return true;
        } else {
            System.out.println("!Invalid move");
            System.out.println("You tried to move a piece of the enemy!");
            return false;
        }
    }

    /**
     * checks wherever the move input included a promotion
     * @return true if move input included a promotion, false if not
     */
    public boolean isPromotion(){
        return move.length() == 6;
    }

    /**
     * validates if the give input is a legal move command
     * @param input String that should be validated
     * @return true if String is valid move command, false if not
     */
    private boolean validMoveInput(String input) {
        String invalidOut = "!Invalid move";
        //Exception if string to short
        if (input.length() != 5 && input.length() != 6) {
            System.out.println(invalidOut);
            return false;
        }
        //Exception if string not: xx-xx
        if (!(input.charAt(2) == '-')) {
            System.out.println(invalidOut);
            return false;
        }
        if (input.length() == 6 && !validPromotion(input.substring(5))) {
            return false;
        }
        if (!validDenotation(input.substring(0,2)) || !validDenotation(input.substring(3,5))) {
            System.out.println(invalidOut);
            return false;
        }
        return true;
    }


    /**
     * validates if the given last character of the input is a correct piece for promotion
     * @param prom String that the pawn should be promoted to
     * @return boolean if the letter is legal character for promotion
     */
    private boolean validPromotion(String prom) {
        if (prom.length() != 1){
            return false;
        }
        char letter = prom.charAt(0);
        String[] legalLetters = {"Q", "R", "B", "N"};
        return Arrays.asList(legalLetters).contains(String.valueOf(letter));
    }

    /**
     * validates if a given string is a correct denotation
     * @param denotation string that should be validated
     * @return true if valid, false if incorrect
     */
    private boolean validDenotation(String denotation){
        if (denotation.length() != 2){
            return false;
        }
        char letter = denotation.charAt(0);
        char number = denotation.charAt(1);

        String[] legalLetters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        if (!Arrays.asList(legalLetters).contains(String.valueOf(letter))){
            return false;
        }

        String[] legalNumbers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        return Arrays.asList(legalNumbers).contains(String.valueOf(number));
    }

    /**
     * checks if input was yes or no, else calls own recursively
     * @return true if input was yes, false in it was no
     */
    public boolean yes(){
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();
        if (in.equals("y") || in.equals("yes")){
            return true;
        } else if (in.equals("n") || in.equals("no")){
            return false;
        } else {
            return yes();
        }
    }

}
