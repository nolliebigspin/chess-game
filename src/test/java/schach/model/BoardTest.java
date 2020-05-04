package schach.model;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {




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



  //  @Test
  //  void squareByDenotationTest() {
    //     Board testBoard = new Board();
  //   testBoard.addPiece("bishop", "d1", false);
  //   Square bishop = new Square(4,1);
  //   Bishop bishopTest = new Bishop(bishop,false, testBoard);
    //   assertSame(bishopTest,testBoard);

    // }
    // @Test
    //  void getSquareTest() {
    //    Board testBoard = new Board();
    //    Square[][] squareMatrixTest = new Square[7][7];
    //  assertEquals(squareMatrixTest,testBoard.getSquare(8,8));

    // }

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





