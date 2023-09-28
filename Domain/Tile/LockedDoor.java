package Domain.Tile;

import Domain.Coord;

public class LockedDoor extends Tile {
    
    public LockedDoor(Coord loc){
        super(loc);
    }

    public String toString(){
        return "|L|";
    }
}
