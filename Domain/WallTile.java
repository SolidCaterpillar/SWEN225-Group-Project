package Domain;
import javax.swing.*;

public class WallTile extends Tile {

    public enum WallType {
        NORMAL,      // represents a normal wall
        EXIT_LOCK,   // represents an exit lock
        LOCKED_DOOR  // represents a locked door
    }

    private WallType wallType;

    public WallTile(Coord location, WallType wallType, JPanel gui) {
        super(location, TileType.WALL, gui);
        this.wallType = wallType;
    }


    //any additional methods or behaviors specific to WallTile here

    public WallType getWallType() {
        return wallType;
    }

    public void setWallType(WallType wallType) {
        this.wallType = wallType;
    }

    @Override
    public void interact(Entity entity) {
        switch (wallType) {
            case NORMAL:
                // Handle interaction for a normal wall
                break;
            case EXIT_LOCK:
                // Handle interaction for an exit lock
                break;
            case LOCKED_DOOR:
                // Handle interaction for a locked door
                break;
        }
    }

}

