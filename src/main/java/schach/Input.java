package schach;

import java.util.Scanner;

/**
 * Class Input that controls the Input and updates the board
 */
public class Input {
    private String currentInputLine;
    private String startingPos;
    private String targetPos;
    private boolean valide = false;
    private Board board;

    /**
     * Constructor
     * @param board to be updated
     */
    public Input(Board board){
        this.board = board;
    }

    /**
     * Routine that reads the input, validates it and updates the board
     */
    public void inOutRoutine(){
        boolean running = true;
        while (running){
            readInput();
            validate();
            if (valide){
                board.movePiece(startingPos, targetPos);
                board.printBoard();
            }
            valide = false;
        }
    }

    /**
     * reads input via scanner class
     */
    public void readInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your move: ");
        this.currentInputLine = scanner.nextLine();
    }

    /**
     * Validates the input
     * @todo validate for type xx-xxQ
     */
    private void validate(){
        String validLetter = "abcdefgh";
        String validInteger = "12345678";
        //Exception if string to short
        if (currentInputLine.length() != 5){
            System.out.println("!Invalide Move");
            return;
        }
        //Exception if string not: xx-xx
        if (!(currentInputLine.charAt(2) == '-')){
            System.out.println("!Invalide Move");
            return;
        }
        //Exception if no correct letter (a...h)
        if (validLetter.indexOf(currentInputLine.charAt(0)) == -1 || validLetter.indexOf(currentInputLine.charAt(3)) == -1){
            System.out.println("!Invalide Move");
            return;
        }
        //Exception if no correct number (1...8)
        if (validInteger.indexOf(currentInputLine.charAt(1) ) == -1 || validInteger.indexOf(currentInputLine.charAt(4) ) == -1){
            System.out.println("!Invalide Move");
            return;
        }
        this.startingPos = "" + currentInputLine.charAt(0) + currentInputLine.charAt(1);
        this.targetPos = "" + currentInputLine.charAt(3) + currentInputLine.charAt(4);
        this.valide = true;
    }
}

