package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;

public class FreeTile extends Tile{

    public FreeTile(Coord loc){
        super(loc);
    }

    public String toString(){
        return super.objEntity != null ? "|"+super.objEntity+"|" : "|_|";
    }

    @Override
    public boolean enemyWalkeable(){
        if(this instanceof FreeTile) {
            Entity check = this.getEntity();
            return check == null || check instanceof Player;
        }
        return false;
    }

}
