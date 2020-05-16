package schach.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.controller.Interfaces.Input;
import schach.model.Board;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class that test if input methods work properly
 */
class InputTest {

    Board board;
    Input input;

    @BeforeEach
    void init(){
        this.board = new Board();
        this.input = new Input(board);
    }

    /**
     * test if method validDenotation() works correctly
     */
    @Test
    public void testValidDenotation(){
        assertFalse(input.validDenotation("asdf"));
        assertFalse(input.validDenotation("4e"));
        assertFalse(input.validDenotation("ag"));
        assertTrue(input.validDenotation("c7"));
    }

    /**
     * tests if method validPromotion() works correctly
     */
    @Test
    public void testValidPromotion(){
        assertFalse(input.validPromotion("dffaa"));
        assertFalse(input.validPromotion("A"));
        assertTrue(input.validPromotion("Q"));
    }

    /**
     * tests if method validateMoveInput() works correctly for invalid inputs
     */
    @Test
    public void testInvalidMoveInput(){
        assertFalse(input.validMoveInput("e2e6e5"));
        assertFalse(input.validMoveInput("e4h7"));
        assertFalse(input.validMoveInput("e7-a3g"));
        assertFalse(input.validMoveInput("af-f4"));
    }

    /**
     * tests if method validateMoveInput() works correctly for valid inputs
     */
    @Test
    public void testValidMoveInput(){
        assertTrue(input.validMoveInput("a1-e3"));
        assertTrue(input.validMoveInput("f4-a2B"));
    }

    /**
     * tests if method readInput() works correctly
     */
    @Test
    public void testReadInput(){
        String in = "Hello World!";
        System.setIn(new ByteArrayInputStream(in.getBytes()));
        String unexpected = "WHATS UP";
        String expected = "Hello World!";
        String actual = input.readInput();
        assertNotEquals(unexpected, actual);
        assertEquals(expected, actual);
    }

    /**
     *
     */
    @Test
    public void testCheckTurn(){
        lineUp(" white-king-e1, black-king-e8");
        assertTrue(input.checkTurn("e1-e2", 0));
        assertFalse(input.checkTurn("e8-e7", 0));
        assertTrue(input.checkTurn("e8-e7", 1));
        assertFalse(input.checkTurn("a1-a7", 2));
    }


    /**
     * Method that simplifies the Board lineup;
     * @param input lineup in a certain string format
     */
    private void lineUp(String input){
        String[] commands = input.split(",");
        for (String s: commands){
            String[] denot = s.split("-");
            boolean b = denot[0].equals(" white");
            board.addPiece(denot[1], denot[2],b);
        }
        board.updateAllLegalSquares();
    }



}