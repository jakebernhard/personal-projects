package planner.plannermodel;

import java.util.Comparator;

/**
 * Compares two events by totalStartTime.
 */
public class CompareByTotalStartTime implements Comparator<IEvent> {

  private final boolean toggleSunday;

  public CompareByTotalStartTime(boolean toggleSunday) {
    this.toggleSunday = toggleSunday;
  }

  /**
   * Compares an events time to another event.
   * @param e1 the first object to be compared.
   * @param e2 the second object to be compared.
   * @return 0 if equal, 1 if greater, -1 if lesser
   */
  public int compare(IEvent e1, IEvent e2) {
    if (e1.totalStartTime(toggleSunday) == e2.totalStartTime(toggleSunday)) {
      return 0;
    } else if (e1.totalStartTime(toggleSunday) < e2.totalStartTime(toggleSunday)) {
      return -1;
    } else {
      return 1;
    }
  }
}
