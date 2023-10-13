package nz.ac.wgtn.swen225.lc.domain;
import javax.swing.*;

import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Represents a game board with tiles and entities.
 * @author gautamchai ID: 300505029
 */
public class Board {

    int level;
    Tile[][] board = new Tile[20][20];

    static final int arrayDim = 20;
    static final int tileSize = 122;



    /**
     * Constructs a Board with a given set of tiles.
     *
     * @param boardObj The 2D array of tiles representing the board.
     */
    public Board( Tile[][] boardObj){
        board = boardObj;

    }


    /**
     * Gets the current state of the board.
     *
     * @return The 2D array of tiles.
     */
    public Tile[][] getBoard(){ //For returning game
        return board;
    }


    /**
     * Returns the dimensions of the board.
     *
     * @return The dimension size.
     */
    public static int getDim(){return arrayDim; }


    /**
     * Provides a string representation of the board.
     *
     * @return A string representation of the board.
     */
    public String toString(){
        String b = "";
        for(int x = 0; x < board.length; x++){
            for(int y = 0; y < board[0].length; y++){
                b += board[x][y];
            }
            b += "\n";
        }
        return b;
    }



    /**
     * Gets the tile at a specific coordinate.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The tile at the specified coordinate.
     */
    public Tile getTileAt(int x, int y){
        return board[x][y];
    }

    /**
     * Gets the size of the tiles.
     *
     * @return The tile size.
     */
    public int getSize(){
        return tileSize;
    }



    /**
     * Checks if a coordinate is within the board's bounds.
     *
     * @param check The coordinate to check.
     * @return True if the coordinate is within bounds, otherwise false.
     */
    public static boolean checkInBound(Coord check) {
        int x = check.x();
        int y = check.y();
        int dim = arrayDim;

        return x >= 0 && x < dim && y >= 0 && y < dim;
    }





    /**
     * Retrieves the tile at a given coordinate location.
     *
     * @param location The location of the tile.
     * @return The tile at the given location.
     * @throws IllegalArgumentException if the location is out of bounds.
     */
    public Tile getTileAtLocation(Coord location) {
        int x = location.x();
        int y = location.y();

        if (checkInBound(location)) {
            return board[x][y];
        } else {
            throw new IllegalArgumentException("Invalid location: " + location);
        }
    }


    /**
     * Replaces a tile at a given coordinate with a new tile.
     *
     * @param coord The coordinate of the tile to replace.
     * @param newTile The new tile to place at the coordinate.
     * @throws IllegalArgumentException if the coordinate is out of bounds.
     */
    public void replaceTileAt(Coord coord, Tile newTile) {
        int x = coord.x();
        int y = coord.y();

        if (checkInBound(coord)) {
            board[x][y] = newTile;
        } else {
            throw new IllegalArgumentException("Invalid location: " + coord);
        }
    }


    /**
     * Test method to add an entity to the game at a specified location.
     *
     * @param entity The entity to add.
     * @param loc The location to place the entity.
     */
    public void addEntityToGame(Entity entity, Coord loc){
        int x = loc.x();
        int y = loc.y();

        Tile curr = board[x][y];
        curr.setEntity(entity);
    }


    /**
     * Retrieves a list of tiles based on given coordinates.
     *
     * @param coords The list of coordinates.
     * @return A list of tiles corresponding to the coordinates.
     */
    public static ArrayList<Tile> getTileList(ArrayList<Coord> coords){
            ArrayList<Tile> tiles = new ArrayList<>();
            for (Coord coord : coords) {
                if (Board.checkInBound(coord)) {
                    tiles.add(Domain.getInstance().getBoard().getTileAtLocation(coord));
                }
            }

            return tiles;
        }

    /**
     * Checks if two boards are equal based on their tile arrangement.
     *
     * @param obj The other board to compare with.
     * @return True if the boards are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Board board = (Board) obj;
        String newBoard = Board.buildString(board.getBoard());
        String thisBoard = Board.buildString(this.getBoard());
        // Compare attributes of the Board here. For example:
        System.out.println(newBoard);
        System.out.println(thisBoard);
        return newBoard.equals(thisBoard);
    }


    /**
     * Initializes an array of FreeTile tiles.
     *
     * @return A 2D array of FreeTile tiles.
     */
        public static Tile[][] initializeTiles() {
            Tile[][] tiles = new Tile[20][20];
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    tiles[i][j] = new FreeTile(new Coord(i, j));
                }
            }
            return tiles;
        }


        /**
     * Moves the player to a new location on the board.
     *
     * @param board The board containing the player.
     * @param newLocation The new location to move the player to.
     */
    public static void setPlacePlayer(Board board, Coord newLocation) {
        Tile[][] tiles = board.getBoard();

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                Tile currentTile = tiles[x][y];
                Entity entity = currentTile.getEntity();

                if (entity instanceof Player) {
                    // Remove the player entity from the current tile
                    currentTile.setEntity(null);

                    // Place the player entity at the new location
                    tiles[newLocation.x()][newLocation.y()].setEntity(entity);

                    return; // Player found and placed, so exit the loop
                }
            }
        }
    }


    /**
     * Builds a string representation of a 2D tile array.
     *
     * @param stringBoard The 2D tile array.
     * @return A string representation of the array.
     */
    public static String buildString(Tile[][] stringBoard) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < stringBoard.length; i++) {
            for (int j = 0; j < stringBoard[0].length; j++) {
                ret.append(stringBoard[i][j].toString());
                if (j == stringBoard[0].length - 1) {
                    ret.append("\n");
                }
            }
        }
        System.out.println(ret.toString());
        return ret.toString();
    }



}


