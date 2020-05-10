package schach.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.*;

import java.util.ArrayList;
import java.util.List;

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
        lineUp(" white-king-a1, black-bishop-e3, white-pawn-d4, black-queen-g7, black-king-a8");
        Piece king = board.squareByDenotation("a1").getOccupier();
        Piece foundKing = check.searchKing(true);
        assertEquals(foundKing, king);
    }

    /**
     * Searches for non existing black King

    @Test
    public void testSearchNullBlackKing(){
        lineUp(" white-king-a1, black-bishop-e3, white-pawn-d4, black-queen-g7");
        assertNull(check.searchKing(false));
    }
    */

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

    /**
     * Tests method inCheckedIfMoved()
     */
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
     * Tests if the virtual move changes the board, tests if undo works properly
     */
    @Test
    public void testVirtualMove(){
        lineUp(" white-king-a1, black-king-a8, black-bishop-e3, white-pawn-d4, black-queen-g7");
        List<Piece> piecesBefore = board.allActivePieces(true);
        check.inCheckIfMoved(board.squareByDenotation("e3").getOccupier(), board.squareByDenotation("d4"));
        List<Piece> piecesAfter = board.allActivePieces(true);
        assertEquals(piecesBefore, piecesAfter);
    }

    /**
     * Tests if piece(pawn)is labeled as attackingKing if it is attacking the King
     */
    @Test
    public void testAttackingKing(){
        lineUp(" white-king-a1, black-king-d6, white-pawn-e4");
        assertFalse(check.attackingKing(board.squareByDenotation("e4").getOccupier()));
        board.movePiece("e4", "e5");
        assertTrue(check.attackingKing(board.squareByDenotation("e5").getOccupier()));
    }

    /**
     * Tests if List attackersSettingCheck() is right size
     */
    @Test
    public void testAttackersSettingCheckList(){
        lineUp(" black-queen-a3, black-knight-c3, white-king-e3, white-pawn-c7, black-king-g7");
        assertTrue(check.attackersSettingCheck(true).isEmpty());
        board.movePiece("c3", "d5");
        assertEquals(2, check.attackersSettingCheck(true).size());
        board.movePiece("a3", "a4");
        assertEquals(1, check.attackersSettingCheck(true).size());
    }

    /**
     * Test inBetweenSquaresRook List contains the right squares for rook and king in the same col
     */
    @Test
    public void testInBewtweenRookCol(){
        lineUp(" black-king-a8, white-king-e6, black-rook-e3");
        List<Square> between = check.inBetweenSquares(true);
        assertEquals(2, between.size());

        initBoard();
        lineUp(" black-king-h2, white-king-a1, white-rook-h4");
        between = check.inBetweenSquares(false);
        Square h3 = board.squareByDenotation("h3");
        assertTrue(between.contains(h3));
        board.movePiece("h4", "h3");
        assertTrue(check.inBetweenSquares(false).isEmpty());
    }

    /**
     * Test inBetweenSquaresRook List contains the right squares for rook and king in the same row
     */
    @Test
    public void testInBewtweenRookRow(){
        lineUp(" white-king-a1, white-rook-c4, black-king-f4");
        assertEquals(2, check.inBetweenSquares(false).size());
        board.printBoard();

        initBoard();
        lineUp(" white-king-c3, black-rook-h3, black-king-a8");
        assertEquals(4, check.inBetweenSquares(true).size());
        board.printBoard();
    }

    /**
     * test if list of squares to resolve check is correct
     */
    @Test
    public void testlegalsToResolveCheck(){
        lineUp(" white-king-a1, white-rook-c4, black-king-f4, white-queen-e5");
        board.printBoard();
        Piece queen = board.squareByDenotation("e5").getOccupier();
        Square c3 = board.squareByDenotation("c3");
        Square e3 = board.squareByDenotation("e3");
        List<Square> expList = new ArrayList<>();
        expList.add(c3);
        expList.add(e3);
        assertTrue(check.legalsToResolveCheck(queen).containsAll(expList));
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
        board.updateAllLegalSquares();
    }


}