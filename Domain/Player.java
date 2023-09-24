package Domain;
import javax.swing.*;
import java.util.*;

//REDUNDANT CLASS REPLACED WITH CHAP

public class Player implements Entity{

    protected ArrayList<Treasure> treasures;
    protected Coord location;

    protected ArrayList<Key> keys;

    public Player(Coord loc){
        location = loc;
        treasures = new ArrayList<>();
        keys = new ArrayList<>();
    }

    //For checking if move is valid and then using swapTile to move
    public boolean checkMove(){ //Keyboard listener here
        //if() if Move +1,-1(x) or same for y is valid as TileType == FREE then swapTile also if this == a player otherwise false.
        return false;
    }

    public ArrayList<Key> getKeys() { //Keygetter
        return new ArrayList<>(Collections.unmodifiableCollection(keys));
    }

    public ArrayList<Treasure> getTreasure(){ //Treasuregetter
        return new ArrayList<>(Collections.unmodifiableCollection(treasures));
    }

    public Coord getLocation() {
        return location;
    }
}
