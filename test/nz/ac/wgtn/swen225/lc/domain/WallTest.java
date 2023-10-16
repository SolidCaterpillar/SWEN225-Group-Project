package test.nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.Tile.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WallTest {

    private Wall wall;

    @BeforeEach
    void setUp() {
        Coord location = new Coord(1, 1); // Example location
        wall = new Wall(location);
    }


    @Test
    void isWalkable() {
        assertFalse(wall.isWalkable()); // A Wall is not walkable
    }

    @Test
    void toStringTest() {
        assertEquals("|#|", wall.toString()); // Check the string representation of Wall
    }


    @Test
    void testTile() {
        Tile check = new Tile(new Coord(0,2));
        assertFalse(check.enemyWalkeable()); // Check the string representation of Wall
    }


}
