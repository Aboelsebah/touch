package race;

import exceptions.Error;
import users.UsersList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//TODO add a user score

public class Race {
  @FXML
  Button backButton, pauseButton;
  @FXML
  Label textNew, textOld, gameTitle, gameDesc, timerLabel, gameScore, highscore,
      playAgain, speedLabel;
  @FXML
  Line textLine;
  @FXML
  SVGPath firstLife, secondLife, thirdLife;
  @FXML
  ProgressBar progressBar;
  @FXML
  StackedAreaChart<Number, Number> areaChart;
  @FXML
  NumberAxis yAxis, xAxis;

  final String FXML_MAIN = "../view/main.fxml";
  private static final Integer START_TIME = 20;
  double secondsRemaining;
  private Timeline timeline;
  private int counter = 0;
  private int userhighscore;
  //  private int lives = 3;
  private ReaderText t2 = new ReaderText("score1.txt");
  final private List<Stats> statsTime = new ArrayList<>(START_TIME * 10);
  NumberFormat nf = NumberFormat.getNumberInstance(Locale.UK);
  DecimalFormat fmt = (DecimalFormat)nf;
  NumberFormat fmt1 = new DecimalFormat("#");

  /**
   * returns to the main menu
   */
  public void backToMain() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource(FXML_MAIN));
    Stage race = (Stage) backButton.getScene().getWindow();
    race.setScene(new Scene(root, 1200, 800));
  }

  /**
   * pause the race
   * TODO pause has to be implemented correctly
   */
  public void pause(ActionEvent actionEvent) {
    timeline.stop();
    new Error(Alert.AlertType.INFORMATION, "Game is paused");
    runTimer();
  }

  {
    Platform.runLater(() -> progressBar.setProgress(1));
  }

  /**
   * displays the game layout and hide the menu
   */
  public void displayGame() {
    counter = 0;
    textNew.setVisible(true);
    textOld.setVisible(true);
    textLine.setVisible(true);
    areaChart.setVisible(false);
    gameTitle.setVisible(false);
    gameDesc.setVisible(false);
    pauseButton.setDisable(false);
    timerLabel.setVisible(true);
    progressBar.setVisible(true);
    playAgain.setVisible(false);
    speedLabel.setVisible(false);
    textNew.setText(t2.getFileText(counter, counter + 20));
    textOld.setText(t2.getFileText(counter - 20, counter));
    secondsRemaining = START_TIME;
    userhighscore = UsersList.users.getCurrentUser().getScore();
    highscore.setText(String.valueOf(userhighscore));
    runTimer();
  }

  /**
   * display the menu and hide the game layout
   */
  public void displayMenu(String headerMessage) {
    textNew.setVisible(false);
    textOld.setVisible(false);
    textLine.setVisible(false);
    areaChart.setVisible(true);
    gameTitle.setVisible(true);
    speedLabel.setVisible(true);
    playAgain.setVisible(true);
    firstLife.setVisible(true);
    secondLife.setVisible(true);
    thirdLife.setVisible(true);
    gameTitle.setText(headerMessage);
  }

  int count = 0; // to be removed

  /**
   * if a key is pressed it checks first if user is in a menu
   * if user is in a menu it displays the game
   * if user is in the game it checks if the pressed key is equal to the current position
   * TODO handle correctly the fail (subtract a live or game over if user dont have any lives left)
   * then increments the current position and refresh the displayed text
   */
  public void keyPress(KeyEvent event) {
    if (event.getCode() == KeyCode.SHIFT) return;
    if (event.getCode() == KeyCode.CAPS) {
      System.out.println("Warning KEYCAPS is pressed");
      return;
    }
    if (!textNew.isVisible() || !textOld.isVisible()) {
      displayGame();
    } else {
      var input = event.isShiftDown() ?
          event.getText().toUpperCase() : event.getText();
      boolean check = t2.checkChar(counter, input);
      if (!check) {
        System.out.println("Fail: " + count++);
        checkLives(event);
      }

      counter++;
      textNew.setText(t2.getFileText(counter, counter + 20));
      textOld.setText(t2.getFileText(counter - 20, counter));
      gameScore.setText(String.valueOf(counter));

      //if no text is left in the file it displays finish screen
      if (t2.getFileText(counter, counter + 20) == null) {
        endIt();
        displayMenu("Finish");
      }

    }
  }

  private void checkLives(KeyEvent event) {
    if (event.getCode() == KeyCode.SHIFT || event.getCode() == KeyCode.CAPS) return;
    if (firstLife.isVisible()) firstLife.setVisible(false);
    else if (secondLife.isVisible()) secondLife.setVisible(false);
    else if (thirdLife.isVisible()) thirdLife.setVisible(false);
    else {
      endIt();
      new Error(Alert.AlertType.WARNING, "Game Over");
    }
  }

  private void runTimer() {
    if (timeline != null) timeline.stop();
    secondsRemaining = (secondsRemaining >= 0) ? secondsRemaining : START_TIME;

    timerLabel.setText(fmt.format(secondsRemaining));
    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    runKeyframes();
    timeline.playFromStart();
  }

  private void runKeyframes() {
    List<Integer> clicksPerSecond = new ArrayList<>();
    timeline.getKeyFrames().add(
        new KeyFrame(Duration.millis(100), event1 -> {
          secondsRemaining -= 0.1;
          timerLabel.setText(fmt1.format(secondsRemaining));
          clicksPerSecond.add(counter);
          if (Double.parseDouble(fmt.format(secondsRemaining)) % 1 == 0) {
            saveStats(calculateSpeed(calculateClicksPerSecond(clicksPerSecond)), 0, false);
            clicksPerSecond.clear();
          }
          progressBar.setProgress(progressBar.getProgress() - .005);
          if (secondsRemaining <= 0) {
            timeline.stop();
            timerLabel.setVisible(false);
            endIt();
          }
        }));
  }

  private int calculateSpeed(int clicksPerSecond) {
    return (clicksPerSecond * 60) / 5;
  }

  private int calculateClicksPerSecond(List<Integer> clicks) {
    return clicks.get(clicks.size() - 1) - clicks.get(0);
  }

  private void saveStats(int speed, int rawSpeed, boolean mistake) {
    var stat = new Stats(speed, rawSpeed, mistake);
    statsTime.add(stat);
  }

  //TODO when finished press enter to continue
  private void endIt() {
    int intervals = statsTime.size();
    areaChart.getData().clear();
    XYChart.Series<Number, Number> rawSpeed = new XYChart.Series<>();
    rawSpeed.setName("Raw Speed");
    for (var i = 0; i < intervals; i++)
      rawSpeed.getData().add(new XYChart.Data<>(i, statsTime.get(i).getSpeed()));
    if (intervals > 1) {
      int yAxisUpperBound = statsTime.stream()
          .mapToInt(Stats::getSpeed)
          .max()
          .orElse(0);
      xAxis.setUpperBound(intervals - 1);
      xAxis.setLabel("Seconds");
      yAxis.setUpperBound((Math.round(yAxisUpperBound % 10) * 10) + 50);
      yAxis.setLabel("Clicks");
    }

    areaChart.getData().add(rawSpeed);

    if (counter > userhighscore) {
      UsersList.users.getCurrentUser().setScore(counter);
      UsersList.users.saveSettings();
    }
    speedLabel.setText("You type " +
        (int) statsTime.stream()
            .mapToInt(Stats::getSpeed)
            .average()
            .orElse(0) + " word per minute.");

    secondsRemaining = 0;
    progressBar.setProgress(1);
    progressBar.setVisible(false);
    displayMenu("Finish");
  }

  //TODO display wrong letters in red

}
