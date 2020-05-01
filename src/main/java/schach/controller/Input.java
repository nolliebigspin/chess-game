package schach.controller;

import schach.model.Board;

import java.util.Scanner;

/**
 * Class Input that controls the Input and updates the board
 */
public class Input {
    private String currentInputLine;
    private String startingPos;
    private String targetPos;
    private boolean valid;
    private Board board;

    /**
     * Constructor
     * @param board to be updated
     */
    public Input(Board board) {
        this.board = board;
        this.valid = false;
    }

    /**
     * Routine that reads the input, validates it and updates the board
     */
    public void inOutRoutine() {
        boolean running = true;
        while (running) {
            if (startingLineUpWanted()){
                board.initLineUp();
            }
            board.printBoard();
            readInput();
            validate();
            if (valid) {
                board.movePiece(startingPos, targetPos);
                board.printBoard();
            }
            valid = false;
        }
    }

    private boolean startingLineUpWanted(){
        System.out.println("StartingLineup? (y/n)");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equals("y")){
            return true;
        } else if (scanner.nextLine().equals("n")) {
            return false;
        } else {
            return startingLineUpWanted();
        }
    }

    private void individualLineUp(){
        boolean running = true;
        while (running){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter piece, like: 'pawn e2 black' ");
            String input = scanner.nextLine();
            if (validatePieceInput(input)){
                String[] inputArray = input.split(" ");
                boolean isWhite;
                if (inputArray[2].equals("white")){
                    isWhite = true;
                } else {
                    isWhite = false;
                }
                board.addPiece(inputArray[0], inputArray[1], isWhite);
            }

        }
    }

    private boolean validatePieceInput(String input){
        String[] inputArray = input.split(" ");
        if (inputArray.length != 3);
        String[] legalPieceName = {"pawn", "rook", "bishop", "knight", "queen", "king"};
        boolean valid = false;
        for (String string: legalPieceName){
            if (inputArray[0].equals(string)){
                valid = true;
            }
        }
        if (!validateDenotation(inputArray[1])){
            return false;
        }
        if (!(inputArray[2].equals("white") && inputArray[2].equals("black"))){
            return false;
        }
        return valid;
    }

    /**
     * validates if a given string is a correct denotation
     * @param denotation string that should be validated
     * @return true if valid, false if incorrect
     */
    private boolean validateDenotation(String denotation){
        String legalLetters = "abcdefgh";
        String legalNumbers = "12345678";
        boolean valid = false;
        if (denotation.length() != 2){
            return false;
        }
        char letter = denotation.charAt(0);
        char number = denotation.charAt(1);
        if (legalLetters.indexOf(letter) == - 1){
            return false;
        }
        if (legalNumbers.indexOf(number) == - 1){
            return false;
        }
        return true;
    }


    /**
     * reads input via scanner class
     */
    private void readInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your move: ");
        this.currentInputLine = scanner.nextLine();
    }

    /**
     * Validates the input
     * TODO validate for type xx-xxQ
     */
    private void validate() {
        String validLetter = "abcdefgh";
        String validInteger = "12345678";
        String invalidOut = "!Invalid Move";
        //Exception if string to short
        if (currentInputLine.length() != 5){
            System.out.println(invalidOut);
            return;
        }
        //Exception if string not: xx-xx
        if (!(currentInputLine.charAt(2) == '-')) {
            System.out.println(invalidOut);
            return;
        }
        //Exception if no correct letter (a...h)
        if (validLetter.indexOf(currentInputLine.charAt(0)) == -1 || validLetter.indexOf(currentInputLine.charAt(3)) == -1) {
            System.out.println(invalidOut);
            return;
        }
        //Exception if no correct number (1...8)
        if (validInteger.indexOf(currentInputLine.charAt(1) ) == -1 || validInteger.indexOf(currentInputLine.charAt(4) ) == -1) {
            System.out.println(invalidOut);
            return;
        }
        this.startingPos = "" + currentInputLine.charAt(0) + currentInputLine.charAt(1);
        this.targetPos = "" + currentInputLine.charAt(3) + currentInputLine.charAt(4);
        this.valid = true;
    }
}

