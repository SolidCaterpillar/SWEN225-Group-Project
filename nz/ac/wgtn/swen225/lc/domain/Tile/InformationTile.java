package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;

public class InformationTile extends FreeTile{

    protected String information;

    public InformationTile(Coord loc, String info) {
        super(loc);
        information = info;
    }

    public String getInformation() {
        return information;
    }

    public String toString(){
        return "I";
    }


    public boolean isChapOn(Player player){
        return player.getLocation().equals(this.loc);
        //If chap is on then Renderer can use this to do stuff
    }


}
