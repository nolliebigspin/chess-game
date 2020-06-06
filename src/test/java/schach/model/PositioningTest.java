package schach.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test the Positioning Class
 */
class PositioningTest {

    /**
     * Board the check rules should be tested on
     */
    Board board;

    @BeforeEach
    void initBoard(){
        this.board = new Board();
    }

    @Test
    void testPositioning(){
        lineUp(" white-queen-a1, white-king-c1, white-knight-f1, white-pawn-c3, white-pawn-b5, black-bishop-g6, black-rook-h8, black-queen-e8, black-king-c8");
        board.printBoard();
        Board nBoard = new Board(board.getPositioning());
        nBoard.printBoard();
        nBoard.movePiece("f1","g3");
        nBoard.printBoard();
    }

    /**
     * Method that simplifies the Board lineup;
     * @param input lineup in a certain string format
     */
    private void lineUp(String input){
        String[] commands = input.split(",");
        for (String s: commands){
            String[] denote = s.split("-");
            boolean b = denote[0].equals(" white");
            board.addPiece(denote[1], denote[2],b);
        }
        board.updateAllLegalSquares();
    }

}