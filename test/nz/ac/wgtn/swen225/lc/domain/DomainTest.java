package test.nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.LevelE;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import nz.ac.wgtn.swen225.lc.persistency.Level;
import nz.ac.wgtn.swen225.lc.persistency.Persistency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DomainTest {

    private Domain domain;

    @BeforeEach
    void setUp() {
        domain = Domain.getInstance();
    }

    @Test
    void testGetBoard() {
        // Create a test Board instance
        domain = Domain.getInstance();
        domain.pickLevel(LevelE.TEST_ONE);
        Board testBoard = new Board(Board.initializeTiles());
        assertTrue(testBoard.toString().equals(domain.getBoard().toString()));
    }

    @Test
    void testGetPlayer() {
        // Create a test Player instance
        Player testPlayer = new Player(new Coord(0, 0));
        domain = Domain.getInstance();
        domain.pickLevel(LevelE.TEST_ONE);
        assertTrue(testPlayer.equals(domain.getPlayer()));
    }

    @Test
    void testStaticBoard() {
        // Create a static Board instance
        Board testBoard = new Board(Board.initializeTiles());
        domain = Domain.getInstance();
        domain.pickLevel(LevelE.TEST_ONE);

        System.out.println(testBoard.toString());
        System.out.println(domain.getBoard().toString());

        assertTrue(testBoard.toString().equals(domain.getBoard().toString()));
    }


    @Test
    void testGetEnemies() {
        // Set up test data

        domain.pickLevel(LevelE.TEST_ONE);
        domain.reset(); // This reset might be clearing the enemies list again; consider its necessity.
        ArrayList<Enemy> testEnemies = new ArrayList<>();
        Enemy enemy1 = new Enemy(new Coord(1, 1));
        Enemy enemy2 = new Enemy(new Coord(2, 2));
        testEnemies.add(enemy1);
        testEnemies.add(enemy2);
        domain.setEnemies(testEnemies);

        assertEquals(2, Domain.getInstance().getEnemies().size());
    }


    @Test
    void testPlayerAliveState() {
        domain.pickLevel(LevelE.LEVEL_ONE);
        assertFalse(Domain.StateClass.isPlayerDead());
    }

    @Test
    void testInitialization() {
        domain.reset();
        assertNull(domain.getBoard());
        assertNull(domain.getPlayer());
    }

    @Test
    void testPickLevel() {
        domain.pickLevel(LevelE.TEST_ONE);
        assertNotNull(domain.getBoard());
        assertNotNull(domain.getPlayer());
    }
    @Test
    void testPickLevelTwo() {
        domain.pickLevel(LevelE.LEVEL_TWO);
        assertNotNull(domain.getBoard());
        assertNotNull(domain.getPlayer());
    }

    @Test
    void reloadLevel(){
        domain.pickLevel(LevelE.LEVEL_ONE);
        domain.getPlayer().checkMove('w');
        Coord posChanged = domain.getPlayer().getLocation();
        Domain.StateClass.reloadCurrentLevel();
        Coord originalPos = domain.getPlayer().getLocation();

        assertFalse(posChanged.equals(originalPos));
    }




}


