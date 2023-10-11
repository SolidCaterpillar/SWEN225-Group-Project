package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;

public class InformationTile extends FreeTile{

    protected String information;

    boolean chapIsOn = false;
    public InformationTile(Coord loc, String info) {
        super(loc);
        information = info;
        chapIsOn = false;
    }

    public String getInformation() {
        return information;
    }

    public String toString(){
        if(chapIsOn){
            return "PP";
        }
            return "I";
    }


    public void isChapOn(Player player){
        chapIsOn = player.getTrueLocation().equals(this.getLocation()) ? true : false;
        //if true renderer draws Chap icon over Info wall
    }

    public boolean enemyWalkeable() {
        return false; // Default behavior for non-FreeTile instances
    }

}
