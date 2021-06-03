package users;

import exceptions.Error;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the users list and saving the settings, Singleton,
 * Don't try to instantiate it outside this class, Use the users
 * static field users instead after importing it from any place.
 *
 * @author The team.
 * @version 0.1
 * @since 0.1
 */
public class UsersList {
  public static UsersList users = new UsersList(); //The Singleton.
  final private String XML_FILE = "settings.xml"; //Path to XML file.
  final private int MAX_USERS_NR = 8; //Max user allowed.
  final private int MAX_USERNAME_CHARS = 10;
  private List<IUser> list = new ArrayList<>();
  private IUser currentUser; //Points to the current selected user.

  private UsersList() { // Private constructor, Singleton Design pattern.
    loadSettings();
  }

  /**
   * This method loads the data previously saved, if it's the first time,
   * or it can't find the file, it will create one.
   */
  private void loadSettings() {
    try {
      var fis = new FileInputStream(XML_FILE);
      XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(fis));
      Object settingsFile = decoder.readObject();
      list = (List<IUser>) settingsFile;
      decoder.close();
    } catch (FileNotFoundException e) {
      new Error(Alert.AlertType.ERROR, "Error loading data, " +
          "new settings file will be created.");
      list.add(new User("Guest", 0, 0));
    }

    getCurrentUser();
    saveSettings();
  }

  /**
   * Validates the username, throws an error if it's not valid,
   * and creates an object of User and adds it to the list.
   *
   * @param username the username entered from addUserField
   * @see exceptions.Error
   */
  public void add(String username) {
    var user = new User(username.trim(), 0, 0);

    if (usernameExists(user) ||
        usernameLengthExceedMaxChars(username) ||
        usersProfilesExceedMax() ||
        usernameIsEmpty(username)) return;

    if (currentUser != null) currentUser.setCurrent(false);
    currentUser = user;
    currentUser.setCurrent(true);
    list.add(currentUser);
    saveSettings();
  }

  /**
   * Checks against if the username is empty.
   *
   * @param username String, to be checked.
   * @return boolean, true if the username is empty or only have spaces.
   */
  private boolean usernameIsEmpty(String username) {
    if (username.trim().equals("")) {
      new Error(Alert.AlertType.WARNING,
          "Username can't be empty.");
      return true;
    }
    return false;
  }

  /**
   * Checks if the username exist already in the list.
   *
   * @param user the user object which name will be checked.
   * @return boolean, true if the username already exists.
   */
  private boolean usernameExists(IUser user) {
    if (isDuplicate(user)) {
      new Error(Alert.AlertType.ERROR,
          "Error adding user",
          "Username already exist.",
          "Username was not saved, please try again with a new" +
              " username or remove the duplicate from the list.");
      return true;
    }
    return false;
  }

  /**
   * Checks if the username length exceed the max characters.
   *
   * @param username the string which will be checked.
   * @return boolean, true if the username exceed the max length.
   */
  private boolean usernameLengthExceedMaxChars(String username) {
    if (username.length() > MAX_USERNAME_CHARS) {
      new Error(Alert.AlertType.ERROR,
          "Error adding user",
          "Max number of characters allowed is "
              + MAX_USERNAME_CHARS + ".",
          "Username was not saved, please try " +
              "with a shorter username");
      return true;
    }
    return false;
  }

  /**
   * Checks if users list reached max number.
   *
   * @return boolean, true if the list size equal to maximum characters
   * allowed.
   */
  private boolean usersProfilesExceedMax() {
    if (list.size() > MAX_USERS_NR) {
      new Error(Alert.AlertType.ERROR,
          "Error adding user",
          "Max number of profiles allowed is "
              + MAX_USERS_NR + ".",
          "Username was not saved, please try to remove user" +
              " from the list first, before adding a username.");
      return true;
    }
    return false;
  }

  /**
   * Checks for duplicated of the user object.
   *
   * @param user is a user object to check against.
   * @return boolean, true if the User object name already exists.
   */
  private boolean isDuplicate(IUser user) {
    for (var i : list)
      if (i.equals(user)) return true;
    return false;
  }

  /**
   * Saves the users list to XML file.
   */
  public void saveSettings() {
    try {
      var fos = new FileOutputStream(XML_FILE);
      XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(fos));
      encoder.writeObject(list);
      encoder.close();
    } catch (FileNotFoundException fileNotFoundException) {
      new Error(Alert.AlertType.ERROR, "Error Saving data.");
    }
  }

  /**
   * Gets the last index of the users list.
   *
   * @return int of the last index in the list.
   */
  public int getLast() {
    return list.size() == 0 ? 0 : list.size() - 1;
  }

  /**
   * Removes a user object from the list if there is more than one
   * present in the list, resets the current user if null.
   *
   * @param user the user object to remove.
   */
  public void removeUser(IUser user) {
    if (list.size() < 2) {
      new Error(Alert.AlertType.WARNING,
          "Please add another user before deleting this one.");
      return;
    }
    list.remove(user);
    if (currentUser == null) currentUser = list.get(getLast());
    saveSettings();
  }

  /**
   * Sets the current user object to a new user object.
   *
   * @param user the user object to  be set
   */
  public void setCurrentUser(IUser user) {
    if (currentUser != null) currentUser.setCurrent(false);

    currentUser = user;
    currentUser.setCurrent(true);
    saveSettings();
  }

  /**
   * Looks for the current user in the users list, if not found it
   * will return the last user in the list.
   *
   * @return user object from the list.
   */
  public IUser getCurrentUser() {
    for (var i : list)
      if (i.isCurrent()) currentUser = i;
    if (currentUser == null) currentUser = list.get(0);
    return currentUser;
  }

  /**
   * Creates an observable list for the ComboBox to be displayed.
   *
   * @return ObservableList of type IUser.
   */
  public ObservableList<IUser> getDropDownList() {
    return FXCollections.observableArrayList(list);
  }
}
