package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.persistency.plugin.main.java.org.json.*;

import java.util.ArrayList;

import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Entity.Treasure;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.InformationTile;
import nz.ac.wgtn.swen225.lc.domain.Colour;
/*
 * class that parses the json into game objects
 * @author salvadalvi
 */
public class ParseJson {
    
        /**
     * Parses the json file into usable game objs
     * @param JSONObject, contains all info to construct game
     * objs
     * @return ReadJson, obj that contains all parsed game objs
     * 
     *  **/
    public static ReadJson parse(JSONObject json){
        
        //breaks apart the  "json" into the main 3 JSONObjs
        JSONObject dimensions = json.getJSONObject("dimension");
        JSONObject tilesJson = json.getJSONObject("tiles");
        JSONObject entitesJson = json.getJSONObject("entites");

        //parse entites and tiles
        Entites entites = parseEntites(entitesJson);
        ArrayList<Tile> tiles = parseTiles(tilesJson);

        //returns obj with game tiles and entites
        return new ReadJson(dimensions.getInt("width"),dimensions.getInt("length"),entites , tiles);
    }
    /**
     * Parses the entity objects from a JSONobj
     * @param entites, contains all info to construct entites
     * @return Entites, all entites in play 
     * 
     *  **/
    private static Entites parseEntites(JSONObject entites){

        //breaks apart "entites" JSON obj into the main
        //entites
        JSONObject jsonPlayer = entites.getJSONObject("player");
        JSONArray jsonKeys = entites.getJSONArray("keys");
        JSONArray jsonTreasure = entites.getJSONArray("treasures");
        JSONArray jsonEnemies = entites.getJSONArray("enemies");
        
        //creates vars to hold these entites
        Player player = createPlayer(jsonPlayer);
        ArrayList<Entity> allEntity = new ArrayList<>();
        ArrayList<Key> keys = new ArrayList<>();
        ArrayList<Treasure> treasures = new ArrayList<>();
        ArrayList<Enemy> enemies = new ArrayList<>();



        //parses and creates new keys
        for(Object o : jsonKeys){
            if(o instanceof JSONObject key){
                int x = key.getInt("x");
                int y = key.getInt("y");
                Colour keyColour = parseColour(key.getString("colour"));
                keys.add(new Key(new Coord(x,y), keyColour));
            }
        }

        //parses and creates new treasures
        for(Object o : jsonTreasure){
            if(o instanceof JSONObject treasure){
                int x = treasure.getInt("x");
                int y = treasure.getInt("y");

                treasures.add(new Treasure(new Coord(x,y)));
            }
        }

        //parses and creates enemies in the game
        for(Object o : jsonEnemies){
            if(o instanceof JSONObject enemy){
                int x = enemy.getInt("x");
                int y = enemy.getInt("y");

                enemies.add(new Enemy(new Coord(x,y)));
            }
        }

        //adds the entity to a greater list with all
        //entites
        allEntity.add(player);
        allEntity.addAll(keys);
        allEntity.addAll(treasures);
        allEntity.addAll(enemies);

        return new Entites(player, treasures,keys,enemies, allEntity);
    }

