package levels;

import javafx.scene.layout.*;
import users.UsersList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LevelMenu {
    final String FXML_MAIN = "../view/main.fxml", LEVEL_MENU_TITLES = "levelTitles.txt";
    private int unlockedLevel, lineNr = 0, countTitles = 0, countLevels = 1;
    final int SMALL_100 = 100, LARGE_210 = 210;

    @FXML
    Button backButton;

    @FXML
    GridPane levelGrid;

    @FXML
    AnchorPane anchorPaneScrollDisplay;

    public LevelMenu(){}

    /**
     * Sets initial Data, based on how many Levels there are
     * @throws IOException
     * @throws URISyntaxException
     */
    public void initData() throws IOException, URISyntaxException {
        fillGridPane();
    }

    /**
     * fills the GridPane with Titles and Levels
     * @throws IOException
     * @throws URISyntaxException
     */
    public void fillGridPane() throws IOException, URISyntaxException {
        int lines = getAllLines();
        int i = 0, xT = 1, yT = -3, xObj = -1, yObj = 1;

        while(i < lines){
            //set Row Title
            String readLine = readLevelTitleFile();
            if(readLine.charAt(0) == '#'){
                yT += yObj + 2;
                if(yT != 0){
                    levelGrid.add(getEmptyRowPane(), 0, yT-1);
                    xObj = -1;
                }
                levelGrid.add(getRowTitleLabel(readLine), xT, yT);
                if(yT != 0){
                }
                yObj = yT+1;
            }
            else{
                // set each Levels.Level!
                if(xObj <= 7){
                    xObj += 2;
                }
                else {
                    yObj += 2;
                    xObj = 1;
                    levelGrid.add(getEmptyRowPane(), xObj-1, yObj-1);
                }
                //creates new levelObject
                String title = getTitle(readLine);
                String path = getPath(readLine);
                var levelObj = new LevelObject(title, path, isUnlocked());
                levelGrid.add(levelObj, xObj, yObj);
                countLevels++;
            }
            i++;
        }
        //add final row and set Display size of anchor pane
        levelGrid.add(getEmptyRowPane(), 0, yObj+1);
        anchorPaneScrollDisplay.setMinHeight(getGridSizeDisplay());
    }

    /**
     * @param line is the read line from the txt file, which holds Title - Path
     * @return return the Title of a Level
     */
    public String getTitle(String line){
        String result = "";
        Pattern pattern = Pattern.compile("(.*?) -");
        Matcher matcher = pattern.matcher(line);
        if(matcher.find()){
            result =  matcher.group(1);
        }
        return result;
    }

    /**
     * @param line is the read line from the txt file, which holds Title - Path
     * @return the path of the story for the respective Level
     */
    public String getPath(String line){
        String result = "";
        Pattern pattern = Pattern.compile("(?<=- ).*lv\\d+_story.txt");
        Matcher matcher = pattern.matcher(line);
        if(matcher.find()){
            result = matcher.group();
        }
        return result;
    }

    /**
     * @return reads the Title of a group of Levels, such as: Middle Row, Top Row
     * @throws IOException
     * @throws URISyntaxException
     */
    public String readLevelTitleFile() throws IOException, URISyntaxException {
        String result = Files.readAllLines(Paths.get(getAbsolutePath())).get(lineNr);
        lineNr++;
        return result;
    }

    /**
     * @return adds an empty pane to the grid to be able to make a nicer layout
     */
    public Pane getEmptyRowPane(){
        Pane emptyPane = new Pane();
        emptyPane.setMinHeight(100);
        return emptyPane;
    }

    /**
     * @param title returns the Label with the Title for a group of Levels
     * @return
     */
    public Label getRowTitleLabel(String title){
        Label rowText = new Label(title.substring(1));
        rowText.setId("rowTitles");
        rowText.setMinHeight(100);
        countTitles++;
        return rowText;
    }

    /**
     * @return the size of the entire GridPane to be able to set the AnchorPane accordingly -> to have a dynamic size of scrollpane
     */
    public int getGridSizeDisplay(){
        int result, rows = levelGrid.getRowCount();
        result = (rows - countTitles) / 2;
        result = (result * SMALL_100) + (LARGE_210 * result);
        result += countTitles * SMALL_100;
        return result;
    }

    /**
     * checks if a Level has been unlocked
     * @return boolean true for unlocked and false for locked
     */
    public boolean isUnlocked(){
        boolean result;
        unlockedLevel = UsersList.users.getCurrentUser().getLevel() + 1;
        if(countLevels <= unlockedLevel){
            result = true;
        }
        else{
            result = false;
        }
        return result;
    }

    /**
     * converts the String Path levelTitles.txt into an absolute Path
     * @return the Absolute path as a string
     * @throws URISyntaxException
     */
    public String getAbsolutePath() throws URISyntaxException {
        URL res = getClass().getClassLoader().getResource(LEVEL_MENU_TITLES);
        if(res == null){
            errorWindow();
            return "error";
        }
        File file = Paths.get(res.toURI()).toFile();
        return file.getAbsolutePath();
    }

    /**
     * uses the absolute path to evaluate how many lines there are in a file
     * @return the number of lines in a file
     * @throws URISyntaxException
     * @throws IOException
     */
    public int getAllLines() throws URISyntaxException, IOException {
        int result = 0;
        BufferedReader reader = new BufferedReader (new FileReader (getAbsolutePath()));
        while (reader.readLine() != null) {
            result++;
        }
        reader.close();
        return result;
    }

    /**
     * will return to the MAIN window on pressing the BACK button
     * @throws IOException
     */
    public void backToMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(FXML_MAIN));
        Stage levelMenu = (Stage) backButton.getScene().getWindow();
        levelMenu.setScene(new Scene(root, 1200, 800));
        levelMenu.setResizable(false);
    }

    /**
     * Error window
     */
    public void errorWindow(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Sorry Level Menu could not load");
        alert.setContentText("Make sure all the file paths are correct");
        alert.showAndWait();
    }

}
