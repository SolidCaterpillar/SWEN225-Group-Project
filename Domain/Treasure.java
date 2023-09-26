package Domain;
public class Treasure implements Entity{

    protected Coord location;
    public Treasure(Coord co){
        location = co;
    }
    @Override
    public Coord getLocation() {
        return location;
    }

    public String toString(){
        return "s";
    }
}
