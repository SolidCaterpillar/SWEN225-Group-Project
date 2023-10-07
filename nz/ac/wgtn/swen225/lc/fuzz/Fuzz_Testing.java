package nz.ac.wgtn.swen225.lc.Fuzz;

import nz.ac.wgtn.swen225.lc.app.GUI;
import nz.ac.wgtn.swen225.lc.app.Main;


import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Fuzz testing class for testing game levels 1 and 2.
 */
public class FuzzTest {

    private static GUIPanel panel;
    private static final List<Integer[]> moves = List.of(new Integer[]{0, 1}, new Integer[]{0, -1}, // all available moves, up, down, left, right, may need to change depended on how movement works
            new Integer[]{-1, 0}, new Integer[]{1, 0});


    /**
     * Initializes a HashMap of random test paths.
     * Each path is represented as a list of move directions.
     * Each path is associated with an initial score of 0.
     *
     * @return Map: Keys = list of x y Co-ords,   Values =  score initially set to 0
     */
    private Map<List<Integer[]>, Integer> initializeRandomTestPaths() {
        System.out.println("Initializing random test paths...");

        Map<List<Integer[]>, Integer> initTestPaths = new HashMap<>(); // make sure hashMap is empty for initial paths
        List<Integer[]> path; // empty path, path is sequence of moves
        int random;


        // generate specified amount of random paths, of length X - WILL DECIDE LATER
        for (int i = 0; i < 10; i++) {
            path = new ArrayList<Integer[]>();// reset path to empty list

            // generate X random directions, append to path - WILL DECIDE AMOUNT LATER
            for (int j = 0; j < 2; j++) {
                random = new Random().nextInt(4); // Select the next direction in path by random
                path.add(moves.get(random)); //appends a random move to
            }


            initTestPaths.put(path, 0); // add randomly generated path to map with default score of 0
        }

        return initTestPaths; // Return Map of list of x y Co-ords to score value of 0
    }


    /**
     * Generates evolved paths by appending possible moves to the top-scoring paths.
     *
     * @param currentPaths The current map of paths with their corresponding scores.
     * @return A map containing evolved paths with appended moves and scores initialized to 0.
     */
    private Map<List<Integer[]>, Integer> nextEvolvedPaths(Map<List<Integer[]>, Integer> currentPaths) {
        //pick the highest scored previous paths as the base for appending new moves
        Map<List<Integer[]>, Integer> topFourPaths = topPaths(currentPaths);
        Map<List<Integer[]>, Integer> nextPaths = new HashMap<>();

        //for each path in topFourPaths
        for (List<Integer[]> path : topFourPaths.keySet()) {
            //add top four paths to nextPaths (path + up,down,left,right) co-ords with score 0
            for (Integer[] move : moves) {
                List<Integer[]> copy = new ArrayList<>(path);
                copy.add(move);
                nextPaths.put(copy, 0);
            }
        }
        return nextPaths;
    }


    /**
     * Calculates and updates the scores of paths based on interactions within the game.
     *
     * @param currentPaths The current map of paths with their corresponding scores.
     * @param level        The game level being tested.
     * @return A map containing the updated path scores based on game interactions.
     */
    private Map<List<Integer[]>, Integer> calculatePathScores(Map<List<Integer[]>, Integer> currentPaths, Integer level) {

        System.out.println("Calculating Scores...");
        int score; // score for each path

        //for each path in current paths
        for (List<Integer[]> path : currentPaths.keySet()) {
            score = 0;

            if (level == 1) {
                loadLevel(1);
            } else {
                loadLevel(2);
            }


            Set<List<Integer[]>> visited = new HashSet<>();//store visited locations

            //for each direction in path
            for (Integer[] direction : path) {

                //Check if game has been won and apply score, need method from app
                //Should i check if game has ended also?

                //get info on interactions before
                int[] pastKeyCount = appMethod.keyInfo();
                int[] pastTreasureCount = appMethod.treasureInfo();
                int[] pastDoorCount = appMethod.doorInfo();

                //move player
                appMethod.movePlayer(direction[0], direction[1]);

                //Check if game has been won after move is made and apply score, need method from app
                //Should i check if game has ended also?

                //get info on interactions after
                int[] currentKeyCount = appMethod.keyInfo();
                int[] currentTreasureCount = appMethod.treasureInfo();
                int[] currentDoorCount = appMethod.doorInfo();
                visited.add(appMethod.playerCoords()); // player current location

                //score the move based on criteria
                score += compareKeys(pastKeyCount, currentKeyCount);
                score += compareDoors(pastDoorCount, currentDoorCount);
                score += compareTreasure(pastTreasureCount, currentTreasureCount);


            }

            score += visited.size() * 2; // add X points for each unique tile visited on board during movement on this path (no points for going over the same tile)
            currentPaths.put(path, score); //update paths score after evaluation

        }


        return currentPaths;
    }


