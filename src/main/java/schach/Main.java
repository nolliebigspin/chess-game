package schach;

import schach.controller.interfaces.Input;
import schach.controller.interfaces.HumanVsComputer;
import schach.model.Board;

import java.util.Scanner;

/**
 * main Class with main-method
 */
public class Main {

    /**
     * Main method starting the game, in the wanted mode (GUI or No-GUI)
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0 || !args[0].equals("--no-gui")){
            System.out.println("No GUI implemented yet, please add argument: --no-gui");
        } else {
            System.out.println("Welcome :) ");
            System.out.println("White starts first!");
            Board board = new Board();
            Input input = new Input(board);
            input.inOutRoutine();
        }
     }

    private static boolean vsHuman(){
        System.out.println("Playing against computer or another human? (computer/human) ");
        boolean running = true;
        while (running){
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("human")){
                return true;
            }
            if (input.equals("computer")){
                return false;
            }
        }
        return false;
    }
}
