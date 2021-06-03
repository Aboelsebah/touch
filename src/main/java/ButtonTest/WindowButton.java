package ButtonTest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class WindowButton {

    //STORYLEVEL
    @FXML
    Label titleLabel;
    @FXML
    Button backButton;

    void initData(String title) throws IOException, URISyntaxException {
        titleLabel.setText(title);
        System.out.println("window button:" + title);
    }

    public void backToLevelMenu() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("buttontest.fxml"));
        Stage levelMenu = (Stage) backButton.getScene().getWindow();
        levelMenu.setScene(new Scene(root, 1200, 800));
        levelMenu.setResizable(false);
    }

}
