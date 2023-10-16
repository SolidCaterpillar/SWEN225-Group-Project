package nz.ac.wgtn.swen225.lc.domain.Entity;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;


/**
 * Represents an enemy in the game. Enemies are dynamic entities that can move
 * around the game board. Their movement is randomized but restricted by their movement history
 * to avoid repetitive patterns.
 *
 * @author gautamchai ID: 300505029
 */
public class Enemy implements Entity{

    protected Coord location;

    //Takes random moves and if the last 2 tiles contain next tile in moves doesn't take it.
    private Queue<Coord> movementHistory = new LinkedList<>();

    Random rand = new Random();

    /**
     * Constructor to initialize the enemy with its starting position.
     *
     * @param loc The starting position of the enemy.
     */
    public Enemy(Coord loc){
        location = loc;
    }

    @Override
    public Coord getLocation() {
        return location;
    }


    /**
     * Updates the enemy's position on the board. The method determines valid random moves
     * and then performs the movement.
     */
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
                break;
            }
        }
    }


    /**
     * Performs the movement of the enemy on the board from its current location to a new location.
     *
     * @param coord The new position the enemy should move to.
     */
    private void performMovement(Coord coord) {
        Tile oldPos = Tile.tileAtLoc(location, Domain.getInstance().getBoard()).orElseThrow(() -> new IllegalArgumentException("Original position not found!"));

        Tile newLoc = Tile.tileAtLoc(coord,Domain.getInstance().getBoard()).orElseThrow(() -> new IllegalArgumentException("New position not found!"));

        newLoc.moveEntity(oldPos);


        location = coord;


        movementHistory.offer(coord);

        if (movementHistory.size() > 3) {
            movementHistory.poll();
        }
    }



    /**
     * Checks if moving to the specified coordinate is a valid move for the enemy.
     *
     * @param coord The coordinate to check.
     * @return True if the move is valid; false otherwise.
     */
    private boolean isValidMove(Coord coord) {
        // make it check if it's adjacent and on the board
        if (!Board.checkInBound(coord)) {
            return false;
        }

        // Check if the coordinate is adjacent and not one of the last two visited
        if (movementHistory.contains(coord)) {
            return false;
        }

        Tile destinationTile = Tile.tileAtLoc(coord,Domain.getInstance().getBoard()).orElseThrow(()-> new IllegalArgumentException("Tile to move to does not exist!"));
        return destinationTile.enemyWalkeable();
    }



    /**
     * Implements the Fisher-Yates shuffle algorithm to shuffle an array of coordinates.
     * This helps in randomizing the enemy's movements.
     *
     * @param array The array of coordinates to shuffle.
     */
    private void shuffleArray(Coord[] array) {

        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            Coord temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    @Override
    public String toString() {
        return "QX";
    }
}

