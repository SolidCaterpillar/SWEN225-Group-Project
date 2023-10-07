package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;

import nz.ac.wgtn.swen225.lc.domain.Tile.ExitTile;
import nz.ac.wgtn.swen225.lc.persistency.Persistency;
import nz.ac.wgtn.swen225.lc.persistency.Level;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;


public class Domain {

    protected static Board curBoard;
    protected static Player curPlayer;

    static ArrayList<Treasure> treasures;
    static ArrayList<Key> keys;
    static ArrayList<Enemy> enemies;

    public static void picKLevel(LevelE level){
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


    public static ArrayList<Enemy> getEnemies(){return enemies;}

    public static ArrayList<Treasure> getTreasure(){
        return treasures;
    }




    public static class StateClass {
        private int currentLevel = 1; //starting level
        private boolean gameOver = false;
        private boolean gameWon = false;

        public void checkGameState(Domain d) {
            // Check if the player is still alive
            if (curPlayer.isDead()) {
                System.out.println("Player has died. Reloading current level...");
                reloadCurrentLevel();
                return;
            }

            // Check if the player is on an exit tile
            if (curBoard.getTileAtLocation(curPlayer.getLocation()) instanceof ExitTile) {
                if (currentLevel == 1) {
                    System.out.println("Player reached the exit on level 1. Advancing to level 2...");
                    currentLevel = 2;
                    loadLevel(LevelE.LEVEL_TWO);
                } else if (currentLevel == 2) {
                    System.out.println("Player reached the exit on level 2. You win!");
                    gameWon = true;
                    gameOver = true;
                }
            }
        }

        private void reloadCurrentLevel() {
            loadLevel(currentLevel == 1 ? LevelE.LEVEL_ONE : LevelE.LEVEL_TWO);
        }

        private void loadLevel(LevelE level) {
            picKLevel(level);

        }

        private void loadLevel1() {
            currentLevel = 1;
            picKLevel(LevelE.LEVEL_ONE);

        }

        private void loadLevel2() {
            currentLevel = 2;
            picKLevel(LevelE.LEVEL_TWO);

        }
    }



}

