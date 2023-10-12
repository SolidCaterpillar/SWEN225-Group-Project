package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.persistency.plugin.main.java.org.json.*;
import java.util.ArrayList;

import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;
import nz.ac.wgtn.swen225.lc.domain.Entity.Treasure;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.Colour;
import nz.ac.wgtn.swen225.lc.domain.Board;
/**
 * class that reads game objects, and saves them in a
 * Json format.
 * @author Alvien T. Salvador (Salvadalvi) 300614650
 */
public class SaveJson {

    /**
     * Turns the game objects into a jsonobject
     * so it be written on to a .json file
     *
     * @param saveLevel, level to be turned into a json obj
     * @return JsonObject, json object with level field
     */
    public static JSONObject saveAsJson(Level saveLevel){
        //initalzies main json obj
        JSONObject json = new JSONObject();

        JSONObject dimension = new JSONObject();

        //turns entites into json form
        JSONObject entites = saveEntites(saveLevel);

        //turn the board into a json format
        JSONObject tiles = saveTiles(saveLevel.board());
        //saves the dimension of the board

        //initalizes dimension size in json
        dimension.put("width", 20);
        dimension.put("length", 20);


        //adds object information to main jsin
        json.put("dimension", dimension);
        json.put("tiles", tiles);
        json.put("entites", entites);

        return json;
    }
    /*INTERNAL METHODS FOR SAVING THE TILES INTO JSON */

    /**
     * Takes the a board, and turns the speical tiles into
     * json objects
     *
     * @param board, the board to be turned into a json
     * @return JsonObject, json object with board info
     */
    private static JSONObject saveTiles(Board board){
        JSONObject tiles = new JSONObject();

       //intalizes all json objects
        JSONObject exit = new JSONObject();
        JSONArray walls = new JSONArray();
        JSONArray lockedDoor = new JSONArray();
        JSONArray exitLock = new JSONArray();
        JSONArray questionBlock = new JSONArray();

        //loops through the boaed
        for(int x = 0; x < Board.getDim(); x++){

            for(int y = 0; y < Board.getDim(); y++){

                Tile tile = board.getTileAt(x,y);
                JSONObject obj = new JSONObject();

                //checks what kind of tile

                //if exit tile add to exitTile jsonObj
                if(tile instanceof ExitTile){
                    exit.put("x",tile.getLocation().x());

                    exit.put("y",tile.getLocation().y());

                //if wall tile create new json obj, and add to WallJsonArray
                }else if(tile instanceof LockedDoor lo){

                    obj.put("x",lo.getLocation().x());

                    obj.put("y",lo.getLocation().y());

                    obj.put("colour", loadColour(lo.getColour()));
                    lockedDoor.put(obj);

                    //if ExitLock create new json obj, add to LockedDoorArray
                }else if(tile instanceof Wall){

                    obj.put("x",tile.getLocation().x());

                    obj.put("y",tile.getLocation().y());

                    obj.put("length_down", 0);
                    obj.put("length_up", 0);
                    obj.put("length_right", 0);
                    obj.put("Length_left", 0);

                    walls.put(obj);

                //if LockedDoor create new json obj, add to LockedDoorArray
                }else if(tile instanceof ExitLock){
                    obj.put("x",tile.getLocation().x());

                    obj.put("y",tile.getLocation().y());

                    exitLock.put(obj);

                //if infoTile create new json obj, add to infoTileArray
                }else if(tile instanceof InformationTile msg){
                    obj.put("x",msg.getLocation().x());

                    obj.put("y",msg.getLocation().y());

                    obj.put("message",msg.getInformation() );

                    questionBlock.put(obj);
                    break;
                }
            }

        }

        //adds all JSONobjet into main JSonfile

        tiles.put("exit", exit);

        tiles.put("walls",walls);

        tiles.put("lock_Door", lockedDoor);

        tiles.put("exit_Lock",exitLock);

        tiles.put("question_Block", questionBlock);

        return tiles;
    }


    /*INTERNAL METHODS FOR SAVING THE ENTITES INTO JSON */

    /**
     * Takes the entites in the level, and turns them into
     * a json format.
     *
     * @param l, level where the entites have to be saved
     *
     * @return JsonObject, json object with \entitiy info
     */
    private static JSONObject saveEntites(Level l){

        JSONObject entites = new JSONObject();
        //creates and saves a jsonObject, containing the
        //respective entity into the entity JSONObject
        entites.put("player", savePlayer(l.player()));

        entites.put("keys", saveKeys(l.keys()));

        entites.put("treasures", saveTreasure(l.treasures()));

        entites.put("enemies", saveEnemies(l.enemies()));

        return entites;
    }

    /**
     * Takes the player, and turns it into a JSONobject
     *
     * @param p, player to be saved
     *
     * @return JsonObject, json object with player info
     */
    private static JSONObject savePlayer(Player p){
        JSONObject player = new JSONObject();
        JSONObject inventory = new  JSONObject();


        //sets up the player inventory
        inventory.put("keys", saveKeys(p.getKeys()));

        inventory.put("treasures", saveTreasure(p.getTreasure()));

        player.put("x", p.getX());

        player.put("y", p.getY());


        player.put("inventory", inventory);

        return player ;
    }

    /**
     * Takes the Keys, and turns it into a JSONobject
     * adding it to a greater JSON array
     *
     * @param key, keys to be saved
     *
     * @return JSONarray, json Array with jSONobjects with
     * key info
     */
    private static JSONArray saveKeys(ArrayList<Key> key){
        JSONArray keys = new JSONArray();
        for(Key k : key){
            JSONObject obj = new JSONObject();
            obj.put("x", k.getLocation().x());

            obj.put("y", k.getLocation().y());
            obj.put("colour", loadColour(k.getColour()));
            keys.put(obj);
        }
        return keys;
    }

    /**
     * Takes the Treasure, and turns it into a JSONobject
     * adding it to a greater JSON array
     *
     * @param tre, treasures to be saved
     *
     * @return JSONarray, json Array with jSONobjects with
     * treasure info
     */

    private static JSONArray saveTreasure(ArrayList<Treasure> tre){

        JSONArray treasure = new JSONArray();
        for(Treasure t: tre){

            JSONObject obj = new JSONObject();
            obj.put("x", t.getLocation().x());
            obj.put("y", t.getLocation().y());
            treasure.put(obj);

        }
        return treasure;
    }

    /**
     * Takes the enemies, and turns it into a JSONobject
     * adding it to a greater JSON array
     *
     * @param ens, enemies to be saved
     *
     * @return JSONarray, json Array with jSONobjects with
     *  enemy info
     */

    private static JSONArray saveEnemies(ArrayList<Enemy> ens){
        JSONArray enemies = new JSONArray();
        for(Enemy e: ens){
            JSONObject obj = new JSONObject();
            obj.put("x", e.getLocation().x());
            obj.put("y", e.getLocation().y());
            enemies.put(obj);
        }
        return enemies;
    }

    /**
     * Takes a enum colour, and turns it into
     * string representation
     *
     * @param colour, enum rep of colour
     *
     * @return colour, string rep of colour
    */

       private static String loadColour(Colour colour){
           return switch (colour) {
               case PINK -> "pink";
               case PURPLE -> "purple";
               case RED -> "red";
               case YELLOW -> "yellow";
           };
       }
}

