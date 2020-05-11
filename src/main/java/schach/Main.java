package schach;

import schach.controller.Input;
import schach.model.Board;

/**
 * main Class with main-method
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome :) ");
        System.out.println("White starts first!");
        Board board = new Board();
        Input input = new Input(board);

        input.inOutRoutine();
    }
}
