package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;

public class LockedDoor extends Tile {

    protected Colour colour;
    private Key unlockKey;
    public LockedDoor(Coord loc /*,Key unlockKey, Colour col */){
        super(loc);
        //this.unlockKey = unlockKey;
        //colour = col;
    }

    public String toString(){
        return "|L|";
    }

    public Key getUnlockKey(){
        return unlockKey;
    }

    public Colour getColour(){
        return colour;
    }

}
