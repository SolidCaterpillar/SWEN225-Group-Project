package Persistency;

public class Test {
    public static void main(String[] args){
        Level l1 = Persistency.loadLevel1();
        System.out.println(l1.board());
    }
}
