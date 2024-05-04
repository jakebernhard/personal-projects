package planner.plannermodel;

/**
 * Represents a time in hours and minutes.
 */
public class Time {
  int hour;
  int minutes;

  /**
   * Constructs a Time object.
   * @param hour hours integer
   * @param minutes minutes integer
   */
  public Time(int hour, int minutes) {
    if (hour > 23 || hour < 0) {
      throw new IllegalArgumentException("Invalid hour.");
    }
    if (minutes > 59 || minutes < 0) {
      throw new IllegalArgumentException("Invalid minutes.");
    }
    this.hour = hour;
    this.minutes = minutes;
  }

  public int toInt() {
    return hour * 100 + minutes;
  }

  @Override
  public String toString() {
    return Integer.toString(toInt());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Time other = (Time) obj;
    return this.hour == other.hour && this.minutes == other.minutes;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Converts the given string to time.
   * @param s the string.
   * @return the time based on the given string.
   */
  public static Time toTime(String s) {
    try {
      int time = Integer.parseInt(s);
      return new Time(time / 100, time % 100);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid number format.");
    }

  }

  /**
   * Returns the time from the given total time.
   * @param totalTime the total time being converted.
   * @return the time based on the given total time.
   */
  public static Time timeFromTotalTime(int totalTime) {
    totalTime = totalTime % 10000;
    int hours = totalTime / 100;
    int minutes = totalTime % 100;
    return new Time(hours, minutes);
  }

}
