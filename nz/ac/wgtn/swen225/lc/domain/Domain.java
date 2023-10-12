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

    // Singleton instance
    private static Domain instance;

    // Convert static members to instance members
    private Board curBoard;
    private Player curPlayer;

    private ArrayList<Treasure> treasures;
    private ArrayList<Key> keys;
    private ArrayList<Enemy> enemies;

    private boolean gameWon = false;
    private String currPath = null;


    public void reset() {
        curBoard = null;
        curPlayer = null;
        treasures = null;
        keys = null;
        enemies = null;
        gameWon = false;
    }


    private Domain() {

    }


    public static Domain getInstance() {
        if (instance == null) {
            instance = new Domain();
        }
        return instance;
    }

    public void pickLevel(LevelE level){
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

    public ArrayList<Enemy> getEnemies(){return enemies;}

    public  ArrayList<Key> getKeys(){return keys;}

    public  ArrayList<Treasure> getTreasure(){
        return treasures;
    }



    public static class StateClass {
        private static int currentLevel = 1; //starting level



        public static boolean isPlayerDead() {
            Coord playerLocation = Domain.getInstance().getPlayer().getLocation();

            for (Enemy enemy : Domain.getInstance().getEnemies()) {
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
            Board curBoard = Domain.getInstance().curBoard;
            Player curPlayer = Domain.getInstance().curPlayer;
            // Check if the player is on an exit tile
            if (curBoard.getTileAtLocation(curPlayer.getTrueLocation()) instanceof ExitTile extile) {
                //currentLevel = extile.getNextLevel().ordinal();
                currentLevel = level;
                if (currentLevel == 1) {
                    System.out.println("Player reached the exit on level 1. Advancing to level 2...");
                    currentLevel = 2;
                    loadLevel(LevelE.LEVEL_TWO);
                    return 1;
                } else if (currentLevel == 2) {
                    System.out.println("Player reached the exit on level 2. You win!");
                    winState();
                    System.out.println(Domain.getInstance().gameWon);
                    return 2;
                }
            }
            return 3;
        }

        public static int movePlayer(char move){
            return Domain.getInstance().curPlayer.checkMove(move);
        }

        public static void reloadCurrentLevel() {
            loadLevel(currentLevel == 1 ? LevelE.LEVEL_ONE : LevelE.LEVEL_TWO);
        }

        private static void loadLevel(LevelE level) {
            Domain.getInstance().pickLevel(level);
        }

        private void loadLevel1() {
            currentLevel = 1;
            Domain.getInstance().pickLevel(LevelE.LEVEL_ONE);
        }

        private void loadLevel2() {
            currentLevel = 2;
            Domain.getInstance().pickLevel(LevelE.LEVEL_TWO);
        }

        private static void winState(){
            Domain.getInstance().gameWon = true;
        }

        private static boolean checkWon(Domain domain){
            return  Domain.getInstance().gameWon;
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
        Domain.getInstance().currPath = path;
        Board curBoard = Domain.getInstance().curBoard;
        Player curPlayer = Domain.getInstance().curPlayer;
        var keys = Domain.getInstance().getKeys();
        var treasures = Domain.getInstance().getTreasure();
        var enemies = Domain.getInstance().getEnemies();
        return Persistency.saveLevel(path, new Level(curBoard,curPlayer, keys,treasures,enemies));
    }


}

