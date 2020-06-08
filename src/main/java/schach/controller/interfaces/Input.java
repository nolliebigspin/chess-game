package schach.controller.interfaces;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import schach.model.Board;
import schach.model.Pawn;
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
                    movingPiece.setValidMoveFalse();
                } catch (Exception e){
                    movingPiece = null;
                }
                board.movePiece(input.substring(0,2), input.substring(3,5));

                // calling promotion method for piece if target Square is occupied
                checkForPromotion(input, movingPiece);

                if (movingPiece != null && movingPiece.isValidMove()){
                    System.out.println("!" + input);
                    currentMove++;
                    System.out.println("Move counter: " + currentMove);
                }
                board.printBoard();

                if (movingPiece.isValidMove() && board.getCheck().isCheckMate(!movingPiece.isWhite())) {
                    System.out.println("CHECKMATE.");
                    String color = "";
                    if (board.getCheck().isCheckMate((true))) {
                        color = "White";
                    } else {
                        color = "Black";
                    }
                    running = false;
                }

            }

        }
    }

    private void checkForPromotion(String input, Piece piece){
        if (!(piece instanceof Pawn)){
            return;
        }
        Pawn pawn = (Pawn) piece;
        int finalRow = 8;
        if (!pawn.isWhite()){
            finalRow = 1;
        }
        if (pawn.getPosition().getRow() != finalRow){
            return;
        }
        if (input.length() == 5){
            pawn.doPromotion("Q");
            return;
        }
        if (input.length() == 6){
            String prom = input.substring(5);
            pawn.doPromotion(prom);
            return;
        }
    }

    /**
     * validates if a given string is a correct denotation
     * @param denotation string that should be validated
     * @return true if valid, false if incorrect
     */
    public boolean validDenotation(String denotation){
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
            System.out.println("!Move not allowed");
            System.out.println("It's not your turn.");
            return false;
        }
        System.out.println("!Move not allowed");
        System.out.println("No piece found.");
        return false;
    }

    /**
     * validates if the given last character of the input is a correct piece for promotion
     * @param prom String that the pawn should be promoted to
     * @return boolean if the letter is legal character for promotion
     */
    public boolean validPromotion(String prom) {
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
    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your move: ");
        return scanner.nextLine();
    }

    /**
     * validates if the give input is a legal move command
     * @param input String that should be validated
     * @return true if String is valid move command, false if not
     */
    public boolean validMoveInput(String input) {
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
        if (input.length() >= 6 && !validPromotion(input.substring(5))) {
            System.out.println(invalidOut);
            return false;
        }
        if (!validDenotation(input.substring(0,2)) || !validDenotation(input.substring(3,5))) {
            System.out.println(invalidOut);
            return false;
        }
        return true;
    }
}

