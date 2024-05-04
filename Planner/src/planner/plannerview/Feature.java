package planner.plannerview;


import planner.plannermodel.IEvent;

/**
 * Interface representing the features.
 */
public interface Feature {

  /**
   * Opens an empty event window.
   */
  void openEmptyEventWindow();

  /**
   * Opens a file chooser window.
   */
  void openFileChooser();

  /**
   * Opens a file choose window with directories only.
   */
  void openDirectoryChooser();

  /**
   * Open an event window with the given event.
   * @param event the event whose window is being open.
   */
  void openPopulatedEventWindow(IEvent event);

  /**
   * Refresh the planner to account for updates.
   */
  void updateVisualizedSchedule();

  /**
   * Creates a new event.
   * @param event the event to create.
   */
  void createNewEvent(IEvent event);

  /**
   * Opens an error message frame.
   * @param message the message to be displayed.
   */
  void openErrorMessage(String message);

  /**
   * Removes the given event.
   * @param event the event being removed.
   */
  void removeEvent(IEvent event);

  /**
   * Modifies the given event to the given changed event.
   * @param event the original event.
   * @param changedEvent the event it is being altered to.
   */
  void modifyEvent(IEvent event, IEvent changedEvent);

  /**
   * Add the given file to the planner.
   * @param xmlFile the file being added.
   */
  void addFile(String xmlFile);

  /**
   * Saves all the files to the given directory.
   * @param directory the directory to write the files.
   */
  void saveFiles(String directory);

  /**
   * Opens a scheduling window.
   */
  void openScheduleWindow();

  /**
   * Adds the given event to all users.
   * @param event the event being added.
   */
  void schedule(IEvent event);

  /**
   * Toggles whether the host color is visible or not.
   */
  void toggleHostColor();

  /**
   * Opens a new user window.
   */
  void newUserWindow();

  /**
   * Adds the new user to the planner.
   */
  void createNewUser(String user);
}
