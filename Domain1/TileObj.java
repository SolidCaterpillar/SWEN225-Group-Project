public interface TileObj {
    Entity getEntity(); // Get the entity associated with the tile
    void setEntity(Entity entity); // Set the entity associated with the tile
    void interact(Entity entity); // Define how the tile interacts with an entity
}
