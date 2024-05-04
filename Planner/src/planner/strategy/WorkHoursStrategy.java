package planner.strategy;

import java.util.ArrayList;
import java.util.List;

import planner.plannermodel.ReadOnlyNUPlanner;

/**
 * Class representing a strategy of work hours.
 */
public class WorkHoursStrategy implements Strategy {

  public final boolean sundayStart;
  private final List<Integer> validTimes;

  /**
   * Constructor.
   */
  public WorkHoursStrategy(boolean sundayStart) {
    this.sundayStart = sundayStart;
    if (sundayStart) {
      this.validTimes = getValidTimes();
    } else {
      this.validTimes = getValidTimesSaturday();
    }
  }

  @Override
  public int findFirstAvailableTime(int length, ReadOnlyNUPlanner model) {
    for (int i = 0; i < validTimes.size(); i++) {
      int startingDay = validTimes.get(i) / 10000;
      if (validTimes.size() > i + length) {
        if (model.availableAtTime(validTimes.get(i))
                && model.availableAtTime(validTimes.get(i + length))) {
          if (checkBlock(i, length, model)) {
            if (validTimes.get(i + length) / 10000 == startingDay) {
              return validTimes.get(i);
            }
          }
        }
      }
    }
    throw new IllegalStateException("Cannot schedule event. No room in everybody's schedule.");
  }

  @Override
  public int findFirstEndTime(int length, ReadOnlyNUPlanner model) {
    int index = validTimes.indexOf(findFirstAvailableTime(length, model));
    if (index + length < validTimes.size()) {
      return validTimes.get(index + length);
    }
    return validTimes.get(validTimes.size() - 1);
  }

  private List<Integer> getValidTimes() {
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < 62400; i++) {
      if (i % 100 < 60 && i % 10000 < 2400) {
        if (i >= 10000 && i < 60000) {
          if (i % 10000 >= 900 && i % 10000 <= 1700) {
            result.add(i);
          }
        }
      }
    }
    return result;
  }

  private List<Integer> getValidTimesSaturday() {
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < 62400; i++) {
      if (i % 100 < 60 && i % 10000 < 2400) {
        if (i >= 20000) {
          if (i % 10000 >= 900 && i % 10000 <= 1700) {
            result.add(i);
          }
        }
      }
    }
    return result;
  }

  private boolean checkBlock(int startingIndex, int length, ReadOnlyNUPlanner model) {
    for (int time = startingIndex; time < startingIndex + length; time++) {
      int t = validTimes.get(time);
      if (!model.availableAtTime(t)) {
        return false;
      }
    }
    return true;
  }
}
