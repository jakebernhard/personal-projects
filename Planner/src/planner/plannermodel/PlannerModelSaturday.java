package planner.plannermodel;

import java.util.ArrayList;
import java.util.List;

import planner.strategy.Strategy;
import planner.writerreader.PlannerReader;

/**
 * Class for representing a planner model that starts on a saturday.
 */
public class PlannerModelSaturday implements NUPlanner {

  private List<ISchedule> schedules;

  private final Strategy strategy;



  /**
   * Constructor.
   */
  public PlannerModelSaturday(Strategy strategy) {
    schedules = new ArrayList<>();
    this.strategy = strategy;
  }


  /**
   * Constructs a PlannerModel from a list of input schedule xml files.
   * @param xmlDocs list of input xml files
   */
  public PlannerModelSaturday(List<String> xmlDocs, Strategy strategy) {
    schedules = new ArrayList<ISchedule>();
    PlannerReader reader = new PlannerReader();
    for (String file : xmlDocs) {
      reader.addUser(file, this);
    }
    this.strategy = strategy;
  }


  @Override
  public void add(IEvent event, String user) {
    if (!containsUser(user)) {
      throw new IllegalArgumentException("Please enter a valid user in the system");
    }
    ISchedule s = getScheduleByUser(user);
    event = getModelInstance(event);
    s.add(event);
  }

  @Override
  public void modify(IEvent event, IEvent event2,  String user) {
    if (!containsUser(user)) {
      throw new IllegalArgumentException("Please enter a valid user in the system");
    }
    ISchedule s = getScheduleByUser(user);
    event = getModelInstance(event);
    boolean validate = true;
    for (String uid : event.getUsers()) {
      if (containsUser(uid)) {
        ISchedule schedule = getScheduleByUser(uid);
        if (!schedule.checkModifiedEvent(event2, event)) {
          validate = false;
        }
      }
    }
    if (validate) {
      s.modify(event, event2);
    } else {
      throw new IllegalStateException("Cannot make change. Conflict is one of users schedule.");
    }
  }

  @Override
  public void remove(IEvent event, String user) {
    if (!containsUser(user)) {
      throw new IllegalArgumentException("Please enter a valid user in the system");
    }
    event = getModelInstance(event);
    if (!event.getUsers().isEmpty()) {
      if (user.equals(event.getHost())) {
        for (ISchedule s : this.schedules) {
          if (s.containsEvent(event)) {
            s.remove(event);
          }
        }
      } else {
        ISchedule s = getScheduleByUser(user);
        s.remove(event);
      }
    } else {
      ISchedule s = getScheduleByUser(user);
      s.remove(event);
    }
  }

  @Override
  public ISchedule addSchedule(String username) {
    if (containsUser(username)) {
      throw new IllegalStateException("User already entered");
    }
    if (username.strip().equals("")) {
      throw new IllegalArgumentException("Need characters to be valid");
    }
    ISchedule s =  new Schedule(username, false);
    schedules.add(s);
    return s;
  }


  @Override
  public ISchedule getScheduleByUser(String user) {
    for (ISchedule s: schedules) {
      if (user.equals(s.getName())) {
        return s;
      }
    }
    throw new IllegalArgumentException("Given user is not the in database.");
  }


  @Override
  public List<String> getUserList() {
    List<String> result = new ArrayList<>();
    for (ISchedule s: schedules) {
      result.add(s.getName());
    }
    return result;
  }


  @Override
  public List<ISchedule> getCopySchedules() {
    List<ISchedule> result = new ArrayList<>();
    for (ISchedule s : schedules) {
      result.add(s);
    }
    return result;
  }

  @Override
  public void addEventToAll(IEvent event, String user) {
    add(event, user);
    for (ISchedule schedule : schedules) {
      if (!schedule.getName().equals(user)) {
        add(event, schedule.getName());
      }
    }
  }

  @Override
  public IEvent getModelInstance(IEvent event) {
    for (ISchedule s : this.schedules) {
      for (IEvent e : s.copyEventList()) {
        if (e.equals(event)) {
          return e;
        }
      }
    }
    return event;
  }

  @Override
  public boolean availableAtTime(int time) {
    for (ISchedule s : this.schedules) {
      if (!s.availableAtTime(time)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getFirstFreeTime(int eventLength) {
    return this.strategy.findFirstAvailableTime(eventLength, this);
  }

  @Override
  public Strategy getStrategy() {
    return this.strategy;
  }

  @Override
  public Day startDay() {
    return Day.SATURDAY;
  }

  private boolean containsUser(String user) {
    for (ISchedule s: schedules) {
      if (user.equals(s.getName())) {
        return true;
      }
    }
    return false;
  }
}