    private Integer evaluatePath() { //might do this differently in calculate path scores
        // implement evaluating path
        return 0;
    }

    /**
     * Selects the top-scoring paths from currentPaths.
     *
     * @param currentPaths The current map of paths with their corresponding scores.
     * @return A map containing the top-scoring paths and their scores.
     */
    private Map<List<Integer[]>, Integer> topPaths(Map<List<Integer[]>, Integer> currentPaths) {

        // find the top paths create a list
        // use list to store top scoring paths temp

        // need to work with the entries of the currentPaths map
        // sort entries by scores in descending order
        // have a limit ensures tp only consider the top X amount of entries
        // map entries back to corresponding paths store


        // create new map called topFourPaths to store the final top paths

        // use a loop to pick the top 4 paths and add them to topFourPaths. {
        // generate random integer called to select a path randomly
        // add the randomly selected path to topFourPaths with an initial score of 0.

        return topFourPaths;
    }


    /**
     * Compares the count of doors opened before and after a move and returns a score based on the difference.
     *
     * @param pastKeyCount    represents the count of keys before making moves.
     * @param currentKeyCount represents the count of keys after making moves.
     * @return A score of X if a key is collected during the move, or 0 if no keys are collected or all keys are already collected.
     */
    private Integer compareKeys(int[] pastKeyCount, int[] currentKeyCount) {
        if (currentKeyCount[1] == 0) {
            return 10;
        } // if all keys are picked up

        int past = pastKeyCount[0];
        int current = currentKeyCount[0];

        if (current > past) {
            return 10;
        } // if picked up a key

        return 0;

    }


    /**
     * Compares the count of doors opened before and after a move and returns a score based on the difference.
     *
     * @param pastDoorCount    The count of doors opened before making moves.
     * @param currentDoorCount The count of doors opened after making moves.
     * @return A score of X if a door is opened during the move, or 0 if no doors are opened or all doors are already opened.
     */
    private Integer compareDoors(int[] pastDoorCount, int[] currentDoorCount) {
        if (currentDoorCount[1] == 0) {
            return 10;
        } // if all keys are picked up

        int past = pastDoorCount[0];
        int current = currentDoorCount[0];

        if (current > past) {
            return 10;
        } // if picked up a key

        return 0;

    }


    /**
     * Compares the count of treasures collected before and after a move and returns a score based on the difference.
     *
     * @param pastTreasureCount    The count of treasures collected before making moves.
     * @param currentTreasureCount The count of treasures collected after making moves.
     * @return A score of X if a treasure is collected during the move, or 0 if no treasures are collected or all treasures are already collected.
     */
    private Integer compareTreasure(int[] pastTreasureCount, int[] currentTreasureCount) {
        if (currentTreasureCount[1] == 0) {
            return 10;
        } // if all keys are picked up

        int past = pastTreasureCount[0];
        int current = currentTreasureCount[0];

        if (current > past) {
            return 10;
        } // if picked up a key

        return 0;

    }


    GUIPanel guiPanel; //need access to this from app
    GUIFram guiFrame; //need access to this from app

    /**
     * Initializes a new game by creating a new GUI panel and frame for testing.
     * This method sets up the necessary components for running the game test.
     */
    private void newGame() {
        // new gui panel
        guiFrame = new GUIFrame("Fuzz Test");
        guiPanel = new GUIPanel(guiFrame);
    }

    /**
     * Closes the game by closing the GUI frame.
     * This method is used to terminate the game after testing.
     */
    private void closeGame() {
        guiFrame.close(); //need implementation to close from app
    }


    /**
     * Loads the specified game level for testing.
     *
     * @param level The game level being tested.
     */
    private void loadLevel(int level) {
        System.out.println("loading level " + level + "...");
        //need implementation to load from app
    }


    /**
     * Runs fuzz testing for levels 1 and 2
     */
    @Test
    public void fuzzTesting() {
        levelTest1();
        //levelTest2();
    }

    /**
     * Test level one of the game for 60 seconds
     */
    private void levelTest1() {
        System.out.println("Testing level 1...");
        long start = System.currentTimeMillis(); //start time of the test
        while ((System.currentTimeMillis() - start) / 1000 < 60) {
            testLevel(1);
        }

    }

    /**
     * Test level two of the game for 60 seconds
     */
    private void levelTest2() {
        //copy test1 but for level 2

    }


    /**
     * @param level The game level being tested.
     */
    private void testLevel(int level) {
        //make first generation
        Map<List<Integer[]>, Integer> currentPaths = initializeRandomTestPaths();
        //loop X times
        for (int i = 0; i < 10; i++) {
            //evaluate current paths
            currentPaths = calculatePathScores(currentPaths, level);
            //make next paths based on calculations
            currentPaths = nextEvolvedPaths(currentPaths);
        }

    }


}//sub