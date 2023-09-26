<<<<<<< HEAD:Domain/Enemy.java
package Domain;

import java.util.Queue;

public class Enemy implements Entity{

    public Coord location;

    Queue<Tile> movementRoute; // set of moves this enemy will make.

    public Enemy(Coord loc, Queue<Tile> movementRoute){
        location = loc;
        this.movementRoute = movementRoute;
    }

    @Override
    public Coord getLocation() {
        return location;
    }

    public void updateEnemyLocation(){

    }



    public void updateEnemyLocation(Tile newTile) {
        // derive location from the newTile
        Coord newLocation = newTile.getLoc();

        // check if the new tile is walkable (implement a method like isWalkable() in Tile)
        if (newTile.isWalkable() && isAdjacent(location, newLocation)) {
            //remove last stored entity. This should be cleared everytime a character walks off it
            newTile.tileAtLoc(location).setEntity(null);

            // update the enemy's location
            location = newLocation;

            // add enemy entity to the new tile
            newTile.setEntity(this);
        }
    }


    private boolean isAdjacent(Coord coord1, Coord coord2) {
        int dx = Math.abs(coord1.x() - coord2.x());
        int dy = Math.abs(coord1.y() - coord2.y());

        //if diff is 1 for x or y its adjacent
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    public Queue<Tile> getMoves(){
        return movementRoute;
    }

}
=======
import java.util.Queue;

public class Enemy implements Entity{

    public Coord location;

    Queue<Tile> movementRoute; // set of moves this enemy will make.

    public Enemy(Coord loc, Queue<Tile> movementRoute){
        location = loc;
        this.movementRoute = movementRoute;
    }

    @Override
    public Coord getLocation() {
        return location;
    }

    public void updateEnemyLocation(){

    }



    public void updateEnemyLocation(Tile newTile) {
        // derive location from the newTile
        Coord newLocation = newTile.getLoc();

        // check if the new tile is walkable (implement a method like isWalkable() in Tile)
        if (newTile.isWalkable() && isAdjacent(location, newLocation)) {
            //remove last stored entity. This should be cleared everytime a character walks off it
            if(newTile.tileAtLoc(location).isPresent()){
                newTile.tileAtLoc(location).get().setEntity(null);
            }
            else{ throw new IllegalArgumentException("Tile was not found to set to null.");}


            // update the enemy's location
            location = newLocation;

            // add enemy entity to the new tile
            newTile.setEntity(this);
        }
    }


    private boolean isAdjacent(Coord coord1, Coord coord2) {
        int dx = Math.abs(coord1.x() - coord2.x());
        int dy = Math.abs(coord1.y() - coord2.y());

        //if diff is 1 for x or y its adjacent
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    public Queue<Tile> getMoves(){
        return movementRoute;
    }

}

>>>>>>> be607156458326e1027608d7cc99362c51633e05:Domain1/Enemy.jav