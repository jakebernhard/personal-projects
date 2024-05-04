import org.junit.Assert;
import org.junit.Test;

import planner.plannermodel.Day;

public class TestDay {

  // Tests the functionality of the toString method.
  @Test
  public void testToString() {
    Assert.assertEquals(Day.SUNDAY.toString(), "Sunday");
    Assert.assertEquals(Day.MONDAY.toString(), "Monday");
    Assert.assertEquals(Day.TUESDAY.toString(), "Tuesday");
    Assert.assertEquals(Day.WEDNESDAY.toString(), "Wednesday");
    Assert.assertEquals(Day.THURSDAY.toString(), "Thursday");
    Assert.assertEquals(Day.FRIDAY.toString(), "Friday");
    Assert.assertEquals(Day.SATURDAY.toString(), "Saturday");
  }

  // Tests the functionality of the static toDay method.
  @Test
  public void testToDay() {
    Assert.assertEquals(Day.toDay("Sunday"), Day.SUNDAY);
    Assert.assertEquals(Day.toDay("Monday"), Day.MONDAY);
    Assert.assertEquals(Day.toDay("Tuesday"), Day.TUESDAY);
    Assert.assertEquals(Day.toDay("Wednesday"), Day.WEDNESDAY);
    Assert.assertEquals(Day.toDay("Thursday"), Day.THURSDAY);
    Assert.assertEquals(Day.toDay("Friday"), Day.FRIDAY);
    Assert.assertEquals(Day.toDay("Saturday"), Day.SATURDAY);
    Assert.assertThrows(IllegalArgumentException.class, () -> Day.toDay("hello"));
    Assert.assertThrows(IllegalArgumentException.class, () -> Day.toDay("blah blah"));
  }

  // Tests the functionality of the static dayFromTotalTime method.
  @Test
  public void testDayFromTotalTime() {
    Assert.assertEquals(Day.dayFromTotalTime(2100), Day.SUNDAY);
    Assert.assertEquals(Day.dayFromTotalTime(10400), Day.MONDAY);
    Assert.assertEquals(Day.dayFromTotalTime(21000), Day.TUESDAY);
    Assert.assertEquals(Day.dayFromTotalTime(32030), Day.WEDNESDAY);
    Assert.assertEquals(Day.dayFromTotalTime(40030), Day.THURSDAY);
    Assert.assertEquals(Day.dayFromTotalTime(50100), Day.FRIDAY);
    Assert.assertEquals(Day.dayFromTotalTime(60745), Day.SATURDAY);
    Assert.assertThrows(IllegalArgumentException.class, () -> Day.dayFromTotalTime(80000));
    Assert.assertThrows(IllegalArgumentException.class, () -> Day.dayFromTotalTime(-10000));
  }

  // Tests the functionality of the static dayFromTotalTimeSaturday method.
  @Test
  public void testDayFromTotalTimeSaturday() {
    Assert.assertEquals(Day.dayFromTotalTimeSaturday(2100), Day.SATURDAY);
    Assert.assertEquals(Day.dayFromTotalTimeSaturday(10400), Day.SUNDAY);
    Assert.assertEquals(Day.dayFromTotalTimeSaturday(21000), Day.MONDAY);
    Assert.assertEquals(Day.dayFromTotalTimeSaturday(32030), Day.TUESDAY);
    Assert.assertEquals(Day.dayFromTotalTimeSaturday(40030), Day.WEDNESDAY);
    Assert.assertEquals(Day.dayFromTotalTimeSaturday(50100), Day.THURSDAY);
    Assert.assertEquals(Day.dayFromTotalTimeSaturday(60745), Day.FRIDAY);
    Assert.assertThrows(IllegalArgumentException.class, () -> Day.dayFromTotalTime(80000));
    Assert.assertThrows(IllegalArgumentException.class, () -> Day.dayFromTotalTime(-10000));
  }

}
