package TextRun;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Demo extends Application {
  Text right = new Text();
  Label left = new Label();

  @Override
  public void start(Stage stage) throws Exception {
    VBox root = new VBox();
    root.setSpacing(10);
    root.setPadding(new Insets(10));
    Scene sc = new Scene(root, 600, 600);
    stage.setScene(sc);
    stage.show();
initi();


    root.getChildren().add(right);
    root.setOnKeyPressed(this::keyPressed);

  }

  private void initi() {
    right.setText("This is the text");
  }

  private void keyPressed(KeyEvent event) {
    System.out.println(event.getText());
  }
}
