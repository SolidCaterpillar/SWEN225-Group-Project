package Domain.Entity;

import Domain.Coord;

public class Key implements Entity{

    protected Coord location;

    public Key(Coord co){
        location = co;
    }

    @Override
    public Coord getLocation() {
        return location;
    }

    public String toString(){
        return "k";
    }
}
