package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.Coord;

public class InformationTile extends Tile{

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


    public void display(){
        //Overridable for Renderer not sure rn
    }
}
