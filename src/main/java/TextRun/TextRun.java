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
import java.util.Deque;
public class TextRun extends GridPane implements Observer {
  Deque<String> history = new ArrayDeque<>();

  Label label;
  String str = "this is it thank you for trying this out this is a very long text and this a difficult sequential word";
  InlineCssTextField textField = new InlineCssTextField();
  CaretNode extraCaret = new CaretNode("another caret", textField);


  public TextRun() {
    label = new Label(str);
    label.setPadding(new Insets(5));
    label.setTextFill(Color.WHITE);
    label.setFont(new Font(30));
    getChildren().addAll(textField);
    getColumnConstraints().add(new ColumnConstraints(400));
    add(label, 1, 0);

    textFieldStyling();
    mistakeListener();

    extraCaret.getStyleClass().remove("caret");
    extraCaret.setStrokeWidth(4.0);
    extraCaret.setStroke(Color.WHITE);
    extraCaret.setBlinkRate(Duration.millis(500));
  }

  public InlineCssTextField getTextField() {
    return textField;
  }
  private void mistakeListener() {
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (textField.getText().length() > 0)
        if (str.charAt(textField.getText().length() - 1) == textField.getText().charAt(textField.getText().length() - 1))
          textField.setStyle(textField.getText().length() - 1, textField.getText().length(),
              "-fx-fill: green");
        else
          textField.setStyle(textField.getText().length() - 1, textField.getText().length(),
              "-fx-fill: red");

      extraCaret.moveTo(textField.getText().length());
//      textField.requestFollowCaret();
    });
  }

  private void textFieldStyling() {
    textField.setOnKeyPressed(this::update);
    textField.setAlignment(TextAlignment.RIGHT);
    textField.setMinWidth(410);
    textField.setStyle(
        "-fx-faint-focus-color: transparent;" + "-fx-focus-color: transparent;" +
            "-fx-background-insets: 0;" + "-fx-padding: 5;" + "-fx-fill: white;" +
            "-fx-background-color: transparent;" + "-fx-font-size: 30");
  }

  private void historyRestore() {
    if (history.size() > 0)
      label.setText(history.removeLast());
  }

  @Override
  public void update(KeyEvent event) {
    if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
      if (event.getCode() == KeyCode.BACK_SPACE) {
        historyRestore();
        return;
      }
      history.add(label.getText());
      label.setText(label.getText().substring(1));
    }
  }
}
