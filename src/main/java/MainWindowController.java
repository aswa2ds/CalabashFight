import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import static java.lang.Thread.sleep;


public class MainWindowController {
    @FXML private Canvas fightBlock;
    @FXML private Label finishLabel;
    private final Image fightBackGround = new Image("/pics/firekeeper.png");
    private int[][] creatureMap = new int[20][10];
    private final int columnCount = 20;
    private final int rowCount = 10;
    private Double unitWidth;
    private Double unitHeight;
    private int leftCount;
    private int rightCount;
    private boolean fighting;
    private Position lastPos;
    private List<Creature> creatures;
    @FXML protected void handleExitWindow(ActionEvent event){
        Platform.exit();
    }

    @FXML protected void handleNextStep(KeyEvent keyEvent){
        System.out.println(keyEvent.getText());
    }

    @FXML protected void handleMusicPlay(ActionEvent event){
        MenuItem pressedItem = (MenuItem)event.getSource();
        BackGroundMusic.play(pressedItem.getId());
    }

    private void initCreatures(boolean init) throws Exception{
        File initFile;
        if(init)
            initFile = new File("src/main/resources/files/init");
        else
            initFile = new File("src/main/resources/files/save");

        if(!initFile.exists()){
            throw new Exception("file does not exist");
        }

        BufferedReader bufReader = new BufferedReader(new FileReader(initFile));
        String temp;

        while((temp = bufReader.readLine()) != null){
            InputStream is = new ByteArrayInputStream(temp.getBytes());
            temp = "";
            char tempChar;
            Vector<Integer> attribute = new Vector<Integer>();
            while(is.available() > 0){
                tempChar = (char)is.read();
                if(tempChar != ' ' && tempChar != ';')
                    temp += tempChar;
                else {
                    attribute.add(Integer.parseInt(temp));
                    temp = "";
                }
            }
            if(attribute.elementAt(0) == 0){
                final CalabashEnum calabashEnum = CalabashEnum.values()[attribute.elementAt(1)];
                //System.out.println(calabashEnum);
                final Position position = new Position(attribute.elementAt(2), attribute.elementAt(3));
                final Calabash calabash = new Calabash(calabashEnum, position);
                creatures.add(calabash);
                leftCount++;
            }
            else{
                MonsterEnum monsterEnum = MonsterEnum.values()[attribute.elementAt(1)];
                Position position = new Position(attribute.elementAt(2), attribute.elementAt(3));
                Monster monster = new Monster(monsterEnum, position);
                creatures.add(monster);
                rightCount++;
            }
        }
        for(int i = 0; i < creatures.size(); ++i) {
            Creature creature = creatures.get(i);
            creatureMap[(int)creature.position.getX()][(int)creature.position.getY()] = i;
        }

    }

    private int readCreatureMap(Position pos){
        return creatureMap[(int)pos.getX()][(int)pos.getY()];
    }

    private void detectAndFight(Position pos){
        int creatureIndex1 = readCreatureMap(pos);
        boolean alive = true;
        for(int i = 0; i < 4 && alive; ++i){
            Position neighboor = pos.neighboor(i);
            //if(neighboor.getX() == neighboor.getX() && neighboor.getY() == neighboor.getY()) continue;
            int creatureIndex2 = readCreatureMap(pos.neighboor(i));
            if(creatureIndex2 == -1) continue;
            if(creatures.get(creatureIndex1).camp == creatures.get(creatureIndex2).camp) continue;
            Random random = new Random();
            //TODO： 由于creatures 是一个List, 所以在remove 中间一个的时候，后面的所有元素都发生了窜动，需要对index与生物进行绑定
            if(random.nextBoolean()){
                alive = false;
                creatures.set(creatureIndex1, null);
                creatureMap[(int)pos.getX()][(int)pos.getY()] = -1;
                leftCount--;
            }else{
                creatures.set(creatureIndex2, null);
                creatureMap[(int)neighboor.getX()][(int)neighboor.getY()] = -1;
                rightCount--;
            }
        }
    }

    private void initFightBlock(final GraphicsContext gc){
        unitWidth = fightBlock.getWidth()/columnCount;
        unitHeight = fightBlock.getHeight()/rowCount;

        gc.drawImage(fightBackGround, 0, 0, fightBlock.getWidth(), fightBlock.getHeight());
        for(int i = 1; i < rowCount; ++i)
            gc.strokeLine(0, i*unitHeight, 1600, i*unitHeight);
        for(int j = 1; j < columnCount; ++j)
            gc.strokeLine(j*unitWidth, 0, j*unitWidth, 900);

        creatures = new ArrayList<Creature>();
        for(int i = 0; i < 20; ++i){
            for(int j = 0; j < 10; ++j){
                creatureMap[i][j] = -1;
            }
        }
        leftCount = 0;
        rightCount = 0;
        fighting = true;
        finishLabel.setVisible(false);
        fightBlock.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                if(!fighting)
                    return;
                if(lastPos == null){
                    lastPos = new Position(me.getX(), me.getY());
                }else{
                    Position newPos = new Position(me.getX(), me.getY());
                    if(lastPos.distance(newPos) <= 100)
                        return;
                    else{
                        lastPos = newPos;
                    }
                }

                for(Creature creature:creatures) {
                    if(creature == null)
                        continue;
                    detectAndFight(creature.position);
                }
                gc.drawImage(fightBackGround, 0, 0, fightBlock.getWidth(), fightBlock.getHeight());
                for(int i = 1; i < rowCount; ++i)
                    gc.strokeLine(0, i*unitHeight, 1600, i*unitHeight);
                for(int j = 1; j < columnCount; ++j)
                    gc.strokeLine(j*unitWidth, 0, j*unitWidth, 900);
                for (int i = 0; i < creatures.size(); ++i) {
                    Creature creature = creatures.get(i);
                    if(creature == null)
                        continue;
                    try {
                        creatureMap[(int)creature.position.getX()][(int)creature.position.getY()] = -1;
                        creature.move(creatureMap);
                        creatureMap[(int)creature.position.getX()][(int)creature.position.getY()] = i;
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    creature.drawSelf(gc, unitWidth, unitHeight);
                }
                if(leftCount==0){
                    finishLabel.setText("Monsters Win!");
                    finishLabel.setVisible(true);
                    fighting = false;
                }else if(rightCount==0){
                    finishLabel.setText("Calabashes Win!");
                    finishLabel.setVisible(true);
                    fighting = false;
                }
            }
        });
    }

    @FXML protected void handleStartGame(ActionEvent event) throws Exception{
        final GraphicsContext gc = fightBlock.getGraphicsContext2D();
        initFightBlock(gc);
        initCreatures(true);
        for(Creature creature : creatures)
            creature.drawSelf(gc, unitWidth, unitHeight);
        //TODO: repaint (move one creature)
    }

    @FXML protected void handleSaveGame(ActionEvent event) throws Exception{
        PrintWriter writer = new PrintWriter("src/main/resources/files/save", "UTF-8");
        for(Creature creature: creatures){
            if(creature!=null)
                writer.printf("%d %d %d %d;\n", creature.camp, creature.rank, (int)creature.position.getX(), (int)creature.position.getY());
        }
        writer.close();
    }

    @FXML protected void handleReloadGame(ActionEvent event) throws Exception{
        final GraphicsContext gc = fightBlock.getGraphicsContext2D();
        initFightBlock(gc);
        initCreatures(false);
        for(Creature creature : creatures)
            creature.drawSelf(gc, unitWidth, unitHeight);
    }
}
