package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
public class FreeTile extends Tile{

    public FreeTile(Coord loc){
        super(loc);
    }

    public String toString(){
        return super.objEntity != null ? "|"+super.objEntity+"|" : "|_|";
    }
}
