package nz.ac.wgtn.swen225.lc.domain.Entity;

import nz.ac.wgtn.swen225.lc.domain.Colour;
import nz.ac.wgtn.swen225.lc.domain.Coord;

public class Key implements Entity{

    protected Colour colour;

    protected Coord location;

    public Key(Coord co /*,Colour col */){

        location = co;
        //colour = col;
    }

    @Override
    public Coord getLocation() {
        return location;
    }

    public String toString(){
        return "k";
    }

    public Colour getColour(){
        return colour;
    }

}
