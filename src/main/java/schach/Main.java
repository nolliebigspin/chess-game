package schach;

import schach.controller.Interfaces.Input;
import schach.controller.Interfaces.InputVsAi;
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
        try{
            if (args[0].equals("--no-gui")){
                if (vsHuman()){
                    System.out.println("Welcome :) ");
                    System.out.println("White starts first!");
                    Board board = new Board();
                    Input input = new Input(board);
                    input.inOutRoutine();
                }
                else {
                    InputVsAi input = new InputVsAi();
                    input.runningRoutine();
                }

            }
        }
        catch (Exception e){
            System.out.println("No GUI implemented yet, please add argument: --no-gui");
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
