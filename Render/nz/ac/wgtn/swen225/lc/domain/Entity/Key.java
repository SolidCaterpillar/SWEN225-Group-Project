package nz.ac.wgtn.swen225.lc.domain.Entity;

import nz.ac.wgtn.swen225.lc.domain.Coord;

public class Key implements Entity{

    protected Coord location;

    public Key(Coord co){
        location = co;
    }

    @Override
    public Coord getLocation() {
        return location;
    }

    public String toString(){
        return "k";
    }
}
