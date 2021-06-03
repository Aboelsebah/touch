package keyboard;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FNKey extends Key {
  public FNKey(char title) {
    super(title);
    renameTitle();
  }

  @Override
  public void setDimensions() {
    super.setDimensions();
    switch (title) {
      case "ئ":
      case "ز":
      case "ر":
        setMinSize(60, 40);
        break;
      case "⇩":
      case "ة":
      case "ط":
        setMinSize(70, 40);
        break;
      case "⇧":
        setMinSize(95, 40);
        break;
      case "⇋":
        setMinSize(55, 40);
        break;
      case "ؤ":
      case "و":
        setMinSize(54, 40);
        break;
      case "ى":
        setMinSize(298, 40);
        break;
      case "ش":
        setMinSize(135, 40);
        break;
      case "ا":
        setMinSize(100, 40);
        break;
    }
  }

  private void renameTitle() {
    switch (title) {
      case "ئ":
      case "ز":
        title = "CTRL";
        break;
      case "ؤ":
      case "و":
        title = "alt";
        break;
      case "ر":
      case "ة":
        title = "cmd";
        break;
      case "ى":
        title = "_";
        break;
      case "ش":
        title = "⇧";
        break;
      case "ط":
        title = "⤶";
        break;
      case "ا":
        title = "←";
        break;
    }
    setText(title);
  }

  @Override
  public void update(KeyEvent e) {
    if (e.getEventType() == KeyEvent.KEY_PRESSED) {
      if (e.getCode() == KeyCode.SPACE && title.equals("_")) arm();
      else if (e.getCode() == KeyCode.SHIFT && title.equals("⇧")) arm();
      else if (e.getCode() == KeyCode.BACK_SPACE && title.equals("←")) arm();
      else if (e.getCode() == KeyCode.ENTER && title.equals("⤶")) arm();
      else if (e.getCode() == KeyCode.ALT && title.equals("alt")) arm();
      else if (e.isMetaDown() && title.equals("cmd")) arm();
      else if (e.getCode() == KeyCode.CONTROL && title.equals("CTRL")) arm();
      else if (isCapsDown() && title.equals("⇩")) {
        System.out.println("CapsLock is pressed");
        setStyle(" -fx-background-color:" +
            "linear-gradient(from 0% 93% to 0% 100%, #bf7474 0%, #b56868 100%)," +
            "#be7070, /*Linning*/ #de8080," +
            "radial-gradient(center 50% 50%, radius 100%, #c48383, #dba2a2);");
        arm();
      }else {
        setStyle(" -fx-background-color:" +
            "linear-gradient(from 0% 93% to 0% 100%, #a7a7a7 0%, #a9a9a9 100%)," +
            "#9d9d9d, /*Linning*/ #ffffff," +
            "radial-gradient(center 50% 50%, radius 100%, #efefef, #dedede);");
      }
    } else {
      if (!e.isShiftDown())
        disarm();
      if (title.equals("_")) requestFocus();
    }
  }

  @Override
  public void fire() {
    if (title.equals("_")) System.out.println(" ");
  }
}