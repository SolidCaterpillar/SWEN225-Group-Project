package nz.ac.wgtn.swen225.lc.domain;



/**
 * Represents a 2D coordinate with x and y values.
 * @author gautamchai ID: 300505029
 */
public record Coord(int x, int y){
    //Storing location in an object.

    /**
     * Moves the current coordinate one unit up.
     *
     * @return A new Coord with an updated y value.
     */
    public Coord moveUp(){
        return new Coord(x,y-1);
    }

    /**
     * Moves the current coordinate one unit down.
     *
     * @return A new Coord with an updated y value.
     */
    public Coord moveDown(){
        return new Coord(x, y+1);
    }

    /**
     * Moves the current coordinate one unit to the left.
     *
     * @return A new Coord with an updated x value.
     */
    public Coord moveLeft(){
        return new Coord(x-1,y);
    }

    /**
     * Moves the current coordinate one unit to the right.
     *
     * @return A new Coord with an updated x value.
     */
    public Coord moveRight(){
        return new Coord(x+1,y);
    }

    /**
     * Moves the current coordinate based on a specified key press.
     *
     * @param keyPressed The character representing the direction: 'w' for up, 's' for down, 'a' for left, 'd' for right.
     * @return A new Coord with updated x or y values based on the key pressed.
     * @throws IllegalArgumentException if an invalid key is pressed.
     */
    public Coord move(char keyPressed) {
        switch (keyPressed) {
            case 'w':
                return moveUp();
            case 's':
                return moveDown();
            case 'a':
                return moveLeft();
            case 'd':
                return moveRight();
            default:
                //Error case
                throw new IllegalArgumentException("Invalid input: " + keyPressed+". Only use wasd!");
        }
    }

}
