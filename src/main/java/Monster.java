import javafx.scene.image.Image;

enum MonsterEnum{
    SNAKE(0, "蛇精", "/pics/snake.png"), SCORPION(1, "蝎子", "/pics/scorpion.png"), FROG(2, "蛤蟆", "/pics/frog.png");

    public final int rank;
    public final String name;
    public final Image image;
    private MonsterEnum(int rank, String name, String url){
        this.rank = rank;
        this.name = name;
        this.image = new Image(url);
    }
}

public class Monster extends Creature {
    public Monster(MonsterEnum monster, Position position){
        super(1, monster.name, position, monster.image);
        this.rank = monster.rank;
    }
    public Monster(MonsterEnum monster, double x, double y){
        super(1, monster.name, x, y, monster.image);
        this.rank = monster.rank;
    }
}
