package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;

public class ExitTile extends FreeTile{
    public ExitTile(Coord loc) {
        super(loc);
    }

    public String toString(){
        return "EX";
    }
}
