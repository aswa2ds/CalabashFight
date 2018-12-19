import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    private final Dimension SCREENDIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    private final Dimension WINDOWDIMENSION = new Dimension(SCREENDIMENSION.width*2/3, SCREENDIMENSION.height*2/3);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/layout/apperance.fxml"));
        primaryStage.setTitle("CalabashFight!");
        primaryStage.setScene(new Scene(root, WINDOWDIMENSION.width, WINDOWDIMENSION.height));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
