package schach.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the concrete piece Class Bishop
 */
class BishopTest {

    private String bishop = "bishop";
    private String pawn = "pawn";
    private String king = "king";
    private String queen = "queen";


    // Test up right scenario with a possible move forward for a black pawn
    @Test
    void checkUpRightWhite1() {
        Board testBoard = new Board();
        testBoard.addPiece(bishop, "d3", true);
        testBoard.addPiece(bishop, "g6", true);
        testBoard.addPiece(pawn, "c4", true);
        testBoard.addPiece(pawn, "c2", true);
        testBoard.addPiece(pawn, "e2", true);
        testBoard.addPiece(king, "h8", true);
        testBoard.addPiece(king, "a8", false);;
        Bishop b1 = new Bishop(testBoard.getSquare(4,3),true,testBoard);
        b1.updateLegals();
        List<Square> Actual =b1.getLegalNextSquares();
        Square empty1 = testBoard.getSquare(5 , 4);
        Square empty2 = testBoard.getSquare(6 , 5);
        List<Square> legals = new ArrayList<Square>();
        legals.add(empty1);
        legals.add(empty2);
        legals.add(testBoard.squareByDenotation("e2"));
        legals.add(testBoard.squareByDenotation("c2"));
        legals.add(testBoard.squareByDenotation("c4"));
        legals.add(testBoard.squareByDenotation("g6"));
        // test the CheckUpLeft method
        assertTrue((legals.containsAll(Actual)) && Actual.containsAll(legals));
    }


    // Test up right scenario with a possible move forward for a black pawn
    @Test
    void checkUpRightWhite2() {
        Board testBoard = new Board();
        testBoard.addPiece(bishop, "d3", true);
        testBoard.addPiece(bishop, "g6", false);
        testBoard.addPiece(pawn, "c4", true);
        testBoard.addPiece(pawn, "c2", true);
        testBoard.addPiece(pawn, "e2", true);
        testBoard.addPiece(king, "h8", true);
        testBoard.addPiece(king, "a8", false);
        Bishop b1 = new Bishop(testBoard.getSquare(4,3),true,testBoard);
        b1.updateLegals();
        List<Square> Actual =b1.getLegalNextSquares();
        Square empty1 = testBoard.getSquare(5 , 4);
        Square empty2 = testBoard.getSquare(6 , 5);
        Square enemy = testBoard.getSquare(7 , 6);
        List<Square> legals = new ArrayList<Square>();
        legals.add(empty1);
        legals.add(empty2);
        legals.add(enemy);
        legals.add(testBoard.squareByDenotation("e2"));
        legals.add(testBoard.squareByDenotation("c2"));
        legals.add(testBoard.squareByDenotation("c4"));
        // test the CheckUpLeft method
        assertTrue((legals.containsAll(Actual)) && Actual.containsAll(legals));
    }

    // Test up right scenario with a possible move forward for a black pawn
    @Test
    void checkUpLeftWhite() {
        Board testBoard = new Board();
        testBoard.addPiece(bishop, "d3", true);
        testBoard.addPiece(bishop, "a6", false);
        testBoard.addPiece(pawn, "e4", true);
        testBoard.addPiece(pawn, "c2", true);
        testBoard.addPiece(pawn, "e2", true);
        testBoard.addPiece(king, "h8", true);
        testBoard.addPiece(king, "a8", false);

        Bishop b1 = new Bishop(testBoard.getSquare(4,3),true,testBoard);
        b1.updateLegals();
        List<Square> Actual =b1.getLegalNextSquares();
        Square empty1 = testBoard.getSquare(3 , 4);
        Square empty2 = testBoard.getSquare(2 , 5);
        Square enemy = testBoard.getSquare(1 , 6);
        List<Square> legals = new ArrayList<Square>();
        legals.add(empty1);
        legals.add(empty2);
        legals.add(enemy);
        legals.add(testBoard.squareByDenotation("e2"));
        legals.add(testBoard.squareByDenotation("c2"));
        legals.add(testBoard.squareByDenotation("e4"));
        // test the CheckUpLeft method
        assertTrue((legals.containsAll(Actual)) && Actual.containsAll(legals));
    }



    @Test
    void checkBehindLeftRightWhite() {
        Board testBoard = new Board();
        testBoard.addPiece(bishop, "d3", true);
        testBoard.addPiece(bishop, "c4", true);
        testBoard.addPiece(pawn, "e4", true);
        testBoard.addPiece(king, "c2", false);
        testBoard.addPiece(king, "h8", true);

        Bishop b1 = new Bishop(testBoard.getSquare(4,3),true,testBoard);
        b1.updateLegals();
        List<Square> Actual =b1.getLegalNextSquares();
        Square empty = testBoard.getSquare(5 , 2);
        Square empty2 = testBoard.getSquare(6 , 1);
        Square enemy = testBoard.getSquare(3 , 2);
        List<Square> legals = new ArrayList<Square>();
        legals.add(empty);
        legals.add(empty2);
        legals.add(enemy);
        legals.add(testBoard.squareByDenotation("c4"));
        legals.add(testBoard.squareByDenotation("e4"));
        // test the CheckUpLeft method
        assertTrue((legals.containsAll(Actual)) && Actual.containsAll(legals));
    }



    @Test
    void checkBehindLeftRightBlack() {
        Board testBoard = new Board();
        testBoard.addPiece(bishop, "d3", false);
        testBoard.addPiece(bishop, "c4", true);
        testBoard.addPiece(pawn, "e4", true);
        testBoard.addPiece(king, "c2", true);
        testBoard.addPiece(king, "h8", false);
        testBoard.addPiece(queen, "e2", true);
        Bishop b1 = new Bishop(testBoard.getSquare(4,3),false,testBoard);
        b1.updateLegals();
        List<Square> Actual =b1.getLegalNextSquares();
        Square enemy1 = testBoard.getSquare(5 , 4);
        Square enemy2 = testBoard.getSquare(5 , 2);
        Square enemy3 = testBoard.getSquare(3 , 4);
        Square enemy4 = testBoard.getSquare(3 , 2);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy1);
        legals.add(enemy3);
        legals.add(enemy2);
        legals.add(enemy4);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    @Test
    void checkBehindRight() {
        Board testBoard = new Board();
        testBoard.addPiece(bishop, "d3", true);
        testBoard.addPiece(queen, "e2", true);
        testBoard.addPiece(pawn, "c4", true);
        testBoard.addPiece(king, "e4", true);
        testBoard.printBoard();
        Bishop b1 = new Bishop(testBoard.getSquare(4,3),true,testBoard);
        b1.updateLegals();
        List<Square> Actual =b1.getLegalNextSquares();
        Square empty1 = testBoard.getSquare(3 , 2);
        Square empty2 = testBoard.getSquare(2 , 1);

        List<Square> legals = new ArrayList<Square>();
        legals.add(empty1);
        legals.add(empty2);
        legals.add(testBoard.squareByDenotation("e2"));
        legals.add(testBoard.squareByDenotation("e4"));
        legals.add(testBoard.squareByDenotation("c4"));
        // test the CheckUpLeft method
        assertTrue((legals.containsAll(Actual)) && Actual.containsAll(legals));
    }


    // test the print method
    // white bishop
    @Test
    void printTestWhite() {
        Board testBoard = new Board();
        testBoard.addPiece(bishop, "a1", true);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u2657";
        assertEquals(expected, actual);
    }


    // black bishop
    @Test
    void printTestBlack() {
        Board testBoard = new Board();
        testBoard.addPiece(bishop, "a1", false);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u265D";
        assertEquals(expected, actual);
    }


}