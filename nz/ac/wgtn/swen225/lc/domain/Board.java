package nz.ac.wgtn.swen225.lc.domain;
import javax.swing.*;

import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

//sup
public class Board {

    int level;
    Tile[][] board = new Tile[20][20];

    static final int arrayDim = 20;
    static final int tileSize = 122;


    public Board( Tile[][] boardObj){
        board = boardObj;

    }


    public Tile[][] getBoard(){ //For returning game
        return board;
    }

public static int getDim(){return arrayDim; }
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



    //Tile at location
    public Tile getTileAt(int x, int y){
        return board[x][y];
    }

    public int getSize(){
        return tileSize;
    }




    public static boolean checkInBound(Coord check) {
        int x = check.x();
        int y = check.y();
        int dim = arrayDim;

        return x >= 0 && x < dim && y >= 0 && y < dim;
    }






    public Tile getTileAtLocation(Coord location) {
        int x = location.x();
        int y = location.y();

        if (checkInBound(location)) {
            return board[x][y];
        } else {
            throw new IllegalArgumentException("Invalid location: " + location);
        }
    }



    public void replaceTileAt(Coord coord, Tile newTile) {
        int x = coord.x();
        int y = coord.y();

        if (checkInBound(coord)) {
            board[x][y] = newTile;
        } else {
            throw new IllegalArgumentException("Invalid location: " + coord);
        }
    }


    //TEST METHOD
    public void addEntityToGame(Entity entity, Coord loc){
        int x = loc.x();
        int y = loc.y();

        Tile curr = board[x][y];
        curr.setEntity(entity);
    }

    public static ArrayList<Tile> getTileList(ArrayList<Coord> coords){
            ArrayList<Tile> tiles = new ArrayList<>();
            for (Coord coord : coords) {
                if (Board.checkInBound(coord)) {
                    tiles.add(Domain.getInstance().getBoard().getTileAtLocation(coord));
                }
            }

            return tiles;
        }

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



        public static Tile[][] initializeTiles() {
            Tile[][] tiles = new Tile[20][20];
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    tiles[i][j] = new FreeTile(new Coord(i, j));
                }
            }
            return tiles;
        }

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


    //String tester
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


