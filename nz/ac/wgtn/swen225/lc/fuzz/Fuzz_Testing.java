package test.nz.ac.wgtn.swen225.lc.fuzz;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class Fuzz_Testing {
	// NEED TO ENSURE THIS WAY OF TESTING ADHERES TO BRIEF

	private app Main;

	public Fuzz_Testing() {
		// Initialize the game here or pass an initialized game instance.
		// Make sure the game is in a state where it can start playing levels.
		game = new Main();
	}

	public void test1() {
		// Testing for level 1
		playLevel(1);
	}

	public void test2() {
		// Testing for level 2
		playLevel(2);
	}

	private void playLevel(int level) {
		// Implement fuzz testing logic for playing a specific level.
		// Involves testing scenarios level specific, such as key presses,
		// and simulating user interactions with the objects in the game.

		// Loop through action (Up, down, left, right)??

		// Tests scenarios generic to both levels
		if (level == 1 || level == 2) {
			testWallCollisions();
			testWrongPosition();
			testInvalidInput();
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

	// BOARDS FOR TEST SCENARIOS
	// CAN I DO THIS THIS WAY WITH SPECIFIC SCENARIOS?

	public void collisionBoard() {
		// Initialize the game board with a simple layout for testing
		// Set up simple board with walls
		// 'W' represents walls, P' represents player, F represents free tiles
		gameBoard = new GameBoard(2, 2); // Adjust the dimensions as needed
		// F|W|F
		// W|P|W
		// F|W|F

		// Set player location
		gameBoard.setTile(1, 1, TileType.PLAYER);

		// Set wall tiles
		gameBoard.setTile(1, 0, TileType.WALL);
		gameBoard.setTile(2, 1, TileType.WALL);
		gameBoard.setTile(1, 2, TileType.WALL);
		gameBoard.setTile(0, 1, TileType.WALL);
	}

	// TESTS

	// A lot of redundancy here if everything is done this way improve later
	// (list of actions to loop through?)
	// Can do similar for all various tile interactions
	
	
	@Test
	public void testWallCollisions() {
		collisionBoard();
		
		//FORCE RANDOM INPUT ONCE WORKING

		// Test collisions from all directions: up, down, left, right

		// Attempt to move up into a wall
		boolean upCollision = checkCollision(playerX, playerY - 1);
		assertTrue(upCollision); // Expecting a collision with the wall

		// Attempt to move down into a wall
		boolean downCollision = checkCollision(playerX, playerY + 1);
		assertTrue(downCollision); // Expecting a collision with the wall

		// Attempt to move left into a wall
		boolean leftCollision = checkCollision(playerX - 1, playerY);
		assertTrue(leftCollision); // Expecting a collision with the wall

		// Attempt to move right into a wall
		boolean rightCollision = checkCollision(playerX + 1, playerY);
		assertTrue(rightCollision); // Expecting a collision with the wall

	}
	
	
	
	//DOMAIN TESTS
	
	
	
	@Test
	public void testWrongPosition() {
	    collisionBoard();
	    
	    // Test a bunch of invalid coordinates
	    // This is accessed through app, domain is responsible for coord

	    // Invalid coordinates that are outside the board boundaries
	    Coord invalidCoord1 = new Coord(-1, -1);
	    Coord invalidCoord2 = new Coord(4, 4);
	    Coord invalidCoord3 = new Coord(0, -1);
	    Coord invalidCoord4 = new Coord(-1, 0);

	    // Test if these coordinates are out of bounds - DONE IN DOMAIN
	    boolean isInvalid1 = checkInbound(invalidCoord1.x(), invalidCoord1.y());
	    boolean isInvalid2 = checkInbound(invalidCoord2.x(), invalidCoord2.y());
	    boolean isInvalid3 = checkInbound(invalidCoord3.x(), invalidCoord3.y());
	    boolean isInvalid4 = checkInbound(invalidCoord4.x(), invalidCoord4.y());

	    // Assert that all of these coordinates are invalid
	    assertFalse(isInvalid1);
	    assertFalse(isInvalid2);
	    assertFalse(isInvalid3);
	    assertFalse(isInvalid4);
	}

	
	@Test
	public void testInvalidInput() {
	    collisionBoard();

	    // Create an array of invalid input keys
	    char[] invalidKeys = {'x', 'z', '1', '!', '@'};

	    // Iterate through each invalid key and test it
	    for (char invalidKey : invalidKeys) {
	        try {
	            move(invalidKey); // Attempt to move with invalid input - DONE IN DOMAIN
	            fail("Expected IllegalArgumentException for invalid input: " + invalidKey);
	        } catch (IllegalArgumentException e) {
	            // This is expected, as an exception should be thrown for invalid input
	            // No need to add any specific assertions here
	        }
	    }
	}
	
	


	    @Test
	    public void testValidLevels() {
	        // Test valid levels (0, 1, 2)
	        for (int level = 0; level <= 2; level++) {
	            assertDoesNotThrow(() -> new Board(level, new Tile[26][26]));
	        }
	    }

	    
	    @Test
	    public void testInvalidLevels() {
	        // Test invalid levels (< 0 and > 2)
	        assertThrows(IllegalArgumentException.class, () -> new Board(-1, new Tile[26][26]));
	        assertThrows(IllegalArgumentException.class, () -> new Board(3, new Tile[26][26]));
	    }
	
	
	    public void enemyNullPosCheck() {
	    	
	    	
	    }
	    
	    

	public static void main(String[] args) {
		Fuzz_Testing fuzzTester = new Fuzz_Testing();

		// Run fuzz tests for level 1 and level 2
		fuzzTester.test1();
		fuzzTester.test2();
	}

}
