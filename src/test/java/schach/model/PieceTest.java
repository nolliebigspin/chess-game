package schach.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {


    @Test
    void move() {
        Board testBoard = new Board();
        testBoard.addPiece("king", "a1", true);
        testBoard.addPiece("pawn", "b3", true);
        King k = new King(testBoard.getSquare(1,1),true,testBoard);
        k.move(testBoard.getSquare(2,2));
        assertEquals(testBoard.squareByDenotation("b2").getOccupier(), k);
        assertFalse(testBoard.squareByDenotation("a1").isOccupied());

        //assertEquals(testBoard.getSquare(2,2),k.move(testBoard.getSquare(2,2)));

    }

    @Test
    void acceptMove() {
    }

    @Test
    void refuseMove() {
    }

    @Test
    void getLegalSquares() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "a7", true);
        Pawn p = new Pawn(testBoard.getSquare(1,7),true,testBoard);
        List<Square> legalNextSquares = new ArrayList<Square>();
        legalNextSquares.add(testBoard.getSquare(1,8));
        assertEquals(legalNextSquares,p.getLegalSquares());

    }

    @Test
    void printLegals() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "a7", true);
        Pawn p = new Pawn(testBoard.getSquare(1,7),true,testBoard);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        final PrintStream originalErr = System.err;
        System.setOut(new PrintStream(outContent));
        p.printLegals();
        assertEquals("a8", outContent.toString());
        System.setOut(originalOut);


    }

    @Test
    void doPromotionNotOnEdge() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "a7", true);
        Pawn p = new Pawn(testBoard.getSquare(1,7),true,testBoard);
        assertNotNull(testBoard.squareByDenotation("a7").getOccupier());
        testBoard.printBoard();
        p.doPromotion("Q",testBoard.getSquare(1,7));
        testBoard.printBoard();
        String actual = testBoard.squareByDenotation("a7").getOccupier().print();
        String expected = "\u2659";
        assertEquals(expected, actual);
    }

    @Test
    void doPromotionQueen() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "a7", true);
        Pawn p = new Pawn(testBoard.getSquare(1,7),true,testBoard);
        p.move(testBoard.getSquare(1,8));
        //assertNull(testBoard.squareByDenotation("a7").getOccupier());
        testBoard.printBoard();
        p.doPromotion("Q",testBoard.getSquare(1,8));
        testBoard.printBoard();
        String actual = testBoard.squareByDenotation("a8").getOccupier().print();
        String expected = "\u2655";
        assertEquals(expected, actual);
    }

    @Test
    void doPromotionRook() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "a7", true);
        Pawn p = new Pawn(testBoard.getSquare(1,7),true,testBoard);
        p.move(testBoard.getSquare(1,8));
        testBoard.printBoard();
        p.doPromotion("R",testBoard.getSquare(1,8));
        testBoard.printBoard();
        String actual = testBoard.squareByDenotation("a8").getOccupier().print();
        String expected = "\u2656";
        assertEquals(expected, actual);
    }

    @Test
    void doPromotionKnight() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "a2", false);
        Pawn p = new Pawn(testBoard.getSquare(1,2),false,testBoard);
        p.move(testBoard.getSquare(1,1));
        testBoard.printBoard();
        p.doPromotion("N",testBoard.getSquare(1,1));
        testBoard.printBoard();
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u265E";
        assertEquals(expected, actual);
    }

    @Test
    void doPromotionBishop() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "a2", false);
        Pawn p = new Pawn(testBoard.getSquare(1,2),false,testBoard);
        p.move(testBoard.getSquare(1,1));
        testBoard.printBoard();
        p.doPromotion("B",testBoard.getSquare(1,1));
        testBoard.printBoard();
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u265D";
        assertEquals(expected, actual);
    }


    @Test
    void refuseMoveTest() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "a3", true);
        Pawn p = new Pawn(testBoard.getSquare(1,3),true,testBoard);
        p.move(testBoard.getSquare(1,2));
        assertEquals("!Move is invalid\n", outContent.toString());
        System.setOut(originalOut);
    }


    @Test
    void getPositionTest(){
         Board testBoard = new Board();
         testBoard.addPiece("pawn", "a2", false);
         Pawn p = new Pawn(testBoard.getSquare(1,2),false,testBoard);
         Square expected = testBoard.squareByDenotation("a2");
        assertEquals(expected,p.getPosition());
    }




}




