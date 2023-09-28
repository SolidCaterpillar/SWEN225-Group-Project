package Domain.Entity;
import javax.swing.*;

import Domain.Coord;
import Domain.Orientation;
import Domain.Tile.Tile;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Chap extends Player{

    protected ArrayList<Treasure> treasures;
    protected Coord location;

    protected ArrayList<Key> keys;

    public Chap(Coord loc){
        super(loc);
        treasures = new ArrayList<>();
        keys = new ArrayList<>();
    }



    public void checkMove(int keyCode) {
        Coord loc = null;
        Tile newPos = null;

        loc = switch (keyCode) {
            case KeyEvent.VK_W -> loc = this.location.moveUp();
            case KeyEvent.VK_A -> loc = this.location.moveLeft();
            case KeyEvent.VK_S -> loc = this.location.moveDown();
            case KeyEvent.VK_D -> loc = this.location.moveRight();
            default -> throw new IllegalArgumentException("Invalid key pressed!");
        };

            if (checkInBound(loc)) { //if new loc in bound
                Optional<Tile> optionalTile = Tile.tileAtLoc(loc); //get new tile
                if (optionalTile.isPresent()) { //if new tile presen
                    newPos = optionalTile.get();
                    if (newPos instanceof Domain.Tile.FreeTile) {


                        Tile oldPos = Tile.tileAtLoc(this.location).orElseThrow(
                                () -> new IllegalArgumentException("OG player position not found")
                        );


                        newPos.moveEntity(oldPos);
                        this.location = loc;
                    } else {
                        //Play sound
                        System.out.println("Invalid move");
                    }
                } else {
                    throw new IllegalArgumentException("No tile at the target location!");
                }
            }
        }


    public boolean checkInBound(Coord check) {
        int x = check.x();
        int y = check.y();
        int dim = Domain.Board.getDim();

        return x >= 0 && x < dim && y >= 0 && y < dim;
    }

    public ArrayList<Key> getKeys() { //Keygetter
        return new ArrayList<>(Collections.unmodifiableCollection(keys));
    }

    public ArrayList<Treasure> getTreasure(){ //Treasuregetter
        return new ArrayList<>(Collections.unmodifiableCollection(treasures));
    }

    @Override
    public Coord getLocation() {
        return location;
    }
    public int getX(){
        return this.location.x();
    }

    public int getY(){
        return this.location.y();
    }
}
