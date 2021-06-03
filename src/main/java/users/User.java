package users;

import java.io.Serializable;
import java.util.Objects;
/**
 * This class handle the User object and it's settings, JavaBean.
 *
 * @author The team.
 * @version 0.1
 * @since 0.1
 * @see IUser
 */
public class User implements IUser, Serializable {
  private String name;
  private int score;
  private int level;
  private boolean current;

  /**
   * No-args constructor is required for the java bean implementation.
   */
  public User() {
  }

  /**
   * Constructor for setting the User Object
   *
   * @param name String, name to be passed to the object.
   * @param score int, to be passed to the object initially set to 0.
   * @param level int, how many levels the user has already passed.
   */
  public User(String name, int score, int level) {
    this.name = name;
    this.score = score;
    this.level = level;
  }

  /**
   * @return a string, the name of the user.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * @param name a string, to set the object user name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return int, the score of the user.
   */
  @Override
  public int getScore() {
    return score;
  }

  /**
   * @param score int, to set the object score so far.
   */
  @Override
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * @return int, the level of the user.
   */
  @Override
  public int getLevel() {
    return level;
  }

  /**
   * @param level int, to set the object level so far.
   */
  @Override
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * @return the name of the user as a representation of the object.
   */
  @Override
  public String toString() {
    return name;
  }

  /**
   * @return true if the user is selected current in the users list.
   */
  @Override
  public boolean isCurrent() {
    return current;
  }

  /**
   * @param current passes true if the user is selected as current
   *                in the users list
   */
  @Override
  public void setCurrent(boolean current) {
    this.current = current;
  }

  /**
   * Checks for equality based on the user name, Ignores case.
   *
   * @param o the object to check against.
   * @return boolean, true if the object has the same name.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return name.equalsIgnoreCase(user.getName());
  }

  /**
   * @return basic implementation of the hashCode method.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, score, level);
  }
}
