package planner.plannerview;


import planner.plannermodel.IEvent;

/**
 * Represents a GUI for the Planner.
 */
public interface PlannerGUI {

  /**
   * Visualizes the planner model.
   * @param show true when you want the GUI to be displayed.
   */
  void display(boolean show);

  /**
   * Add features.
   * @param feature the feature being added.
   */
  void addFeatures(Feature feature);

  /**
   * Opens an empty event window.
   */
  void emptyEventWindow();

  /**
   * Opens an event window of the given event.
   * @param event the event being displayed.
   */
  void populatedEventWindow(IEvent event);

  /**
   * Opens a schedule window.
   */
  void openScheduleWindow();

  /**
   * Opens a file chooser.
   */
  void fileWindow();

  /**
   * Opens a file chooser directory only.
   */
  void directoryWindow();

  /**
   * Refreshes the panel window to visualize the updated schedule.
   */
  void refreshPanelWindow();

  /**
   * Gets the current user of the schedule being visualized.
   * @return the name of the user.
   */
  String getCurrentUser();

  /**
   * Opens an error frame with the given message.
   * @param message the message being displayed.
   */
  void errorFrame(String message);

  /**
   * Updates the user box to account for newly added files.
   */
  void refreshUserBox();

  /**
   * Toggles whether the host color is visible.
   */
  void toggleHostColor();

  /**
   * Opens a new user window.
   */
  void openNewUserWindow();

}
