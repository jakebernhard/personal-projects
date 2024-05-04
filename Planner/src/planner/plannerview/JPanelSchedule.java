package planner.plannerview;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import planner.plannermodel.Day;
import planner.plannermodel.Event;
import planner.plannermodel.IEvent;
import planner.plannermodel.ReadOnlyNUPlanner;
import planner.plannermodel.Time;

/**
 * Class for representing a scheduling panel.
 */
public class JPanelSchedule extends JPanel {
  private static final String[] BOOL_VALUES = {"true", "false"};

  private final ReadOnlyNUPlanner model;
  private JTextField nameBox;
  private JComboBox<String> onlineBox;

  private JTextField placeBox;

  private JTextField minutesBox;

  private JList<String> listUsersBox;

  private List<Feature> features;


  /**
   * Constructor.
   * @param model the current model.
   */
  public JPanelSchedule(ReadOnlyNUPlanner model) {
    this.model = model;
    this.nameBox = new JTextField(20);
    this.onlineBox = new JComboBox<>(BOOL_VALUES);
    onlineBox.setSelectedItem("true");
    this.placeBox = new JTextField(15);
    this.listUsersBox = new JList<>(model.getUserList().toArray(new String[0]));
    this.minutesBox = new JTextField(20);
    this.setLayout(null);
    addAndSizeButtons();
    this.features = new ArrayList<>();
  }


  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 400x400 pixels.
   * @return  Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 600);
  }


  /**
   * Add feature to schedule panel.
   * @param feature add feature.
   */
  public void addFeatures(Feature feature) {
    features.add(feature);
  }

  /**
   * Creates an event based on the filled in information.
   * @return a new event.
   */
  public IEvent createEvent() {
    return new Event(nameBox.getText(),
            getCorrectDay(getAvailableTime()),
            getCorrectDay(getEndTime()),
            Time.timeFromTotalTime(getAvailableTime()),
            Time.timeFromTotalTime(getEndTime()),
            Boolean.parseBoolean(onlineBox.getSelectedItem().toString()),
            placeBox.getText());
  }

  /**
   * Returns true iff the request is valid meaning all info is filled in.
   * @return if the request is valid.
   */
  public boolean validateRequest() {
    return !(nameBox.getText().isEmpty() || placeBox.getText().isEmpty()
            || minutesBox.getText().isEmpty());
  }




  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.scale(1,-1);
    g2d.translate(0, -600);
  }


  private void addAndSizeButtons() {
    addAndSizeLocationButtons();
    addAndSizeMinuteButtons();
    addAndSizeUserButtons();
  }

  private void addAndSizeLocationButtons() {
    JLabel name = new JLabel("Event name:");
    name.setBounds(5, 15, 100, 20);
    this.add(name);
    this.nameBox.setBounds(5, 50, 590, 70);
    this.add(this.nameBox);
    JLabel location = new JLabel("Location:");
    location.setBounds(5, 150, 100, 20);
    this.add(location);
    this.onlineBox.setBounds(5, 205, 100, 35);
    this.add(onlineBox);
    placeBox.setBounds(120, 185, 475, 70);
    this.add(placeBox);
  }

  private void addAndSizeMinuteButtons() {
    JLabel minutes = new JLabel("Duration in minutes:");
    minutes.setBounds(5, 285, 150, 20);
    this.add(minutes);
    this.minutesBox.setBounds(5, 320, 590, 70);
    this.add(this.minutesBox);
  }

  private void addAndSizeUserButtons() {
    JLabel users = new JLabel("Available users");
    users.setBounds(5, 420, 100, 20);
    this.add(users);
    listUsersBox.setBounds(5, 455, 590, 100);
    this.add(listUsersBox);
  }

  private int getAvailableTime() {
    try {
      return model.getStrategy().findFirstAvailableTime(
              Integer.parseInt(minutesBox.getText()), model);
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Invalid duration entry.");
    }
  }

  private int getEndTime() {
    try {
      return model.getStrategy().findFirstEndTime(
              Integer.parseInt(minutesBox.getText()), model);
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Invalid duration entry.");
    }
  }

  private Day getCorrectDay(int totalTime) {
    if (model.startDay() == Day.SUNDAY) {
      return Day.dayFromTotalTime(totalTime);
    } else {
      return Day.dayFromTotalTimeSaturday(totalTime);
    }
  }

}
