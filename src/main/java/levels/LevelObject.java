package levels;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import users.UsersList;

import java.io.IOException;
import java.net.URISyntaxException;

public class LevelObject extends GridPane{
    final String FXML_STORY_LEVEL = "../view/storyLevel.fxml";
    Button btn;
    Label levelTitle;
    Pane lock;
    String storyPath, title;
    boolean isUnlocked;

    public LevelObject(String title, String storyPath, boolean isUnlocked){
        this.title = title;
        this.storyPath = storyPath;
        this.isUnlocked = isUnlocked;

        setMinHeight(210);
        setMinWidth(100);

        levelTitle = new Label(title);
        levelTitle.setId("levelLabels");
        levelTitle.setAlignment(Pos.BOTTOM_CENTER);
        levelTitle.setPrefHeight(161);
        levelTitle.setPrefWidth(161);

        btn = new Button("Start");
        btn.setPrefWidth(172);
        btn.setPrefHeight(50);
        btn.setOnAction(this::launchStoryLevel);

        lock = new Pane();
        lock.setPrefHeight(232);
        lock.setPrefWidth(190);

        // displays the Levels differently based on whether they have been unlocked or not
        if(isUnlocked){
            btn.setId("getStartedButton");
            lock.setId("lockunlocked");
        }
        else{
            btn.setId("getStartedButtonLocked");
            lock.setId("locklocked");
        }

        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(120);
        row1.setPrefHeight(155);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(30);
        getRowConstraints().addAll(row1, row2);

        add(levelTitle, 0,0);
        add(btn, 0,1 );
        add(lock, 0, 0);
    }

    /**
     * is executed once a button is pressed!
     * if level is unlocked -> laods StoryLevel with init Data
     * if level is locked -> displays alert!
     * @param actionEvent
     */
    private void launchStoryLevel(ActionEvent actionEvent) {
        if(isUnlocked){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_STORY_LEVEL));
                Stage levelMenu = (Stage) this.getScene().getWindow();
                levelMenu.setScene(new Scene(loader.load()));
                levelMenu.setResizable(false);

                StoryLevel controller = loader.getController();
                controller.initData(title, storyPath);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Level locked");
            alert.setHeaderText("This Level is locked");
            alert.setContentText("You have not yet unlocked this Level. \nPlease proceed with Level " + (UsersList.users.getCurrentUser().getLevel()+1) +  ".");
            alert.showAndWait();
        }

    }
}
