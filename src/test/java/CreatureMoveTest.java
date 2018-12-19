import org.junit.Test;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreatureMoveTest {
    private Logger log = Logger.getLogger("Test");
    @Test
    public void test(){
        log.setLevel(Level.WARNING);
        Random random = new Random();
        for(int i = 0; i < 2000; ++i) {
            Creature creature = new Creature(random.nextInt(2), "TestCreature", new Position(random.nextInt(20), random.nextInt(10)), null);
            try{
                creature.move(null);
            }catch(Exception ex){
                log.warning(ex.getMessage());
                return;
            }
        }
    }
}
