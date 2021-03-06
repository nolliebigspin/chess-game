package schach.model;

import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the abstract class Piece
 */
class PieceTest {

    private String pawn = "pawn";
    private String king = "king";

    // moving a piece on the board
    @Test
    void move() {
        Board testBoard = new Board();
        testBoard.addPiece(king, "a1", true);
        testBoard.addPiece(king, "g7", false);
        testBoard.addPiece(pawn, "b3", true);
        King k = new King(testBoard.getSquare(1,1),true,testBoard);
        k.updateLegals();
        // makes sure that the piece is there
        assertEquals(testBoard.squareByDenotation("a1").getOccupier(), k);
        //move the piece
        k.move(testBoard.getSquare(2,2));
        k.updateLegals();
        //assert that the piece is now on the new location
        assertEquals(testBoard.squareByDenotation("b2").getOccupier(), k);
        //assert that the old location is empty
        assertFalse(testBoard.squareByDenotation("a1").isOccupied());

    }

    @Test
    void getLegalSquares() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a5", true);
        testBoard.addPiece(king, "b2", true);
        testBoard.addPiece(king, "g7", false);
        Pawn p = new Pawn(testBoard.getSquare(1,5),true,testBoard);
        List<Square> legalNextSquares = new ArrayList<Square>();
        legalNextSquares.add(testBoard.getSquare(1,6));
        p.updateLegals();
        assertEquals(legalNextSquares,p.getLegalNextSquares());

    }

    @Test
    void printLegals() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a7", true);
        testBoard.addPiece(king, "b2", true);
        testBoard.addPiece(king, "g7", false);
        Pawn p = new Pawn(testBoard.getSquare(1,7),true,testBoard);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        p.updateLegals();
        System.setOut(new PrintStream(outContent));
        p.printLegals();
        assertEquals("a8", outContent.toString());
        System.setOut(originalOut);

    }

    @Test
    void doPromotionNotOnEdge() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a7", true);
        Pawn p = new Pawn(testBoard.getSquare(1,7),true,testBoard);
        assertNotNull(testBoard.squareByDenotation("a7").getOccupier());
        p.doPromotion("Q");
        String actual = testBoard.squareByDenotation("a7").getOccupier().print();
        String expected = "\u2659";
        assertEquals(expected, actual);
    }

    @Test
    void doPromotionQueen() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a8", true);
        Pawn p = (Pawn) testBoard.squareByDenotation("a8").getOccupier();
        //assertNull(testBoard.squareByDenotation("a7").getOccupier());
        p.doPromotion("Q");
        String actual = testBoard.squareByDenotation("a8").getOccupier().print();
        String expected = "\u2655";
        assertEquals(expected, actual);
    }

    @Test
    void doPromotionRook() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a8", true);
        Pawn p = (Pawn) testBoard.squareByDenotation("a8").getOccupier();
        p.doPromotion("R");
        String actual = testBoard.squareByDenotation("a8").getOccupier().print();
        String expected = "\u2656";
        assertEquals(expected, actual);
    }

    @Test
    void doPromotionKnight() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a1", false);
        Pawn p = (Pawn) testBoard.squareByDenotation("a1").getOccupier();
        p.doPromotion("N");
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u265E";
        assertEquals(expected, actual);
    }

    @Test
    void doPromotionBishop() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a1", false);
        Pawn p = (Pawn) testBoard.squareByDenotation("a1").getOccupier();
        p.doPromotion("B");
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u265D";
        assertEquals(expected, actual);
    }

    @Test
    void getPositionTest(){
         Board testBoard = new Board();
         testBoard.addPiece(pawn, "a2", false);
         Pawn p = new Pawn(testBoard.getSquare(1,2),false,testBoard);
         Square expected = testBoard.squareByDenotation("a2");
        assertEquals(expected,p.getPosition());
    }

    @Test
    void testFilteredLegals(){
        Board testBoard = new Board();
        testBoard.initLineUp();
        Piece p = testBoard.squareByDenotation("e2").getOccupier();
        p.updateLegals();
        assertTrue(p.filteredLegals().contains(testBoard.squareByDenotation("e3")));
    }


}




