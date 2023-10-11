package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
public class ExitLock extends Tile{
    
    public ExitLock(Coord loc){
        super(loc);
    }

    public String toString(){
        return "|M|";
    }
}
