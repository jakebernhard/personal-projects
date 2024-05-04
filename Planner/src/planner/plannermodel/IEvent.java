package planner.plannermodel;

import java.util.List;

/**
 * Interface for representing an event.
 */
public interface IEvent {


  /**
   * Returns the name of the event.
   * @return the name.
   */
  String getName();

  /**
   * Sets the name of the event.
   * @param name the value the name is set to.
   */
  void setName(String name);

  /**
   * Returns the start day of the event.
   * @return the start day of the event.
   */
  public Day getStartDay();

  /**
   * Sets the start day of the event.
   * @param day the day the start day is set to.
   */
  void setStartDay(Day day);


  /**
   * Returns the start time of the event.
   * @return the start time.
   */
  Time getStartTime();

  /**
   * Sets the start time of the event.
   * @param time the time the start time is set to.
   */
  void setStartTime(Time time);

  /**
   * Returns the end day of the event.
   * @return the end day.
   */
  Day getEndDay();

  /**
   * Sets the end day of the event.
   * @param day the day the end day is set to.
   */
  void setEndDay(Day day);

  /**
   * Returns the end time of the event.
   * @return the end time.
   */
  Time getEndTime();

  /**
   * Sets the end time of the event.
   * @param time the time the end time is set to.
   */
  void setEndTime(Time time);

  /**
   * Returns the online value.
   * @return the online value.
   */
  boolean isOnline();

  /**
   * Sets the online value.
   * @param online the value online is set to.
   */
  void setOnline(boolean online);

  /**
   * Returns the place of the event.
   * @return the place.
   */
  String getPlace();

  /**
   * Sets the place of the event.
   * @param place the place the event is being set to.
   */
  void setPlace(String place);

  /**
   * Returns the list of users attending an event.
   * @return the list of users.
   */
  List<String> getUsers();

  /**
   * Adds a user to attending the event.
   * @param user the user newly in attendance.
   */
  void addUser(String user);

  /**
   * Removes the user from the attendees list.
   * @param user the user being removed.
   */
  void removeUser(String user);


  /**
   * Returns the host.
   * @return the host.
   */
  String getHost();


  /**
   * Returns the total time.
   * @param day the day.
   * @param time the time.
   * @return an int representing the total time.
   */
  int totalTime(Day day, int time);

  /**
   * Total time for start time.
   * @param sunday whether the day of the week starts on sunday or not.
   * @return total start time.
   */
  int totalStartTime(boolean sunday);

  /**
   * Total end time.
   * @param sunday whether the day of the week starts on sunday or not.
   * @return total end time.
   */
  int totalEndTime(boolean sunday);



}
