package Domain.Tile;

import Domain.Coord;
public class FreeTile extends Tile{

    public FreeTile(Coord loc){
        super(loc);
    }

    public String toString(){
        return super.objEntity != null ? "|"+super.objEntity+"|" : "|_|";
    }
}
