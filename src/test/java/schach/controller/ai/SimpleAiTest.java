package schach.controller.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.Board;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test the simple ai
 */
class SimpleAiTest {

    Board board;

    AiInterface ai;

    @BeforeEach
    void init(){
        this.board = new Board();
        ai = new SimpleAi(board, true);
    }

    @Test
    void testNextSquare(){
        board.initLineUp();
        List<String> strings = new ArrayList<>();
        List<Move> moves = ai.getAiMoves();
        for (Move move: moves){
            strings.add(move.moveAsString());
        }
        assertTrue(strings.contains(ai.getNextMove()));
    }

}