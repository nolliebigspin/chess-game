package schach.model;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the concrete piece Class Pawn
 */
class PawnTest {

    private String pawn = "pawn";
    private String king = "king";

    @Test
    void UpdateLegalsTestCheckAhead1() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a2", true);
        Pawn p = new Pawn(testBoard.getSquare(1,2),true,testBoard);
        testBoard.addPiece(king, "a8", true);
        testBoard.addPiece(king, "h8", false);
        p.updateLegals();
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
        testBoard.addPiece(pawn, "b3", true);
        testBoard.addPiece(king, "a8", true);
        testBoard.addPiece(king, "h8", false);
        Pawn p = new Pawn(testBoard.getSquare(2,3),true,testBoard);
        p.updateLegals();
        int oneUp = 1;
        Square ahead = testBoard.getSquare(2, 3 + oneUp);
        List<Square> legalNextSquaresTest = new ArrayList<Square>();
        legalNextSquaresTest.add(ahead);
        assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("b3").getOccupier().getLegalNextSquares());
    }


    @Test
    void UpdateLegalsTestCheckAhead5() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a7", false);
        Pawn p = new Pawn(testBoard.getSquare(1,7),false,testBoard);
        testBoard.addPiece(king, "c8", true);
        testBoard.addPiece(king, "h8", false);
        p.updateLegals();
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
        testBoard.addPiece(pawn, "d5", false);
        Pawn p = new Pawn(testBoard.getSquare(4,5),false,testBoard);
        testBoard.addPiece(king, "c8", true);
        testBoard.addPiece(king, "h8", false);
        p.updateLegals();
        int oneUp = -1;
        Square ahead = testBoard.getSquare(4, 5 + oneUp);
        List<Square> legalNextSquaresTest = new ArrayList<Square>();
        legalNextSquaresTest.add(ahead);
        assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("d5").getOccupier().getLegalNextSquares());
    }

    // Test up right scenario for a white pawn
    @Test
    void UpdateLegalsTestCheckUpRight1() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "f5", true);
        testBoard.addPiece(pawn, "f6", true);
        testBoard.addPiece(pawn, "g6", false);
        testBoard.addPiece(king, "c8", true);
        testBoard.addPiece(king, "h8", false);
        Pawn whiteEats = new Pawn(testBoard.getSquare(6,5),true,testBoard);
        whiteEats.updateLegals();
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
        testBoard.addPiece(pawn, "f7", false);
        testBoard.addPiece(pawn, "f6", false);
        testBoard.addPiece(pawn, "e6", true);
        testBoard.addPiece(king, "c8", true);
        testBoard.addPiece(king, "h8", false);
        Pawn whiteEats = new Pawn(testBoard.getSquare(6,7),false,testBoard);
        whiteEats.updateLegals();
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

    // Test up left scenario for a white pawn
    @Test
    void UpdateLegalsTestCheckUpLeft1() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "g3", true);
        testBoard.addPiece(pawn, "g4", true);
        testBoard.addPiece(pawn, "f4", false);
        testBoard.addPiece(king, "c8", true);
        testBoard.addPiece(king, "h8", false);
        Pawn whiteEats = new Pawn(testBoard.getSquare(7,3),true,testBoard);
        whiteEats.updateLegals();
        int oneUp = 1;
        int left = -1;
        Square aheadLeft = testBoard.getSquare(7 + left, 3 + oneUp);
        List<Square> legalNextSquaresTest = new ArrayList<Square>();
        legalNextSquaresTest.add(aheadLeft);
        // checks occupancy of the square up left for a possible enemy
        assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("g3").getOccupier().getLegalNextSquares());
        List<Square> Actual =whiteEats.legalNextSquares;
        Square enemy = testBoard.getSquare(6 , 4);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    // Test up left scenario with a possible move forward for a black pawn
    @Test
    void UpdateLegalsTestCheckUpLeft2() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a5", false);
        testBoard.addPiece(pawn, "a4", false);
        testBoard.addPiece(pawn, "b3", true);
        testBoard.addPiece(king, "c8", true);
        testBoard.addPiece(king, "h8", false);
        Pawn whiteEats = new Pawn(testBoard.getSquare(1,4),false,testBoard);
        whiteEats.updateLegals();
        int oneUp = -1;
        int left = 1;
        Square aheadLeft = testBoard.getSquare(1 + left, 4 + oneUp);
        Square ahead = testBoard.getSquare(1 , 3);
        List<Square> legalNextSquaresTest = new ArrayList<Square>();
        legalNextSquaresTest.add(ahead);
        legalNextSquaresTest.add(aheadLeft);
        // checks occupancy of the square up left for a possible enemy
        assertEquals(legalNextSquaresTest,testBoard.squareByDenotation("a4").getOccupier().getLegalNextSquares());
        List<Square> Actual =whiteEats.legalNextSquares;
        Square enemy = testBoard.getSquare(2 , 3);
        Square empty = testBoard.getSquare(1 , 3);
        List<Square> legals = new ArrayList<Square>();
        legals.add(empty);
        legals.add(enemy);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }



    // test the print method
    // white pawn
    @Test
    void printTestWhite() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a2", true);
        String actual = testBoard.squareByDenotation("a2").getOccupier().print();
        String expected = "\u2659";
        assertEquals(expected, actual);
    }


    // black pawn
    @Test
    void printTestBlack() {
        Board testBoard = new Board();
        testBoard.addPiece(pawn, "a3", false);
        String actual = testBoard.squareByDenotation("a3").getOccupier().print();
        String expected = "\u265F";
        assertEquals(expected, actual);
    }


}
































