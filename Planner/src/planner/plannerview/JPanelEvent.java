package planner.plannerview;


import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;

import planner.plannermodel.Day;
import planner.plannermodel.Event;
import planner.plannermodel.IEvent;
import planner.plannermodel.Time;


/**
 * A class for representing the event screen.
 */
public class JPanelEvent extends JPanel {

  private static final String[] BOOL_VALUES = {"true", "false"};

  private static final String[] DAYS =
      {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


  private final String currentUser;
  private JTextField nameBox;

  private JComboBox<String> startDayBox;

  private JComboBox<String> endDayBox;

  private JTextField startTimeBox;

  private JTextField endTimeBox;

  private JComboBox<String> onlineBox;

  private JTextField placeBox;

  private JList<String> listUsersBox;

  private IEvent event;

  private List<Feature> features;


  /**
   * Constructor.
   * @param currentUser current user.
   */
  public JPanelEvent(String currentUser) {
    this.currentUser = currentUser;
    this.event = null;
    this.nameBox = new JTextField(20);
    this.startDayBox = new JComboBox<>(DAYS);
    startDayBox.setSelectedItem("Sunday");
    this.endDayBox = new JComboBox<>(DAYS);
    endDayBox.setSelectedItem("Sunday");
    this.startTimeBox = new JTextField(15);
    this.endTimeBox = new JTextField(15);
    this.onlineBox = new JComboBox<>(BOOL_VALUES);
    onlineBox.setSelectedItem("true");
    this.placeBox = new JTextField(15);
    this.listUsersBox = new JList<>(new String[0]);
    this.setLayout(null);
    addAndSizeButtons();
    this.features = new ArrayList<>();
  }


  /**
   * Constructor.
   * @param event the event.
   */
  public JPanelEvent(String currentUser, IEvent event) {
    this.currentUser = currentUser;
    this.event = Objects.requireNonNull(event);
    this.nameBox = new JTextField(this.event.getName(), 20);
    this.startDayBox = new JComboBox<>(DAYS);
    startDayBox.setSelectedItem(this.event.getStartDay().toString());
    this.endDayBox = new JComboBox<>(DAYS);
    endDayBox.setSelectedItem(this.event.getEndDay().toString());
    this.startTimeBox = new JTextField(this.event.getStartTime().toString(), 15);
    this.endTimeBox = new JTextField(this.event.getEndTime().toString(),15);
    this.onlineBox = new JComboBox<>(BOOL_VALUES);
    onlineBox.setSelectedItem(Boolean.toString(this.event.isOnline()));
    this.placeBox = new JTextField(event.getPlace(),15);
    this.listUsersBox = new JList<>(event.getUsers().toArray(new String[0]));
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
    return new Dimension(400, 700);
  }


  /**
   * Add features.
   * @param feature the feature to be added.
   */
  public void addFeatures(Feature feature) {
    features.add(feature);
  }

  /**
   * Creates an event based on the filled in information.
   * @return a new event based on the event frame data.
   */
  public IEvent createEvent() {
    return new Event(nameBox.getText(),
            Day.toDay(startDayBox.getSelectedItem().toString()),
            Day.toDay(endDayBox.getSelectedItem().toString()),
            Time.toTime(startTimeBox.getText()),
            Time.toTime(endTimeBox.getText()),
            Boolean.parseBoolean(onlineBox.getSelectedItem().toString()),
            placeBox.getText());
  }

  /**
   * Returns true iff all the event data is filled out.
   * @return true if the event data is filled out.
   */
  public boolean validateEvent() {
    return !(nameBox.getText().isEmpty() || placeBox.getText().isEmpty()
            || startTimeBox.getText().isEmpty() || endTimeBox.getText().isEmpty());
  }

  /**
   * Returns the original event when the frame is first opened. Null when empty event frame is open.
   * @return the original event of the event frame.
   */
  public IEvent getBaseEvent() {
    if (this.event == null) {
      throw new IllegalStateException("Empty event frame does not have a base event");
    }
    return this.event;
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
    addAndSizeTimeButtons();
    addAndSizeUserButtons();
  }

  private void addAndSizeLocationButtons() {
    JLabel name = new JLabel("Event name:");
    name.setBounds(5, 10, 100, 20);
    this.add(name);
    this.nameBox.setBounds(5, 50, 390, 50);
    this.add(this.nameBox);
    JLabel location = new JLabel("Location:");
    location.setBounds(5, 120, 100, 20);
    this.add(location);
    this.onlineBox.setBounds(5, 150, 100, 35);
    this.add(onlineBox);
    placeBox.setBounds(120, 150, 270, 40);
    this.add(placeBox);
  }


  private void addAndSizeTimeButtons() {
    JLabel startingDay = new JLabel("Starting Day:");
    startingDay.setBounds(5, 210, 100, 20);
    this.add(startingDay);
    startDayBox.setBounds(100, 205, 290, 35);
    this.add(startDayBox);
    JLabel startingTime = new JLabel("Starting time:");
    startingTime.setBounds(5, 270, 100, 20);
    this.add(startingTime);
    startTimeBox.setBounds(110, 265, 280, 40);
    this.add(startTimeBox);
    JLabel endingDay = new JLabel("Ending Day:");
    endingDay.setBounds(5, 330, 100, 20);
    this.add(endingDay);
    endDayBox.setBounds(100, 325, 290, 35);
    this.add(endDayBox);
    JLabel endTime = new JLabel("Ending time:");
    endTime.setBounds(5, 390, 100, 20);
    this.add(endTime);
    endTimeBox.setBounds(110, 390, 280, 40);
    this.add(endTimeBox);
  }

  private void addAndSizeUserButtons() {
    JLabel users = new JLabel("Available users");
    users.setBounds(5, 450, 100, 20);
    this.add(users);
    listUsersBox.setBounds(5, 500, 400, 100);
    this.add(listUsersBox);
  }

}
