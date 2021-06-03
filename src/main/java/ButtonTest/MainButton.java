package ButtonTest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainButton extends Application {

    //Levels.LevelMenu
    private final Stage primaryStage = new Stage();
    VBox root = new VBox();

    public void start(Stage primaryStage) throws IOException {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("level1");
        titles.add("level2");
        titles.add("level3");
        titles.add("level4");
        titles.add("level5");
        primaryStage.setTitle("Touch Typing");
        initRootScene();
        int test = 4, count = 0;
        while (count <= test){
            var btn = new Buttons(titles.get(count), "texts");
            System.out.println(titles.get(count));
            root.getChildren().add(btn);
            count++;
        }
    }

    void initRootScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        String FXML_MAIN = "buttontest.fxml";
        loader.setLocation(getClass().getResource(FXML_MAIN));
        root = loader.load();
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

