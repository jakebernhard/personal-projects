package planner.plannermodel;



/**
 * An interface representing a model of PlannerModel.PlannerModel.NUPlanner.
 */
public interface NUPlanner extends ReadOnlyNUPlanner {

  /**
   * Adds an event to a users schedule.
   * @param event the event being added.
   * @param user the user the event is being added to.
   */
  void add(IEvent event, String user);

  /**
   * Modifies the event in the users schedule with all changes.
   * @param event the event being modified.
   * @param changedEvent the changes to be made to the event.
   */
  void modify(IEvent event, IEvent changedEvent, String user);

  /**
   * Removes an event in a users schedule.
   * @param event the event being removed.
   * @param user the user whose schedule has the event being removed.
   */
  void remove(IEvent event, String user);

  /**
   * Adds a new empty schedule for the given username.
   * @param username the username for the user being entered.
   * @return the new schedule added.
   * @throws IllegalStateException if the user is already in the database.
   * @throws IllegalArgumentException when the given username has no characters/
   */
  ISchedule addSchedule(String username);

  /**
   * Adds event to all users in the database.
   */
  void addEventToAll(IEvent event, String user);

  /**
   * Returns the instance of the event in the planner, otherwise returns a new event.
   * @param event the event being checked.
   * @return the instance of the event or a new event.
   */
  IEvent getModelInstance(IEvent event);



}
