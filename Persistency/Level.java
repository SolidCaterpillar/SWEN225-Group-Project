package Persistency;
import java.util.ArrayList;
import Domain.*;
import Domain.Entity.Enemy;
import Domain.Entity.Key;
import Domain.Entity.Player;
import Domain.Entity.Treasure;

public record Level(Board board,Player player, ArrayList<Key> keys,ArrayList<Treasure> treasures,ArrayList<Enemy> enemies){


}
