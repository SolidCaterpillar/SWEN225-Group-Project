package test.nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Colour;
import nz.ac.wgtn.swen225.lc.domain.Tile.LockedDoor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LockedDoorTest {

    private LockedDoor redDoor;
    private LockedDoor blueDoor;

    @BeforeEach
    void setup() {
        redDoor = new LockedDoor(new Coord(1, 1), Colour.RED);
        blueDoor = new LockedDoor(new Coord(2, 2), Colour.PINK);
    }

    @Test
    void testDoorColour() {
        assertEquals(Colour.RED, redDoor.getColour(), "Red door's colour should be RED.");
        assertEquals(Colour.PINK, blueDoor.getColour(), "Blue door's colour should be BLUE.");
    }

    @Test
    void testDoorCoord() {
        assertEquals(new Coord(1, 1), redDoor.getLocation(), "Red door's location should be (1, 1).");
        assertEquals(new Coord(2, 2), blueDoor.getLocation(), "Blue door's location should be (2, 2).");
    }

    @Test
    void testDoorStringRepresentation() {
        assertTrue("|L|".toString().equals(redDoor.toString()));
        assertTrue("|L|".toString().equals(blueDoor.toString()));
    }

    // If there are more methods or properties added to the LockedDoor class, they should be tested here.
}
