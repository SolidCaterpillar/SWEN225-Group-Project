package Persistency;
import java.util.ArrayList;
import Domain.*;
class Level{

    private Board board;
    private ArrayList<Entity> entites;
    private Player player;

    public Level(){
        Tile tiles[][] = new Tile[26][26];
        
        board = new Board(1, tiles);
        entites = new ArrayList<>();
        player = new Player(new Coord(0,0));
    }

    public Board getBoard(){
        return  board;
    }

    public ArrayList<Entity> getEntites(){
        return entites;
    }

    public Player getPlayer(){

        return player;
    }

}
