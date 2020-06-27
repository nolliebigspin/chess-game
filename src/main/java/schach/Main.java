package schach;

import schach.controller.interfaces.Input;
import schach.controller.interfaces.HumanVsComputer;
import schach.model.Board;
import schach.view.GuiMain;

import java.util.Arrays;
import java.util.List;
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
        List<String> arguments = Arrays.asList(args);
        if (arguments.size() == 0 || !arguments.contains("--no-gui") && !arguments.contains("--simple")){
            String[] guiArgs = null;
            GuiMain.main(guiArgs);
        } else if (arguments.contains("--simple")){
            System.out.println("Welcome :) ");
            System.out.println("White starts first!");
            Board board = new Board();
            Input input = new Input(board);
            input.inOutRoutine();
        } else {
            if (vsHuman()){
                System.out.println("Welcome :) ");
                System.out.println("White starts first!");
                Board board = new Board();
                Input input = new Input(board);
                input.inOutRoutine();
            }
            else {
                HumanVsComputer input = new HumanVsComputer();
                input.runningRoutine();
            }
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
