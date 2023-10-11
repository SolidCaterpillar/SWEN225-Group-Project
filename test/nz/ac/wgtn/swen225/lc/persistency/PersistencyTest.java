package test.nz.ac.wgtn.swen225.lc.persistency;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.wgtn.swen225.lc.domain.Entity.*;
import nz.ac.wgtn.swen225.lc.persistency.*;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

class PersistencyTest {

    /*
    * Test for loading:
    *
    * loads chap onto the board
    *
    * @author Alvien T. Salvador (Salvadalvi)
    * */
    @Test
    void loadLevel1() {

        Player player = new Player(new Coord(0,0));

        Level l1 = Persistency.loadLevel("level/junitTest1.json");

        Level l1Model = initalizeLevel(player, new ArrayList<>(), new ArrayList<>());

        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /*
     * Test for loading:
     *
     * loads chap onto the board
     *
     * */
    @Test
    void loadLevel2() {

        Player player = new Player(new Coord(5,5));

        Level l1 = Persistency.loadLevel("level/junitTest2.json");

        Level l1Model = initalizeLevel(player, new ArrayList<>(), new ArrayList<>());

        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /*
     * Test for loading:
     *
     * loads chap onto the board and walls
     *
     * */
    @Test
    void loadLevel3() {

        Player player = new Player(new Coord(5,5));
        ArrayList<Tile> walls = new ArrayList<>();
        walls.add(new Wall(new Coord(10,10)));
        walls.add(new Wall(new Coord(10,11)));

        Level l1 = Persistency.loadLevel("level/junitTest3.json");

        Level l1Model = initalizeLevel(player, walls, new ArrayList<>());

        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /*
     * Test for loading:
     *
     * loads chap onto the board and walls and
     * Keys, and Treasrue
     *
     * */
    @Test
    void loadLevel4() {

        Player player = new Player(new Coord(5,5));

        ArrayList<Tile> walls = new ArrayList<>();
        ArrayList<Entity> entites = new ArrayList<>();

        walls.add(new Wall(new Coord(10,10)));
        walls.add(new Wall(new Coord(10,11)));

        entites.add(new Key(new Coord(9,9), Colour.PINK));

        entites.add(new Treasure(new Coord(3,3)));

        Level l1 = Persistency.loadLevel("level/junitTest4.json");



        Level l1Model = initalizeLevel(player, walls, entites);

        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /*
     * Test for loading:
     *
     * loads chap onto the board, and his inventory
     * Keys and invetory
     * */
    @Test
    void loadLevel5() {

        Player player = new Player(new Coord(10,10));
        ArrayList<Key> keys = new ArrayList<>();
        ArrayList<Treasure> treasures = new ArrayList<>();
        Level l1 = Persistency.loadLevel("level/junitTest5.json");

        keys.add(new Key(new Coord(9,9), Colour.PINK));
        treasures.add(new Treasure(new Coord(0,0)));
        //Persistency.loadLevel2();

        player.setTreasure(treasures);
        player.setKeys(keys);

        Level l1Model = initalizeLevel(player, new ArrayList<>(), new ArrayList<>());


        assertTrue(l1.player().getKeys().equals(l1.player().getKeys())
                && l1.player().getTreasure().equals(l1.player().getTreasure()));

    }
    /*
     * Test for loading:
     *
     * loads chap onto the board, and his inventory
     * */
    @Test
    void loadLevel6() {

        Player player = new Player(new Coord(10,10));
        ArrayList<Key> entites = new ArrayList<>();

        Level l1 = Persistency.loadLevel("level/junitTest6.json");


        player.setKeys(entites);

        Level l1Model = initalizeLevel(player, new ArrayList<>(), new ArrayList<>());


        assertTrue(l1.player().getKeys().equals(l1.player().getKeys()));

    }


    /*
     * Test for saving:
     *
     * saves chap and board
     * */
    @Test
    void saveLevel1(){

        Player player = new Player(new Coord(0,0));

        Level l1Model = initalizeLevel(player, new ArrayList<>(), new ArrayList<>());

        Persistency.saveLevel("junitTest7.json", l1Model);

        Level l1 = Persistency.loadLevel("level/junitTest7.json");

        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /*
     * Test for saving:
     *
     * saves chap and board
     * */
    @Test
    void saveLevel2(){

        Player player = new Player(new Coord(5,5));

        Level l1Model = initalizeLevel(player, new ArrayList<>(), new ArrayList<>());

        Persistency.saveLevel("junitTest8.json", l1Model);

        Level l1 = Persistency.loadLevel("level/junitTest8.json");

        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /*
     * Test for saving:
     *
     * saves chap and board and walls
     * */
    @Test
    void saveLevel3(){

        Player player = new Player(new Coord(5,5));

        ArrayList<Tile> walls = new ArrayList<>();

        walls.add(new Wall(new Coord(10,10)));
        walls.add(new Wall(new Coord(10,11)));

        Level l1Model = initalizeLevel(player, walls, new ArrayList<>());

        Persistency.saveLevel("junitTest9.json", l1Model);

        Level l1 = Persistency.loadLevel("level/junitTest9.json");

        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /*
     * Test for saving:
     *
     * saves chap and board and walls and speical tiles
     * */
    @Test
    void saveLevel4(){

        Player player = new Player(new Coord(5,5));

        ArrayList<Tile> walls = new ArrayList<>();
        ArrayList<Entity> entites = new ArrayList<>();

        walls.add(new Wall(new Coord(10,10)));
        walls.add(new Wall(new Coord(10,11)));
        walls.add(new ExitLock(new Coord(6,6)));
        walls.add(new InformationTile(new Coord(0,0), "HHHH"));

        walls.add(new LockedDoor(new Coord(10,5), Colour.PINK));
       // entites.add(new Key(new Coord(9,9), Colour.PINK));

        //entites.add(new Treasure(new Coord(3,3)));

        Level l1Model = initalizeLevel(player, walls, entites);



        Persistency.saveLevel("junitTest8.json", l1Model);

        Level l1 = Persistency.loadLevel("level/junitTest8.json");



        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /*
     * initalizes board
     * */
    Board initalizeBoard(Player p, ArrayList<Tile> walls, ArrayList<Entity> en){

        Tile[][] board = new Tile[20][20];

        for(int x = 0; x < 20; x++){
            for(int y = 0; y < 20; y++){

                board[x][y] = new FreeTile(new Coord(x,y));
            }
        }

        for(Tile w : walls){
            Coord loc = w.getLocation();
            board[loc.x()][loc.y()] = w;
        }

        for(Entity e : en){

            Coord loc = e.getLocation();
            board[loc.x()][loc.y()].setEntity(e);
        }

        board[p.getX()][p.getY()].setEntity(p);
        return new Board(3,board);
    }

    Level initalizeLevel(Player p, ArrayList<Tile> wall, ArrayList<Entity> en){

        Board board = initalizeBoard(p, wall, en);

        return new Level(board, p , new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    }



}