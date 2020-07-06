package schach.controller.ai;

import schach.model.Board;
import schach.model.BoardState;
import schach.model.Piece;
import schach.model.Square;

import java.util.*;

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
        System.out.println("\n --------------------------------------------------" + value);
        List<Move> bestMoves = new ArrayList<>();
        Move bestMove = null;
        int bestValue;
        if (whitesTurn){
            bestValue = -1000;
            for (Move move: moves){
                String[] denot = move.moveAsString().split("-");
                board.movePiece(denot[0], denot[1]);
                BoardValueNode boardValueNode = new BoardValueNode(board, !whitesTurn, move);
                int minmaxValue = boardValueNode.minmax(maxDepth, -10000, 10000);
                System.out.println(move.moveAsString() + "   " + minmaxValue);
                if (minmaxValue >= bestValue){
                    if (minmaxValue > bestValue){
                        bestMoves.clear();
                        bestValue = minmaxValue;
                    }
                    bestMoves.add(boardValueNode.getLastMoved());
                }
                board.loadState(board.getStates().size() - 2);
            }
        } else {
            bestValue = 1000;
            for (Move move: moves){
                String[] denot = move.moveAsString().split("-");
                board.movePiece(denot[0], denot[1]);
                BoardValueNode boardValueNode = new BoardValueNode(board, !whitesTurn, move);
                int minmaxValue = boardValueNode.minmax(maxDepth, -10000, 10000);
                System.out.println(move.moveAsString() + "   " + minmaxValue);
                if (minmaxValue <= bestValue){
                    if (minmaxValue < bestValue){
                        bestMoves.clear();
                        bestValue = minmaxValue;
                    }
                    bestMoves.add(boardValueNode.getLastMoved());
                }
                board.loadState(board.getStates().size() - 2);
            }
        }
        int i = new Random().nextInt(bestMoves.size());
        System.out.println("random:" + i);
        return bestMoves.get(i);
        /*Map<Integer, Move> valueMap = new HashMap<>();
        List<Integer> values = new ArrayList<>();
        for (Move move: moves){
            String[] denot = move.moveAsString().split("-");
            board.movePiece(denot[0], denot[1]);
            BoardValueNode boardValueNode = new BoardValueNode(board, !whitesTurn, move);
            int value = boardValueNode.minmax(maxDepth);
            values.add(value);
            valueMap.put(value, move);
            board.loadState(board.getStates().size() - 2);
        }
        if (whitesTurn){
            int max = Collections.max(values);
            return valueMap.get(max);
        } else {
            int min = Collections.min(values);
            return valueMap.get(min);
        }*/
    }

    /**
     * the min-max algorithm, iterating through the children and returning the max or min value depending
     * wherever its a turn by white or black
     * @param depth the maximum search depth
     * @return minmax value of the current node
     */
    public int minmax(int depth, int alpha, int beta){
        if (moves.size() == 0){ //== checkmate
            if (whitesTurn){
                return -1000;
            } else {
                return 1000;
            }
        }
        if (depth == 0){
            return value;
        }
        if (whitesTurn){
            int maxVal = -10000;
            for (Move move: moves){
                String[] denot = move.moveAsString().split("-");
                board.movePiece(denot[0], denot[1]);
                BoardValueNode boardValueNode = new BoardValueNode(board, !whitesTurn, move);
                int val = boardValueNode.minmax(depth - 1, alpha, beta);
                maxVal = Math.max(maxVal, val);
                board.loadState(board.getStates().size() - 2);
                alpha = Math.max(alpha, val);
                /*if (beta <= alpha){
                    break;
                }*/
            }
            return maxVal;
        } else {
            int minEval = 10000;
            for (Move move: moves){
                String[] denot = move.moveAsString().split("-");
                board.movePiece(denot[0], denot[1]);
                BoardValueNode boardValueNode = new BoardValueNode(board, !whitesTurn, move);
                int val = boardValueNode.minmax(depth - 1, alpha, beta);
                minEval = Math.min(minEval, val);
                board.loadState(board.getStates().size() - 2);
                beta = Math.min(beta, val);
                /*if (beta <= alpha){
                    break;
                }*/
            }
            return minEval;
        }

        /*List<Integer> values = new ArrayList<>();
        for (Move move: moves){
            String[] denot = move.moveAsString().split("-");
            board.movePiece(denot[0], denot[1]);
            BoardValueNode boardValueNode = new BoardValueNode(board, !whitesTurn, move);
            int value = boardValueNode.minmax(depth - 1);
            values.add(value);
            board.loadState(board.getStates().size() - 2);
        }
        if (whitesTurn){
            return Collections.max(values);
        } else {
            return Collections.min(values);
        }*/
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
