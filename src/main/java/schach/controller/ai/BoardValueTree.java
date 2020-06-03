package schach.controller.ai;

import schach.model.Board;
import schach.model.Piece;
import schach.model.Square;

import java.util.ArrayList;
import java.util.List;

public class BoardValueTree {

    private int value;

    private Board board;

    private List<Move> moves;

    private int maxDepth;

    private boolean whitesTurn;

    private List<BoardValueTree> children;

    private Move lastMoved;

    public BoardValueTree(Board board, int maxDepth, boolean isWhitesTurn, Move lastMoved){
        System.out.println(maxDepth);
        this.board = board;
        this.maxDepth = maxDepth;
        this.whitesTurn = isWhitesTurn;
        this.value = calcBoardValue();
        this.lastMoved = lastMoved;
        this.moves = getMoves();
        this.children = generateChild();
    }

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

    private List<BoardValueTree> generateChild(){
        if (maxDepth == 0){
            return null;
        } else {
            List<BoardValueTree> children = new ArrayList<>();
            for (Move move: moves){
                Board nextBoard = new Board(board.getPositioning());
                String[] denotation = move.moveAsString().split("-");
                nextBoard.movePiece(denotation[0], denotation[1]);
                if (lastMoved != null){
                    nextBoard.setLastMoved(lastMoved.getPiece());
                }
                children.add(new BoardValueTree(nextBoard, maxDepth - 1, !whitesTurn, move));
            }
            return children;
        }
    }

    protected int calcBoardValue(){
        return calcWhiteValue() + calcBlackValue();
    }

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
