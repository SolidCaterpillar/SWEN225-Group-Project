package nz.ac.wgtn.swen225.lc.domain.Entity;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
public class Enemy implements Entity{

    protected Coord location;

    //Takes random moves and if the last 2 tiles contain next tile in moves doesn't take it.
    private Queue<Coord> movementHistory = new LinkedList<>();

    public Enemy(Coord loc, Queue<Tile> movementRoute){
        location = loc; //starting pos
    }

    @Override
    public Coord getLocation() {
        return location;
    }



 //Kind of unecessary
    private boolean isAdjacent(Coord coord1, Coord coord2) {
        int dx = Math.abs(coord1.x() - coord2.x());
        int dy = Math.abs(coord1.y() - coord2.y());

        //if diff is 1 for x or y its adjacent
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    public void updateEnemy() {
        //set or random m0ves
        Coord[] adjacentCoords = {
                location.moveUp(),
                location.moveLeft(),
                location.moveDown(),
                location.moveRight()
        };

        //shuffle them
        shuffleArray(adjacentCoords);

        for (Coord coord : adjacentCoords) {
            if (isValidMove(coord)) {
                performMovement(coord);
                break; // exit the loop after a valid move
            }
        }
    }


    private void performMovement(Coord coord) {
        // Get tile for movement
        Tile oldPos = Tile.tileAtLoc(location).orElseThrow(() -> new IllegalArgumentException("Original position not found!"));

        Tile newLoc = Tile.tileAtLoc(coord).orElseThrow(() -> new IllegalArgumentException("New position not found!"));

        newLoc.moveEntity(oldPos);

        // update the enemy's location
        location = coord;

        // add the new location to the movement history
        movementHistory.offer(coord);

        // Remove the oldest location from the history if it's longer than 2
        if (movementHistory.size() > 2) {
            movementHistory.poll();
        }
    }


    private boolean isValidMove(Coord coord) {
        Coord temp = coord;
        // make it check if it's adjacent and on the board
        if (!Board.checkInBound(temp)) {
            return false;
        }

        // Check if the coordinate is adjacent and not one of the last two visited
        if (!isAdjacent(coord, location) || movementHistory.contains(coord)) {
            return false;
        }

        Tile destinationTile = Tile.tileAtLoc(coord).orElseThrow(()-> new IllegalArgumentException("Tile to move to does not exist!"));
        return destinationTile instanceof FreeTile;
    }



    //array shuffle algorithm
    private void shuffleArray(Coord[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            Coord temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }



}

