package planner.plannerview;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;

import planner.plannermodel.Day;
import planner.plannermodel.IEvent;
import planner.plannermodel.ISchedule;
import planner.plannermodel.ReadOnlyNUPlanner;

/**
 * Represents a Jpanel for a planner.
 */
public class JPanelPlanner extends JPanel {
  private final ReadOnlyNUPlanner model;

  private ISchedule currentSchedule;

  private HashMap<Rectangle, IEvent> eventRectangles;

  private JComboBox<String> userBox;

  private JButton eventCreation = new JButton("Create Event");

  private JButton eventSchedule = new JButton("Schedule Event");

  private JButton addCalender = new JButton("Add Calender");

  private  JButton saveCalenders = new JButton("Save Calendars");

  private JButton toggleHostColor = new JButton("Toggle Host Color");

  private JButton addNewUser = new JButton("Add New User");

  private List<Feature> features;

  private boolean toggleEnable = false;


  /**
   * Constructs a JpanelPlanner.
   *
   * @param model input model
   */
  public JPanelPlanner(ReadOnlyNUPlanner model) {
    this.model = Objects.requireNonNull(model);
    this.setLayout(null);

    String[] users = model.getUserList().toArray(new String[0]);
    userBox = new JComboBox<>(users);
    userBox.setBounds(5, 650, 220, 35);
    this.add(userBox);
    this.currentSchedule = model.getScheduleByUser(userBox.getSelectedItem().toString());
    userBox.addActionListener(this::userBoxUpdate);

    eventCreation.setBounds(235, 650, 220, 35);
    this.add(eventCreation);
    eventCreation.addActionListener(this::createEventButtonClick);

    eventSchedule.setBounds(470, 650, 220, 35);
    this.add(eventSchedule);
    eventSchedule.addActionListener(this::scheduleEventButtonClick);

    addCalender.setBounds(110, 5, 100, 20);
    this.add(addCalender);
    addCalender.addActionListener(this::addScheduleButtonClick);

    saveCalenders.setBounds(215, 5, 100, 20);
    this.add(saveCalenders);
    saveCalenders.addActionListener(this::saveSchedulesButtonClick);

    toggleHostColor.setBounds(545, 5, 150, 20);
    this.add(toggleHostColor);
    toggleHostColor.addActionListener(this::toggleColorUpdate);

    addNewUser.setBounds(5, 5, 100, 20);
    this.add(addNewUser);
    addNewUser.addActionListener(this::newUserButtonClick);

    this.features = new ArrayList<>();

    addMouseListener();

  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 400x400 pixels.
   * @return  Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(700, 700);
  }

  /**
   * Adds feature to panel.
   * @param feature feature to be added.
   */
  public void addFeatures(Feature feature) {
    features.add(feature);
  }


  /**
   * Updates the current schedule to the current selected user.
   */
  public void refreshSchedule() {
    currentSchedule = model.getScheduleByUser(userBox.getSelectedItem().toString());
  }

  /**
   * Returns the current user of the panel.
   * @return the current user whose schedule is being viewed.
   */
  public String getSelectedUser() {
    return userBox.getSelectedItem().toString();
  }

  /**
   * Toggles whether the user can see if they are the host of an event.
   */
  public void toggleHostVisibility() {
    toggleEnable = !toggleEnable;
  }

