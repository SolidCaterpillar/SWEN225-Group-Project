package nz.ac.wgtn.swen225.lc.fuzz;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.event.KeyEvent;
import java.util.Random;

import org.junit.Test;

import nz.ac.wgtn.swen225.lc.app.GUI;



/**
 * Fuzz testing class for game-related functionality.
 */
public class Fuzz_Testing {
	// NEED TO ENSURE THIS WAY OF TESTING ADHERES TO BRIEF

	private GUI gameGUI;

	/**
	 * 
	 */
	public Fuzz_Testing() {
		// Initialize the game here or pass an initialized game instance.
		// Make sure the game is in a state where it can start playing levels.
		gameGUI = new GUI();
	}

	/**
     * Perform fuzz testing for Level 1.
     */
	public void test1() {
		// Testing for level 1
		playLevel(1);
	}

	/**
     * Perform fuzz testing for Level 2.
     */
	public void test2() {
		// Testing for level 2
		playLevel(2);
	}

	
	/**
     * Runs all tests level dependent.
     */
	private void playLevel(int level) {
		// Implement fuzz testing logic for playing a specific level.
		// Involves testing scenarios level specific, such as key presses,
		// and simulating user interactions with the objects in the game.

		// Loop through action (Up, down, left, right)??

		// Tests scenarios generic to both levels
		if (level == 1 || level == 2) {
			//testWallCollisions();
			testWrongPosition();
		//	testInvalidInput();
			testValidLevels();
			testInvalidLevels();

		}

		// Tests for scenarios specific to level 2 (assuming that level two has
		// everything level 1 has and more)
		if (level == 2) {
			// i.e here automated enemies will be specific don't need to test for level 1 as
			// they don't exist

		}
	}


	
	
	/**
     * Checks handling for invalid player positions through domain.
     */
	@Test
	public void testWrongPosition() {
	    
	    // Tests invalid coordinates
	    // This is accessed through app, domain is responsible for coord

	    // Invalid coordinates that are outside the board boundaries
	    Coord invalidCoord1 = new Coord(-1, -1);
	    Coord invalidCoord2 = new Coord(4, 4);
	    Coord invalidCoord3 = new Coord(0, -1);
	    Coord invalidCoord4 = new Coord(-1, 0);

	    // Test if these coordinates are out of bounds - DONE IN DOMAIN
	    boolean isInvalid1 = Player.checkMove(invalidCoord1);
	    boolean isInvalid2 = Player.checkMove(invalidCoord2);
	    boolean isInvalid3 = Player.checkMove(invalidCoord3);
	    boolean isInvalid4 = Player.checkMove(invalidCoord4);

	    // Assert that all of these coordinates are invalid
	    assertFalse(isInvalid1);
	    assertFalse(isInvalid2);
	    assertFalse(isInvalid3);
	    assertFalse(isInvalid4);
	}

	
	
//	/**
//     * Checks handling for invalid input in move method through domain.
//     */
//	@Test
//	public void testInvalidInput() {
//	    //collisionBoard();
//
//	    // Create an array of invalid input keys
//		char[] invalidKeyCodes = {KeyEvent.VK_X, KeyEvent.VK_Z, KeyEvent.VK_1, KeyEvent.VK_EXCLAMATION, KeyEvent.VK_AT};
//
//	    // Iterate through each invalid key and test it
//	    for (char invalidKey : invalidKeyCodes) {
//	        try {
//	            Chap.checkMove(invalidKey); // Attempt to move with invalid input - DONE IN DOMAIN
//	            fail("Expected IllegalArgumentException for invalid input: " + invalidKey);
//	        } catch (IllegalArgumentException e) {
//	            // This is expected, as an exception should be thrown for invalid input
//	            // No need to add any specific assertions here
//	        }
//	    }
//	}
	
	

	
	int level;
	/**
     * Checks handling for valid level provided through domain.
     */
	    @Test
	    public void testValidLevels() {
	        // Test valid levels ( 1, 2)
	    	
	        for (level = 1; level <= 2; level++) {
	            assertDoesNotThrow(() -> new Board(level, new Tile[26][26]));
	        }
	    }

	    
	    /**
	     * Checks handling for invalid level provided through domain.
	     */
	    @Test
	    public void testInvalidLevels() {
	        // Test invalid levels (< 0 and > 2)
	        assertThrows(IllegalArgumentException.class, () -> new Board(-1, new Tile[26][26]));
	        assertThrows(IllegalArgumentException.class, () -> new Board(3, new Tile[26][26]));
	    }


	public static void main(String[] args) {
		Fuzz_Testing fuzzTester = new Fuzz_Testing();

		// Run fuzz tests for level 1 and level 2
		fuzzTester.test1();
		fuzzTester.test2();
	}

}


