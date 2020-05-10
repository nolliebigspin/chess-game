package schach.model;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testOccupancy() {
        Board testBoard = new Board();
        testBoard.addPiece("queen", "a1", false);

        assertTrue(testBoard.squareByDenotation("a1").isOccupied());
        assertFalse(testBoard.squareByDenotation("b5").isOccupied());
    }



    @Test
    void addPieceTest() {
        Board testBoard = new Board();
        testBoard.addPiece("queen", "a1", false);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        final PrintStream originalErr = System.err;
        System.setOut(new PrintStream(outContent));
        testBoard.addPiece("pawn", "a1", true);
        assertEquals("The given position is either occupied or it's an invalid backward move!", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void addInvalidPieceTest() {
        Board testBoard = new Board();
        testBoard.addPiece("queen", "a1", false);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        final PrintStream originalErr = System.err;
        System.setOut(new PrintStream(outContent));
        testBoard.addPiece("pawn", "a1", true);
        assertEquals("The given position is either occupied or it's an invalid backward move!", outContent.toString());
        System.setOut(originalOut);
    }


    @Test
    void testIsWhite(){
        Board testBoard = new Board();
        testBoard.addPiece("queen", "a1", false);
        assertFalse(testBoard.squareByDenotation("a1").getOccupier().isWhite);
        testBoard.printBoard();

    }


    @Test
    void allActiveBlackPiecesTest(){
        Board testBoard = new Board();
        testBoard.addPiece("queen", "a1", false);
        testBoard.addPiece("king", "b2", false);
        List<Piece> blackPieces = new ArrayList<>();
        blackPieces.add(testBoard.squareByDenotation("a1").getOccupier());
        blackPieces.add(testBoard.squareByDenotation("b2").getOccupier());
        assertEquals(blackPieces,testBoard.getActiveBlackPieces());

    }

    @Test
    void allActiveWhitePiecesTest(){
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c5", true);
        testBoard.addPiece("bishop", "h8", true);
        List<Piece> whitePieces = new ArrayList<>();
        whitePieces.add(testBoard.squareByDenotation("c5").getOccupier());
        whitePieces.add(testBoard.squareByDenotation("h8").getOccupier());
        assertEquals(whitePieces,testBoard.getActiveWhitePieces());

    }

    @Test
    void whitePiecesUnderAttack(){
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c2", true);
        testBoard.addPiece("bishop", "d3", true);
        testBoard.addPiece("pawn", "b2", true);
        testBoard.addPiece("pawn", "c3", true);
        testBoard.addPiece("pawn", "e2", true);
        testBoard.addPiece("pawn", "c4", true);
        testBoard.addPiece("pawn", "d2", true);
        testBoard.addPiece("queen", "c1", false);
        testBoard.addPiece("pawn", "e4", false);
        List<Square> blackUnderAttack = new ArrayList<>();
        blackUnderAttack.add(testBoard.getSquare(2,4));
        blackUnderAttack.add(testBoard.getSquare(2,3));
        blackUnderAttack.add(testBoard.getSquare(3,1));
        blackUnderAttack.add(testBoard.getSquare(3,5));
        blackUnderAttack.add(testBoard.getSquare(5,4));
        blackUnderAttack.add(testBoard.getSquare(5,3));
        assertEquals(blackUnderAttack,testBoard.getSquaresAttackedWhite());

    }


    @Test
    void BlackPiecesUnderAttack(){
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c2", true);
        testBoard.addPiece("bishop", "d3", true);
        testBoard.addPiece("pawn", "b2", true);
        testBoard.addPiece("pawn", "c3", true);
        testBoard.addPiece("pawn", "e2", true);
        testBoard.addPiece("pawn", "c4", true);
        testBoard.addPiece("pawn", "d2", true);
        testBoard.addPiece("queen", "c1", false);
        testBoard.addPiece("pawn", "d1", false);
        testBoard.addPiece("pawn", "b1", false);
        List<Square> blackUnderAttack = new ArrayList<>();
        blackUnderAttack.add(testBoard.getSquare(3,2));
        blackUnderAttack.add(testBoard.getSquare(4,2));
        blackUnderAttack.add(testBoard.getSquare(2,2));
        assertEquals(blackUnderAttack,testBoard.getSquaresAttackedBlack());

    }

    @Test
    void blackIsUnderAttackTest(){
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c2", true);
        testBoard.addPiece("pawn", "c7", false);
        Rook r1 = new Rook(testBoard.getSquare(3,2),true,testBoard);
        Rook r2 = new Rook(testBoard.getSquare(3,7),false,testBoard);
        assertTrue(testBoard.isUnderAttack("c2",false));

    }

    @Test
    void whiteIsUnderAttackTest(){
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c2", true);
        testBoard.addPiece("pawn", "c7", false);
        Rook r1 = new Rook(testBoard.getSquare(3,2),true,testBoard);
        Rook r2 = new Rook(testBoard.getSquare(3,7),false,testBoard);
        assertTrue(testBoard.isUnderAttack("c7",true));

    }

    // @Test
    //void squareByDenotationTest() {
    //    Board testBoard = new Board();

    //     assertNull(testBoard.squareByDenotation("a10"));
    //    assertNotNull(testBoard.squareByDenotation("a7"));

    //}

    //  @Test
    // void getSquareTest() {
    //     Board testBoard = new Board();
    //     testBoard.addPiece("bishop", "d1", false);
    //    assertEquals(testBoard.squareByDenotation("d1")getOccupier().getPosition(),testBoard.getSquare(4,1).);

    //}

    // @Test
    // void movePieceTest() throws java.lang.Exception {
    //   Board testBoard = new Board();
    //   testBoard.addPiece("pawn", "a2", true);
    //   testBoard.movePiece("c1","c2");
    //   ByteArrayOutputStream out= new ByteArrayOutputStream();
    //   System.setOut(new PrintStream(out));
    //   testBoard.movePiece("c1","c2");
    //  assertEquals("!Invalid Move: No Piece found!", out.toString());
        //assertSame( "!Invalid Move: No Piece found!",res);
    // }


}





