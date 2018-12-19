
import static java.lang.Math.sqrt;

public class Position {
    private double x;
    private double y;
    public static final double columnCount = 20;
    public static final double rowCount = 10;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Position(Position pos){
        this.x = pos.x;
        this.y = pos.y;
    }

    public double distance(Position pos){
        double deltaX = pos.x - x;
        double deltaY = pos.y - y;
        return sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public double getX(){ return x; }
    public double getY(){ return y; }
    public void setX(double x){ this.x = x; }
    public void setY(double y){ this.y = y; }

    public static boolean outOfBounds(Position pos){
        if(pos.x < 0 || pos.x >= columnCount || pos.y < 0 || pos.y >= rowCount)
            return true;
        return false;
    }

    public void print(){
        System.out.printf("(%f, %f)\n", x, y);
    }

    //direction: 0:up 1:right 2:down 3:left
    public void moveTo(int direction){
        switch (direction){
            case 0:
                if(!outOfBounds(new Position(x, y-1))){
                    y--;
                    break;
                }
            case 1:
                if(!outOfBounds(new Position(x+1, y))){
                    x++;
                    break;
                }
            case 2:
                if(!outOfBounds(new Position(x, y+1))){
                    y++;
                    break;
                }
            case 3:
                if(!outOfBounds(new Position(x - 1, y))) {
                    x--;
                    break;
                }
        }
    }

    public Position neighboor(int direction){
        Position position = new Position(this);
        position.moveTo(direction);
        return position;
    }


}
