package ButtonTest;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class Buttons extends GridPane{

    // Levels.LevelObject
    Button btn;
    Label levelTitle;
    Pane lock;
    String storypath, title;

    public Buttons(String title, String path){
        storypath = path;
        this.title = title;
        setMinSize(160, 210);
        setMaxSize(161, 210);
        setStyle("-fx-background-color: grey");

        btn = new Button("Start");
        btn.setId("getStartedButton");
        btn.setPrefWidth(172);
        btn.setPrefHeight(50);
        add(btn, 0,1);

        levelTitle = new Label(title);
        levelTitle.setId("levelLabels");
        levelTitle.setAlignment(Pos.BOTTOM_CENTER);
        levelTitle.setPrefHeight(161);
        levelTitle.setPrefWidth(161);
        add(levelTitle, 0, 0);

        lock = new Pane();
        lock.setId("lockunlocked");
        lock.setPrefHeight(232);
        lock.setPrefWidth(190);
        add(lock, 0, 0);

        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(120);
        row1.setPrefHeight(155);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(30);
        getRowConstraints().addAll(row1, row2);

        btn.setOnAction(this::launchButtonWindow);

    }

    private void launchButtonWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("windowbutton.fxml"));
            Stage levelMenu = (Stage) this.getScene().getWindow();
            levelMenu.setScene(new Scene(loader.load()));
            levelMenu.setResizable(false);

            WindowButton controller = loader.getController();
            controller.initData(title);
            System.out.println(title);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("");
    }


    public Button getButton(){
        return btn;
    }

    public void setTitle(){
        levelTitle.setText(title);
    }

    public String getTitle(){
        return title;
    }

    public void locklevel(){
        lock.setId("locklocked");
    }

    public void unlocklevel(){
        lock.setId("lockunlocked");
    }

    public String getPath(){
        return storypath;
    }

}
