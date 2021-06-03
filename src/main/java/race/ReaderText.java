package race;

import java.io.InputStream;
import java.util.Scanner;

public class ReaderText {

    private String text;

    /**
     * Constructor for setting the Text Object
     *
     * @param file name of file in the texts folder
     */
    public ReaderText(String file) {
        text = "";

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("raceTexts/" + file);
        //File txtFile = new File(is);
        if (is == null) {
            throw new IllegalArgumentException("no text file");
        }
        Scanner s = new Scanner(is);
        while (s.hasNextLine()) {
            if (text.length() == 0) {
                text = s.nextLine();
            } else {
                text = text + " " + s.nextLine();
            }
        }
    }


    /**
     * returns the text of the object
     *
     * @param startPos position where the returned text should begin
     * @param endPos   position where the returned text should end
     */
    public String getFileText(int startPos, int endPos) {
        while (startPos < 0) {
            startPos++;
        }
        if (endPos <= text.length()) {
            return text.substring(startPos, endPos);
        } else if (startPos < text.length()) {
            return text.substring(startPos);
        } else {
            return null;
        }
    }


    /**
     * returns the character at a given position of the object
     *
     * @param pos psoition of the character
     */
    public String getChar(int pos) {
        if (pos < text.length()) {
            return String.valueOf(text.charAt(pos));
        } else {
            throw new IllegalArgumentException("THis position doesnt exist in the string");
        }
    }


    /**
     * checks if the pressed Key is equal to the current position of the object
     *
     * @param pos   position of the character
     * @param input KeyCode of the pressed Key
     */
    public boolean checkChar(int pos, String input) {
        return getChar(pos).equals(input);
    }
}
