package schach.model;

public class Player {

    private final String name;
    private final String color;
    private boolean active;

    public Player(String name, String color){
        this.name = name;
        this.color =  color;
        this.setActive(color.equals("White"));
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

}
