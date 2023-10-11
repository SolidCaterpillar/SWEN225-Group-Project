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



    public boolean enemyWalkeable() {
        return false; // Default behavior for non-FreeTile instances
    }

}
