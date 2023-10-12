package test.nz.ac.wgtn.swen225.lc.domain;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.LevelE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import java.util.*;

public class EnemyTest {

    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        Domain domain = Domain.getInstance();
        domain.reset(); // Reset the domain
        domain.pickLevel(LevelE.TEST_ONE); // Pick a test level, assuming you have this enum value
        enemy = new Enemy(new Coord(5, 5));
        ArrayList<Enemy> testEnemies = new ArrayList<>();
        testEnemies.add(enemy);
        domain.setEnemies(testEnemies); // Set up the enemy in Domain
    }

    @Test
    public void testUpdateEnemy() {
        Board board = new Board(Board.initializeTiles());
        enemy.updateEnemy();
        assertNotEquals(new Coord(5, 5), enemy.getLocation());
    }
}
