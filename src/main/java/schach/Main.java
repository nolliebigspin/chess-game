package schach;

/**
 * main Class with main-method
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("This is a chess game!");
        System.out.println("Please enter if you want to play against another human or against our artificial intelligence?");
        Board board = new Board();
        board.printBoard();
        Input input = new Input(board);
        input.inOutRoutine();
    }
}
