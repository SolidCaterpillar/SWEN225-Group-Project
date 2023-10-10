package nz.ac.wgtn.swen225.lc.persistency;

public class Test {
    


    public static void main(String args[]){
        
        Level l1 = Persistency.loadLevel1();

        System.out.println(l1.board().toString());

        Persistency.saveLevel("TESTING.json",l1);

        Level test = Persistency.loadLevel("level/TESTING.json");

        System.out.println(test.board().toString());
    }
}
