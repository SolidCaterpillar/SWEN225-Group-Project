package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;

import nz.ac.wgtn.swen225.lc.domain.Tile.ExitLock;
import nz.ac.wgtn.swen225.lc.domain.Tile.ExitTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import nz.ac.wgtn.swen225.lc.persistency.Persistency;
import nz.ac.wgtn.swen225.lc.persistency.Level;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;


public class Domain {

    static Board curBoard;
    static Player curPlayer;

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

    public static void loadTest(){
        Level curLevel = null;

        curLevel = Persistency.loadLevelTest();

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

    public static Board staticBoard(){return curBoard;}
    public static ArrayList<Enemy> getEnemies(){return enemies;}

    public static ArrayList<Key> getKeys(){return keys;}

    public static ArrayList<Treasure> getTreasure(){
        return treasures;
    }





    public static class StateClass {
        private static int currentLevel = 1; //starting level


        //Run in app to check every tick.
        //then run checkGameState
        public static boolean isPlayerDead() {
            Coord playerLocation = curPlayer.getLocation();

            for (Enemy enemy : enemies) {
                if (enemy.getLocation().equals(playerLocation)) {
                    return true;
                }
            }

            return false; // Player is not dead
        }



        public static void checkGameState() {
            // Check if the player is still alive
            if (isPlayerDead()) {
                System.out.println("Player has died. Reloading current level...");

                try {
                    Thread.sleep(2000); // Sleep for 2 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
System.exit(1);
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
                    winState();
                }
            }
        }


        private static void reloadCurrentLevel() {
            loadLevel(currentLevel == 1 ? LevelE.LEVEL_ONE : LevelE.LEVEL_TWO);
        }

        private static void loadLevel(LevelE level) {
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

        private static void winState(){}
    }


    //FOLLOWING TESTING ONLY
    public void setEnemies(ArrayList<Enemy> en){
        for(Enemy enemy: en){
            enemies.add(enemy);
        }
    }

    public void createTileAtLoc(Tile tile) {
        if (Board.checkInBound(tile.getLocation())) {
            curBoard.replaceTileAt(tile.getLocation(), tile); // Replace the old tile with the new
        }
    }

}

