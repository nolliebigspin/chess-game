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


    // Test up right scenario for a white pawn
    @Test
    void UpdateLegalsTestCheckUpRight1() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "f5", true);
        testBoard.addPiece("pawn", "f6", true);
        testBoard.addPiece("pawn", "g6", false);
        Pawn whiteEats = new Pawn(testBoard.getSquare(6,5),true,testBoard);
        int oneUp = 1;
        int right = 1;
        Square aheadRight = testBoard.getSquare(6 + right, 5 + oneUp);
        List<Square> legalNextSquaresTest = new ArrayList<Square>();
        legalNextSquaresTest.add(aheadRight);
        // checks occupancy of the square up right for a possible enemy
         assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("f5").getOccupier().getLegalNextSquares());
        List<Square> Actual =whiteEats.legalNextSquares;
        Square enemy = testBoard.getSquare(7 , 6);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy);
        // test the CheckUpRight method
        assertEquals(legals,Actual);
    }


    // Test up right scenario for a black pawn
    @Test
    void UpdateLegalsTestCheckUpRight2() {
        Board testBoard = new Board();
        testBoard.addPiece("pawn", "f7", false);
        testBoard.addPiece("pawn", "f6", false);
        testBoard.addPiece("pawn", "e6", true);
        Pawn whiteEats = new Pawn(testBoard.getSquare(6,7),false,testBoard);
        int oneUp = -1;
        int right = -1;
        Square aheadRight = testBoard.getSquare(6 + right, 7 + oneUp);
        List<Square> legalNextSquaresTest = new ArrayList<Square>();
        legalNextSquaresTest.add(aheadRight);
        // checks occupancy of the square up right for a possible enemy
        assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("f7").getOccupier().getLegalNextSquares());
        List<Square> Actual =whiteEats.legalNextSquares;
        Square enemy = testBoard.getSquare(5 , 6);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy);
        // test the CheckUpRight method
        assertEquals(legals,Actual);
    }











 }



























