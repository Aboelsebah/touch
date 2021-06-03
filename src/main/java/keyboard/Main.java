package keyboard;


import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application { //To be Deleted
  private final Stage primaryStage = new Stage();
  private final ArrayList<Observer> observers = new ArrayList<>();
  VBox root;

  public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Touch Typing");
    initRootScene();

    var keyboard = new Keyboard();
    root.getChildren().add(keyboard);
    observers.add(keyboard);

    root.setOnKeyPressed(this::update);
    root.setOnKeyReleased(this::update);
  }

  private void update(KeyEvent event) {
    Task<Void> task = new Task<>() {
      @Override
      protected Void call() {
        for (var i : observers) i.update(event);
        return null;
      }
    };
    task.run();
  }

  void initRootScene() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    String FXML_MAIN = "../view/proofConcept.fxml";
    loader.setLocation(getClass().getResource(FXML_MAIN));
    root = loader.load();
    Scene scene = new Scene(root, 1200, 700);
    primaryStage.setScene(scene);
    root.setSpacing(5);
    primaryStage.show();
  }
}
