package Persistency;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import Domain.Tile;
import Domain.Entity;
import Domain.Player;
import Domain.Treasure;
import Domain.Key;
import Domain.Enemy;
import Domain.Coord;
import Domain.WallTile;
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
        ArrayList<Tile> tiles = parseTiles(tilesJson);

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

                keys.add(new Key(new Coord(x,y)));
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

                enemies.add(new Enemy(new Coord(x,y), null));
            }
        }
        allEntity.add(player);
        allEntity.addAll(keys);
        allEntity.addAll(treasures);
        allEntity.addAll(enemies);

        return new Entites(player, treasures,keys,enemies, allEntity);
    }

    /**
     * Parses the tile objects from the json
     * @param JSONObject
     * @return ArrayList<Tile>
     * 
     *  **/
    private static ArrayList<Tile> parseTiles(JSONObject jsonTiles){
        ArrayList<Tile> tiles = new ArrayList<>();

        JSONArray walls = jsonTiles.getJSONArray("walls");
        JSONArray lockDoor = jsonTiles.getJSONArray("lock_Door");
        JSONArray exitLock = jsonTiles.getJSONArray("exit_Lock");
        //JSONArray questionBlock = jsonTiles.getJSONArray("Walls");

        //parses wall tiles
        for(Object o : walls){
            if(o instanceof JSONObject wall){
                int x = wall.getInt("x");
                int y = wall.getInt("y");
                int lengthDown = wall.getInt("length_down");
                int lengthUp = wall.getInt("length_up");
                int lengthRight = wall.getInt("length_right"); 
                int lengthLeft = wall.getInt( "Length_left");


                tiles.add(new WallTile(new Coord(x,y), WallTile.WallType.NORMAL ,null));

                for(int down = 0; down <= lengthDown; down++){
                    tiles.add(new WallTile(new Coord(x,y+down), WallTile.WallType.NORMAL ,null));
                }

                for(int up = 0; up <= lengthUp; up++){
                    tiles.add(new WallTile(new Coord(x,y-up), WallTile.WallType.NORMAL ,null));
                }

                for(int right = 0; right <= lengthRight; right++){
                    tiles.add(new WallTile(new Coord(x+right,y), WallTile.WallType.NORMAL ,null));
                }
                for(int left = 0; left <= lengthLeft; left++){
                    tiles.add(new WallTile(new Coord(x-left,y), WallTile.WallType.NORMAL ,null));
                }

            }
        }
        
        //parses lock doors
        for(Object o : lockDoor){
            if(o instanceof JSONObject wall){
                int x = wall.getInt("x");
                int y = wall.getInt("y");

                tiles.add(new WallTile(new Coord(x,y), WallTile.WallType.LOCKED_DOOR ,null));
            }
        }

        //parses exit lock
            for(Object o : exitLock){
            if(o instanceof JSONObject wall){
                int x = wall.getInt("x");
                int y = wall.getInt("y");

                tiles.add(new WallTile(new Coord(x,y), WallTile.WallType.EXIT_LOCK ,null));
            }
        }

        //     for(Object o : questionBlock){
        //     if(o instanceof JSONObject wall){
        //         int x = wall.getInt("x");
        //         int y = wall.getInt("y");

        //         tiles.add(new WallTile(new Coord(x,y), WallTile.WallType.NORMAL ,null));
        //     }
        // }


        return tiles;
    }

    protected record Entites(Player player, ArrayList<Treasure> treasures, ArrayList<Key> keys ,ArrayList<Enemy> enemies, ArrayList<Entity> entites){

    }

    protected record ReadJson(int width, int length, Entites entites, ArrayList<Tile> tiles){

    }
}
