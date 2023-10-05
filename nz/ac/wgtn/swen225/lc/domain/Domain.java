package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;

import nz.ac.wgtn.swen225.lc.persistency.Persistency;
import nz.ac.wgtn.swen225.lc.persistency.Level;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;


public class Domain {

    Board curBoard;
    Player curPlayer;

    ArrayList<Treasure> treasures;
    ArrayList<Key> keys;
    ArrayList<Enemy> enemies;

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

        ArrayList<Treasure> treasures = curLevel.treasures();
        ArrayList<Key> keys = curLevel.keys();
        ArrayList<Enemy> enemies = curLevel.enemies();
    }

    public Board getBoard(){
        return curBoard;
    }

    public Player getPlayer(){
        return curPlayer;
    }


}
