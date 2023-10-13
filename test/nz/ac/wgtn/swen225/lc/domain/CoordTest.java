package test.nz.ac.wgtn.swen225.lc.domain;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.wgtn.swen225.lc.domain.Coord;
import org.junit.jupiter.api.Test;

public class CoordTest {

    @Test
    public void testMoveUp() {
        Coord coord = new Coord(1, 1);
        Coord newCoord = coord.moveUp();
        assertEquals(1, newCoord.x());
        assertEquals(0, newCoord.y());
    }

    @Test
    public void testMoveDown() {
        Coord coord = new Coord(1, 1);
        Coord newCoord = coord.moveDown();
        assertEquals(1, newCoord.x());
        assertEquals(2, newCoord.y());
    }

    @Test
    public void testMoveLeft() {
        Coord coord = new Coord(1, 1);
        Coord newCoord = coord.moveLeft();
        assertEquals(0, newCoord.x());
        assertEquals(1, newCoord.y());
    }

    @Test
    public void testMoveRight() {
        Coord coord = new Coord(1, 1);
        Coord newCoord = coord.moveRight();
        assertEquals(2, newCoord.x());
        assertEquals(1, newCoord.y());
    }

    @Test
    public void testMoveWithKeyPress() {
        Coord coord = new Coord(1, 1);
        Coord newCoord = coord.move('w');
        assertEquals(1, newCoord.x());
        assertEquals(0, newCoord.y());
    }

}
