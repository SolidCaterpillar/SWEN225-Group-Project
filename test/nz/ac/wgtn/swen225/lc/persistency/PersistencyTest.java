package test.nz.ac.wgtn.swen225.lc.persistency;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.wgtn.swen225.lc.domain.Entity.*;
import nz.ac.wgtn.swen225.lc.persistency.*;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

class PersistencyTest {

    /**
    * Test for loading:
    *
    * loads chap onto the board
    *
    * @author Alvien T. Salvador (Salvadalvi) 300614650
    * */
    @Test
    void loadLevel1() {

        Player player = new Player(new Coord(0,0));

        Level l1 = Persistency.loadLevel("level/junitTest1.json");

        Level l1Model = initalizeLevel(player, new ArrayList<>(), new ArrayList<>());

        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /**
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

    /**
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

    /**
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

    /**
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

        player.setTreasure(treasures);
        player.setKeys(keys);

        Level l1Model = initalizeLevel(player, new ArrayList<>(), new ArrayList<>());


        assertTrue(l1.player().getKeys().equals(l1.player().getKeys())
                && l1.player().getTreasure().equals(l1.player().getTreasure()));

    }
    /**
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


    /**
     * Test for saving:
     *
     * saves chap and board
     * */
    @Test
    void saveLevel1(){

        //initalizes the game
        Player player = new Player(new Coord(0,0));

        Level l1Model = initalizeLevel(player, new ArrayList<>(), new ArrayList<>());

        //saves game
        Persistency.saveLevel("junitTest7.json", l1Model);

        Level l1 = Persistency.loadLevel("level/junitTest7.json");

        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /**
     * Test for saving:
     *
     * saves chap and board
     * */
    @Test
    void saveLevel2(){
        //intinalizes the game
        Player player = new Player(new Coord(5,5));

        Level l1Model = initalizeLevel(player, new ArrayList<>(), new ArrayList<>());

        Persistency.saveLevel("junitTest8.json", l1Model);

        Level l1 = Persistency.loadLevel("level/junitTest8.json");

        //checks if boards are identical
        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /**
     * Test for saving:
     *
     * saves chap and board and walls
     * */
    @Test
    void saveLevel3(){
        //initailize the level
        Player player = new Player(new Coord(5,5));

        ArrayList<Tile> walls = new ArrayList<>();

        walls.add(new Wall(new Coord(10,10)));
        walls.add(new Wall(new Coord(10,11)));
        //makes level
        Level l1Model = initalizeLevel(player, walls, new ArrayList<>());

        //saves the game
        Persistency.saveLevel("junitTest9.json", l1Model);

        Level l1 = Persistency.loadLevel("level/junitTest9.json");

        //checks if level is the same as as saved one
        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    /**
     * Test for saving:
     *
     * saves chap and board and walls and exit
     *
     * */
    @Test
    void saveLevel4(){
        //initailize the level
        Player player = new Player(new Coord(5,5));

        ArrayList<Tile> walls = new ArrayList<>();
        walls.add(new ExitTile(new Coord(0,0)));
        walls.add(new Wall(new Coord(10,10)));
        walls.add(new Wall(new Coord(10,11)));
        //makes level
        Level l1Model = initalizeLevel(player, walls, new ArrayList<>());



        //saves the game
        Persistency.saveLevel("junitTest9.json", l1Model);

        Level l1 = Persistency.loadLevel("level/junitTest9.json");

        //checks if level is the same as as saved one
        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }

    @Test
    void saveLevel5(){
        //initailize the level
        Player player = new Player(new Coord(5,5));
        ArrayList<Entity> entites = new ArrayList<>();

        ArrayList<Tile> walls = new ArrayList<>();

        walls.add(new Wall(new Coord(10,10)));
        walls.add(new Wall(new Coord(10,11)));

        //add entity in the board
        entites.add(new Key(new Coord(9,4), Colour.PINK));
        entites.add(new Key(new Coord(9,4), Colour.RED));
        entites.add(new Key(new Coord(9,4), Colour.PURPLE));
        entites.add(new Key(new Coord(9,4), Colour.YELLOW));

        entites.add(new Treasure(new Coord(3,3)));
        entites.add(new Enemy(new Coord(6, 6)));

        //makes level
        Level l1Model = initalizeLevel(player, walls, entites);



        //saves the game
        Persistency.saveLevel("junitTest8.json", l1Model);

        Level l1 = Persistency.loadLevel("level/junitTest8.json");


        //checks if level is the same as as saved one
        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

    }


    /**
     * Test for saving:
     *
     * saves chap and board and walls and speical tiles
     * also saving inventory
     * */
    @Test
    void saveLevel6(){
        //initailize the level
        Player player = new Player(new Coord(5,5));

        ArrayList<Tile> walls = new ArrayList<>();
        ArrayList<Entity> entites = new ArrayList<>();

        ArrayList<Key> keys = new ArrayList<>();
        ArrayList<Treasure> treasures = new ArrayList<Treasure>();

        walls.add(new Wall(new Coord(10,10)));
        walls.add(new Wall(new Coord(10,11)));
        walls.add(new ExitLock(new Coord(6,6)));
        walls.add(new InformationTile(new Coord(0,0), "test"));

        walls.add(new LockedDoor(new Coord(10,5), Colour.PINK));

       keys.add(new Key(new Coord(9,9), Colour.PINK));
        treasures.add(new Treasure(new Coord(3,3)));

        player.setKeys(keys);
        player.setTreasure(treasures);
        // make level
        Level l1Model = initalizeLevel(player, walls, entites);


        //saves level
        Persistency.saveLevel("junitTest9.json", l1Model);

        Level l1 = Persistency.loadLevel("level/junitTest9.json");

        assertTrue(l1.board().toString().equals(l1Model.board().toString()));

        //checks that the keys in player inventory are the
        //same
        for(Key k : l1.player().getKeys()){
            for(Key k2 : l1Model.player().getKeys()){
                Coord loc = k.getLocation();
                Coord loc2 = k2.getLocation();
                assertTrue(loc.x() == loc2.x());
                assertTrue(loc.y() == loc2.y());
                assertTrue(k.getColour() == k2.getColour());
            }
        }
        //checks that the treasure in player inventory are the
        //same
        for(Treasure t : l1.player().getTreasure()){
            for(Treasure t2 : l1Model.player().getTreasure()){
                Coord loc = t.getLocation();
                Coord loc2 = t.getLocation();
                assertTrue(loc.x() == loc2.x());
                assertTrue(loc.y() == loc2.y());

            }
        }

    }

    /**
     * method that initalizes board
     *
     * @param p, player to be added in the game
     * @param walls, tiles placed in the board
     * @param en, entites to be added
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
        return new Board(board);
    }
    /**
     * method that initalizes the level
     *
     * @param p, player to be added in the game
     * @param wall, tiles placed in the board
     * @param en, entites to be added
     * */
    Level initalizeLevel(Player p, ArrayList<Tile> wall, ArrayList<Entity> en){

        Board board = initalizeBoard(p, wall, en);
        //gets the keys from arrayList of en
        ArrayList<Key> keys = en.stream()

                .filter(e-> e instanceof Key).map(k -> {
                    if(k instanceof Key ky) return ky;

                    return null;}

        ).collect(Collectors.toCollection(ArrayList::new));

        //gets treastures from arrayList of en
        ArrayList<Treasure> treasures = en.stream()

                .filter(t-> t instanceof Treasure).map(t-> {
                    if(t instanceof Treasure te) return te;

                    return null;}

                ).collect(Collectors.toCollection(ArrayList::new));
        //gets enemy from arraylist of en
        ArrayList<Enemy> enemy = en.stream()

                .filter(e-> e instanceof Enemy).map(e-> {
                    if(e instanceof Enemy ene) return ene;

                    return null;}

                ).collect(Collectors.toCollection(ArrayList::new));

        return new Level(board, p , keys,treasures, enemy);
    }



}