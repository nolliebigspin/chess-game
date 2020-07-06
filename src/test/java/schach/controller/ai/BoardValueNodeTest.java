package schach.controller.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schach.model.Board;
import schach.model.Piece;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tests the BoardValueNode Class
 */
class BoardValueNodeTest {

    private Board board;

    @BeforeEach
    void init(){
        this.board = new Board();
    }

    @Test
    void testMinmax(){
        board.initLineUp();
        board.movePiece("b1", "c3");
        board.movePiece("b7", "b5");
        BoardValueNode bvt = new BoardValueNode(board, true, null);
        assertEquals(10, bvt.minmax(2, -10000, 10000));
    }

    @Test
    void testBestValuedMoveWhite(){
        board.initLineUp();
        board.movePiece("b1", "c3");
        board.movePiece("b7", "b5");
        BoardValueNode bvt = new BoardValueNode(board, true, null);
        Piece expected = bvt.bestValuedMove(true, 1).getPiece();
        assertEquals(expected, board.squareByDenotation("c3").getOccupier());
    }

    @Test
    void testBestValuedMoveBlack(){
        board.initLineUp();
        BoardValueNode bvt1 = new BoardValueNode(board, false, null);
        bvt1.bestValuedMove(true, 1);
        board.movePiece("b2", "b4");
        board.movePiece("b8", "c6");
        board.printBoard();
        BoardValueNode bvt = new BoardValueNode(board, false, null);
        Piece expected = bvt.bestValuedMove(false, 1).getPiece();
        assertEquals(expected, board.squareByDenotation("c6").getOccupier());
    }

    @Test
    void testGetValue(){
        board.initLineUp();
        BoardValueNode bvt = new BoardValueNode(board, false, null);
        assertEquals(0, bvt.getValue());
    }





}