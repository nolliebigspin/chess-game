package schach.controller.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.Board;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test the minmax ai
 */
class MinMaxAiTest {

    Board board;

    AiInterface ai;

    @BeforeEach
    void init(){
        this.board = new Board();
        this.ai = new MinMaxAi(board, true);
    }

    /*@Test
    void testAi(){
        board.initLineUp();
        board.movePiece("c2", "c4");
        board.movePiece("b8", "c6");
        board.movePiece("h2", "h3");
        board.movePiece("c6", "b4");
        String expected = "d1-a4";
        assertEquals(expected, ai.getNextMove());
    }*/

}