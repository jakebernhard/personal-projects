package planner.plannerview;


import planner.plannermodel.Day;
import planner.plannermodel.IEvent;
import planner.plannermodel.ISchedule;
import planner.plannermodel.NUPlanner;


/**
 * Represents a text view of a planner.
 */
public class PlannerTextualView implements PlannerTextView {
  private final NUPlanner system;


  /**
   * Constructs a planner textual view.
   * @param system input planner
   */
  public PlannerTextualView(NUPlanner system) {
    this.system = system;
  }

  /**
   * Create a string view of the planner.
   * @return a String
   */
  public String print() {
    String result = "";
    for (ISchedule s : system.getCopySchedules()) {
      result += printUser(s);
    }
    return result;
  }


  private String printUser(ISchedule s) {
    String result = "";
    result += "User: " + s.getName() + "\n";
    for (Day day : Day.values()) {
      result += day + ":\n";
      result += printEventsForDay(s, day);
    }
    return result;
  }

  private String printEventsForDay(ISchedule s, Day day) {
    String result = "";
    for (IEvent e : s.copyEventList()) {
      if (e.getStartDay() == day) {
        result += printEvent(e);
      }
    }
    return result;
  }

  private String printEvent(IEvent event) {
    String buffer = "        ";
    return buffer + "name: " + event.getName() + "\n" +
           buffer + "time: " + event.getStartDay() + ": " + event.getStartTime().toString() +
           " -> " + event.getEndDay() + ": " + event.getEndTime().toString() + "\n" +
           buffer + "location: " + event.getPlace() + "\n" +
           buffer + "online: " + Boolean.toString(event.isOnline()) + "\n" +
           buffer + "invitees: " + printEventUsers(event);
  }

  private String printEventUsers(IEvent event) {
    String result = "";
    String buffer = "        ";
    for (String user : event.getUsers()) {
      if (result.isEmpty()) {
        result += user + "\n";
      } else {
        result += buffer + user + "\n";
      }
    }
    return result;
  }

}
