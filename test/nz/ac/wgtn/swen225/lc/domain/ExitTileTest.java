package test.nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.LevelE;
import nz.ac.wgtn.swen225.lc.domain.Tile.ExitTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExitTileTest {

    @Test
    public void testExitTileToString() {
        Coord location = new Coord(1, 1);
        LevelE nextLevel = LevelE.LEVEL_2; // Replace with the appropriate LevelE value
        ExitTile exitTile = new ExitTile(location, nextLevel);

        String expectedString = "EX";
        String actualString = exitTile.toString();

        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void testGetNextLevel() {
        Coord location = new Coord(2, 2);
        LevelE nextLevel = LevelE.LEVEL_3; // Replace with the appropriate LevelE value
        ExitTile exitTile = new ExitTile(location, nextLevel);

        LevelE retrievedLevel = exitTile.getNextLevel();

        Assertions.assertEquals(nextLevel, retrievedLevel);
    }
}