package schach.model;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for the Board class
 */
class BoardTest {

    private String bishop = "bishop";
    private String pawn = "pawn";
    private String king = "king";
    private String queen = "queen";
    private String rook = "rook";

    @Test
    void testOccupancy() {
        Board testBoard = new Board();
        testBoard.addPiece(queen, "a1", false);

        assertTrue(testBoard.squareByDenotation("a1").isOccupied());
        assertFalse(testBoard.squareByDenotation("b5").isOccupied());
    }



    @Test
    void addPieceTest() {
        Board testBoard = new Board();
        testBoard.addPiece(queen, "a1", false);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        testBoard.addPiece(pawn, "a1", true);
        assertEquals("The given position is either occupied or it's an invalid backward move!\r\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void addInvalidPieceTest() {
        Board testBoard = new Board();
        testBoard.addPiece(queen, "a1", false);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        testBoard.addPiece(pawn, "a1", true);
        assertEquals("The given position is either occupied or it's an invalid backward move!\r\n", outContent.toString());
        System.setOut(originalOut);
    }


    @Test
    void testIsWhite(){
        Board testBoard = new Board();
        testBoard.addPiece(queen, "a1", false);
        assertFalse(testBoard.squareByDenotation("a1").getOccupier().isWhite);
        testBoard.printBoard();

    }


    @Test
    void allActiveBlackPiecesTest(){
        Board testBoard = new Board();
        testBoard.addPiece(queen, "a1", false);
        testBoard.addPiece(king, "b2", false);
        List<Piece> blackPieces = new ArrayList<>();
        blackPieces.add(testBoard.squareByDenotation("a1").getOccupier());
        blackPieces.add(testBoard.squareByDenotation("b2").getOccupier());
        assertEquals(blackPieces,testBoard.getActiveBlackPieces());

    }

    @Test
    void allActiveWhitePiecesTest(){
        Board testBoard = new Board();
        testBoard.addPiece(rook, "c5", true);
        testBoard.addPiece(bishop, "h8", true);
        List<Piece> whitePieces = new ArrayList<>();
        whitePieces.add(testBoard.squareByDenotation("c5").getOccupier());
        whitePieces.add(testBoard.squareByDenotation("h8").getOccupier());
        assertEquals(whitePieces,testBoard.getActiveWhitePieces());

    }

    @Test
    void blackIsUnderAttackTest(){
        Board testBoard = new Board();
        testBoard.addPiece(rook, "c2", true);
        testBoard.addPiece(rook, "c7", false);
        testBoard.addPiece(king, "g4", true);
        testBoard.addPiece(king, "h5", false);
        Rook r1 = new Rook(testBoard.getSquare(3,2),true,testBoard);
        Rook r2 = new Rook(testBoard.getSquare(3,7),false,testBoard);
        r1.updateLegals();
        r2.updateLegals();
        assertTrue(testBoard.isUnderAttack("c2",false));

    }

    @Test
    void whiteIsUnderAttackTest(){
        Board testBoard = new Board();
        testBoard.addPiece(rook, "c2", true);
        testBoard.addPiece(rook, "c7", false);
        testBoard.addPiece(king, "g4", true);
        testBoard.addPiece(king, "h5", false);
        Rook r1 = new Rook(testBoard.getSquare(3,2),true,testBoard);
        Rook r2 = new Rook(testBoard.getSquare(3,7),false,testBoard);
        r1.updateLegals();
        r2.updateLegals();
        assertTrue(testBoard.isUnderAttack("c7",true));

    }

    @Test
    void whiteIsNotUnderAttackTest(){
        Board testBoard = new Board();
        testBoard.addPiece(rook, "c2", true);
        testBoard.addPiece(rook, "b7", false);
        new Rook(testBoard.getSquare(3,2),true,testBoard);
        new Rook(testBoard.getSquare(2,7),false,testBoard);
        assertFalse(testBoard.isUnderAttack("b7",true));

    }

    @Test
    void beatenTestEmpty(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        Board testBoard = new Board();
        testBoard.addPiece(rook, "c2", true);
        testBoard.addPiece(pawn, "c7", false);
        testBoard.printBeaten();
        assertEquals("no pieces beaten yet\r\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void beatenTes(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        Board testBoard = new Board();
        testBoard.addPiece(rook, "c2", true);
        testBoard.addPiece(rook, "c7", false);
        testBoard.addPiece(king, "g4", true);
        testBoard.addPiece(king, "h5", false);
        Rook r1 = new Rook(testBoard.getSquare(3,2),true,testBoard);
        new Rook(testBoard.getSquare(3,7),false,testBoard);
        r1.updateLegals();
        r1.move(testBoard.getSquare(3,7));
        testBoard.printBeaten();
        assertEquals("\u265C\r\n", outContent.toString());
        System.setOut(originalOut);

    }

    @Test
    void squareByDenotationNull(){
        Board testBoard = new Board();
        assertNull(testBoard.squareByDenotation("a9"));

    }


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


    //@Test
    //void movePieceTest(){
        //final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        //final PrintStream originalOut = System.out;
        //System.setOut(new PrintStream(outContent));
        //Board testBoard = new Board();
        //testBoard.addPiece(pawn, "c2", true);
        //testBoard.addPiece(pawn, "c1", true);
        //testBoard.addPiece(king, "g4", true);
        //testBoard.addPiece(king, "h5", false);
        //Pawn p2 = new Pawn(testBoard.getSquare(3,2),true,testBoard);
        //Pawn p1 = new Pawn(testBoard.getSquare(3,1),true,testBoard);
        //p1.updateLegals();
        //p2.updateLegals();
        //p1.move(testBoard.getSquare(3,2));
        //assertEquals("!Invalid Move: Square is already occupied\r\n", outContent.toString());
       // System.setOut(originalOut);
       //}

      @Test
      void PrintLegalsTest(){
      final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      final PrintStream originalOut = System.out;
      System.setOut(new PrintStream(outContent));
      Board testBoard = new Board();
      testBoard.addPiece(pawn, "c3", true);
      testBoard.addPiece(king, "g4", true);
      testBoard.addPiece(king, "h5", false);
      Pawn p1 = new Pawn(testBoard.getSquare(3,3),true,testBoard);
      p1.updateLegals();
      testBoard.printLegals();
      assertEquals("c4", outContent.toString());
       System.setOut(originalOut);
    }

    }












