package planner.plannermodel;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for representing an event.
 */
public class Event implements IEvent {

  private String name;
  private Day startDay;
  private Day endDay;
  private Time startTime;
  private Time endTime;
  private boolean online;
  private String place;
  private List<String> users;
  private String host;

  /**
   * Constructor.
   * @param name name.
   * @param startDay day.
   * @param endDay day.
   * @param startTime time.
   * @param endTime time.
   * @param online online.
   * @param place place.
   * @param users list of users.
   */
  public Event(String name, Day startDay, Day endDay,
               Time startTime, Time endTime, boolean online, String place, List<String> users) {
    this.name = name;
    this.startDay = startDay;
    this.endDay = endDay;
    this.startTime = startTime;
    this.endTime = endTime;
    if (!ensureTime()) {
      throw new IllegalArgumentException("Times cannot be equal");
    }
    this.online = online;
    this.place = place;
    this.users = new ArrayList<>();
    this.host = null;
    for (String uid : users) {
      this.users.add(uid);
    }
  }

  /**
   * Constructs an Event.
   * @param name name field
   * @param startDay startDay as a Day
   * @param endDay endDay as a day
   * @param startTime startTime as a Time
   * @param endTime endTime as a Time
   * @param online true if online, false if offline
   * @param place string place
   */
  public Event(String name, Day startDay, Day endDay,
               Time startTime, Time endTime, boolean online, String place) {
    this.name = name;
    this.startDay = startDay;
    this.endDay = endDay;
    this.startTime = startTime;
    this.endTime = endTime;
    if (!ensureTime()) {
      throw new IllegalArgumentException("Times cannot be equal");
    }
    this.online = online;
    this.place = place;
    this.users = new ArrayList<>();
    this.host = null;
  }


  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Day getStartDay() {
    return startDay;
  }

  @Override
  public void setStartDay(Day day) {
    Day temp = this.startDay;
    this.startDay = day;
    if (!ensureTime()) {
      this.startDay = temp;
      throw new IllegalArgumentException("Cannot make start and end times the same.");
    }
  }

  @Override
  public Time getStartTime() {

    return startTime;
  }

  @Override
  public void setStartTime(Time time) {
    Time temp = this.startTime;
    this.startTime = time;
    if (!ensureTime()) {
      this.startTime = temp;
      throw new IllegalArgumentException("Cannot make start and end times the same.");
    }
  }

  @Override
  public Day getEndDay() {
    return endDay;
  }

  @Override
  public void setEndDay(Day day) {
    Day temp = this.endDay;
    this.endDay = day;
    if (!ensureTime()) {
      this.endDay = temp;
      throw new IllegalArgumentException("Cannot make start and end times the same.");
    }
  }

  @Override
  public Time getEndTime() {
    return endTime;
  }

  @Override
  public void setEndTime(Time time) {
    Time temp = this.startTime;
    this.endTime = time;
    if (!ensureTime()) {
      this.endTime = temp;
      throw new IllegalArgumentException("Cannot make start and end times the same.");
    }
  }

  @Override
  public boolean isOnline() {
    return online;
  }

  @Override
  public void setOnline(boolean online) {
    this.online = online;
  }

  @Override
  public String getPlace() {
    return place;
  }

  @Override
  public void setPlace(String place) {
    this.place = place;
  }

  @Override
  public List<String> getUsers() {
    return users;
  }

  @Override
  public void addUser(String user) {
    if (users.contains(user)) {
      throw new IllegalArgumentException();
    }
    users.add(user);
  }

  @Override
  public void removeUser(String user) {
    users.remove(user);
  }


  @Override
  public String getHost() {
    return users.get(0);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Event other = (Event) obj;
    return this.name.equals(other.name)
            && this.startDay == other.startDay
            && this.endDay == other.endDay
            && this.startTime.equals(other.startTime)
            && this.endTime.equals(other.endTime)
            && this.online == other.online
            && this.place.equals(other.place);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public int totalTime(Day day, int time) {
    int num;
    switch (day) {
      case SUNDAY:
        num = 0;
        break;
      case MONDAY:
        num = 1;
        break;
      case TUESDAY:
        num = 2;
        break;
      case WEDNESDAY:
        num = 3;
        break;
      case THURSDAY:
        num = 4;
        break;
      case FRIDAY:
        num = 5;
        break;
      case SATURDAY:
        num = 6;
        break;
      default:
        throw new IllegalArgumentException("Invalid day");
    }
    return (num * 10000 + time);
  }


  @Override
  public int totalStartTime(boolean sunday) {
    if (sunday) {
      return totalStartSunday();
    } else {
      return totalStartSaturday();
    }
  }

  @Override
  public int totalEndTime(boolean sunday) {
    if (sunday) {
      return totalEndSunday();
    } else {
      return totalEndSaturday();
    }
  }

  private boolean ensureTime() {
    return this.totalStartSunday() != this.totalEndSunday();
  }

  private int totalTimeSaturday(Day day, int time) {
    int total = totalTime(day, time);
    if (total < 60000) {
      return total + 10000;
    } else {
      return total - 60000;
    }
  }

  private int totalStartSaturday() {
    return totalTimeSaturday(this.startDay, this.startTime.toInt());
  }

  private int totalEndSaturday() {
    return totalTimeSaturday(this.endDay, this.endTime.toInt());
  }

  private int totalStartSunday() {
    return totalTime(this.startDay, this.startTime.toInt());
  }

  private int totalEndSunday() {
    return totalTime(this.endDay, this.endTime.toInt());
  }

}
