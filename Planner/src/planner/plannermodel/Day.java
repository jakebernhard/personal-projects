package planner.plannermodel;

/**
 * Represents a Day.
 */
public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;


  @Override
  public String toString() {
    if (this == SUNDAY) {
      return "Sunday";
    }
    if (this == MONDAY) {
      return "Monday";
    }
    if (this == TUESDAY) {
      return "Tuesday";
    }
    if (this == WEDNESDAY) {
      return "Wednesday";
    }
    if (this == THURSDAY) {
      return "Thursday";
    }
    if (this == FRIDAY) {
      return "Friday";
    }
    if (this == SATURDAY) {
      return "Saturday";
    }
    throw new IllegalStateException("Not valid day");
  }


  /**
   * Static method for turning a String into the given Day.
   * @param s the given string.
   * @return the day that corresponds to the string.
   */
  public static Day toDay(String s) {
    if (s.equals("Sunday")) {
      return Day.SUNDAY;
    }
    if (s.equals("Monday")) {
      return Day.MONDAY;
    }
    if (s.equals("Tuesday")) {
      return Day.TUESDAY;
    }
    if (s.equals("Wednesday")) {
      return Day.WEDNESDAY;
    }
    if (s.equals("Thursday")) {
      return Day.THURSDAY;
    }
    if (s.equals("Friday")) {
      return Day.FRIDAY;
    }
    if (s.equals("Saturday")) {
      return Day.SATURDAY;
    }
    throw new IllegalArgumentException("Invalid string of a day");
  }

  /**
   * Gets the day from the given total time.
   * @param totalTime the total time.
   * @return the day of the total time.
   */
  public static Day dayFromTotalTime(int totalTime) {
    int day = totalTime / 10000;
    switch (day) {
      case 0:
        return Day.SUNDAY;
      case 1:
        return Day.MONDAY;
      case 2:
        return Day.TUESDAY;
      case 3:
        return Day.WEDNESDAY;
      case 4:
        return Day.THURSDAY;
      case 5:
        return Day.FRIDAY;
      case 6:
        return Day.SATURDAY;
      default:
        throw new IllegalArgumentException("Invalid total time value.");
    }
  }


  /**
   * Gets the day from the given total time for Saturday start day.
   * @param totalTime the total time.
   * @return the day of the total time.
   */
  public static Day dayFromTotalTimeSaturday(int totalTime) {
    int day = totalTime / 10000;
    switch (day) {
      case 0:
        return Day.SATURDAY;
      case 1:
        return Day.SUNDAY;
      case 2:
        return Day.MONDAY;
      case 3:
        return Day.TUESDAY;
      case 4:
        return Day.WEDNESDAY;
      case 5:
        return Day.THURSDAY;
      case 6:
        return Day.FRIDAY;
      default:
        throw new IllegalArgumentException("Invalid total time value.");
    }
  }


}

