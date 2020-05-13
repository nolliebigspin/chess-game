package schach;

import schach.controller.Input;
import schach.model.Board;

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
                System.out.println("Welcome :) ");
                System.out.println("White starts first!");
                Board board = new Board();
                Input input = new Input(board);

                input.inOutRoutine();
            }
        }
        catch (Exception e){
            System.out.println("No GUI implemented yet, please add argument: --no-gui");
        }
    }
}
