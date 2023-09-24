package Domain;

public record Coord(int x, int y){
    //Storing location in an object.


    public Coord moveUp(){
        return new Coord(x,y-1);
    }
    public Coord moveDown(){
        return new Coord(x, y+1);
    }
    public Coord moveLeft(){
        return new Coord(x-1,y);
    }
    public Coord moveRight(){
        return new Coord(x+1,y);
    }

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
