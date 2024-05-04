package planner.plannermodel;


import java.util.List;

import planner.strategy.Strategy;

/**
 * Implements a read only planner.
 */
public class ReadOnlyPlannerModel implements ReadOnlyNUPlanner {

  private final NUPlanner planner;

  public ReadOnlyPlannerModel(NUPlanner planner) {
    this.planner = planner;
  }


  @Override
  public List<ISchedule> getCopySchedules() {
    return planner.getCopySchedules();
  }

  @Override
  public List<String> getUserList() {
    return planner.getUserList();
  }

  @Override
  public ISchedule getScheduleByUser(String user) {
    return planner.getScheduleByUser(user);
  }

  @Override
  public boolean availableAtTime(int time) {
    return planner.availableAtTime(time);
  }

  @Override
  public int getFirstFreeTime(int eventLength) {
    return planner.getFirstFreeTime(eventLength);
  }

  @Override
  public Strategy getStrategy() {
    return planner.getStrategy();
  }

  @Override
  public Day startDay() {
    return planner.startDay();
  }
}
