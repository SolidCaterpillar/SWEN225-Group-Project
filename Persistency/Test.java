package Persistency;

public class Test {
    public static void main(String[] args){
        Level l1 = Persistency.loadLevel1();

       Persistency.saveLevel("newFile.json",l1);


    }
}
