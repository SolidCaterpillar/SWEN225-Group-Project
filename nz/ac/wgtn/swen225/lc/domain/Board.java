package nz.ac.wgtn.swen225.lc.domain;
import javax.swing.*;

import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.*;
import java.awt.*;
import java.util.ArrayList;

//sup
public class Board {

    int level;
    Tile[][] board = new Tile[20][20];

    static final int arrayDim = 20;
    static final int tileSize = 122;


    public Board(int lev, Tile[][] boardObj){
        if(lev > 3 || lev < 0) throw new IllegalArgumentException("Invalid levels only 0-2"); //ONLY ALLOW LEVELS 0-3
        level = lev;

        //Test board constructor
        if(level == 0) {

        }

        else{
            board = boardObj;
        }
        //setupBoard(board);
    }


    public Tile[][] getBoard(){ //For returning game
        return board; //Unecessary copy was playing around
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


    //String tester
    public static void buildString(Tile[][] stringBoard) {
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

    //REMOVE EXIT TILES IF PLAYER HAS ALL OF THE TREASURE
    public static void openExitTile(Board b) {
        Tile[][] currentboard = b.getBoard();
        int dim = currentboard.length;

        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                Tile currentTile = currentboard[x][y];

                if (currentTile instanceof ExitLock) {
                    // Replace the ExitTile with a FreeTile at the same location
                    currentboard[x][y] = new FreeTile(new Coord(x, y));
                }
            }
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
                    tiles.add(Domain.staticBoard().getTileAtLocation(coord));
                }
            }

            return tiles;
        }
    }


