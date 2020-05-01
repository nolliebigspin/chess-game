package schach.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {


    @BeforeEach
    void setUp() throws Exception {
        Board TestBoard = new Board();
    }

    @Test
    void testOccupancy() {
        Board testBoard = new Board();
        testBoard.addPiece("queen", "a1", false);

        assertTrue(testBoard.squareByDenotation("a1").isOccupied());
    }

    @Test
    void testBoardDenotation() {
        Board testBoard = new Board();
        testBoard.addPiece("king", "b1", true);
        String actual = testBoard.squareByDenotation("b1").getOccupier().print();
        String expected = "\u2654";
        assertEquals(expected, actual);
    }


    @Test
    void printBoardTest()throws Exception {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c1", false);
        testBoard.printBoard();
        ByteArrayOutputStream out= new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        System.out.print(" ");
        assertEquals(" ", out.toString());
    }



    @Test
    void squareByDenotationTest() {
        Board testBoard = new Board();
        testBoard.addPiece("bishop", "d1", false);
        Square[][] squareMatrixTest = new Square[4][1];
        Bishop bishopTest = new Bishop(squareMatrixTest,false,testBoard);

        assertSame(Square.get,testBoard.squareByDenotation("d1"));
    }

    @Test
    void getSquareTest() {
    }

    @Test
    void movePieceTest() {
    }
}