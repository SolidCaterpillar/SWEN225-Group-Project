package test.nz.ac.wgtn.swen225.lc.domain;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.wgtn.swen225.lc.domain.Tile.FreeTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;

public class FreeTileTest {

    private FreeTile freeTile;

    @BeforeEach
    public void setUp() {
        freeTile = new FreeTile(new Coord(1, 1));
    }

    @Test
    public void testToStringWithEntity() {
        freeTile.setEntity(new Player(new Coord(1, 1)));
        assertTrue(freeTile.toString().equals("|PP|")); // We're checking only the starting of the string because Player@ is followed by its hashcode.
    }

    @Test
    public void testToStringWithoutEntity() {
        assertEquals("|_|", freeTile.toString());
    }

    @Test
    public void testEnemyWalkableWithPlayer() {
        freeTile.setEntity(new Player(new Coord(1, 1)));
        assertTrue(freeTile.enemyWalkeable());
    }

    @Test
    public void testEnemyWalkableWithoutEntity() {
        assertNull(freeTile.getEntity());
        assertTrue(freeTile.enemyWalkeable());
    }
}
