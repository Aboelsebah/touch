package keyboard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Hands extends GridPane {

  String[] leftChars = new String[]{"q", "w", "e", "r", "t",
      "a", "s", "d", "f", "g", "z", "x", "c", "v", "b"};
  List<String> lefthandCharacter = new LinkedList<>();
  File file = new File("hands/left-rest.png");
  File file1 = new File("hands/right-rest.png");
  InputStream is = new FileInputStream(file);
  InputStream is1 = new FileInputStream(file1);
  Image img = new Image(is);
  Image img1 = new Image(is1);
  ImageView iv = new ImageView(img);
  ImageView iv1 = new ImageView(img1);
  HBox leftHand = new HBox(iv);
  HBox rightHand = new HBox(iv1);

  public Hands() throws FileNotFoundException {
    iv.setFitHeight(550);
    iv1.setFitHeight(550);
    iv.setPreserveRatio(true);
    iv1.setPreserveRatio(true);
    leftHand.setMouseTransparent(true);
    rightHand.setMouseTransparent(true);

    getChildren().addAll(leftHand, rightHand);
    lefthandCharacter.addAll(Arrays.asList(leftChars));

    getColumnConstraints().addAll(new ColumnConstraints(15),
        new ColumnConstraints(345));
    setColumnIndex(rightHand, 2);
    setColumnIndex(leftHand, 1);

  }

  public void changeHands(String character) {
    if (lefthandCharacter.contains(character)) changeLeftHand(character);
    else changeRightHand(character);
  }

  public void changeLeftHand(String character) {
    is = getInputStream(character);
    img = new Image(is);
    iv = new ImageView(img);
    iv.setFitHeight(550);
    iv.setPreserveRatio(true);
    leftHand = new HBox(iv);
    getChildren().set(0, leftHand);
    setColumnIndex(leftHand, 1);
  }

  public void changeRightHand(String character) {
    is1 = getInputStream(character);
    img1 = new Image(is1);
    iv1 = new ImageView(img1);
    iv1.setFitHeight(550);
    iv1.setPreserveRatio(true);
    rightHand = new HBox(iv1);
    getChildren().set(1, rightHand);
    setColumnIndex(rightHand, 2);
  }

  private InputStream getInputStream(String character) {
    file = new File("hands/" + character + ".png");
    InputStream is = null;
    try {
      is = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      System.out.println("Error loading the image of the hands");
    }
    return is;
  }
}
