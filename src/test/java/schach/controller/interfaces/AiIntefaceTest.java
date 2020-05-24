package schach.controller.interfaces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.Board;

import static org.junit.jupiter.api.Assertions.*;

class AiIntefaceTest {

    Board board;

    AiInteface aiInteface;

    @BeforeEach
    private void init(){
        this.board = new Board();
        this.aiInteface = new AiInteface(board, true);
    }


    /**
     * Method that simplifies the Board lineup;
     * @param input lineup in a certain string format
     */
    private void lineUp(String input){
        String[] commands = input.split(",");
        for (String s: commands){
            String[] denote = s.split("-");
            boolean b = denote[0].equals(" white");
            board.addPiece(denote[1], denote[2],b);
        }
        board.updateAllLegalSquares();
    }

}