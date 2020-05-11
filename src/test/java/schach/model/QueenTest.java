package schach.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    @Test
    void scenario1() {
        Board testBoard = new Board();
        testBoard.addPiece("queen", "e5", false);
        testBoard.addPiece("king", "e1", false);
        testBoard.addPiece("bishop", "f4", true);
        testBoard.addPiece("pawn", "f5", true);
        testBoard.addPiece("king", "f6", true);
        testBoard.addPiece("pawn", "d4", true);
        testBoard.addPiece("bishop", "d5", true);
        testBoard.addPiece("pawn", "d6", true);
        testBoard.addPiece("pawn", "e4", true);
        testBoard.addPiece("pawn", "e6", true);
        Queen q1 = new Queen(testBoard.getSquare(5,5),false,testBoard);
        q1.updateLegals();
        List<Square> Actual =q1.getLegalNextSquares();
        Square en1 = testBoard.getSquare(6 , 6);
        Square en2 = testBoard.getSquare(6 , 5);
        Square en3 = testBoard.getSquare(6 , 4);
        Square en4 = testBoard.getSquare(5 , 6);
        Square en5 = testBoard.getSquare(5 , 4);
        Square en6 = testBoard.getSquare(4 , 6);
        Square en7 = testBoard.getSquare(4 , 5);
        Square en8 = testBoard.getSquare(4 , 4);
        List<Square> legals = new ArrayList<Square>();
        legals.add(en4);
        legals.add(en5);
        legals.add(en2);
        legals.add(en7);
        legals.add(en1);
        legals.add(en6);
        legals.add(en3);
        legals.add(en8);

        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }


    @Test
    void scenario2() {
        Board testBoard = new Board();
        testBoard.addPiece("queen", "e5", false);
        testBoard.addPiece("king", "h8", false);
        testBoard.addPiece("bishop", "g7", true);
        testBoard.addPiece("pawn", "g5", true);
        testBoard.addPiece("king", "g3", true);
        testBoard.addPiece("pawn", "c7", true);
        testBoard.addPiece("bishop", "c5", true);
        testBoard.addPiece("pawn", "c3", true);
        testBoard.addPiece("pawn", "e7", true);
        testBoard.addPiece("pawn", "e3", true);
        Queen q1 = new Queen(testBoard.getSquare(5,5),false,testBoard);
        q1.updateLegals();
        List<Square> Actual =q1.getLegalNextSquares();
        Square en1 = testBoard.getSquare(6 , 6);
        Square en2 = testBoard.getSquare(6 , 5);
        Square en3 = testBoard.getSquare(6 , 4);
        Square en4 = testBoard.getSquare(5 , 6);
        Square en5 = testBoard.getSquare(5 , 4);
        Square en6 = testBoard.getSquare(4 , 6);
        Square en7 = testBoard.getSquare(4 , 5);
        Square en8 = testBoard.getSquare(4 , 4);
        Square em1 = testBoard.getSquare(7 , 7);
        Square em2 = testBoard.getSquare(7 , 5);
        Square em3 = testBoard.getSquare(7 , 3);
        Square em4 = testBoard.getSquare(5 , 7);
        Square em5 = testBoard.getSquare(5 , 3);
        Square em6 = testBoard.getSquare(3 , 7);
        Square em7 = testBoard.getSquare(3 , 5);
        Square em8 = testBoard.getSquare(3 , 3);
        List<Square> legals = new ArrayList<Square>();
        legals.add(en4);
        legals.add(em4);
        legals.add(en5);
        legals.add(em5);
        legals.add(en2);
        legals.add(em2);
        legals.add(en7);
        legals.add(em7);
        legals.add(en1);
        legals.add(em1);
        legals.add(en6);
        legals.add(em6);
        legals.add(en3);
        legals.add(em3);
        legals.add(en8);
        legals.add(em8);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }

    // test the print method for a queen piece

    // white queen
    @Test
    void printTestWhite() {
        Board testBoard = new Board();
        testBoard.addPiece("queen", "a1", true);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u2655";
        assertEquals(expected, actual);
    }


    // black queen
    @Test
    void printTestBlack() {
        Board testBoard = new Board();
        testBoard.addPiece("queen", "a1", false);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u265B";
        assertEquals(expected, actual);
    }





}