    /**
     * Parses the tile objects from the json and puts them
     * in array list of tiles, that will be used to update
     * the board.
     * 
     * @param JsonTiles, JSON containing info to construct tiles
     * @return ArrayList<Tile>, returns all tiles in the game
     *  **/
    private static ArrayList<Tile> parseTiles(JSONObject jsonTiles){
        ArrayList<Tile> tiles = new ArrayList<>();

        //breaks apart "jsonTiles" into all spesific entity classes
        JSONObject exit = jsonTiles.getJSONObject("exit");
        JSONArray walls = jsonTiles.getJSONArray("walls");
        JSONArray lockDoor = jsonTiles.getJSONArray("lock_Door");
        JSONArray exitLock = jsonTiles.getJSONArray("exit_Lock");
        JSONArray questionBlock = jsonTiles.getJSONArray("question_Block");
        //parses the exit tile

        //parses and constructs exit
        int exitX = exit.getInt("x");
        int exitY = exit.getInt("y");

        tiles.add(new ExitTile(new Coord(exitX, exitY)));

        //parses wall tiles
        for(Object o : walls){
            if(o instanceof JSONObject wall){
                //starts where wall to be drawn
                int x = wall.getInt("x");
                int y = wall.getInt("y");
            
                int lengthDown = wall.getInt("length_down");
                int lengthUp = wall.getInt("length_up");
                int lengthRight = wall.getInt("length_right"); 
                int lengthLeft = wall.getInt( "Length_left");

                //creates new wall
                tiles.add(new Wall(new Coord(x,y)));

                //constructs if wall is in a line
                for(int down = 0; down <= lengthDown; down++){
                    tiles.add(new Wall(new Coord(x,y+down)));
                }

                for(int up = 0; up <= lengthUp; up++){
                    tiles.add(new Wall(new Coord(x,y-up)));
                }

                for(int right = 0; right <= lengthRight; right++){
                    tiles.add(new Wall(new Coord(x+right,y)));
                }
                for(int left = 0; left <= lengthLeft; left++){
                    tiles.add(new Wall(new Coord(x-left,y)));
                }

            }
        }
        
        //parses lock doors
        for(Object o : lockDoor){
            if(o instanceof JSONObject wall){
                int x = wall.getInt("x");
                int y = wall.getInt("y");
                Colour doorColour = parseColour(wall.getString("colour"));

                //Key keyDoor = keys.stream().filter(e-> e.getColour() == doorColor).findFirst().orElseThrow(()-> new IllegalArgumentException());
                tiles.add(new LockedDoor(new Coord(x,y),doorColour));

            }
        }

        //parses exit lock
            for(Object o : exitLock){
            if(o instanceof JSONObject wall){
                int x = wall.getInt("x");
                int y = wall.getInt("y");

                tiles.add(new ExitLock(new Coord(x,y)));
            }
        }

        //parses question tiles 
        for(Object o : questionBlock){
             if(o instanceof JSONObject wall){
                    int x = wall.getInt("x");
                    int y = wall.getInt("y");
                    String info = wall.getString("message");
                 tiles.add( new InformationTile(new Coord(x, y), info));
         }
         }


        return tiles;
    }
    
        /**
     * Creates a new player object, initalzing the player
     * inventory
     * 
     * @param jsonPlayer, json with player info
     * @return Player, new constructed player
     * 
     *  **/

    public static Player createPlayer(JSONObject jsonPlayer){
                //parses and creates new player
        Player player = new Player(new Coord(jsonPlayer.getInt("x"), jsonPlayer.getInt("y")));

        JSONObject jsonInventory  =  jsonPlayer.getJSONObject("inventory");
        
        ArrayList<Key> inventoryKey = new ArrayList<>();
        ArrayList<Treasure> inventoryTreasure = new ArrayList<>();

        //adds key to player invetory
        for(Object o : jsonInventory.getJSONArray("keys")){
            if(o instanceof JSONObject key){
                Coord keyCO = new Coord(key.getInt("x"), key.getInt("y"));

                inventoryKey.add(new Key(keyCO,parseColour(key.getString("colour"))));
            }
        }

        //adds treasures to player inventory
        for(Object o : jsonInventory.getJSONArray("treasures")){
            if(o instanceof JSONObject tre){
                Coord treCO = new Coord(tre.getInt("x"), tre.getInt("y"));
                inventoryTreasure.add(new Treasure(treCO));
            }
        }
        //player.setKeys(inventoryKeys);
        //player.setTresure(inventoryTreasure);
        return player;
    }
    /**
     * Takes the string representation of the colour turning 
     * it into the enum version.
     * 
     * @param col, String rep of colour
     * @return Colour, enum of colour
     * 
     * @exception IlleglaArgumentException, if a invalid colour
     * as found
     *  **/
    
    private static Colour parseColour(String col){
        
        switch(col){

            case "pink":
                return Colour.PINK;
            case "purple":
                return Colour.PURPLE;
            case "red":
                return Colour.RED;
            case "yellow":
                return Colour.YELLOW;
        }
        //if color is not valid, will throw error
        throw new IllegalArgumentException(); 
    }

    /**
     *Record containing all eneites that was parsed
     * 
     * @param player, the player in the game
     * @param treasures, all treasures in play in the game
     * @param keys, all keys in play
     * @param enemies, all enemies
     * @param entites, combination of all entites in the game
     *  **/
    protected record Entites(Player player, ArrayList<Treasure> treasures, ArrayList<Key> keys ,ArrayList<Enemy> enemies, ArrayList<Entity> entites){

    }

    /**
     *Record containing all parsed objs from json
     * 
     * @param width, board with
     * @param length, board length
     * @param entites, all entites in the game
     * @param tiles, all tiles in the game
     *  **/
    protected record ReadJson(int width, int length, Entites entites, ArrayList<Tile> tiles){

    }
}
