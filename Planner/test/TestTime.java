import org.junit.Assert;
import org.junit.Test;

import planner.plannermodel.Time;

public class TestTime {

  private Time nineThirty;
  private Time sixFortyFive;
  private Time twentyOne;

  public void init() {
    nineThirty = new Time(9, 30);
    sixFortyFive = new Time(6,45);
    twentyOne = new Time(21, 0);
  }

  // Tests invalid arguments throw errors in constructor.
  @Test
  public void testConstructorErrors() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new Time(-5, 40));
    Assert.assertThrows(IllegalArgumentException.class, () -> new Time(50, 40));
    Assert.assertThrows(IllegalArgumentException.class, () -> new Time(24, 40));
    Assert.assertThrows(IllegalArgumentException.class, () -> new Time(5, 61));
    Assert.assertThrows(IllegalArgumentException.class, () -> new Time(8, -10));
    Assert.assertThrows(IllegalArgumentException.class, () -> new Time(8, 100));
  }

  // Tests the functionality of toInt().
  @Test
  public void testToInt() {
    init();
    Assert.assertEquals(nineThirty.toInt(), 930);
    Assert.assertEquals(sixFortyFive.toInt(), 645);
    Assert.assertEquals(twentyOne.toInt(), 2100);
  }

  // Tests functionality of toString.
  @Test
  public void testToString() {
    init();
    Assert.assertEquals(nineThirty.toString(), "930");
    Assert.assertEquals(sixFortyFive.toString(), "645");
    Assert.assertEquals(twentyOne.toString(), "2100");
  }

  // Tests functionality of equals.
  @Test
  public void testEquals() {
    init();
    Time nineThirtyAgain = new Time(9, 30);
    Assert.assertTrue(nineThirty.equals(nineThirtyAgain));
    Assert.assertFalse(sixFortyFive.equals(nineThirtyAgain));
    Assert.assertFalse(twentyOne.equals(nineThirtyAgain));
  }

  // Tests functionality of toTime static method.
  @Test
  public void testToTime() {
    init();
    String nineThirtyString = "930";
    String sixFortyFiveString = "645";
    String twentyOneString = "2100";
    Assert.assertEquals(Time.toTime(nineThirtyString), nineThirty);
    Assert.assertEquals(Time.toTime(sixFortyFiveString), sixFortyFive);
    Assert.assertEquals(Time.toTime(twentyOneString), twentyOne);
  }

  // Tests functionality of timeFromTotalTime static method.
  @Test
  public void testTimeFromTotalTime() {
    init();
    Assert.assertEquals(Time.timeFromTotalTime(60930), nineThirty);
    Assert.assertEquals(Time.timeFromTotalTime(20645), sixFortyFive);
    Assert.assertEquals(Time.timeFromTotalTime(42100), twentyOne);
  }




}
