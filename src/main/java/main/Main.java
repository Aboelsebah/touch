package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class, loads the stage, implements MVC design pattern.
 *
 * @author The team.
 * @version 0.1
 * @since 0.1
 */
public class Main extends Application {
  final String FXML_MAIN = "../view/main.fxml";

  /**
   * Primary method Overridden from the Application abstract class.
   *
   * @param primaryStage Stage, the main stage to be loaded.
   * @throws IOException exception, if the fxml file cannot be loaded.
   */
  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource(FXML_MAIN));
    primaryStage.setTitle("Touch Typing");
    primaryStage.setScene(new Scene(root, 1200, 800));
    primaryStage.setResizable(false);
    primaryStage.show();
  }

}
