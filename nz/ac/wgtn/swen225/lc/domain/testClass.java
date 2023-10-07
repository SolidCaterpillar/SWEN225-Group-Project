package nz.ac.wgtn.swen225.lc.domain;
import nz.ac.wgtn.swen225.lc.persistency.Persistency;
import nz.ac.wgtn.swen225.lc.persistency.Level;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;
public class testClass {
    public testClass(){}

    public static void main(String... args){
        Board test = new Board(0,null);
        Persistency.loadLevel1().board().toString();
    }
}
