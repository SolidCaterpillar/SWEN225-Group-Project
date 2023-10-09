package Domain.Tile;

import Domain.Coord;
public class ExitLock extends Wall {
    
    public ExitLock(Coord loc){
        super(loc);
    }

    public String toString(){
        return "|M|";
    }
}
