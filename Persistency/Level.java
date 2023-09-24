package Persistency;
import java.util.ArrayList;
import Domain.*;
class Level{

    private ArrayList<Tile> tiles;
    private ArrayList<Entity> entites;
    private Player player;

    public Level(){
        tiles = new ArrayList<>();
        entites = new ArrayList<>();
        player = new Player(new Coord(0,0));
    }

    public ArrayList<Tile> getItems(){
        return  tiles;
    }

    public ArrayList<Entity> getEntites(){
        return entites;
    }

    public Player getPlayer(){

        return player;
    }

}
