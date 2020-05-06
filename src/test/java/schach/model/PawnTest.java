package schach.model;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void UpdateLegalsTestCheckAhead1() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "a2", true);
        int oneUp = 1;
        int secondRow = 4;
        Square ahead = testBoard.getSquare(1, 2 + oneUp);
        Square aheadTwice = testBoard.getSquare(1,secondRow);
        List<Square> legalNextSquaresTest = new ArrayList<Square>();
        legalNextSquaresTest.add(aheadTwice);
        legalNextSquaresTest.add(ahead);
        assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("a2").getOccupier().getLegalNextSquares());
    }

    @Test
    void UpdateLegalsTestCheckAhead2() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "b3", true);
        int oneUp = 1;
        Square ahead = testBoard.getSquare(2, 3 + oneUp);
        List<Square> legalNextSquaresTest = new ArrayList<Square>();
        legalNextSquaresTest.add(ahead);
        assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("b3").getOccupier().getLegalNextSquares());
    }

    //in this scenario the legal moves of the pawn at square c4 should be empty
    //@Test
    // void UpdateLegalsTestCheckAhead3() throws Exception {
    //    Board testBoard = new Board();
    //    testBoard.addPiece("pawn", "c4", true);
    //   testBoard.addPiece("pawn", "c5", false);
    //   int oneUp = 1;
    //   Square ahead = testBoard.getSquare(3, 4 + oneUp);
    //    List<Square> legalNextSquaresTest = new ArrayList<Square>();
    //   assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("c4").getOccupier().getLegalNextSquares());
    //}

    // test black pawn allowed forward movements at the initial start  position
    @Test
    void UpdateLegalsTestCheckAhead5() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "a7", false);
        int oneUp = -1;
        int secondRow = 5;
        Square ahead = testBoard.getSquare(1, 7 + oneUp);
        Square aheadTwice = testBoard.getSquare(1,secondRow);
        List<Square> legalNextSquaresTest = new ArrayList<Square>();
        legalNextSquaresTest.add(aheadTwice);
        legalNextSquaresTest.add(ahead);
        assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("a7").getOccupier().getLegalNextSquares());
    }

    // test black pawn allowed forward movements at any square except the initial one
    @Test
    void UpdateLegalsTestCheckAhead6() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "d5", false);
        int oneUp = -1;
        Square ahead = testBoard.getSquare(4, 5 + oneUp);
        List<Square> legalNextSquaresTest = new ArrayList<Square>();
        legalNextSquaresTest.add(ahead);
        assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("d5").getOccupier().getLegalNextSquares());
    }

// test black pawn allowed forward movements at the border

//  @Test
//void UpdateLegalsTestCheckAhead7()  {
//    Board testBoard = new Board();
//    testBoard.addPiece("pawn", "d1", false);
//    int oneUp = -1;
//   Square ahead = testBoard.getSquare(4, 1 + oneUp);
//   List<Square> legalNextSquaresTest = new ArrayList<Square>();
//   assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
//       testBoard.squareByDenotation("d5").getOccupier().getLegalNextSquares();
//   });
// }



























