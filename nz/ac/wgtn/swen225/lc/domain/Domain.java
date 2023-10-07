package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;

import nz.ac.wgtn.swen225.lc.persistency.Persistency;
import nz.ac.wgtn.swen225.lc.persistency.Level;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;


public class Domain {

    protected static Board curBoard;
    protected static Player curPlayer;

    static ArrayList<Treasure> treasures;
    static ArrayList<Key> keys;
    static ArrayList<Enemy> enemies;

    public void picKLevel(LevelE level){
                Level curLevel = null;
        switch(level){
            case LEVEL_ONE:
                curLevel = Persistency.loadLevel1();
                break;
            case LEVEL_TWO:
                curLevel = Persistency.loadLevel2();
                break;
         }

        curBoard = curLevel.board();
        curPlayer = curLevel.player();

        treasures = curLevel.treasures(); //Initialize the class-level ArrayLists
        keys = curLevel.keys();
        enemies = curLevel.enemies();
    }

    public Board getBoard(){
        return curBoard;
    }

    public Player getPlayer(){
        return curPlayer;
    }

    public static ArrayList<Treasure> getTreasure(){
        return treasures;
    }

}
