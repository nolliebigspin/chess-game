package schach;

import schach.model.Board;
import schach.model.Pawn;
import schach.model.Piece;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class Input that controls the Input and updates the board
 */
public class DebuggerInput {

    private Board board;
    public int currentMove;
    public BufferedReader reader;
    public int inCounter = 0;
    /**
     * Constructor
     * @param board to be updated
     */
    public DebuggerInput(Board board) throws FileNotFoundException {
        this.board = board;
        currentMove = 0;
        reader = new BufferedReader(new FileReader("C:\\Users\\Henry\\Documents\\Studium lokal\\6. Semester\\SwEng Praktikum\\Git\\schach\\src\\main\\java\\schach\\input.txt"));
    }

    /**
     * Routine that reads the input, validates it and updates the board
     */
    public void inOutRoutine() throws IOException {
        board.initLineUp();
        board.printBoard();
        boolean running = true;
        while (running) {
            Scanner scan = new Scanner(System.in);
            String in = scan.nextLine();
            if (in.equals("skip")){

                int i = scan.nextInt();
                System.out.println(i);
                for (int j = 0; j < i; j++){
                    String input = readInput2();
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
                        /*
                        if (input.length() == 6 && board.squareByDenotation(input.substring(3,5)).isOccupied()) {
                            try {
                                Pawn pawn = (Pawn)  board.squareByDenotation(input.substring(3,5)).getOccupier();
                                pawn.doPromotion(input.substring(5));
                            } catch (Exception e) {
                                System.out.println("Promotion not possible.");
                            }
                        }
                         */

                        if (movingPiece != null && movingPiece.isValidMove()){
                            System.out.println("!" + input);
                            currentMove++;
                            System.out.println("Move counter: " + currentMove);
                        }
                        board.printBoard();

                    }
                    if (board.getCheck().isCheckMate(true)
                            || board.getCheck().isCheckMate(false)) {
                        System.out.println("CHECKMATE.");
                        running = false;
                    }
                }
            }
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
                /*
                if (input.length() == 6 && board.squareByDenotation(input.substring(3,5)).isOccupied()) {
                    try {
                        Pawn pawn = (Pawn)  board.squareByDenotation(input.substring(3,5)).getOccupier();
                        pawn.doPromotion(input.substring(5));
                    } catch (Exception e) {
                        System.out.println("Promotion not possible.");
                    }
                }
                 */
                if (movingPiece != null && movingPiece.isValidMove()){
                    System.out.println("!" + input);
                    currentMove++;
                    System.out.println("Move counter: " + currentMove);
                }
                board.printBoard();

            }
            if (board.getCheck().isCheckMate(true)
                    || board.getCheck().isCheckMate(false)) {
                System.out.println("CHECKMATE.");
                running = false;
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
    public String readInput() throws IOException {
        inCounter++;
        String txtInput = reader.readLine().substring(2, 7);
        System.out.println( inCounter + ": "  + txtInput);
        Scanner scanner = new Scanner(System.in);
       if (scanner.nextLine().equals("")){
           return txtInput;
       }
       return txtInput;
    }

    public String readInput2() throws IOException {
        inCounter++;
        if (inCounter == 2394){
            System.out.println("du bist da");
        }
        String txtInput = reader.readLine().substring(2, 7);
        System.out.println( inCounter + ": "  + txtInput);
        return txtInput;
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

