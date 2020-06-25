package schach.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


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
    void testGeneral(){
        board.initLineUp();
        Board nb = new Board(board.getPositioning());
        assertTrue(nb.squareByDenotation("e2").isOccupied());
    }


}