package schach.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    // test the print method
    // white king
    @Test
    void printTestWhite() {
        Board testBoard = new Board();
        testBoard.addPiece("king", "a1", true);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u2654";
        assertEquals(expected, actual);
    }


    // black king
    @Test
    void printTestBlack() {
        Board testBoard = new Board();
        testBoard.addPiece("king", "a1", false);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u265A";
        assertEquals(expected, actual);
    }


    @Test
    void scenario1() {
        Board testBoard = new Board();
        testBoard.addPiece("king", "d3", false);

        King k = new King(testBoard.getSquare(4,3),false,testBoard);
        k.updateLegals();
        List<Square> Actual =k.getLegalNextSquares();
        Square en1 = testBoard.getSquare(4 , 4);
        Square en2 = testBoard.getSquare(4 , 2);
        Square en3 = testBoard.getSquare(3 , 3);
        Square en4 = testBoard.getSquare(5 , 3);
        Square en5 = testBoard.getSquare(5 , 4);
        Square en6 = testBoard.getSquare(5 , 2);
        Square en7 = testBoard.getSquare(3 , 2);
        Square en8 = testBoard.getSquare(3 , 4);
        List<Square> legals = new ArrayList<Square>();
        legals.add(en1);
        legals.add(en2);
        legals.add(en3);
        legals.add(en4);
        legals.add(en5);
        legals.add(en6);
        legals.add(en7);
        legals.add(en8);

        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    @Test
    void scenario2() {
        Board testBoard = new Board();
        testBoard.addPiece("king", "d3", false);

        King k = new King(testBoard.getSquare(4,3),false,testBoard);
        k.updateLegals();
        List<Square> Actual =k.getLegalNextSquares();
        Square en1 = testBoard.getSquare(4 , 4);
        Square en2 = testBoard.getSquare(4 , 2);
        Square en3 = testBoard.getSquare(3 , 3);
        Square en4 = testBoard.getSquare(5 , 3);
        Square en5 = testBoard.getSquare(5 , 4);
        Square en6 = testBoard.getSquare(5 , 2);
        Square en7 = testBoard.getSquare(3 , 2);
        Square en8 = testBoard.getSquare(3 , 4);
        List<Square> legals = new ArrayList<Square>();
        legals.add(en1);
        legals.add(en2);
        legals.add(en3);
        legals.add(en4);
        legals.add(en5);
        legals.add(en6);
        legals.add(en7);
        legals.add(en8);

        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }

    @Test
    void acceptMoveTestScenario1() {
        Board testBoard = new Board();
        testBoard.addPiece("king", "d3", false);
        King k = new King(testBoard.getSquare(4,3),false,testBoard);
        testBoard.addPiece("rook", "d2", true);
        List<Piece> cemetery = new ArrayList<>();
        Rook r = new Rook(testBoard.getSquare(4,2),true,testBoard);
        cemetery.add(testBoard.squareByDenotation("d2").getOccupier());
        k.move(testBoard.getSquare(4,2));
        // makes sure that the rook is added to the cemetery after being eaten by the king
        assertEquals(cemetery,testBoard.getCemetery());

    }














}