package levels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;


public class Level {
    final private String storyPath, title;
    private String nextScreen, levelTextFile;
    private boolean ready = false, screenChange = false;
    private int lineNr;



    public Level(String title, String storyPath){
        this.storyPath = storyPath;
        this.title = title;
    }


    /**
     * reads text from exact position in File (counter/lineNr)and returns String with the new text
     * if # the first character it triggers a new Picture and outputs the next line to the label
     * if * the first Character the GAME starts! -> change of scene
     * @param lineNr inputs the line that needs to be read
     * @return Returns Text for the Label commandlineText
     * @throws FileNotFoundException
     */
    public String nextLineStory(int lineNr) throws IOException, URISyntaxException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL is = classloader.getResource("texts/levels/" + storyPath);
        File f=new File(is.toURI());
        String result = Files.readAllLines(f.toPath()).get(lineNr);

        //starts the game
        if (result.charAt(0) == '*'){
            ready = true;
            levelTextFile = Files.readAllLines(f.toPath()).get(lineNr+2).substring(1);
            return result =  Files.readAllLines(f.toPath()).get(lineNr+1);
        }
        // sets pictures
        else if (result.charAt(0) == '#'){
            screenChange = true;
            nextScreen = result.substring(1);
            ++lineNr;
            result =  Files.readAllLines(f.toPath()).get(lineNr);
            ++lineNr;
        }
        //counts line
        else{
            lineNr++;
        }
        this.lineNr = lineNr;
        return result;
    }

    /**
     * @return the title of the current Level
     */
    public String getTitle(){
        return title;
    }

    /**
     * @return the next line to be read
     */
    public int updateLineNr(){
        return lineNr;
    }

    /**
     * resets the boolean to start the Game
     */
    public void resetReadyBool(){
        ready = false;
    }

    /**
     * resets variable once the new picture has been set - to be able to change more often picture during one level
     */
    public void resetScreenChange(){
        screenChange = false;
    }

    /**
     * @return the URL the next picture has
     */
    public String getScreenUrl(){
        return nextScreen;
    }

    /**
     * @return the URL for the Game File
     */
    public String getLevelTextFile() {return levelTextFile;}

    /**
     * is true when the *START_GAME passes
     * @return boolean if the game can start or not
     */
    public boolean getReadyForGame(){
        return ready;
    }

    /**
     * is true when #picture passes
     * @return boolean allows to change the picture
     */
    public boolean getAllowScreenChange(){
        return screenChange;
    }
}
