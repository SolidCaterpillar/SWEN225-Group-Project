package Persistency;
import java.util.ArrayList;
import Domain.*;
class Level{

    private Board board;
    private ArrayList<Entity> entites;
    private Player player;

    public Level(){
        tiles = new Board(1, new );
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
