package users;

/**
 * Makes sure the User class contains the required methods.
 */
public interface IUser {
  /**
   * @return String, the name of the user object.
   */
  String getName();

  /**
   * @return int, the score of the user object.
   */
  int getScore();

  /**
   * @param score Sets the score of the user object.
   */
  void setScore(int score);

  /**
   * @return int, the score of the user object.
   */
  int getLevel();

  /**
   * @param level Sets the level of the user object.
   */
  void setLevel(int level);

  /**
   * @param current Sets the user to be the current in the list.
   */
  void setCurrent(boolean current);

  /**
   * @return boolean, true if the user is the current in the list.
   */
  boolean isCurrent();
}
