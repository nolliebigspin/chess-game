package schach.controller;

import schach.model.Board;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Class Input that controls the Input and updates the board
 */
public class Input {

    private Board board;

    /**
     * Constructor
     * @param board to be updated
     */
    public Input(Board board) {
        this.board = board;
    }

    /**
     * Routine that reads the input, validates it and updates the board
     */
    public void inOutRoutine() {
        board.initLineUp();
        board.printBoard();
        boolean running = true;
        while (running) {
            String input = readInput();
            if (input.equals("beaten")){
                board.printBeaten();
            } else if (validMoveInput(input)) {
                board.movePiece(input.substring(0,2), input.substring(3,5));
                // calling promotion method for piece if target Square is occupied
                if (input.length() == 6 && board.squareByDenotation(input.substring(3,5)).isOccupied()) {
                    try {
                        board.squareByDenotation(input.substring(3,5)).getOccupier().doPromotion(input.substring(5), board.squareByDenotation(input.substring(3,5)));
                    } catch (Exception e) {
                        System.out.println("Promotion not possible!");
                    }

                }
                board.printBoard();
            }
            if (board.getCheck().isCheckMate(true)
                    || board.getCheck().isCheckMate(false)){
                running = false;
                System.out.println("CHECKMATE!");
            }
        }
    }

    /**
     * validates if a given string is a correct denotation
     * @param denotation string that should be validated
     * @return true if valid, false if incorrect
     */
    protected boolean validDenotation(String denotation){
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
        if (!Arrays.asList(legalNumbers).contains(String.valueOf(number))){
            return false;
        }

        return true;
    }

    /**
     * validates if the given last character of the input is a correct piece for promotion
     * @param prom String that the pawn should be promoted to
     * @return boolean if the letter is legal character for promotion
     */
    protected boolean validPromotion(String prom) {
        if (prom.length() != 1){
            return false;
        }
        char letter = prom.charAt(0);
        String[] legalLetters = {"Q", "R", "B", "N"};
        if (!Arrays.asList(legalLetters).contains(String.valueOf(letter))) {
            return false;
        }
        return true;
    }

    /**
     * reads the input and returns it
     * @return the string of the input line
     */
    protected String readInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your move: ");
        return scanner.nextLine();
    }

    /**
     * validates if the give input is a legal move command
     * @param input String that should be validated
     * @return true if String is valid move command, false if not
     */
    protected boolean validMoveInput(String input) {
        String invalidOut = "!Invalid Move";
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
}

