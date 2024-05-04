import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import planner.plannermodel.NUPlanner;
import planner.plannermodel.PlannerModel;
import planner.plannermodel.PlannerModelSaturday;
import planner.strategy.AnyTimeStrategy;
import planner.strategy.Strategy;
import planner.strategy.WorkHoursStrategy;

public class TestStrategy {
  NUPlanner model;

  NUPlanner modelSaturday;


  public void init() {
    List<String> xmlDocs = new ArrayList<>();
    xmlDocs.add("XMLs/Jake-sched.xml");
    xmlDocs.add("XMLs/Dylan-sched.xml");
    model = new PlannerModel(xmlDocs, new WorkHoursStrategy(true));
    modelSaturday = new PlannerModelSaturday(xmlDocs, new WorkHoursStrategy(false));

  }

  // tests functionality of the anytime strategy on a sunday start day.
  @Test
  public void testAnytimeSunday() {
    init();
    Strategy anytime = new AnyTimeStrategy();
    Assert.assertEquals(anytime.findFirstAvailableTime(120, model), 200);
    Assert.assertEquals(anytime.findFirstAvailableTime(1800, model), 21130);
    Assert.assertEquals(anytime.findFirstAvailableTime(3600, model), 51335);
    Assert.assertEquals(anytime.findFirstAvailableTime(1710, model), 200);

    Assert.assertEquals(anytime.findFirstEndTime(120, model), 400);
    Assert.assertEquals(anytime.findFirstEndTime(1800, model), 31730);
    Assert.assertEquals(anytime.findFirstEndTime(4320, model), 11335);
    Assert.assertEquals(anytime.findFirstEndTime(1710, model), 10630);
  }

  // tests functionality of the work hours strategy on a sunday start day.
  @Test
  public void testWorkHoursSunday() {
    init();
    Strategy workHoursStrategy = new WorkHoursStrategy(true);
    Assert.assertEquals(workHoursStrategy.findFirstAvailableTime(120, model), 21130);
    Assert.assertEquals(workHoursStrategy.findFirstAvailableTime(360, model), 30900);
    Assert.assertEquals(workHoursStrategy.findFirstAvailableTime(480, model), 30900);
    Assert.assertThrows(IllegalStateException.class,
            () -> workHoursStrategy.findFirstAvailableTime(481, model));
    Assert.assertThrows(IllegalStateException.class,
            () -> workHoursStrategy.findFirstAvailableTime(560, model));

    Assert.assertEquals(workHoursStrategy.findFirstEndTime(120, model), 21330);
    Assert.assertEquals(workHoursStrategy.findFirstEndTime(360, model), 31500);
    Assert.assertEquals(workHoursStrategy.findFirstEndTime(480, model), 31700);
    Assert.assertThrows(IllegalStateException.class,
            () -> workHoursStrategy.findFirstEndTime(481, model));
    Assert.assertThrows(IllegalStateException.class,
            () -> workHoursStrategy.findFirstEndTime(560, model));
  }

  // tests functionality of the anytime strategy on a saturday start day.
  @Test
  public void testAnytimeSaturday() {
    init();
    Strategy anytime = new AnyTimeStrategy();
    Assert.assertEquals(anytime.findFirstAvailableTime(120, modelSaturday), 0);
    Assert.assertEquals(anytime.findFirstAvailableTime(1800, modelSaturday), 31130);
    Assert.assertEquals(anytime.findFirstAvailableTime(1710, modelSaturday), 10200);
    Assert.assertEquals(anytime.findFirstAvailableTime(3600, modelSaturday), 61335);

    Assert.assertEquals(anytime.findFirstEndTime(120, modelSaturday), 200);
    Assert.assertEquals(anytime.findFirstEndTime(1800, modelSaturday), 41730);
    Assert.assertEquals(anytime.findFirstEndTime(4320, modelSaturday), 21335);
    Assert.assertEquals(anytime.findFirstEndTime(1710, modelSaturday), 20630);
  }

  // tests functionality of the work hours strategy on a saturday start day.
  @Test
  public void testWorkHoursSaturday() {
    init();
    Strategy workHoursStrategy = new WorkHoursStrategy(false);
    Assert.assertEquals(workHoursStrategy.findFirstAvailableTime(120, modelSaturday),
            31130);
    Assert.assertEquals(workHoursStrategy.findFirstAvailableTime(360, modelSaturday),
            40900);
    Assert.assertEquals(workHoursStrategy.findFirstAvailableTime(480, modelSaturday),
            40900);
    Assert.assertThrows(IllegalStateException.class,
            () -> workHoursStrategy.findFirstAvailableTime(481, modelSaturday));
    Assert.assertThrows(IllegalStateException.class,
            () -> workHoursStrategy.findFirstAvailableTime(560, modelSaturday));

    Assert.assertEquals(workHoursStrategy.findFirstEndTime(120, modelSaturday),
            31330);
    Assert.assertEquals(workHoursStrategy.findFirstEndTime(360, modelSaturday),
            41500);
    Assert.assertEquals(workHoursStrategy.findFirstEndTime(480, modelSaturday)
            , 41700);
    Assert.assertThrows(IllegalStateException.class,
            () -> workHoursStrategy.findFirstEndTime(481, modelSaturday));
    Assert.assertThrows(IllegalStateException.class,
            () -> workHoursStrategy.findFirstEndTime(560, modelSaturday));
  }

}
