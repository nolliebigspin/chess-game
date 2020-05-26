package schach;

import schach.controller.Check;
import schach.model.Board;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DebuggerMain {

    public static void main(String args[]) throws IOException {
        Board board = new Board();
        DebuggerInput input = new DebuggerInput(board);
        input.inOutRoutine();



        Checker checker = new Checker();
        for (int i = 0; i < 10; i++){
            System.out.println(checker.getNextLine());
        }
    }

}
