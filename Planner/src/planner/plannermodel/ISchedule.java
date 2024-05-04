package planner.plannermodel;


import java.util.List;

/**
 * Interface for representing a schedule.
 */
public interface ISchedule {

  /**
   * Adds the event to the current schedule.
   * @param event the event to be added.
   */
  void add(IEvent event);


  /**
   * Removes an event in a users schedule.
   * @param event the event being removed.
   */
  void remove(IEvent event);

  /**
   * Modifies an event in a users schedule.
   * @param e the event being modified
   * @param e2 the event with modifications.
   */
  void modify(IEvent e, IEvent e2);


  /**
   * Returns the name of the user.
   * @return the name.
   */
  String getName();


  /**
   * Checks if the potentially modified event would fit in the schedule.
   * @param event the modification of the event.
   * @param exception the pre-modified event, to be an exception when checking.
   * @return true iff the modified event can be added to the schedule.
   */
  boolean checkModifiedEvent(IEvent event, IEvent exception);


  /**
   * Returns true if the given event is in the schedule.
   * @param event the event being checked.
   * @return true iff the event is in the schedule.
   */
  boolean containsEvent(IEvent event);

  /**
   * Returns a copy of the list of events.
   * @return a copy of the list of events.
   */
  List<IEvent> copyEventList();

  /**
   * Validates that the given event can fit within the schedule.
   * @param event the event being checked.
   * @return true iff the event can fit in the schedule.
   */
  boolean validateEvent(IEvent event);

  /**
   * Returns true if there is no events at the given time in the schedule.
   * @param time the time to be checked.
   * @return true iff there is no events at the given time.
   */
  boolean availableAtTime(int time);
}
