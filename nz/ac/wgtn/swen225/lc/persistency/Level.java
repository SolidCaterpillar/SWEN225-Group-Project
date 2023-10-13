package nz.ac.wgtn.swen225.lc.persistency;

import java.util.ArrayList;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Entity.Treasure;
/**
 * record that holds all objects needed to load the game
 * @author Alvien T. Salvador (Salvadalvi) 300614650
 */
public record Level(Board board,Player player, ArrayList<Key> keys,ArrayList<Treasure> treasures,ArrayList<Enemy> enemies){

    /**
 * Compact constructor with post-conditions
 */
    public Level {
        if(board == null) throw new IllegalArgumentException("board is null");
        if(player == null) throw new IllegalArgumentException("player is null is null");
        if(keys == null) throw new IllegalArgumentException("keys is null");
        if(treasures == null) throw new IllegalArgumentException("treasure is null");
        if(enemies == null) throw new IllegalArgumentException("enemies is null");
        

    }
}
