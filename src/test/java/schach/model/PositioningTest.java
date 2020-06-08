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