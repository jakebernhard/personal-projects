package planner.plannercontroller;

import planner.plannermodel.NUPlanner;
import planner.plannermodel.IEvent;
import planner.plannerview.Feature;
import planner.plannerview.PlannerGUI;
import planner.writerreader.PlannerReader;
import planner.writerreader.PlannerWriter;

/**
 * Represents a Controller for a Planner GUI.
 */
public class PlannerController implements Feature {
  private final NUPlanner model;
  private final PlannerGUI view;


  /**
   * Constructs a PlannerController.
   * @param model the model representation of the planner
   * @param view the gui the model will be printed too
   */
  public PlannerController(NUPlanner model, PlannerGUI view) {
    this.model = model;
    this.view = view;
    this.view.addFeatures(this);
  }

  /**
   * Starts to display the view.
   */
  public void run() {

    this.view.display(true);

  }

  @Override
  public void openEmptyEventWindow() {
    view.emptyEventWindow();
  }


  @Override
  public void openFileChooser() {
    view.fileWindow();
  }

  @Override
  public void openDirectoryChooser() {
    view.directoryWindow();
  }

  public void openPopulatedEventWindow(IEvent event) {
    view.populatedEventWindow(event);
  }

  @Override
  public void updateVisualizedSchedule() {
    view.refreshPanelWindow();
  }

  @Override
  public void createNewEvent(IEvent event) {
    try {
      model.add(event, view.getCurrentUser());
      view.refreshPanelWindow();
    } catch (IllegalArgumentException | IllegalStateException e) {
      openErrorMessage(e.getMessage());
    }
  }

  @Override
  public void openErrorMessage(String message) {
    view.errorFrame(message);
  }

  @Override
  public void removeEvent(IEvent event) {
    try {
      model.remove(event, view.getCurrentUser());
      view.refreshPanelWindow();
    }  catch (IllegalArgumentException | IllegalStateException e) {
      openErrorMessage(e.getMessage());
    }
  }

  @Override
  public void modifyEvent(IEvent event, IEvent changedEvent) {
    try {
      model.modify(event, changedEvent, view.getCurrentUser());
      view.refreshPanelWindow();
    }  catch (IllegalArgumentException | IllegalStateException e) {
      openErrorMessage(e.getMessage());
    }
  }

  @Override
  public void addFile(String xmlFile) {
    try {
      PlannerReader reader =  new PlannerReader();
      reader.addUser(xmlFile, model);
      view.refreshUserBox();
    } catch (IllegalStateException | IllegalArgumentException e) {
      openErrorMessage(e.getMessage());
    }
  }

  @Override
  public void saveFiles(String directory) {
    try {
      PlannerWriter writer = new PlannerWriter();
      writer.writeAllFiles(model, directory);
    } catch (IllegalStateException | IllegalArgumentException e) {
      openErrorMessage(e.getMessage());
    }
  }

  @Override
  public void openScheduleWindow() {
    view.openScheduleWindow();
  }

  @Override
  public void schedule(IEvent event) {
    try {
      model.addEventToAll(event, view.getCurrentUser());
      view.refreshPanelWindow();
    }  catch (IllegalArgumentException | IllegalStateException e) {
      openErrorMessage(e.getMessage());
    }
  }

  @Override
  public void toggleHostColor() {
    view.toggleHostColor();
    view.refreshPanelWindow();
  }

  @Override
  public void newUserWindow() {
    view.openNewUserWindow();
  }

  @Override
  public void createNewUser(String user) {
    try {
      model.addSchedule(user);
      view.refreshUserBox();
    } catch (IllegalArgumentException | IllegalStateException e) {
      openErrorMessage(e.getMessage());
    }
  }

}

