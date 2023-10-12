package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;
import java.util.List;

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

    static boolean gameWon = false;

    static String currPath = null;

    public static void reset() {
        curBoard = null;
        curPlayer = null;
        treasures = null;
        keys = null;
        enemies = null;
        gameWon = false;
    }


    public static void pickLevel(LevelE level){
        gameWon = false;
        Level curLevel = null;
        switch(level){
            case TEST_ONE:
                ArrayList<Key> keyList = new ArrayList<Key>(List.of(new Key(new Coord(0,0),null)));
                ArrayList<Enemy> enemyList = new ArrayList<>(List.of(new Enemy(new Coord(0,0))));
                ArrayList<Treasure> treasureList = new ArrayList<>(List.of(new Treasure(new Coord(0,0))));
                curLevel = new Level(new Board(Board.initializeTiles()),new Player(new Coord(0,0)),keyList, treasureList, enemyList);
                break;
            case LEVEL_ONE:
                curLevel = Persistency.loadLevel1();
                break;
            case LEVEL_TWO:
                curLevel = Persistency.loadLevel2();
                break;
        }


        curBoard = curLevel.board();
        Player newPlayer = curLevel.player();
        if(curPlayer != null){
            curPlayer.setLocation(newPlayer.getLocation());
        }else{
            curPlayer = newPlayer;
        }


        treasures = curLevel.treasures(); //Initialize the class-level ArrayLists
        keys = curLevel.keys();


        if(enemies == null){
            enemies = curLevel.enemies();
        }else{
            enemies.clear();
            enemies.addAll(curLevel.enemies());
        }

        if(!curPlayer.getTreasure().isEmpty()){
            curPlayer.setTreasure(newPlayer.getTreasure());
        }
        if(!curPlayer.getKeys().isEmpty()){
            curPlayer.setKeys(newPlayer.getKeys());
        }

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

            return false;
        }


        public static int checkGameState(int level) {

            // Check if the player is still alive
            if (isPlayerDead()) {
                System.out.println("Player has died. Reloading current level...");
                return 0;
            }
            // Check if the player is on an exit tile
            if (curBoard.getTileAtLocation(curPlayer.getTrueLocation()) instanceof ExitTile) {
                currentLevel = level;
                if (currentLevel == 1) {
                    System.out.println("Player reached the exit on level 1. Advancing to level 2...");
                    currentLevel = 2;
                    loadLevel(LevelE.LEVEL_TWO);
                    return 1;
                } else if (currentLevel == 2) {
                    System.out.println("Player reached the exit on level 2. You win!");
                    winState();
                    System.out.println(gameWon);
                    return 2;
                }
            }
            return 3;
        }

        public static int movePlayer(char move){
            return curPlayer.checkMove(move);
        }

        public static void reloadCurrentLevel() {
            loadLevel(currentLevel == 1 ? LevelE.LEVEL_ONE : LevelE.LEVEL_TWO);
        }

        private static void loadLevel(LevelE level) {
            pickLevel(level);
        }

        private void loadLevel1() {
            currentLevel = 1;
            pickLevel(LevelE.LEVEL_ONE);
        }

        private void loadLevel2() {
            currentLevel = 2;
            pickLevel(LevelE.LEVEL_TWO);
        }

        private static void winState(){
            gameWon = true;
        }

        private static boolean checkWon(Domain domain){
            return Domain.gameWon;
        }
    }


    //FOLLOWING TESTING ONLY
    // FOR TESTING ONLY
    public void setEnemies(ArrayList<Enemy> en) {
        if (enemies == null) {
            enemies = new ArrayList<>();
        }
        for (Enemy enemy : en) {
            enemies.add(enemy);
        }
    }


    public void createTileAtLoc(Tile tile) {
        if (Board.checkInBound(tile.getLocation())) {
            curBoard.replaceTileAt(tile.getLocation(), tile); // Replace the old tile with the new
        }
    }



    public static boolean setPath(String path){
        currPath = path;
        return Persistency.saveLevel(path, new Level(curBoard,curPlayer, keys,treasures,enemies));
    }


}

