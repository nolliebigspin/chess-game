package schach.controller.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.Board;

import static org.junit.jupiter.api.Assertions.*;

class BoardValueTreeTest {

    private Board board;

    @BeforeEach
    private void init(){
        this.board = new Board();
    }

    @Test
    void testGenerally(){
        board.initLineUp();
        new BoardValueTree(board, 3, true, null);
    }

    @Test
    void testMinmax(){
        board.initLineUp();
        board.movePiece("b1", "c3");
        board.movePiece("b7", "b5");
        board.printBoard();
        BoardValueTree bvt = new BoardValueTree(board, true, null);
        System.out.println(bvt.minmax(2));
    }

}