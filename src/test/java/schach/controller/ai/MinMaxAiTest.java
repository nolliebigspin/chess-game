package schach.controller.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.Board;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test the minmax ai
 */
class MinMaxAiTest {

    Board board;

    AiInterface ai;

    @BeforeEach
    void init(){
        this.board = new Board();
        this.ai = new MinMaxAi(board, true);
    }

}