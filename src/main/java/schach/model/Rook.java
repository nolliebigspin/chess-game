package schach.model;

/**
 * Class Rook representing the chess piece rook
 */
public class Rook extends Piece {

    public Rook(Square position, boolean isWhite, Board board) {
        super(position, isWhite, board);
    }

    @Override
    public String print() {
        if (super.isWhite){
            return "\u2656";
        }
        else{
            return "\u265C";
        }
    }

    @Override
    public void updateLegals() {
        legalNextSquares.clear();
        boolean oppositeIsWhite;
        if (isWhite){
            oppositeIsWhite = false;
        } else {
            oppositeIsWhite = true;
        }

        //Forward (white), backwards (black)
        for (int i = 1 ; i <= 8 - position.getRow(); i++){
            Square nextForward = board.getSquare(position.getColumn(), position.getRow() + i);
            if (!(nextForward.isOccupied())){
                legalNextSquares.add(nextForward);
            } else if (nextForward.isOccupied() && (nextForward.getOccupier().isWhite == oppositeIsWhite)){
                legalNextSquares.add(nextForward);
                break;
            }
            else {
                break;
            }
        }

        //backward (white), forward (black)
        for (int i = 1; i < position.getRow(); i++){
            Square nextBackward = board.getSquare(position.getColumn(), position.getRow() - i);
            if (!(nextBackward.isOccupied())){
                legalNextSquares.add(nextBackward);
            }
            else if (nextBackward.isOccupied() && (nextBackward.getOccupier().isWhite == oppositeIsWhite)){
                legalNextSquares.add(nextBackward);
                break;
            }
            else {
                break;
            }
        }

        //right (white), left (black)
        for (int i = 1; i <= 8 - position.getColumn(); i++){
            Square nextRight = board.getSquare(position.getColumn() + i, position.getRow());
            if (!(nextRight.isOccupied())){
                legalNextSquares.add(nextRight);
            }
            else if (nextRight.isOccupied() && (nextRight.getOccupier().isWhite == oppositeIsWhite)){
                legalNextSquares.add(nextRight);
                break;
            }
            else {
                break;
            }
        }

        //right (black), left (white)
        for (int i = 1; i < position.getColumn(); i++){
            Square nextLeft = board.getSquare(position.getColumn() - i, position.getRow());
            if (!(nextLeft.isOccupied())){
                legalNextSquares.add(nextLeft);
            }
            else if (nextLeft.isOccupied() && (nextLeft.getOccupier().isWhite == oppositeIsWhite)){
                legalNextSquares.add(nextLeft);
                break;
            }
            else {
                break;
            }
        }
    }

}

