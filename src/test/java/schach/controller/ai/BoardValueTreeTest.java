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
        new BoardValueTree(board, 2, true);
    }

}