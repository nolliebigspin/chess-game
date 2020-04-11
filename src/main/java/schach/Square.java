package schach;

public class Square {
    private boolean occupied;
    private String denotation;
    private Piece occupier;

    public Square(String denotation){
        this.denotation = denotation;
    }

    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }

    public boolean isOccupied(){
        return this.occupied;
    }

    public String getDenotation(){
        return this.denotation;
    }

    public void setOccupier(Piece occupier){
        this.occupier = occupier;
    }

    public Piece getOccupier(){
        return this.occupier;
    }

}
