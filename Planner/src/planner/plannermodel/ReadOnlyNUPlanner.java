package planner.plannermodel;

import java.util.List;

import planner.strategy.Strategy;

/**
 * Represents a planner that is read only.
 */
public interface ReadOnlyNUPlanner {

  /**
   * Gets a copy of the schedules used for textual views.
   * @return the copy of the list of schedules.
   */
  List<ISchedule> getCopySchedules();

  /**
   * Returns the list of users in a planner.
   * @return the list of users.
   */
  List<String> getUserList();

  /**
   * Returns the schedule of the given user.
   * @param user the user whose schedule is being retrieved.
   * @return the schedule of the given user.
   */
  ISchedule getScheduleByUser(String user);

  /**
   * Returns true iff the given time is available for all schedules in the planner.
   * @param time the time being checked.
   * @return true if the given time if free in all schedules.
   */
  boolean availableAtTime(int time);

  /**
   * Uses the strategy specified at
   * construction to find the first free time for scheduling an event.
   * @param eventLength the length of the event.
   * @return the starting time of the event.
   */
  int getFirstFreeTime(int eventLength);

  /**
   * Returns the strategy.
   * @return the strategy.
   */
  Strategy getStrategy();

  /**
   * Returns the start day.
   * @return the first day of the planner.
   */
  Day startDay();
}
