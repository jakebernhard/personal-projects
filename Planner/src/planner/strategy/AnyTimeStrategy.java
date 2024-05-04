package planner.strategy;

import java.util.ArrayList;
import java.util.List;

import planner.plannermodel.ReadOnlyNUPlanner;

/**
 * Class for representing a strategy of any time.
 */
public class AnyTimeStrategy implements Strategy {

  private final List<Integer> validTimes;

  /**
   * The constructor.
   */
  public AnyTimeStrategy() {
    this.validTimes = getValidTimes();
  }

  @Override
  public int findFirstAvailableTime(int length, ReadOnlyNUPlanner model) {
    for (int i = 0; i < validTimes.size(); i++) {
      if (validTimes.size() > i + length) {
        if (model.availableAtTime(validTimes.get(i))
                && model.availableAtTime(validTimes.get(i + length))) {
          if (checkBlock(i, length, model)) {
            return validTimes.get(i);
          }
        }
      } else {
        boolean conflict = false;
        int time = i;
        while (time < validTimes.size()) {
          int t = validTimes.get(time);
          if (!model.availableAtTime(t)) {
            conflict = true;
          }
          time++;
        }
        if (!conflict) {
          return validTimes.get(i);
        }
      }

    }
    throw new IllegalStateException("Cannot schedule event. No room in everybody's schedule.");
  }

  @Override
  public int findFirstEndTime(int length, ReadOnlyNUPlanner model) {
    int index = validTimes.indexOf(findFirstAvailableTime(length, model));
    if (index + length < validTimes.size()) {
      return validTimes.get((index + length) % validTimes.size());
    }
    return validTimes.get((index + length) % validTimes.size());
  }

  private List<Integer> getValidTimes() {
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < 62400; i++) {
      if (i % 100 < 60 && i % 10000 < 2400) {
        result.add(i);
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





