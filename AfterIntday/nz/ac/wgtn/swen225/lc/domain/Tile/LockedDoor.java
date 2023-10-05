package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;

public class LockedDoor extends Tile {
    
    public LockedDoor(Coord loc){
        super(loc);
    }

    public String toString(){
        return "|L|";
    }
}
