package schach.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the concrete piece Class King
 */
class KingTest {

    private String pawn = "pawn";
    private String king = "king";
    private String rook = "rook";

    // test the print method
    // white king
    @Test
    void printTestWhite() {
        Board testBoard = new Board();
        testBoard.addPiece(king, "a1", true);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u2654";
        assertEquals(expected, actual);
    }


    // black king
    @Test
    void printTestBlack() {
        Board testBoard = new Board();
        testBoard.addPiece(king, "a1", false);
        String actual = testBoard.squareByDenotation("a1").getOccupier().print();
        String expected = "\u265A";
        assertEquals(expected, actual);
    }

    @Test
    void scenario2() {
        Board testBoard = new Board();
        testBoard.addPiece(king, "d3", false);

        King k = new King(testBoard.getSquare(4,3),false,testBoard);
        k.updateLegals();
        List<Square> Actual =k.getLegalNextSquares();
        Square n1 = testBoard.getSquare(4 , 4);
        Square n2 = testBoard.getSquare(4 , 2);
        Square n3 = testBoard.getSquare(3 , 3);
        Square n4 = testBoard.getSquare(5 , 3);
        Square n5 = testBoard.getSquare(5 , 4);
        Square n6 = testBoard.getSquare(5 , 2);
        Square n7 = testBoard.getSquare(3 , 2);
        Square n8 = testBoard.getSquare(3 , 4);
        List<Square> legals = new ArrayList<Square>();
        legals.add(n1);
        legals.add(n2);
        legals.add(n3);
        legals.add(n4);
        legals.add(n5);
        legals.add(n6);
        legals.add(n7);
        legals.add(n8);

        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }

    @Test
    void scenario3() {
        Board testBoard = new Board();
        testBoard.addPiece(king, "d3", false);
        testBoard.addPiece(king, "h8", true);
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

    // test if the eaten piece is in moved to the cemetery
    @Test
    void checkTheCemetery() {
        Board testBoard = new Board();
        testBoard.addPiece(king, "d3", false);
        King k = new King(testBoard.getSquare(4,3),false,testBoard);
        testBoard.addPiece(rook, "d2", true);
        List<Piece> cemetery = new ArrayList<>();
        new Rook(testBoard.getSquare(4,2),true,testBoard);
        cemetery.add(testBoard.squareByDenotation("d2").getOccupier());
        k.updateLegals();
        k.move(testBoard.getSquare(4,2));
        k.updateLegals();
        // makes sure that the rook is added to the cemetery after being eaten by the king
        assertEquals(cemetery,testBoard.getCemetery());

    }

    @Test
    void scenariowhatever() {
        Board testBoard = new Board();
        testBoard.addPiece(king, "d3", false);
        testBoard.addPiece(pawn, "f4", true);
        testBoard.addPiece(pawn, "f5", true);
        testBoard.addPiece(pawn, "f6", true);
        testBoard.addPiece(pawn, "d4", true);
        testBoard.addPiece(pawn, "d5", true);
        testBoard.addPiece(pawn, "d6", true);
        testBoard.addPiece(pawn, "e4", true);
        testBoard.addPiece(pawn, "e6", true);
        King k = new King(testBoard.getSquare(4,3),false,testBoard);
        k.updateLegals();
        List<Square> Actual =k.getLegalNextSquares();
        Square e1 = testBoard.getSquare(4 , 4);
        Square e2 = testBoard.getSquare(4 , 2);
        Square e3 = testBoard.getSquare(3 , 3);
        Square e4 = testBoard.getSquare(5 , 3);
        Square e5 = testBoard.getSquare(5 , 4);
        Square e6 = testBoard.getSquare(5 , 2);
        Square e7 = testBoard.getSquare(3 , 2);
        Square e8 = testBoard.getSquare(3 , 4);
        List<Square> legals = new ArrayList<Square>();
        legals.add(e1);
        legals.add(e2);
        legals.add(e3);
        legals.add(e4);
        legals.add(e5);
        legals.add(e6);
        legals.add(e7);
        legals.add(e8);

        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }

    @Test
    void scenariowhatever2() {
        Board testBoard = new Board();
        testBoard.addPiece(king, "e5", true);
        testBoard.addPiece(pawn, "f4", false);
        testBoard.addPiece(pawn, "f5", false);
        testBoard.addPiece(pawn, "f6", false);
        testBoard.addPiece(pawn, "d4", false);
        testBoard.addPiece(pawn, "d5", false);
        testBoard.addPiece(pawn, "d6", false);
        testBoard.addPiece(pawn, "e4", false);
        testBoard.addPiece(pawn, "e6", false);
        King k = new King(testBoard.getSquare(5,5),true,testBoard);
        k.updateLegals();
        List<Square> Actual =k.getLegalNextSquares();
        Square en1 = testBoard.getSquare(4 , 6);
        Square en2 = testBoard.getSquare(4 , 4);
        Square en3 = testBoard.getSquare(5, 6);
        Square en4 = testBoard.getSquare(6 , 6);
        Square en5 = testBoard.getSquare(6 , 4);
        List<Square> legals = new ArrayList<Square>();
        legals.add(en3);
        legals.add(en4);
        legals.add(en5);
        legals.add(en2);
        legals.add(en1);
        // test the CheckUpLeft method
        assertEquals(legals,Actual);
    }

    @Test
    void testCasteling(){
       Board board = new Board();
       board.initLineUp();
       Piece wKing = board.squareByDenotation("e1").getOccupier();
       board.movePiece("e2","e3");
        board.movePiece("b7","b5");
        board.movePiece("f1","e2");
        board.movePiece("c8","b7");
        board.movePiece("g1","h3");
        board.movePiece("b8","a6");
        board.movePiece("e1","g1");
        board.movePiece("d7","d6");
        board.movePiece("d2","d3");
        board.movePiece("d8","d7");
        board.movePiece("e8","c8");
        assertEquals(board.squareByDenotation("g1").getOccupier(), wKing);
    }










}