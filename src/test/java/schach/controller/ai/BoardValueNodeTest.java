package schach.controller.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.Board;

class BoardValueNodeTest {

    private Board board;

    @BeforeEach
    private void init(){
        this.board = new Board();
    }

    @Test
    void testMinmax(){
        board.initLineUp();
        board.movePiece("b1", "c3");
        board.movePiece("b7", "b5");
        board.printBoard();
        BoardValueNode bvt = new BoardValueNode(board, true, null);
        System.out.println(bvt.minmax(2));
    }

}