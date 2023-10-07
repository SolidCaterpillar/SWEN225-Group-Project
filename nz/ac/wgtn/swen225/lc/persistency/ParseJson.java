package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.persistency.plugin.main.java.org.json.*;

//import org.json.JSONObject;
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

public class ParseJson {
    
        /**
     * Parses the json file into usable objects
     * @param JSONObject 
     * @return Entites
     * 
     *  **/
    public static ReadJson parse(JSONObject json){
        
        JSONObject dimensions = json.getJSONObject("dimension");
        JSONObject tilesJson = json.getJSONObject("tiles");
        JSONObject entitesJson = json.getJSONObject("entites");

        Entites entites = parseEntites(entitesJson);
        ArrayList<Tile> tiles = parseTiles(tilesJson, entites.keys());

        //change the level
        return new ReadJson(dimensions.getInt("width"),dimensions.getInt("length"),entites , tiles);
    }
    /**
     * Parses the entity objects from the json
     * @param JSONObject 
     * @return Entites
     * 
     *  **/
    private static Entites parseEntites(JSONObject entites){

        JSONObject jsonPlayer = entites.getJSONObject("player");
        JSONArray jsonKeys = entites.getJSONArray("keys");
        JSONArray jsonTreasure = entites.getJSONArray("treasures");
        JSONArray jsonEnemies = entites.getJSONArray("enemies");
        
        Player player = null;
        ArrayList<Entity> allEntity = new ArrayList<>();
        ArrayList<Key> keys = new ArrayList<>();
        ArrayList<Treasure> treasures = new ArrayList<>();
        ArrayList<Enemy> enemies = new ArrayList<>();


        player = new Player(new Coord(jsonPlayer.getInt("x"), jsonPlayer.getInt("y")));

        //parses key objects
        for(Object o : jsonKeys){
            if(o instanceof JSONObject key){
                int x = key.getInt("x");
                int y = key.getInt("y");
                Colour keyColor = parseColour(key.getString("colour"));
                keys.add(new Key(new Coord(x,y), keyColor));
            }
        }

        //parses treasure objects
        for(Object o : jsonTreasure){
            //should talk about colours
            if(o instanceof JSONObject treasure){
                int x = treasure.getInt("x");
                int y = treasure.getInt("y");

                treasures.add(new Treasure(new Coord(x,y)));
            }
        }

        for(Object o : jsonEnemies){
            if(o instanceof JSONObject enemy){
                int x = enemy.getInt("x");
                int y = enemy.getInt("y");

                enemies.add(new Enemy(new Coord(x,y)));
            }
        }
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
     * @param JSONObject
     * @return ArrayList<Tile>
     * 
     *  **/
    private static ArrayList<Tile> parseTiles(JSONObject jsonTiles, ArrayList<Key> keys){
        ArrayList<Tile> tiles = new ArrayList<>();

        JSONObject exit = jsonTiles.getJSONObject("exit");
        JSONArray walls = jsonTiles.getJSONArray("walls");
        JSONArray lockDoor = jsonTiles.getJSONArray("lock_Door");
        JSONArray exitLock = jsonTiles.getJSONArray("exit_Lock");
        JSONArray questionBlock = jsonTiles.getJSONArray("question_Block");
        //parses the exit tile

        int exitX = exit.getInt("x");
        int exitY = exit.getInt("y");

        tiles.add(new ExitTile(new Coord(exitX, exitY)));

        //parses wall tiles
        for(Object o : walls){
            if(o instanceof JSONObject wall){
                int x = wall.getInt("x");
                int y = wall.getInt("y");
                int lengthDown = wall.getInt("length_down");
                int lengthUp = wall.getInt("length_up");
                int lengthRight = wall.getInt("length_right"); 
                int lengthLeft = wall.getInt( "Length_left");


                tiles.add(new Wall(new Coord(x,y)));

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
                Colour doorColor = parseColour(wall.getString("colour"));

                Key keyDoor = keys.stream().filter(e-> e.getColour() == doorColor).findFirst().orElseThrow(()-> new IllegalArgumentException());
                tiles.add(new LockedDoor(new Coord(x,y),keyDoor, doorColor));
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
        //if color is no valid, will throw error
        throw new IllegalArgumentException(); 
    }

    protected record Entites(Player player, ArrayList<Treasure> treasures, ArrayList<Key> keys ,ArrayList<Enemy> enemies, ArrayList<Entity> entites){

    }

    protected record ReadJson(int width, int length, Entites entites, ArrayList<Tile> tiles){

    }
}
