package schach.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that can read and write the Positions of Pieces on a Board
 */
public class Positioning {

    /**
     * the Board the Positioning will be effective on
     */
    Board board;

    Map<Square, String> hashMap;

    /**
     * Constructor initializing the board
     * @param board the Piece will be written on / read
     */
    public Positioning(Board board){
        this.board = board;
        hashMap = new HashMap<>();
    }

    /**
     * creates a HashMap Containing a String which represents the Piece as Key
     * and the denotation String of the Square its placed on as a Value
     * @return HashMap containing all Pieces and there Position both represented by Strings
     */
    public List<String> readPositioning(){
        List<Piece> pieces = board.allActivePieces(true);
        pieces.addAll(board.allActivePieces(false));
        List<String> place = new ArrayList<>();
        for (Piece piece: pieces){
            String color = "w";
            if (!piece.isWhite()){
                color = "b";
            }
            String pieceString = color + "-" + piece.print() + "-" + piece.getPosition().getDenotation();
            place.add(pieceString);
            hashMap.put(piece.getPosition(), piece.print());
        }
        return place;
    }

    public Map<Square, String> getPositioningMap(){
        return this.hashMap;
    }

    /**
     * reads a HashMap and places Pieces on the wanted Squares
     * @param positioning HashMap that contains a String representing the Piece as a Key
     *                    and a String with the denotation of the square as a Value
     */
    public void writePositioning(List<String> positioning) {
        List<String> pos = positioning;
        for (String str: pos){
            String[] splitted = str.split("-");
            if (splitted[0].equals("w")){
                createWhitePiece(splitted[1], splitted[2]);
            } else {
                createBlackPiece(splitted[1], splitted[2]);
            }
        }
    }

    private void createWhitePiece(String unicode, String denotation){
        String uni = unicode;
        Square pos = board.squareByDenotation(denotation);
        switch (uni){
            case "\u2659":
                new Pawn(pos, true, board);
                break;
            case "\u2656":
                new Rook(pos, true, board);
                break;
            case "\u2658":
                new Knight(pos, true, board);
                break;
            case "\u2657":
                new Bishop(pos, true, board);
                break;
            case "\u2655":
                new Queen(pos, true, board);
                break;
            case "\u2654":
                new King(pos, true, board);
                break;
        }
    }

    private void createBlackPiece(String unicode, String denotation){
        String uni = unicode;
        Square pos = board.squareByDenotation(denotation);
        switch (uni){
            case "\u265F":
                new Pawn(pos, false, board);
                break;
            case "\u265C":
                new Rook(pos, false, board);
                break;
            case "\u265E":
                new Knight(pos, false, board);
                break;
            case "\u265D":
                new Bishop(pos, false, board);
                break;
            case "\u265B":
                new Queen(pos, false, board);
                break;
            case "\u265A":
                new King(pos, false, board);
                break;
        }
    }
}

