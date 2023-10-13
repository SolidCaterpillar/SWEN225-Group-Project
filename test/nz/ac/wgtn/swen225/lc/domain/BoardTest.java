package test.nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.LevelE;
import nz.ac.wgtn.swen225.lc.domain.Tile.FreeTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.Tile.Wall;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(Board.initializeTiles());
    }

    @Test
    void testGetBoard() {
        Tile[][] boardArray = board.getBoard();
        assertNotNull(boardArray, "Board should not be null");
        assertTrue(boardArray.length == Board.getDim(), "Board dimensions should match");
    }

    @Test
    void testToString() {
        String boardStr = board.toString();
        assertNotNull(boardStr, "String representation should not be null");
    }

    @Test
    void testGetTileAt() {
        Tile tile = board.getTileAt(1, 1);
        assertTrue(tile instanceof FreeTile, "Expected a FreeTile instance at (1,1)");
    }

    @Test
    void testGetTileAtLocation() {
        Coord coord = new Coord(1, 1);
        Tile tile = board.getTileAtLocation(coord);
        assertTrue(tile instanceof FreeTile, "Expected a FreeTile instance at (1,1)");
    }

    @Test
    void testReplaceTileAt() {
        Coord coord = new Coord(1, 1);
        board.replaceTileAt(coord, new Wall(coord));
        Tile tile = board.getTileAt(1, 1);
        assertTrue(tile instanceof Wall, "Expected a FreeTile instance at (1,1)");
    }

    @Test
    void testAddEntityToGame() {
        Coord coord = new Coord(1, 1);
        Entity entity = new Player(coord);
        board.addEntityToGame(entity, coord);
        assertEquals(entity, board.getTileAt(1, 1).getEntity(), "Entity should have been added to the board");
    }

    @Test
    void testGetTileList() {
        ArrayList<Coord> coords = new ArrayList<>(Arrays.asList(
                new Coord(0, 0),
                new Coord(1, 1),
                new Coord(2, 2)
        ));
        ArrayList<Tile> tiles = Board.getTileList(coords);
        assertEquals(3, tiles.size(), "Should have retrieved 3 tiles");
    }

    @Test
    void testEquals() {
        Board otherBoard = new Board(Board.initializeTiles());
        assertTrue(otherBoard.equals(board), "Two initialized boards should be equal");
    }


    @Test
    void testInitializeTiles() {
        Tile[][] tiles = Board.initializeTiles();
        assertNotNull(tiles, "Initialized tiles should not be null");
    }

    @Test
    void testSetPlacePlayer() {
        Coord coord = new Coord(1, 1);
        Entity entity = new Player(coord);
        board.addEntityToGame(entity, coord);

        Coord newLocation = new Coord(5, 5);
        Board.setPlacePlayer(board, newLocation);
        assertEquals(entity, board.getTileAt(5, 5).getEntity(), "Player should have been moved to new location");
    }


    @Test
    void testBuildString() {
        String s1 = "";
        String s2 = "";

        Domain dom = Domain.getInstance();

        dom.pickLevel(LevelE.TEST_ONE);

        s1 = Board.buildString(dom.getBoard().getBoard());

        dom.pickLevel(LevelE.TEST_ONE);

        s2 = Board.buildString(dom.getBoard().getBoard());

        assertEquals(s1,s2);
    }

}

