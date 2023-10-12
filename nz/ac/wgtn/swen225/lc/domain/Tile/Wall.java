package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
public class Wall extends Tile{
    
    public Wall(Coord loc){
        super(loc);
    }


    public boolean isWalkable(){
        return false;
    }

    public String toString(){
        return "|#|";
    }

}
