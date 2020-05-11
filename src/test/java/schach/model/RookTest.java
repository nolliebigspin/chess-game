package schach.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    // white rook
    @Test
    void scenarioWhite1() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "g5", true);
        testBoard.addPiece("bishop", "d5", false);
        testBoard.addPiece("king", "g7", true);
        testBoard.addPiece("king", "g4", false);
        Rook r1 = new Rook(testBoard.getSquare(7,5),true,testBoard);
        r1.updateLegals();
        List<Square> Actual =r1.getLegalNextSquares();
        Square empty1 = testBoard.getSquare(8 , 5);
        Square empty2 = testBoard.getSquare(7 , 6);
        Square empty3 = testBoard.getSquare(6 , 5);
        Square empty4 = testBoard.getSquare(5 , 5);
        Square enemy1 = testBoard.getSquare(7 , 4);
        Square enemy2 = testBoard.getSquare(4 , 5);
        List<Square> legals = new ArrayList<Square>();
        legals.add(empty2);
        legals.add(enemy1);
        legals.add(empty1);
        legals.add(empty3);
        legals.add(empty4);
        legals.add(enemy2);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    @Test
    void scenarioWhite2() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "g5", true);
        testBoard.addPiece("bishop", "d5", false);
        testBoard.addPiece("pawn", "g6", true);
        testBoard.addPiece("king", "g4", false);
        testBoard.addPiece("king", "h5", true);
        Rook r1 = new Rook(testBoard.getSquare(7,5),true,testBoard);
        r1.updateLegals();
        List<Square> Actual =r1.getLegalNextSquares();

        Square empty2 = testBoard.getSquare(5 , 5);
        Square empty3 = testBoard.getSquare(6 , 5);
        Square enemy1 = testBoard.getSquare(7 , 4);
        Square enemy2 = testBoard.getSquare(4 , 5);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy1);

        legals.add(empty3);
        legals.add(empty2);
        legals.add(enemy2);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    @Test
    void scenarioWhite3() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "g5", true);
        testBoard.addPiece("bishop", "d5", false);
        testBoard.addPiece("pawn", "g6", true);
        testBoard.addPiece("king", "g4", false);
        testBoard.addPiece("king", "h8", true);
        Rook r1 = new Rook(testBoard.getSquare(7,5),true,testBoard);
        r1.updateLegals();
        List<Square> Actual =r1.getLegalNextSquares();

        Square empty2 = testBoard.getSquare(5 , 5);
        Square empty3 = testBoard.getSquare(6 , 5);
        Square enemy1 = testBoard.getSquare(7 , 4);
        Square enemy2 = testBoard.getSquare(4 , 5);
        Square enemy3 = testBoard.getSquare(8 , 5);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy1);
        legals.add(enemy3);
        legals.add(empty3);
        legals.add(empty2);
        legals.add(enemy2);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    @Test
    void scenarioWhite4() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "g5", true);
        testBoard.addPiece("bishop", "d5", false);
        testBoard.addPiece("pawn", "g6", true);
        testBoard.addPiece("king", "g3", true);
        testBoard.addPiece("pawn", "h5", false);
        Rook r1 = new Rook(testBoard.getSquare(7,5),true,testBoard);
        r1.updateLegals();
        List<Square> Actual =r1.getLegalNextSquares();

        Square empty2 = testBoard.getSquare(5 , 5);
        Square empty3 = testBoard.getSquare(6 , 5);
        Square empty1 = testBoard.getSquare(7 , 4);
        Square enemy2 = testBoard.getSquare(4 , 5);
        Square enemy3 = testBoard.getSquare(8 , 5);
        List<Square> legals = new ArrayList<Square>();
        legals.add(empty1);
        legals.add(enemy3);
        legals.add(empty3);
        legals.add(empty2);
        legals.add(enemy2);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }




    @Test
    void scenarioWhite5() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "g5", true);
        testBoard.addPiece("bishop", "d5", false);
        testBoard.addPiece("pawn", "g6", true);
        testBoard.addPiece("king", "g3", true);
        testBoard.addPiece("pawn", "h5", false);
        Rook r1 = new Rook(testBoard.getSquare(7,5),true,testBoard);
        r1.updateLegals();
        List<Square> Actual =r1.getLegalNextSquares();

        Square empty2 = testBoard.getSquare(5 , 5);
        Square empty3 = testBoard.getSquare(6 , 5);
        Square empty1 = testBoard.getSquare(7 , 4);
        Square enemy2 = testBoard.getSquare(4 , 5);
        Square enemy3 = testBoard.getSquare(8 , 5);
        List<Square> legals = new ArrayList<Square>();
        legals.add(empty1);
        legals.add(enemy3);
        legals.add(empty3);
        legals.add(empty2);
        legals.add(enemy2);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    // black rook
    @Test
    void scenarioBlack1() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "g5", false);
        testBoard.addPiece("bishop", "d5", true);
        testBoard.addPiece("pawn", "g6", false);
        testBoard.addPiece("king", "g4", true);
        testBoard.addPiece("king", "h8", false);
        Rook r1 = new Rook(testBoard.getSquare(7,5),false,testBoard);
        r1.updateLegals();
        List<Square> Actual =r1.getLegalNextSquares();
        Square empty1 = testBoard.getSquare(8 , 5);
        Square empty2 = testBoard.getSquare(5 , 5);
        Square empty3 = testBoard.getSquare(6 , 5);
        Square enemy1 = testBoard.getSquare(7 , 4);
        Square enemy2 = testBoard.getSquare(4 , 5);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy1);
        legals.add(empty1);
        legals.add(empty3);
        legals.add(empty2);
        legals.add(enemy2);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    @Test
    void scenarioBlack2() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "g5", false);
        testBoard.addPiece("bishop", "d5", true);
        testBoard.addPiece("pawn", "g6", false);
        testBoard.addPiece("king", "g4", true);
        testBoard.addPiece("king", "h5", false);
        Rook r1 = new Rook(testBoard.getSquare(7,5),false,testBoard);
        r1.updateLegals();
        List<Square> Actual =r1.getLegalNextSquares();
        Square empty2 = testBoard.getSquare(5 , 5);
        Square empty3 = testBoard.getSquare(6 , 5);
        Square enemy1 = testBoard.getSquare(7 , 4);
        Square enemy2 = testBoard.getSquare(4 , 5);
        List<Square> legals = new ArrayList<Square>();
        legals.add(enemy1);
        legals.add(empty3);
        legals.add(empty2);
        legals.add(enemy2);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }



    @Test
    void scenarioWhiteLast() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "g5", true);
        testBoard.addPiece("bishop", "g4", true);
        testBoard.addPiece("pawn", "h5", true);
        testBoard.addPiece("king", "f5", true);
        testBoard.addPiece("queen", "g8", false);
        Rook r1 = new Rook(testBoard.getSquare(7,5),true,testBoard);
        r1.updateLegals();
        List<Square> Actual =r1.getLegalNextSquares();
        Square empty1 = testBoard.getSquare(7 , 6);
        Square empty2 = testBoard.getSquare(7 , 7);
        Square enemy = testBoard.getSquare(7 , 8);
        List<Square> legals = new ArrayList<Square>();
        legals.add(empty1);
        legals.add(empty2);
        legals.add(enemy);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    // test the print method
    // white rook
    @Test
    void printTestWhite() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "a1", true);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u2656";
        assertEquals(expected, actual);
    }


    // black rook
    @Test
    void printTestBlack() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "a1", false);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u265C";
        assertEquals(expected, actual);
    }



    @Test
    void RookNeverMoved() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "a1", true);
        Rook r = new Rook(testBoard.getSquare(1,1),true,testBoard);
        assertTrue(r.isNeverMoved());
    }

    @Test
    void RookNeverMoved2() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "a1", true);
        testBoard.addPiece("king", "b5", false);
        testBoard.addPiece("king", "e6", true);
        Rook r = new Rook(testBoard.getSquare(1,1),true,testBoard);
        r.updateLegals();
        assertTrue(r.isNeverMoved());
        r.move(testBoard.getSquare(1,2));
        assertFalse(r.isNeverMoved());
        r.updateLegals();
        r.move(testBoard.getSquare(1,1));
        assertFalse(r.isNeverMoved());
    }
    @Test
    void RookHasMoved() {
        Board testBoard = new Board();
        testBoard.addPiece("rook", "a1", true);
        testBoard.addPiece("king", "b5", false);
        testBoard.addPiece("king", "e6", true);
        Rook r = new Rook(testBoard.getSquare(1,1),true,testBoard);
        r.updateLegals();
        r.move(testBoard.getSquare(1,2));
        assertFalse(r.isNeverMoved());


    }
}











