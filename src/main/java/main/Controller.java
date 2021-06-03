package main;

import users.IUser;
import users.User;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import levels.LevelMenu;


import java.io.IOException;
import java.net.URISyntaxException;

import static users.UsersList.users;

/**
 * Main controller of the app, Implements the MVC design pattern.
 *
 * @author The team.
 * @version 0.1
 * @since 0.1
 */
public class Controller {
  final String FXML_RACE = "../view/race.fxml";
  final String FXML_LEVEL_MENU = "../view/levelMenu.fxml";

  @FXML
  Button raceButton, removeButton, learnButton;
  @FXML
  Label titleLabel;
  @FXML
  TextField addUserField;
  @FXML
  ComboBox<IUser> dropDownMenu;
  @FXML
  HBox selectUserHBox, newUserHBox;

  /**
   * Accepts a name entered in the addUserField and send it to be added
   * to users list if valid.
   * when added changes the view to display the dropdown menu again
   */
  public void addUser() {
    users.add(addUserField.getText());
    loadList();
    dropDownMenu.getSelectionModel().selectLast();
    addUserField.clear();
    dropDownMenu.setVisible(true);
    newUserHBox.setVisible(false);
    selectUserHBox.setVisible(true);
  }

  /**
   * Loads the users list, Highlights current user if available,
   * or highlights the last user in the list.
   * If it has only the predefined Guest user -> displays create User
   * if has users displays dropdownmenu, remove and new User?
   * button -> either select, remove or create new user
   * @see #highlightCurrent()
   */
  @FXML
  public void loadList() {
    dropDownMenu.setItems(users.getDropDownList());
    highlightCurrent();
    removeButton.setVisible(true);

    if(dropDownMenu.getItems().size() == 1 &&
        users.getCurrentUser().equals(new User("Guest", 0, 0))){
      newUserHBox.setVisible(true);
      selectUserHBox.setVisible(false);
      dropDownMenu.setVisible(false);
    } else {
      dropDownMenu.setVisible(true);
      newUserHBox.setVisible(false);
      selectUserHBox.setVisible(true);
    }

  }

  /**
   * Highlights the current user in the ComboBox dropDownMenu.
   */
  @FXML
  public void highlightCurrent() {
    dropDownMenu
        .getSelectionModel()
        .select(users.getCurrentUser());
  }

  /**
   * Removes the highlighted user from the users list.
   */
  @FXML
  public void removeButton() {
    users.removeUser(dropDownMenu.getValue());
    loadList();
    dropDownMenu
        .getSelectionModel()
        .selectLast();
  }

  /**
   * Sets the highlighted user object to the current user.
   */
  @FXML
  public void selectCurrent() {
    var curUser = dropDownMenu
        .getSelectionModel()
        .getSelectedItem();
    if (curUser != null) users.setCurrentUser(curUser);
  }

  { // Makes sure to load the list after loading of all components.
    Platform.runLater(this::loadList);
  }

  /**
   *  Test function -- to be replaced or modified.
   * @throws IOException
   */
  public void openRace() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource(FXML_RACE));
    Stage race = (Stage) raceButton.getScene().getWindow();
    race.setScene(new Scene(root, 1200, 800));
    race.setResizable(false);
  }

  /**
   * opens Levels.Level Menu
   * @throws IOException
   */
  public void openLevelMenu() throws IOException, URISyntaxException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_LEVEL_MENU));
    Stage levelMenu = (Stage) learnButton.getScene().getWindow();
    levelMenu.setScene(new Scene(loader.load()));
    levelMenu.setResizable(false);
    LevelMenu controller = loader.getController();
    controller.initData();
  }

  /**
   * triggered by New User? Button
   * sets the input field allowing to create a new user
   */
  public void newUserInput() {
    selectUserHBox.setVisible(false);
    dropDownMenu.setVisible(false);
    newUserHBox.setVisible(true);
  }
}
