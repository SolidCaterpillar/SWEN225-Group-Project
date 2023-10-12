package nz.ac.wgtn.swen225.lc.persistency;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import nz.ac.wgtn.swen225.lc.persistency.plugin.main.java.org.json.*;

import java.lang.StringBuilder;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;

import nz.ac.wgtn.swen225.lc.persistency.ParseJson.*;
/**
 * The main class in Persistency, that loads and saves
 * the game
 * 
 * @author Alvien T. Salvador (Salvadalvi) 300614650
 * **/
public class Persistency{

    /**
     * Reads a json file, and loads the starting postion
     * of the level
     * .
     * @param path of the json file
     *
     * @return level holds objects needed to start the
     * current game
     */
    public static Level loadLevel(String path){
        //turns json file into json object
        JSONObject json = getJson(path);

        //further breaks down the json
        ReadJson levelObjs = ParseJson.parse(json);
        
        //load json objects to useable objects
        return setupLevel(levelObjs);
    }

    /**
    /* * Loads the first level of the game

     * @return level that holds objects to load level 1
     */
    public static Level loadLevel1(){

        return loadLevel("level/level1.json");

    }

    /**
     * Loads the second level of the game
     *
     * @return level that holds objects to load level 2
     */
    public static Level loadLevel2(){

        return loadLevel("level/level2.json");
    }

    /**
     * Saves the current format of the game
     * 
     * @param filename, new name for the saved level
     * @param savedLevel, level to be loaded
     * 
     * @return worked, returns true if no error
     *
     */
    public static boolean saveLevel(String filename, Level savedLevel){
        //turns the objects of the level into a JSONobject
        JSONObject savedJson = SaveJson.saveAsJson(savedLevel);
        try{
            
            //save this JSONobject string into a file format
            File nFile = new File("level/"+filename);

            FileWriter jsonLevel = new FileWriter(nFile);

            jsonLevel.write(savedJson.toString());
            //format followed is identical to how the levels
            //are written
            jsonLevel.close();
        }catch(IOException e){
            return false;
        }

        return true;
    }

    /**
     * Saves the current format of the game
     * 
     * @param path, finds and opens where json is
     * 
     * @return json, is the json file converted into
     * a object
     *
     */

    private static JSONObject getJson(String path){

        StringBuilder json = new StringBuilder();
        try{
            //opens the json file and put into 
            //a string
            File jsonFile = new File(path);
            Scanner sc = new Scanner (jsonFile);

            while(sc.hasNextLine()){
                json.append(sc.nextLine());
            }

        }catch (IOException e){
            System.out.println(e.toString());
        }

        //turn that string in to json
        return new JSONObject(json.toString());
    }

    /**
     * Combies all game objects in to a board to be
     * used
     * 
     * @param objs, the objects exctracted from the json
     * 
     * @return level, game to be loaded
     * 
     *
     */
    private static Level  setupLevel(ReadJson objs){

        Entites gameEntites = objs.entites();
        ArrayList<Tile> gameTiles = objs.tiles();

        //intializes the board
        Tile[][] board = new Tile[objs.length()][objs.width()];

        //set up board, initalzing eveything to free tile(moveable tile)
        for(int x = 0; x <= objs.length()-1; x++){
            for(int y = 0; y <= objs.width()-1; y++){
                board[x][y] = new FreeTile(new Coord(x,y));
            }
        }

        //put speical tiles on the board
        //speical tiles being: walls, locked door etc.
        for(Tile tile : gameTiles){
            Coord tileCo = tile.getLocation();
            board[tileCo.x()][tileCo.y()] = tile;
        }

        //put entites on the board
        //entites being: players, enemies  etc.
        for(Entity entity : gameEntites.entites()){
            Coord coord = entity.getLocation();
            board[coord.x()][coord.y()].setEntity(entity);
        }
        
        //constructs level object and return it
        return new Level(new Board(board), gameEntites.player(), gameEntites.keys(),gameEntites.treasures(),gameEntites.enemies());
    }

}

