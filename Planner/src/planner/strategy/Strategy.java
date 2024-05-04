package planner.strategy;

import planner.plannermodel.ReadOnlyNUPlanner;

/**
 * Interface for representing a strategy.
 */
public interface Strategy {

  /**
   * Finds the first available time for the event that fits in the schedule.
   * @param length the length of the event.
   * @param model the model the event will be added to.
   * @return the time as an integer.
   */
  int findFirstAvailableTime(int length, ReadOnlyNUPlanner model);

  /**
   * Finds the ending time for the first available time slot for a new event.
   * @param length the length of the event.
   * @param model the model the event will be added to.
   * @return the time as an integer.
   */
  int findFirstEndTime(int length, ReadOnlyNUPlanner model);

}
