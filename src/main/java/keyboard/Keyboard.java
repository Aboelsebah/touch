package keyboard;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Keyboard extends StackPane implements Observer {
  private final VBox root = new VBox();
  private final HBox firstRow = new HBox(), secondRow = new HBox(),
      thirdRow = new HBox(), fourthRow = new HBox(), fifthRow = new HBox();
  private final ArrayList<Observer> observers = new ArrayList<>();
//  Hands hands = new Hands();
  char[] keys = {'\\', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
      '\'', '?', 'ا', '⇋', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p',
      'è', '+', ';', ':', '⇩', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
      'ò', 'à', 'ù', 'ط', '⇧', 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.',
      '-', 'ش', 'ئ', 'ؤ', 'ر', 'ى', 'ة', 'و', 'ز'};

  public Keyboard() throws FileNotFoundException {
    init();
    populateRows();
//    getChildren().add(hands);
//    hands.setPadding(new Insets(125, 0,0,100));
//    hands.setMouseTransparent(true);
  }

  private void populateRows() {
    populateKeys(firstRow, 0, 14, false);
    populateKeys(secondRow, 14, 29, false);
    populateKeys(thirdRow, 29, 43, false);
    populateKeys(fourthRow, 43, 55, false);
    populateKeys(fifthRow, 55, 62, true);
  }

  private void populateKeys(HBox row, int start, int end, boolean fn) {
    row.setSpacing(5);
    row.setAlignment(Pos.BOTTOM_CENTER);
    Key key;
    for (var i = start; i < end; i++) {
      char k = keys[i];
      if (!fn) key = i == start || i == end - 1 ? new FNKey(k) : new Key(k);
      else key = new FNKey(k);
      observers.add(key);
      row.getChildren().add(key);
    }
  }

  void init(){
    root.getChildren().addAll(firstRow, secondRow,
        thirdRow, fourthRow, fifthRow);
    root.setMaxHeight(400);
    getChildren().add(root);
    setAlignment(Pos.TOP_LEFT);

    root.setPadding(new Insets(5, 5, 5, 5));
    root.setAlignment(Pos.BOTTOM_CENTER);
    root.setSpacing(4);
  }

  //TODO fix the tab thingy!!
  @Override
  public void update(KeyEvent event) {
//    if (event.getText().length() > 0) {
//      char chr = event.getText().charAt(0);
//      if (chr > 96 && chr < 122) hands.changeHands(event.getText());
//    }
    Task<Void> task = new Task<>() {
      @Override
      protected Void call() {
        for (var i : observers) i.update(event);
        return null;
      }
    };
    task.run();
  }
}
