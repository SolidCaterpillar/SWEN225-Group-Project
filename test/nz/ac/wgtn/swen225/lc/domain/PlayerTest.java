package test.nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;
import nz.ac.wgtn.swen225.lc.domain.Entity.Orientation;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Entity.Treasure;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Domain domain;

    @BeforeEach
    void setUp() {
        domain = Domain.getInstance();
        domain.pickLevel(LevelE.LEVEL_ONE);
        player = domain.getPlayer();
    }

    @Test
    void testMove() {
        Coord initialLocation = player.getLocation();
        player.checkMove('w');
        Coord newLocation = player.getLocation();
        assertEquals(initialLocation.moveUp(), newLocation);
    }

    @Test
    void testPlayerCollectsKey() {
        Key key = new Key(new Coord(0, 0), Colour.PURPLE);
        domain.createTileAtLoc(new FreeTile(new Coord(10, 10)));
        Domain.getInstance().getBoard().addEntityToGame(key, new Coord(10, 10));
        player.setLocation(new Coord(9, 10));
        player.checkMove('d');
        assertTrue(player.getKeys().contains(key));
    }

    @Test
    void testPlayerCollectsTreasure() {
        Treasure treasure = new Treasure(new Coord(10, 10));
        domain.createTileAtLoc(new FreeTile(new Coord(10, 10)));
        domain.getBoard().addEntityToGame(treasure, new Coord(10, 10));
        player.setLocation(new Coord(9, 10));
        player.checkMove('d');
        assertTrue(player.getTreasure().contains(treasure));
    }

    @Test
    void testPlayerOrientation() {
        player.checkMove('d');
        assertEquals(Orientation.EAST, player.getDirection());
    }



    @Test
    void testInvalidMove() {
        Coord initialLocation = player.getLocation();
        domain.getBoard().replaceTileAt(player.getLocation().moveDown(),new Wall(player.getLocation().moveDown()));
        player.checkMove('s');
        Coord newLocation = player.getLocation();

        System.out.println("Initial: "+ initialLocation.toString());
        System.out.println("second: "+ newLocation.toString());

        assertEquals(initialLocation, newLocation); // Should not move
    }




    @Test
    void testTryOpenAdjacentLockedDoorWithoutKey() {
        Coord initialLocation = player.getLocation();
        domain.createTileAtLoc(new LockedDoor(initialLocation.moveDown(), Colour.PURPLE));
        player.checkMove('d'); // Trying to move right so the function 'tryOpenAdjacentLockedDoor' is triggered
        assertTrue(domain.getBoard().getTileAtLocation(initialLocation.moveDown()) instanceof LockedDoor);
    }

    @Test
    void testTryOpenAdjacentLockedDoorWithKey() {
        Coord initialLocation = player.getLocation();
        domain.createTileAtLoc(new LockedDoor(initialLocation.moveDown(), Colour.PURPLE));
        Key key = new Key(new Coord(0, 0), Colour.PURPLE);
        player.getKeys().add(key);
        player.checkMove('d'); // Trying to move right so the function 'tryOpenAdjacentLockedDoor' is triggered
        assertTrue(domain.getBoard().getTileAtLocation(initialLocation.moveDown()) instanceof FreeTile);
    }


}
