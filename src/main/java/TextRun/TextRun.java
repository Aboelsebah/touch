package TextRun;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import keyboard.Observer;
import org.fxmisc.richtext.CaretNode;
import org.fxmisc.richtext.InlineCssTextField;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
public class TextRun extends GridPane implements Observer {
  Deque<String> history = new ArrayDeque<>();

  Label label;
  String str = "this is it thank you for trying this out this is a very long text and this a difficult sequential word";
  InlineCssTextField css = new InlineCssTextField();
  CaretNode extraCaret = new CaretNode("another caret", css);

  public TextRun() {
    label = new Label(str);
    label.setPadding(new Insets(5));
    getChildren().addAll(css);
    getColumnConstraints().add(new ColumnConstraints(200));
    add(label, 1, 0);

    cssStyling();
    mistakeListener();
    if (!css.addCaret(extraCaret))
      throw new IllegalStateException("caret was not added to area");
    extraCaret.getStyleClass().remove("caret");
    extraCaret.setStrokeWidth(4.0);
    extraCaret.setStroke(Color.BLACK);
    extraCaret.setBlinkRate(Duration.millis(500));
  }

  private void mistakeListener() {
    css.textProperty().addListener((observable, oldValue, newValue) -> {
      if (css.getText().length() > 0)
        if (str.charAt(css.getText().length() - 1) == css.getText().charAt(css.getText().length() - 1))
          css.setStyle(css.getText().length() - 1, css.getText().length(),
              "-fx-fill: green");
        else
          css.setStyle(css.getText().length() - 1, css.getText().length(),
              "-fx-fill: red");
      extraCaret.moveTo(css.getText().length());
    });
  }

  private void cssStyling() {
    css.setOnKeyPressed(this::update);
    css.setAlignment(TextAlignment.RIGHT);
    css.setMinWidth(210);
    css.setStyle(
        "-fx-faint-focus-color: transparent;" + "-fx-focus-color: transparent;" +
            "-fx-background-insets: 0;" + "-fx-padding: 5;");
  }

  private void historyRestore() {
    if (history.size() > 0)
      label.setText(history.removeLast());
  }

  @Override
  public void update(KeyEvent e) {
    if (e.getCode() == KeyCode.BACK_SPACE) {
      historyRestore();
      return;
    }
    history.add(label.getText());
    label.setText(label.getText().substring(1));
  }
}
