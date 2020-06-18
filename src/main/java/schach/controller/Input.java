package schach.controller;

import schach.model.Board;
import schach.model.Piece;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Class Input that controls the Input and updates the board
 */
public class Input {

    private Board board;
    public int currentMove;

    /**
     * Constructor
     * @param board to be updated
     */
    public Input(Board board) {
        this.board = board;
        currentMove = 0;
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
            } else if (validMoveInput(input) && checkTurn(input, currentMove)) {
                Piece movingPiece;
                try {
                    movingPiece = board.squareByDenotation(input.substring(0,2)).getOccupier();
                } catch (Exception e){
                    movingPiece = null;
                }
                board.movePiece(input.substring(0,2), input.substring(3,5));
                // calling promotion method for piece if target Square is occupied
                if (input.length() == 6 && board.squareByDenotation(input.substring(3,5)).isOccupied()) {
                    try {
                     //  board.squareByDenotation(input.substring(3,5)).getOccupier().doPromotion(input.substring(5), board.squareByDenotation(input.substring(3,5)));
                    } catch (Exception e) {
                        System.out.println("Promotion not possible!");
                    }
                }
                board.printBoard();
                if (movingPiece != null && movingPiece.isValidMove()){
                    currentMove++;
                }
                System.out.println(currentMove);
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
        return Arrays.asList(legalNumbers).contains(String.valueOf(number));
    }

    /**
     * Checks if it is the turn of the color that wants to move a piece
     * @param command the denotation of the square the piece that should be moved is on
     * @param currentMove the current move count
     * @return true if it is the turn of the color that wants to move, false if not
     */
    public boolean checkTurn(String command, int currentMove) {
        if  (this.board.squareByDenotation(command.substring(0, 2)).isOccupied()) {
            if (this.board.squareByDenotation(command.substring(0, 2)).getOccupier().isWhite() && currentMove % 2 == 0) {
                return true;
            } else if (!this.board.squareByDenotation(command.substring(0, 2)).getOccupier().isWhite() && currentMove % 2 != 0) {
                return true;
            }
            System.out.println("It's not your turn!");
            return false;
        }
        System.out.println("!Invalid Move");
        System.out.println("No piece found!");
        return false;
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
        return Arrays.asList(legalLetters).contains(String.valueOf(letter));
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

