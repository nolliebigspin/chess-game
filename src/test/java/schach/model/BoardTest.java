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
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        testBoard.addPiece("pawn", "a1", true);
        assertEquals("The given position is either occupied or it's an invalid backward move!\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void addInvalidPieceTest() {
        Board testBoard = new Board();
        testBoard.addPiece("queen", "a1", false);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        testBoard.addPiece("pawn", "a1", true);
        assertEquals("The given position is either occupied or it's an invalid backward move!\n", outContent.toString());
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
        testBoard.addPiece("rook", "c7", false);
        Rook r1 = new Rook(testBoard.getSquare(3,2),true,testBoard);
        Rook r2 = new Rook(testBoard.getSquare(3,7),false,testBoard);
        assertTrue(testBoard.isUnderAttack("c2",false));

    }

    @Test
    void whiteIsUnderAttackTest(){
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c2", true);
        testBoard.addPiece("rook", "c7", false);
        Rook r1 = new Rook(testBoard.getSquare(3,2),true,testBoard);
        Rook r2 = new Rook(testBoard.getSquare(3,7),false,testBoard);
        assertTrue(testBoard.isUnderAttack("c7",true));

    }

    @Test
    void whiteIsNotUnderAttackTest(){
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c2", true);
        testBoard.addPiece("rook", "b7", false);
        Rook r1 = new Rook(testBoard.getSquare(3,2),true,testBoard);
        Rook r2 = new Rook(testBoard.getSquare(2,7),false,testBoard);
        assertFalse(testBoard.isUnderAttack("b7",true));

    }

    @Test
    void beatenTestEmpty(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c2", true);
        testBoard.addPiece("pawn", "c7", false);
        testBoard.printBeaten();
        assertEquals("no pieces beaten yet\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void beatenTes(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c2", true);
        testBoard.addPiece("rook", "c7", false);
        Rook r1 = new Rook(testBoard.getSquare(3,2),true,testBoard);
        Rook r2 = new Rook(testBoard.getSquare(3,7),false,testBoard);
        r1.move(testBoard.getSquare(3,7));
        testBoard.printBeaten();
        assertEquals("\u265C\n", outContent.toString());
        System.setOut(originalOut);

    }

    @Test
    void squareByDenotationNull(){
        Board testBoard = new Board();
        assertNull(testBoard.squareByDenotation("a9"));

    }

    //@Test
    //void printMatrixTest(){
      //  Board testBoard = new Board();
       // Square[][] squareMatrix = new Square[8][8];
    //    assertArrayEquals(squareMatrix,testBoard.getSquareMatrix());
    //}
    @Test
    void initLineUpTest(){
        Board testBoard = new Board();
        testBoard.initLineUp();
        assertTrue(testBoard.squareByDenotation("a1").getOccupier().isWhite);
    }

    @Test
    void addWrongPieceTest(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        Board testBoard = new Board();
        testBoard.addPiece("jackass", "c2", true);
        assertEquals("no valid piece: jackass", outContent.toString());
        System.setOut(originalOut);

    }


}





