package Persistency;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import org.json.JSONObject;

import java.lang.StringBuilder;

import Domain.*;
import Persistency.ParseJson.ReadJson;
import Persistency.ParseJson.Entites;
/** 
 * A class that saves/loads
 * **/
public class Persistency{

    private static Level loadLevel(String path){
        JSONObject json = getJson(path);
        ReadJson levelObjs = ParseJson.parse(json);
        return setupLevel(levelObjs);
    }

    public static boolean saveLevel(String filename, Level savedLevel){
        JSONObject savedJson = SaveJson.saveAsJson(savedLevel.board());
        try{
            
            File nFile = new File("level/"+filename);

            FileWriter jsonLevel = new FileWriter(nFile);

            System.out.println(savedJson.toString());
            jsonLevel.write(savedJson.toString());

            jsonLevel.close();
        }catch(IOException e){
            return false;
        }

        return true;
    }

    public static Level loadLevel1(){
        //Might need to change these paths, they're pretty wonky
        return loadLevel("level/level1.json");

    }

    public static Level loadLevel2(){
        //Might need to change these paths, they're pretty wonky
        return loadLevel("level/level2.json");
    }
    
    private static JSONObject getJson(String path){
        StringBuilder json = new StringBuilder();
        try{
            
            File jsonFile = new File(path);
            Scanner sc = new Scanner (jsonFile);
            
            while(sc.hasNextLine()){
                json.append(sc.nextLine());
            }
  
        }catch (IOException e){
            System.out.println(e.toString());
        }
        return new JSONObject(json.toString());
    }  

    private static Level  setupLevel(ReadJson objs){

        Entites gameEntites = objs.entites();
        ArrayList<Tile> gameTiles = objs.tiles();

        Tile[][] board = new Tile[objs.length()][objs.width()];

        //set up board
        for(int x = 0; x <= objs.length()-1; x++){
            for(int y = 0; y <= objs.width()-1; y++){
                board[y][x] = new Tile(new Coord(x,y), TileType.FREE, null);
            }
        }

        //put speical tiles on the board
        for(Tile tile : gameTiles){
            Coord tileCo = tile.getLoc();
            board[tileCo.y()][tileCo.x()] = tile;
        }

        //put entites on the board
        for(Entity entity : gameEntites.entites()){
            Coord coord = entity.getLocation();
            board[coord.y()][coord.x()].setEntity(entity);
        }

        return new Level(new Board(1,board), gameEntites.player(), gameEntites.keys(),gameEntites.treasures(),gameEntites.enemies());
    }

}

