package planner.plannermodel;


import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing a schedule of a single user. Can modify, add, or remove events.
 */
public class Schedule implements ISchedule {
  private final String name;

  private List<IEvent> userEvents;

  private boolean startSunday;

  /**
   * Constructor.
   * @param name name of the user.
   */
  public Schedule(String name, boolean startSunday) {
    this.name = name;
    this.userEvents = new ArrayList<>();
    this.startSunday = startSunday;
  }

  @Override
  public void add(IEvent event) {
    if (userEvents.contains(event)) {
      throw new IllegalStateException("Event already added");
    }
    if (!validateEvent(event)) {
      throw new IllegalStateException("There is already an event at these times.");
    }
    userEvents.add(event);
    if (!event.getUsers().contains(this.name)) {
      event.addUser(this.name);
    }
    sort();
  }

  @Override
  public void remove(IEvent event) {
    if (!userEvents.contains(event)) {
      throw new IllegalStateException("Event not in schedule.");
    }
    userEvents.remove(event);
    event.removeUser(this.name);
  }

  @Override
  public void modify(IEvent e, IEvent e2) {
    e.setName(e2.getName());
    e.setStartDay(e2.getStartDay());
    e.setEndDay(e2.getEndDay());
    e.setStartTime(e2.getStartTime());
    e.setEndTime(e2.getEndTime());
    e.setOnline(e2.isOnline());
    e.setPlace(e2.getPlace());
  }

  @Override
  public boolean checkModifiedEvent(IEvent event, IEvent exception) {
    List<IEvent> list = new ArrayList<>();
    for (IEvent e : userEvents) {
      if (!e.equals(exception)) {
        list.add(e);
      }
    }
    return validateEventList(event, list) && validateReverse(event, list);
  }

  @Override
  public boolean validateEvent(IEvent e) {
    return validateEventList(e, userEvents) && validateReverse(e, userEvents);
  }

  @Override
  public boolean availableAtTime(int time) {
    for (IEvent e : userEvents) {
      if (inRange(e.totalStartTime(startSunday), e.totalEndTime(startSunday), time)) {
        if (time != e.totalEndTime(startSunday) && time != e.totalStartTime(startSunday)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean containsEvent(IEvent e) {
    for (IEvent event: userEvents) {
      if (e.equals(event)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public List<IEvent> copyEventList() {
    List<IEvent> result = new ArrayList<>();
    for (IEvent e: userEvents) {
      result.add(e);
    }
    return result;
  }

  private void sort() {
    this.userEvents.sort(new CompareByTotalStartTime(startSunday));
  }

  private boolean validateEventList(IEvent e, List<IEvent> events) {
    for (IEvent event: events) {
      if (inRange(event.totalStartTime(startSunday),
              event.totalEndTime(startSunday), e.totalStartTime(startSunday))
              || inRange(event.totalStartTime(startSunday),
              event.totalEndTime(startSunday), e.totalEndTime(startSunday))) {
        if (e.totalStartTime(startSunday) != event.totalEndTime(startSunday)
                && e.totalEndTime(startSunday) != event.totalStartTime(startSunday)) {
          if (e.totalStartTime(startSunday) < e.totalEndTime(startSunday)) {
            return false;
          }
        } else {
          if (e.totalStartTime(startSunday) > e.totalEndTime(startSunday)) {
            if (inRange(event.totalStartTime(startSunday),
                    event.totalEndTime(startSunday), e.totalStartTime(startSunday)) &&
                    e.totalStartTime(startSunday) != event.totalEndTime(startSunday)) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  private boolean validateReverse(IEvent e, List<IEvent> events) {
    for (IEvent event: events) {
      if (inRange(e.totalStartTime(startSunday),
              e.totalEndTime(startSunday), event.totalStartTime(startSunday))
              || inRange(e.totalStartTime(startSunday),
              e.totalEndTime(startSunday), event.totalEndTime(startSunday))) {
        if (e.totalStartTime(startSunday) != event.totalEndTime(startSunday)
                && e.totalEndTime(startSunday) != event.totalStartTime(startSunday)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean inRange(int time1, int time2, int num) {
    return time1 <= num && time2 >= num;
  }


}
