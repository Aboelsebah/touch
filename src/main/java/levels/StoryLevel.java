package levels;

import TextRun.TextRun;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;
import keyboard.Keyboard;
import keyboard.Observer;
import org.fxmisc.richtext.InlineCssTextField;
import users.UsersList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import race.ReaderText;
import keyboard.Keyboard;

public class StoryLevel {
    final String FXML_LEVEL_MENU = "../view/levelMenu.fxml";
    boolean won = false;

    Level levelInfo;
    int lineNr = 1;
    private ReaderText t2;
    private int counter = 0;
    Keyboard keyboard = new Keyboard();
    TextRun racer = new TextRun();
    private final ArrayList<Observer> observers = new ArrayList<>();

    @FXML
    Button backButton, nextButton, startGameButton;
    @FXML
    Label levelTitle, commandLineTxt;
    @FXML
    Pane imageScreen, gameTextDisplay;
    @FXML
    GridPane gameDisplay, storyDisplay;
    @FXML
    Label textNew, textOld, gameTitle, gameDesc, playAgain;
    @FXML
    Line textLine;

    /**
     * constructor needed to set init data - line 51
     */
    public StoryLevel() throws FileNotFoundException {}

    /**
     * goes back one Step -> Levels.LevelMenu
     * @throws IOException
     */
    public void backToLevelMenu() throws IOException, URISyntaxException {
        lineNr = 1;
        levelInfo.resetReadyBool();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_LEVEL_MENU));
        Stage levelMenu = (Stage) backButton.getScene().getWindow();
        levelMenu.setScene(new Scene(loader.load()));
        levelMenu.setResizable(false);
        LevelMenu controller = loader.getController();
        controller.initData();
    }

    // INDIVIDUAL FOR EACH LEVEL

    /**
     * sets the initial Data once a button is pressed
     * Data is: Title, first Image, first Line of Text and the corresponding Button
     * @param title sets the title of the Game
     * @param storyPath passes the path to Level which extracts the important info and texts from it
     * @throws IOException
     * @throws URISyntaxException
     */
    void initData(String title, String storyPath) throws IOException, URISyntaxException {
        levelTitle.setText(title);
        levelInfo = new Level(title, storyPath);
        commandLineTxt.setText(levelInfo.nextLineStory(0)); //first text line
        imageScreen.setId(levelInfo.getScreenUrl());    //first Image
        startGameButton.setVisible(false);
        gameDisplay.setVisible(false);
        lineNr++;
    }

    /**
     * This method is triggered by the NEXT button
     * it reads the txt file and executes commands depending on the given information in the file
     */
    public void nextText() throws IOException, URISyntaxException {
        commandLineTxt.setText(levelInfo.nextLineStory(lineNr));
        lineNr = levelInfo.updateLineNr();

        //Sets picture
        if(levelInfo.getAllowScreenChange()){
            imageScreen.setId(levelInfo.getScreenUrl());
            levelInfo.resetScreenChange();
        }
        //launches GAME
        if (levelInfo.getReadyForGame()){
            nextButton.setVisible(false);
            startGameButton.setVisible(true);
        }
    }

    /**
     * changes entire screen
     * removes: commandline + next button
     * sets: Keyboard highlighting next used keys, Instructions, prepares the game
     * TODO display winner/looser pop up which directs immediately to the Levels.LevelMenu
     */
    public void startGame() {
        gameDisplay.setVisible(true);
        storyDisplay.setVisible(false);
        startGameButton.setVisible(false);
        executeGame();

        if(won == true){
            unlockNextLevel();
        }
        else{
            executeGame();
        }
    }

    /**
     * TODO import Race to this scene, which terminates after 1 mistake
     * is the Game play, checking lives ...
     */
    public void executeGame(){
        //TODO set filename correctly
        t2 = new ReaderText("score1.txt");
        displayMenu("Type a letter to start");
        if(!gameTextDisplay.getChildren().contains(keyboard)) {
          gameTextDisplay.getChildren().addAll(keyboard, racer);
          racer.setLayoutX(300);
          racer.setLayoutY(250);

          keyboard.setLayoutY(400);
          keyboard.setLayoutX(250);
          observers.add(keyboard);
          observers.add(racer);

          gameTextDisplay.setOnKeyPressed(this::update);
          gameTextDisplay.setOnKeyReleased(this::update);
        }
    }
    private void update(KeyEvent event) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                for (var i : observers) i.update(event);
                return null;
            }
        };
        task.run();

        var textField = (InlineCssTextField) racer.getTextField();
        textField.requestFocus();
        textField.moveTo(textField.getText().length());
        textField.requestFollowCaret();
    }
    /**
     * displays the game layout and hide the menu
     */
    public void displayGame() {
        textNew.setVisible(true);
        textOld.setVisible(true);
        textLine.setVisible(true);
        gameTitle.setVisible(false);
        gameDesc.setVisible(false);
        playAgain.setVisible(false);
        textNew.setText(t2.getFileText(counter, counter + 20));
        textOld.setText(t2.getFileText(counter - 20, counter));
    }

    /**
     * display the menu and hide the game layout
     */
    public void displayMenu(String headerMessage) {
        textNew.setVisible(false);
        textOld.setVisible(false);
        textLine.setVisible(false);
        gameTitle.setVisible(true);
        playAgain.setVisible(true);
        gameTitle.setText(headerMessage);
    }


    public void keyPress(KeyEvent event){
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
                //TODO go to a metchod which ends game
                endIt("You fail");
                System.out.println("fail");
            }

            counter++;
            textNew.setText(t2.getFileText(counter, counter + 20));
            textOld.setText(t2.getFileText(counter - 20, counter));

            //if no text is left in the file it displays finish screen
            if (t2.getFileText(counter, counter + 20) == null) {
                endIt("You won");
                displayMenu("Finish");
            }

        }
    }

    public void endIt(String message){
        counter = 0;
        displayMenu(message);
    }

    /**
     * if the game is won this method increments the players level and unlocks the next level
     */
    public void unlockNextLevel(){
        int unlocked = UsersList.users.getCurrentUser().getLevel() + 1;
        UsersList.users.getCurrentUser().setLevel(unlocked + 1);
    }

}
