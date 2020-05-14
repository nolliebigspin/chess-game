package schach.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the concrete piece Class Knight
 */
class KnightTest {

    private String bishop = "bishop";
    private String pawn = "pawn";
    private String king = "king";
    private String queen = "queen";
    private String knight = "knight";

    @Test
    void scenarioWhite1() {
        Board testBoard = new Board();
        testBoard.addPiece(knight, "d4", true);
        testBoard.addPiece(bishop, "c6", false);
        testBoard.addPiece(pawn, "c2", false);
        testBoard.addPiece(king, "e6", false);
        testBoard.addPiece(queen, "e2", false);
        testBoard.addPiece(king, "h8", true);
        Knight Knight1 = new Knight(testBoard.getSquare(4,4),true,testBoard);
        Knight1.updateLegals();
        List<Square> Actual =Knight1.getLegalNextSquares();
        Square enemy1 = testBoard.getSquare(5 , 6);
        Square enemy2 = testBoard.getSquare(3 , 6);
        Square enemy3 = testBoard.getSquare(5 , 2);
        Square enemy4 = testBoard.getSquare(3 , 2);
        Square empty1 = testBoard.getSquare(6 , 5);
        Square empty2 = testBoard.getSquare(6 , 3);
        Square empty3 = testBoard.getSquare(2 , 5);
        Square empty4 = testBoard.getSquare(2 , 3);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy2);
        legals.add(enemy1);
        legals.add(empty1);
        legals.add(empty2);
        legals.add(enemy3);
        legals.add(enemy4);
        legals.add(empty4);
        legals.add(empty3);

        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }
    @Test
    void scenarioWhite2() {
        Board testBoard = new Board();
        testBoard.addPiece(knight, "d4", true);
        testBoard.addPiece(bishop, "b5", false);
        testBoard.addPiece(pawn, "b3", false);
        testBoard.addPiece(king, "f5", false);
        testBoard.addPiece(king, "c8", true);
        testBoard.addPiece(queen, "f3", false);
        Knight Knight1 = new Knight(testBoard.getSquare(4,4),true,testBoard);
        Knight1.updateLegals();
        List<Square> Actual =Knight1.getLegalNextSquares();
        Square enemy1 = testBoard.getSquare(5 , 6);
        Square enemy2 = testBoard.getSquare(3 , 6);
        Square enemy3 = testBoard.getSquare(5 , 2);
        Square enemy4 = testBoard.getSquare(3 , 2);
        Square empty1 = testBoard.getSquare(6 , 5);
        Square empty2 = testBoard.getSquare(6 , 3);
        Square empty3 = testBoard.getSquare(2 , 5);
        Square empty4 = testBoard.getSquare(2 , 3);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy2);
        legals.add(enemy1);
        legals.add(empty1);
        legals.add(empty2);
        legals.add(enemy3);
        legals.add(enemy4);
        legals.add(empty4);
        legals.add(empty3);

        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    @Test
    void scenarioBlack() {
        Board testBoard = new Board();
        testBoard.addPiece(knight, "d4", false);
        testBoard.addPiece(bishop, "b5", true);
        testBoard.addPiece(pawn, "b3", true);
        testBoard.addPiece(king, "f5", true);
        testBoard.addPiece(queen, "f3", true);
        testBoard.addPiece(king, "c8", false);
        Knight Knight1 = new Knight(testBoard.getSquare(4,4),false,testBoard);
        Knight1.updateLegals();
        List<Square> Actual =Knight1.getLegalNextSquares();
        Square enemy1 = testBoard.getSquare(5 , 6);
        Square enemy2 = testBoard.getSquare(3 , 6);
        Square enemy3 = testBoard.getSquare(5 , 2);
        Square enemy4 = testBoard.getSquare(3 , 2);
        Square empty1 = testBoard.getSquare(6 , 5);
        Square empty2 = testBoard.getSquare(6 , 3);
        Square empty3 = testBoard.getSquare(2 , 5);
        Square empty4 = testBoard.getSquare(2 , 3);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy2);
        legals.add(enemy1);
        legals.add(empty1);
        legals.add(empty2);
        legals.add(enemy3);
        legals.add(enemy4);
        legals.add(empty4);
        legals.add(empty3);

        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }

    // test the print method
    // white knight
    @Test
    void printTestWhite() {
        Board testBoard = new Board();
        testBoard.addPiece(knight, "a1", true);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u2658";
        assertEquals(expected, actual);
    }


    // black knight
    @Test
    void printTestBlack() {
        Board testBoard = new Board();
        testBoard.addPiece(knight, "a1", false);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u265E";
        assertEquals(expected, actual);
    }


}

