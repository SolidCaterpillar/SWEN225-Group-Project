package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.persistency.plugin.main.java.org.json.*;
import java.util.ArrayList;
import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;
import nz.ac.wgtn.swen225.lc.domain.Entity.Treasure;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;

import nz.ac.wgtn.swen225.lc.domain.Colour;
public class SaveJson {

    public static JSONObject saveAsJson(Level saveLevel){
        JSONObject json = new JSONObject();

        JSONObject dimension = new JSONObject();
        JSONObject entites = saveEntites(saveLevel);
        JSONObject tiles = saveTiles(saveLevel.board());
        //saves the dimension of the board

        dimension.put("width", 20);
        dimension.put("length", 20);



        json.put("dimension", dimension);
        json.put("tiles", tiles);
        json.put("entites", entites);

        return json;
    }
    /*METHODS FOR SAVING THE TILES INTO JSON */
    private static JSONObject saveTiles(Board board){
        JSONObject tiles = new JSONObject();
        
        JSONObject exit = new JSONObject();
        JSONArray walls = new JSONArray();
        JSONArray lockedDoor = new JSONArray();
        JSONArray exitLock = new JSONArray();
        JSONArray questionBlock = new JSONArray();

        for(int x = 0; x < Board.getDim(); x++){
            for(int y = 0; y < Board.getDim(); y++){
                Tile tile = board.getTileAt(x,y);
                JSONObject obj = new JSONObject();
                if(tile instanceof ExitTile){
                    exit.put("x",tile.getLocation().x());

                    exit.put("y",tile.getLocation().y());

                }else if(tile instanceof Wall){

                    obj.put("x",tile.getLocation().x());

                    obj.put("y",tile.getLocation().y());

                    obj.put("length_down", 0);
                    obj.put("length_up", 0);
                    obj.put("length_right", 0);
                    obj.put("Length_left", 0);

                    walls.put(obj);
                }else if(tile instanceof LockedDoor lo){

                    obj.put("x",lo.getLocation().x());

                    obj.put("y",lo.getLocation().y());
                    
                    //obj.put("colour", loadColour(lo.getColour()));
                    lockedDoor.put(obj);

                }else if(tile instanceof ExitLock){
                    obj.put("x",tile.getLocation().x());

                    obj.put("y",tile.getLocation().y());
                    
                    exitLock.put(obj);

                }else if(tile instanceof InformationTile msg){
                    obj.put("x",msg.getLocation().x());

                    obj.put("y",msg.getLocation().y());

                    obj.put("message",msg.getInformation() );
                    
                    questionBlock.put(obj);

                }
            }

        }

        tiles.put("exit", exit);

        tiles.put("walls",walls);

        tiles.put("lock_Door", lockedDoor);

        tiles.put("exit_Lock",exitLock);

        tiles.put("question_Block", questionBlock);

        return tiles;
    }

    private static String loadColour(Colour colour){
        switch(colour){
            case PINK:
                return "pink";
            case PURPLE:
                return "purple";
            case RED:
                return "red";
            case YELLOW:
                return "yellow";
        }
        throw new IllegalArgumentException();
    }

    /*METHODS FOR SAVING THE ENTITES INTO JSON */

    private static JSONObject saveEntites(Level l){

        JSONObject entites = new JSONObject();

        entites.put("player", savePlayer(l.player()));

        entites.put("keys", saveKeys(l.keys()));

        entites.put("treasures", saveTreasure(l.treasures()));

        entites.put("enemies", saveEnemies(l.enemies()));

        return entites;
    }

    private static JSONObject savePlayer(Player p){
        JSONObject player = new JSONObject();

        player.put("x", p.getX());

        player.put("y", p.getY());

        return player ;
    }

    private static JSONArray saveKeys(ArrayList<Key> key){
        JSONArray keys = new JSONArray();
        for(Key k : key){
            JSONObject obj = new JSONObject(); 
            obj.put("x", k.getLocation().x());

            obj.put("y", k.getLocation().y());
            //obj.put("colour", loadColour(k.getColour()));
            keys.put(obj);
        }
        return keys;
    }

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


}

