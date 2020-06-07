package schach.controller.ai;

import schach.model.Board;
import schach.model.Piece;
import schach.model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that act as a interface between ai and the game/input
 * generates and stores all information the ai will need
 */
public abstract class AiInterface {

    /**
     * the board the game is played on
     */
    protected Board board;


    /**
     * color the ai will play with
     */
    protected boolean white;

    /**
     * the constructor initializing the ai and fields
     * @param board
     * @param isWhite
     */
    public AiInterface(Board board, boolean isWhite){
        this.board = board;
        this.white = isWhite;
    }

    /**
     * gets the next move generated by the ai
     * @return String that represents the next move
     */
    public abstract String getNextMove();

    /**
     * gets all active pieces played by the ai
     * @return List of active Pieces played by the ai
     */
    protected List<Piece> getAiPieces(){
        return board.allActivePieces(white);
    }

    /**
     * gets all moves possible for the ai in the current turn
     * @return List of moves currently possible for the ai
     */
    public List<Move> getAiMoves(){
        List<Move> moves = new ArrayList<>();
        List<Piece> aiPieces = getAiPieces();
        for (Piece piece: aiPieces){
            piece.updateLegals();
            List<Square> legals = piece.filteredLegals();
            for (Square square: legals){
                moves.add(new Move(piece, square));
            }
        }
        return moves;
    }
}
