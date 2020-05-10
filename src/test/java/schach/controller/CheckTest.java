package schach.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.*;

import static org.junit.jupiter.api.Assertions.*;

class CheckTest {

    Board board;

    Check check;

    @BeforeEach
    public void initBoard(){
        this.board = new Board();
        this.check = board.getCheck();
    }

    /**
     * Searches for a white King that exists
     */
    @Test
    public void testSearchKingWhite(){
        lineUp(" white-king-a1, black-bishop-e3, white-pawn-d4, black-queen-g7");
        Piece king = board.squareByDenotation("a1").getOccupier();
        Piece foundKing = check.searchKing(true);
        assertEquals(foundKing, king);
    }

    /**
     * Searches for non existing black King
     */
    @Test
    public void testSearchNullBlackKing(){
        lineUp(" white-king-a1, black-bishop-e3, white-pawn-d4, black-queen-g7");
        assertNull(check.searchKing(false));
    }


    /**
     * Checks for check for pawn
     */
    @Test
    public void testCheckPawn(){
        // white king, black pawn: pawn two-square-move
        lineUp(" black-king-a1, white-king-d4, black-pawn-e7");
        assertFalse(check.kingInCheck(true));
        board.movePiece("e7", "e5");
        assertTrue(check.kingInCheck(true));

        // black king, white pawn: pawn one-square-move
        initBoard();
        lineUp(" white-king-a1, black-king-d4, white-pawn-e2");
        assertFalse(check.kingInCheck(false));
        board.movePiece("e2", "e3");
        assertTrue(check.kingInCheck(false));
    }

    /**
     * Checks check for knight
     */
    @Test
    public void testCheckKnight(){
        lineUp(" white-king-a1, black-king-f7, white-knight-c4");
        assertFalse(check.kingInCheck(false));
        board.movePiece("c4", "e5");
        assertTrue(check.kingInCheck(false));
    }

    /**
     * Checks check for bishop
     */
    @Test
    public void testCheckBishop(){
        // no one in line
        lineUp(" black-king-a8, white-king-b3, black-bishop-h5");
        assertFalse(check.kingInCheck(true));
        board.movePiece("h5", "f7");
        assertTrue(check.kingInCheck(true));

        // pawn in line
        initBoard();
        lineUp(" black-king-a8, white-king-b3, black-bishop-h5, white-pawn-d5");
        assertFalse(check.kingInCheck(true));
        board.movePiece("h5", "f7");
        assertFalse(check.kingInCheck(true));
    }

    /**
     * Checks check for rook
     */
    @Test
    public void testCheckRook(){
        // no one in line
        lineUp(" white-king-a1, black-king-e4, white-rook-c7");
        assertFalse(check.kingInCheck(false));
        board.movePiece("c7", "e7");
        assertTrue(check.kingInCheck(false));

        // pawn in line
        initBoard();
        lineUp(" white-king-a1, black-king-e4, white-rook-c7, black-knight-d4");
        assertFalse(check.kingInCheck(false));
        board.movePiece("c7", "c4");
        assertFalse(check.kingInCheck(false));
    }

    //TODO test kingInCheck attacker: queen
    //TODO check if attackers moves away

    @Test
    public void testInCheckIfMoved(){
        lineUp(" black-king-a8, white-king-c2, white-rook-e4, black-queen-g8");
        Piece rook = board.squareByDenotation("e4").getOccupier();
        Square target = board.squareByDenotation("d4");
        assertFalse(check.inCheckIfMoved(rook, target));
        board.movePiece("g8","h7");
        assertTrue(check.inCheckIfMoved(rook, target));
    }


    /**
     * Method that simplifies the Board lineup;
     * @param input lineup in a certain string format
     */
    private void lineUp(String input){
        String[] commands = input.split(",");
        for (String s: commands){
            String[] denot = s.split("-");
            boolean b = denot[0].equals(" white");
            board.addPiece(denot[1], denot[2],b);
        }
    }


}