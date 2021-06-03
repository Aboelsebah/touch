package keyboard;

import javafx.scene.input.KeyEvent;

public interface Observer {
  void update(KeyEvent e);
}
