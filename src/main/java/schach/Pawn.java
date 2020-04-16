package schach;

/**
 * Class Pawn representing the chess piece peasant
 */
public class Pawn extends Piece{

    public Pawn(Square position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public String print() {
        if (super.isWhite){
            return "\u2659";
        }
        else{
            return "\u265F";
        }
    }

    @Override
    public void move(Square target){
        if (isWhite){
            //Starting move: jump 2 rows ahead
            if (position.getRow() == 2 && target.getRow() == 4 && position.getColumn() == target.getColumn()){
                acceptMove(target);
            }
            //Default move: jump 1 row ahead
            else if (((target.getRow() - position.getRow()) == 1) && target.getColumn() == position.getColumn()){
                acceptMove(target);
            }
            //Attack move: jumps 1 square diagonally if occupied by opposite color
            else if (((target.getRow() - position.getRow()) == 1) && (target.getColumn()-position.getColumn() == Math.abs(1)) && (target.isOccupied())){
                acceptMove(target);
            }
            else {
                refuseMove();
            }
        }
        if (!isWhite){
            //Starting move: jump 2 rows ahead
            if (position.getRow() == 7 && target.getRow() == 5 && position.getColumn() == target.getColumn()){
                acceptMove(target);
            }
            //Default move: jump 1 row ahead
            else if (((target.getRow() - position.getRow()) == -1) && target.getColumn() == position.getColumn()){
                acceptMove(target);
            }
            //Attack move: jumps 1 square diagonally if occupied by opposite color
            else if (((target.getRow() - position.getRow()) == -1) && (target.getColumn()-position.getColumn() == Math.abs(1)) && (target.isOccupied())){
                acceptMove(target);
            }
            else {
                refuseMove();
            }
        }
    }
}
