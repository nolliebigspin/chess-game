package schach.model;
import org.junit.jupiter.api.Test;
import javax.lang.model.type.NullType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    void printBoardTest()throws Exception {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "c1", false);
        testBoard.printBoard();
        ByteArrayOutputStream out= new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        System.out.print(" ");
        // assertEquals(" ", out.toString());
    }



    @Test
    void squareByDenotationTest() {
        Board testBoard = new Board();

        assertNull(testBoard.squareByDenotation("a10"));
        assertNotNull(testBoard.squareByDenotation("a7"));

    }

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