  /**
   * Updates the user box to include newly added files.
   */
  public void updateUserBox() {
    for (String user : model.getUserList()) {
      boolean flag = false;
      for (int i = 0; i < userBox.getItemCount(); i++) {
        if (userBox.getItemAt(i).equals(user)) {
          flag = true;
        }
      }
      if (!flag) {
        userBox.addItem(user);
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    this.eventRectangles = new HashMap<>();
    int buffer = 30;
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    Rectangle bounds = this.getBounds();
    g2d.scale(1,-1);
    g2d.translate(0, -700);
    paintAllScheduleEvents(g);
    g2d.setColor(Color.BLACK);
    for (int i = 1; i < 8; i++) {
      g2d.drawLine(100 * i, buffer, 100 * i, getHeight() - buffer);
    }
    int value = getHeight() - buffer;
    for (int i = 24; i > 0; i--) {
      Stroke oldStroke = g2d.getStroke();
      if (i % 4 == 0) {
        g2d.setStroke(new BasicStroke(5));
      }
      g2d.drawLine(bounds.x, value , bounds.x + getWidth(),
              value);
      g2d.setStroke(oldStroke);
      value -= ((getHeight() - buffer * 2) / 24);
    }
  }

  private void paintAllScheduleEvents(Graphics g) {
    for (IEvent e : currentSchedule.copyEventList()) {
      paintEvent(e,g);
    }
  }

  private void paintEvent(IEvent e, Graphics g) {
    int startDayCoord = dayCoordinate(e.getStartDay());
    int endDayCoord = dayCoordinate(e.getEndDay());
    int startCoord = getTimeCoord(e.getStartTime().toInt());
    int endCoord = getTimeCoord( e.getEndTime().toInt());
    int height = endCoord - startCoord;
    HostColorDecorator decorator = new HostColorDecorator();
    decorator.decorate(e, getSelectedUser(), g, toggleEnable);
    if (startDayCoord == endDayCoord) {
      Rectangle rect = new Rectangle(startDayCoord, startCoord, 100, height);
      this.eventRectangles.put(rect,e);
      g.fillRect(startDayCoord, startCoord, 100, height);
    } else if (startDayCoord > endDayCoord) {
      fillRestOfCalender(g,startCoord,startDayCoord, e);
    } else {
      int dayCoord = startDayCoord;
      int timeCoord = startCoord;
      while (dayCoord < endDayCoord) {
        fillColumn(g, timeCoord, dayCoord, e);
        dayCoord += 100;
        timeCoord = 30;
      }
      Rectangle rect = new Rectangle(dayCoord, 30, 100, endCoord - 30);
      this.eventRectangles.put(rect,e);
      g.fillRect(dayCoord, 30, 100, endCoord - 30);
    }
    g.setColor(Color.BLACK);
  }

  private void fillColumn(Graphics g, int startingHeight, int startDayCoord, IEvent e) {
    Rectangle rect =
            new Rectangle(startDayCoord, startingHeight, 100, 656 - startingHeight);
    this.eventRectangles.put(rect, e);
    g.fillRect(startDayCoord, startingHeight, 100, 656 - startingHeight);

  }

  private void fillRestOfCalender(Graphics g, int startingHeight, int startDayCoord, IEvent e) {
    int dayCoord = startDayCoord;
    int timeCoord = startingHeight;
    while (dayCoord <= 600) {
      fillColumn(g, timeCoord, dayCoord, e);
      dayCoord += 100;
      timeCoord = 30;
    }
  }

  private int getTimeCoord(int t) {
    int hour = t / 100;
    hour *= 26;
    int minutes = t % 100;
    double minutesNotRounded = minutes;
    minutesNotRounded /= 60;
    minutesNotRounded *= 26;
    return hour + (int) minutesNotRounded + 30;
  }

  private int dayCoordinate(Day day) {
    int result;
    switch (day) {
      case SUNDAY:
        result = 0;
        break;
      case MONDAY:
        result = 100;
        break;
      case TUESDAY:
        result = 200;
        break;
      case WEDNESDAY:
        result = 300;
        break;
      case THURSDAY:
        result = 400;
        break;
      case FRIDAY:
        result = 500;
        break;
      case SATURDAY:
        result = 600;
        break;
      default:
        throw new IllegalArgumentException("Invalid day");
    }
    if (model.startDay() == Day.SATURDAY) {
      if (result != 600) {
        return result + 100;
      } else {
        return 0;
      }
    } else {
      return result;
    }
  }

  private void createEventButtonClick(ActionEvent e) {
    for (Feature feature : features) {
      feature.openEmptyEventWindow();
    }
  }

  private void scheduleEventButtonClick(ActionEvent e) {
    for (Feature feature : features) {
      feature.openScheduleWindow();
    }
  }

  private void addScheduleButtonClick(ActionEvent e) {
    for (Feature feature : features) {
      feature.openFileChooser();
    }
  }

  private void newUserButtonClick(ActionEvent e) {
    for (Feature feature : features) {
      feature.newUserWindow();
    }
  }

  private void saveSchedulesButtonClick(ActionEvent e) {
    for (Feature feature : features) {
      feature.openDirectoryChooser();
    }
  }

  private void userBoxUpdate(ActionEvent e) {
    for (Feature feature : features) {
      feature.updateVisualizedSchedule();
    }
  }

  private void toggleColorUpdate(ActionEvent e) {
    for (Feature feature : features) {
      feature.toggleHostColor();
    }
  }

  private void addMouseListener() {
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        eventClick(e);
      }

      @Override
      public void mousePressed(MouseEvent e) {
        // empty
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        // empty
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        // empty
      }

      @Override
      public void mouseExited(MouseEvent e) {
        // empty
      }
    });
  }

  private void eventClick(MouseEvent e) {
    for (Rectangle rect : eventRectangles.keySet()) {
      if (rect.contains(e.getPoint())) {
        for (Feature feature : features) {
          feature.openPopulatedEventWindow(eventRectangles.get(rect));
        }
      }
    }
  }

}






















