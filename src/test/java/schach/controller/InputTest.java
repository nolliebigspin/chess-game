package schach.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.Board;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class InputTest {

    Board board;
    Input input;

    @BeforeEach
    public void init(){
        this.board = new Board();
        this.input = new Input(board);
    }

    @Test
    public void testValidDenotation(){
        assertFalse(input.validDenotation("asdf"));
        assertFalse(input.validDenotation("4e"));
        assertFalse(input.validDenotation("ag"));
        assertTrue(input.validDenotation("c7"));
    }

    @Test
    public void testValidPromotion(){
        assertFalse(input.validPromotion("dffaa"));
        assertFalse(input.validPromotion("A"));
        assertTrue(input.validPromotion("Q"));
    }

    @Test
    public void testInvalidMoveInput(){
        assertFalse(input.validMoveInput("e2e6e5"));
        assertFalse(input.validMoveInput("e4h7"));
        assertFalse(input.validMoveInput("e7-a3g"));
    }

    @Test
    public void testValidMoveInput(){
        assertTrue(input.validMoveInput("a1-e3"));
        assertTrue(input.validMoveInput("f4-a2B"));
    }

}