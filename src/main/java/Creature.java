import com.sun.istack.internal.Nullable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Creature {
    protected int camp;
    protected String name;
    protected Position position;
    protected Image image;

    public Creature(int camp, String name, Position position, @Nullable Image image){
        this.camp = camp;
        this.name = name;
        this.image = image;
        this.position = position;
    }

    public Creature(int camp, String name, double x, double y, @Nullable Image image){
        this.camp = camp;
        this.name = name;
        this.position = new Position(x, y);
        this.image = image;
    }

    public void move(@Nullable int[][] creatureMap) throws Exception{
        Random random = new Random();
        if(creatureMap == null) {
            int direction = random.nextInt(4);
            if (direction >= 4 || direction < 0)
                throw new Exception("unexpected direction");
            position.moveTo(direction);
        }
        else{
            int direction = random.nextInt(4);
            if (direction >= 4 || direction < 0)
                throw new Exception("unexpected direction");
            Position newPos = new Position(this.position);
            newPos.moveTo(direction);
            if(creatureMap[(int)newPos.getX()][(int)newPos.getY()] == -1)
                this.position.moveTo(direction);
        }
        //position.print();
        if(Position.outOfBounds(position))
            throw new Exception("creature move out of bounds");
    }

    public void drawSelf(GraphicsContext gc, double width, double height){
        gc.drawImage(image, position.getX()*width, position.getY()*height, width, height);
    }
}
