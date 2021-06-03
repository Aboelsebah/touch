package race;

public class Stats {
  private int speed;
  private int rawSpeed;
  private boolean mistake;

  public Stats(int speed, int rawSpeed, boolean mistake) {
    this.speed = speed;
    this.rawSpeed = rawSpeed;
    this.mistake = mistake;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getRawSpeed() {
    return rawSpeed;
  }

  public void setRawSpeed(int rawSpeed) {
    this.rawSpeed = rawSpeed;
  }

  public boolean isMistake() {
    return mistake;
  }

  public void setMistake(boolean mistake) {
    this.mistake = mistake;
  }

  @Override
  public String toString() {
    return "Stats{" +
        "speed=" + speed +
        ", rawSpeed=" + rawSpeed +
        ", mistake=" + mistake +
        '}';
  }
}
