package schach.controller.interfaces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.Board;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class testing the PlayerInput class
 */
class PlayerInputTest {

    private Board board;

    private PlayerInput playerInput;


    @BeforeEach
    void init(){
        this.board = new Board();
        board.initLineUp();
        this.playerInput = new PlayerInput(board, true);
    }

    @Test
    void testInputIsMoveValid(){
        assertTrue(playerInput.inputIsMove("e2-a4"));
        assertTrue(playerInput.inputIsMove("a1-a4B"));
    }

    @Test
    void testInputIsMoveInvalid(){
        assertFalse(playerInput.inputIsMove("e7-e5"));
        assertFalse(playerInput.inputIsMove("e2-e4A"));
        assertFalse(playerInput.inputIsMove("a1.a2"));
        assertFalse(playerInput.inputIsMove("aa-77"));
    }

    @Test
    void testInputIsMoveInvalid2(){
        assertFalse(playerInput.inputIsMove("z7-e9"));
    }


    @Test
    void testInputIsMoveBeaten(){
        String expected = "no pieces beaten yet" + System.lineSeparator();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        playerInput.inputIsMove("beaten");
        assertEquals(expected, outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void testInputIsMoveGibberish(){
        String expected = "!Invalid move" + System.lineSeparator() +
                "no valid input given - please enter 'help' for further info" + System.lineSeparator();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        playerInput.inputIsMove("asfdfwq");
        assertEquals(expected, outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void testInputIsMoveHelp(){
        String expected = "enter 'beaten' to show all beaten Pieces  \n" +
                "enter a move by the following format: 'e2-a5' \n" +
                "if you want a promotion, enter move like: a7-a8B" + System.lineSeparator();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        playerInput.inputIsMove("help");
        assertEquals(expected, outContent.toString());
        System.setOut(originalOut);
    }

}