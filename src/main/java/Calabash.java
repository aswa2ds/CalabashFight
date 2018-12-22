import javafx.scene.image.Image;

enum CalabashEnum{
    GRANDPA(0, "爷爷", "/pics/grandpa.png"), RED(1, "大娃", "/pics/red.png"), ORANGE(2, "二娃", "/pics/orange.png"), YELLOW(3, "三娃", "/pics/yellow.png"), GREEN(4, "四娃", "/pics/green.png"), CYAN(5, "五娃", "/pics/cyan.png"), BLUE(6, "六娃", "/pics/blue.png"), PURPLE(7, "七娃", "/pics/purple.png");

    public final int rank;
    public final String name;
    public final Image image;
    private CalabashEnum(int rank, String name, String url){
        this.rank = rank;
        this.name = name;
        this.image = new Image(url);
    }
}

public class Calabash extends Creature {
    public Calabash(CalabashEnum calabash, Position position){
        super(0, calabash.name, position, calabash.image);
        this.rank = calabash.rank;
    }
    public Calabash(CalabashEnum calabash, double x, double y){
        super(0, calabash.name, x, y, calabash.image);
        this.rank = calabash.rank;
    }
}