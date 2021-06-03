package keyboard;

import javafx.scene.control.Button;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;

public class Key extends Button implements Observer {
  protected String title;

  public Key(char title) {
    this.title = title + "";
    this.setText(this.title);
    setDimensions();
    underlineKey();

  }

  public void setDimensions() {
    setMinSize(40, 40);
    setMaxSize(40, 40);
  }

  void changeTitleToUppercase(boolean value) {
    if (value || isCapsDown()) {
      title = title.toUpperCase();
    }
    else {
      title = title.toLowerCase();
    }
    setText(title);
  }

  @Override
  public void update(KeyEvent e) {
    if (e.getEventType() == KeyEvent.KEY_PRESSED) {
      if (e.isShiftDown() || isCapsDown()) {
        changeTitleToUppercase(true);
      }
      if (e.getText().equalsIgnoreCase(title)) {
        if (title.equals("i")) setDefaultButton(true);
        arm();
        fire();
      }
    } else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
      setDefaultButton(false);
      if (!e.isShiftDown()) {
        changeTitleToUppercase(false);
      }
      disarm();
    }
  }

  private void underlineKey() {
    if (title.equals("j") || title.equals("f"))
      setUnderline(true);
  }

  public boolean isCapsDown() {
    return Toolkit.getDefaultToolkit()
        .getLockingKeyState(KeyCode.CAPS.getCode());
  }

  public void fire() {
    System.out.println(title);
  }
}
