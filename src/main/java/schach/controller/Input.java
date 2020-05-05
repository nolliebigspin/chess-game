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
        System.out.println("StartingLineup? (y/n)");
        if (yesNoInput()){
            board.initLineUp();
        } else {
            individualLineUp();
        }
        board.printBoard();
        boolean running = true;
        while (running) {
            String input = readInput();
            if (input.equals("beaten")){
                board.printBeaten();
            } else if (validMoveInput(input)) {
                String[] denotations = input.split("-");
                board.movePiece(denotations[0], denotations[1]);
                board.printBoard();
            }
        }
    }

    /**
     * Method to check wherever the input i 'y' or 'n'
     * @return true for 'y', false for 'n'
     */
    private boolean yesNoInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("y")){
            return true;
        } else if (input.equals("n")) {
            return false;
        } else {
            return yesNoInput();
        }
    }

    /**
     * Adds single pieces to the board
     */
    private void individualLineUp(){
        boolean running = true;
        while (running){
            String[] input = validatePieceInput();
            boolean isWhite;
            if (input[2].equals("white")){
                isWhite = true;
            } else {
                isWhite = false;
            }
            board.addPiece(input[0], input[1], isWhite);
            board.printBoard();
            System.out.println("add another piece (y/n)");
            if (!yesNoInput()){
                running = false;
            }
        }

    }

    /**
     * Validates if input has a certain format ("pawn e1 black"), converts the string to array
     * @return String Array of the values needed to create new Piece ({"pawn", "e1", "black"}
     */
    private String[] validatePieceInput(){
        System.out.print("Enter piece, like: 'pawn e2 black' ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputArray = input.split(" ");
        if (inputArray.length != 3){
            System.out.println("invalid input 1");
            return validatePieceInput();
        }
        String[] legalPieceName = {"pawn", "rook", "bishop", "knight", "queen", "king"};
        if (!Arrays.asList(legalPieceName).contains(inputArray[0])){
            System.out.println("invalid input 2");
            return validatePieceInput();
        }
        if (!validDenotation(inputArray[1])){
            System.out.println("invalid input 3");
            return validatePieceInput();
        }
        if (!(inputArray[2].equals("white") || inputArray[2].equals("black"))){
            System.out.println("invalid input 4");
            return validatePieceInput();
        }
        return inputArray;
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
        if (!Arrays.asList(legalNumbers).contains(String.valueOf(number))){
            return false;
        }

        return true;
    }


    /**
     * reads input via scanner class
     */
    private String readInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your move: ");
        return scanner.nextLine();
    }

    /**
     * Validates the input for a move command
     */
    private boolean validMoveInput(String input) {
        String invalidOut = "!Invalid Move";
        //Exception if string to short
        if (input.length() != 5){
            System.out.println(invalidOut);
            return false;
        }
        //Exception if string not: xx-xx
        if (!(input.charAt(2) == '-')) {
            System.out.println(invalidOut);
            return false;
        }
        String[] inputArray = input.split("-");
        if (!validDenotation(inputArray[0]) || !validDenotation(inputArray[1])){
            System.out.println(invalidOut);
            return false;
        }
        return true;
    }
}

