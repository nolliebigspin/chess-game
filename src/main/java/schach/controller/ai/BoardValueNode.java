package schach.controller.ai;

import schach.model.Board;
import schach.model.Piece;
import schach.model.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that serves as a tree structure, where every object of this class is representing the
 * Board after a move and acting as a node in the tree
 */
public class BoardValueNode {

    /**
     * the value that is determined by all the different white vs all the different black pieces currently on the board
     */
    private int value;

    /**
     * the board this node is representing
     */
    private Board board;

    /**
     * List of moves that are possible as a next move
     */
    private List<Move> moves;

    /**
     * true if the current node is representing a state where the next move will be picked by white, false if its blacks
     * turn
     */
    private boolean whitesTurn;

    /**
     * the move that was run in before, the move of the parent node
     */
    private Move lastMoved;

    /**
     * constructor initializing the fields
     * @param board the board the moves will be executed on
     * @param isWhitesTurn true if its currently whites turn, false if its blacks turn
     * @param lastMoved the last move, null if there was none
     */
    public BoardValueNode(Board board, boolean isWhitesTurn, Move lastMoved){
        this.board = board;
        this.whitesTurn = isWhitesTurn;
        this.value = calcBoardValue();
        this.lastMoved = lastMoved;
        this.moves = getMoves();
    }

    /**
     * calls the min-max algorithm on every child and returns the move of the child with the best value
     * @param whitesTurn if caller is white, false if called by black
     * @param maxDepth the maximum search depth for the min-max algorithm
     * @return the move of the child with the best value
     */
    public Move bestValuedMove(boolean whitesTurn, int maxDepth){
        List<BoardValueNode> children = generateChild(maxDepth);
        Move bestMove = null;
        int bestValue = 0;
        if (whitesTurn){
            for (BoardValueNode child: children){
                if (child.minmax(maxDepth) > bestValue){
                    bestMove = child.getLastMoved();
                }
            }
        } else {
            for (BoardValueNode child: children){
                if (child.minmax(maxDepth) < bestValue){
                    bestMove = child.getLastMoved();
                }
            }
        }
        if (bestMove == null){
            int i = new Random().nextInt(children.size());
            bestMove = children.get(i).getLastMoved();
        }
        return bestMove;
    }

    /**
     * the min-max algorithm, iterating through the children and returning the max or min value depending
     * wherever its a turn by white or black
     * @param depth the maximum search depth
     * @return minmax value of the current node
     */
    public int minmax(int depth){
        if (depth == 0){
            return value;
        }
        List<BoardValueNode> children = generateChild(depth);
        if (whitesTurn){
            int maxVal = -10000;
            for (BoardValueNode boardValueNode : children){
                int val = boardValueNode.minmax(depth - 1);
                maxVal = Math.max(maxVal, val);
            }
            return maxVal;
        } else {
            int minEval = 10000;
            for (BoardValueNode boardValueNode : children){
                int val = boardValueNode.minmax(depth - 1);
                minEval = Math.min(minEval, val);
            }
            return minEval;
        }
    }

    /**
     * generates the tree structure by generating children for every node
     * @param maxDepth the current level
     * @return List of Nodes that represent the children of the current node
     */
    private List<BoardValueNode> generateChild(int maxDepth){
        if (maxDepth == 0){
            return null;
        } else {
            List<BoardValueNode> children = new ArrayList<>();
            for (Move move: moves){
                Board nextBoard = new Board(board.getPositioning());
                String[] denotation = move.moveAsString().split("-");
                nextBoard.movePiece(denotation[0], denotation[1]);
                if (lastMoved != null){
                    nextBoard.setLastMoved(lastMoved.getPiece());
                }
                children.add(new BoardValueNode(nextBoard, !whitesTurn, move));
            }
            return children;
        }
    }

    /**
     * generates List of currently possible moves
     * @return List of currently possible moves
     */
    private List<Move> getMoves(){
        List<Move> moves = new ArrayList<>();
        List<Piece> pieces = board.allActivePieces(whitesTurn);
        for (Piece piece: pieces){
            piece.updateLegals();
            List<Square> legals = piece.filteredLegals();
            for (Square square: legals){
                moves.add(new Move(piece, square));
            }
        }
        return moves;
    }

    /**
     * getter for the value
     * @return the value of the board
     */
    public int getValue() {
        return value;
    }

    /**
     * getter for the last moved Piece
     * @return the last moved piece
     */
    public Move getLastMoved() {
        return lastMoved;
    }

    /**
     * calculates the value of the board by adding the value of all white pieces to the value of all black pieces
     * @return value of the board
     */
    private int calcBoardValue(){
        return calcWhiteValue() + calcBlackValue();
    }

    /**
     * calculating the value of all currently active white pieces by adding all there single values
     * @return value of all currently active white pieces
     */
    private int calcWhiteValue(){
        int value = 0;
        List<Piece> pieces = board.allActivePieces(true);
        for (Piece piece: pieces){
            String unicode = piece.print();
            switch (unicode){
                case "\u2659":
                    value += 10;
                    break;
                case "\u2658":
                case "\u2657":
                    value += 30;
                    break;
                case "\u2656":
                    value += 50;
                    break;
                case "\u2655":
                    value += 90;
                    break;
                case "\u2654":
                    value += 900;
                    break;
            }
        }
        return value;
    }

    /**
     * calculating the value of all currently active black pieces by adding all there single values
     * @return value of all currently active black pieces
     */
    private int calcBlackValue(){
        int value = 0;
        List<Piece> pieces = board.allActivePieces(false);
        for (Piece piece: pieces){
            String unicode = piece.print();
            switch (unicode){
                case "\u265F":
                    value += -10;
                    break;
                case "\u265E":
                case "\u265D":
                    value += -30;
                    break;
                case "\u265C":
                    value += -50;
                    break;
                case "\u265B":
                    value += -90;
                    break;
                case "\u265A":
                    value += -900;
                    break;
            }
        }
        return value;
    }
}
