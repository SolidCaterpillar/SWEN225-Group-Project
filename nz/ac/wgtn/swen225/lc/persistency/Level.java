package nz.ac.wgtn.swen225.lc.persistency;

import java.util.ArrayList;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Entity.Treasure;
/*
 * record that holds all objects needed to load the game
 * @author salvadalvi
 */
public record Level(Board board,Player player, ArrayList<Key> keys,ArrayList<Treasure> treasures,ArrayList<Enemy> enemies){


}
