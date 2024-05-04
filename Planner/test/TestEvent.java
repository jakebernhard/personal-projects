import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import planner.plannermodel.Day;
import planner.plannermodel.Event;
import planner.plannermodel.IEvent;
import planner.plannermodel.Time;

public class TestEvent {

  IEvent guitar;

  IEvent fun;

  IEvent sleep;

  public void init() {
    guitar = new Event("Guitar Playing", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    fun = new Event("Fun", Day.THURSDAY, Day.FRIDAY,
            new Time(13,0), new Time(1, 0), true, "Outside");
    sleep = new Event("Sleep", Day.SUNDAY, Day.SUNDAY,
            new Time(0,0), new Time(6, 0), false, "Bed");
    guitar.addUser("Dylan");
    fun.addUser("Dylan");
    fun.addUser("Jake");
    sleep.addUser("Jake");
  }


  // tests the functionality of the getters.
  @Test
  public void testGetters() {
    init();
    Assert.assertEquals(guitar.getName(), "Guitar Playing");
    Assert.assertEquals(fun.getName(), "Fun");
    Assert.assertEquals(sleep.getName(), "Sleep");

    Assert.assertEquals(guitar.getStartDay(), Day.WEDNESDAY);
    Assert.assertEquals(fun.getStartDay(), Day.THURSDAY);
    Assert.assertEquals(sleep.getStartDay(), Day.SUNDAY);

    Assert.assertEquals(guitar.getEndDay(), Day.WEDNESDAY);
    Assert.assertEquals(fun.getEndDay(), Day.FRIDAY);
    Assert.assertEquals(sleep.getEndDay(), Day.SUNDAY);

    Assert.assertEquals(guitar.getStartTime(), new Time(9,0));
    Assert.assertEquals(fun.getStartTime(), new Time(13,0));
    Assert.assertEquals(sleep.getStartTime(), new Time(0,0));

    Assert.assertEquals(guitar.getEndTime(), new Time(12,0));
    Assert.assertEquals(fun.getEndTime(), new Time(1,0));
    Assert.assertEquals(sleep.getEndTime(), new Time(6,0));

    Assert.assertFalse(guitar.isOnline());
    Assert.assertTrue(fun.isOnline());
    Assert.assertFalse(sleep.isOnline());

    Assert.assertEquals(guitar.getPlace(), "Desk");
    Assert.assertEquals(fun.getPlace(), "Outside");
    Assert.assertEquals(sleep.getPlace(), "Bed");

    Assert.assertEquals(guitar.getUsers(), new ArrayList<>(Arrays.asList("Dylan")));
    Assert.assertEquals(fun.getUsers(), new ArrayList<>(Arrays.asList("Dylan", "Jake")));
    Assert.assertEquals(sleep.getUsers(), new ArrayList<>(Arrays.asList("Jake")));
  }

  // tests the functionality of the setters.
  @Test
  public void testSetters() {
    init();
    Assert.assertEquals(guitar.getName(), "Guitar Playing");
    guitar.setName("Nah");
    Assert.assertEquals(guitar.getName(), "Nah");

    Assert.assertEquals(guitar.getStartDay(), Day.WEDNESDAY);
    guitar.setStartDay(Day.MONDAY);
    Assert.assertEquals(guitar.getStartDay(), Day.MONDAY);

    Assert.assertEquals(guitar.getEndDay(), Day.WEDNESDAY);
    guitar.setEndDay(Day.TUESDAY);
    Assert.assertEquals(guitar.getEndDay(), Day.TUESDAY);

    Assert.assertEquals(guitar.getStartTime(), new Time(9,0));
    guitar.setStartTime(new Time(8, 40));
    Assert.assertEquals(guitar.getStartTime(), new Time(8,40));

    Assert.assertEquals(guitar.getEndTime(), new Time(12,0));
    guitar.setEndTime(new Time(13,35));
    Assert.assertEquals(guitar.getEndTime(), new Time(13,35));

    Assert.assertFalse(guitar.isOnline());
    guitar.setOnline(true);
    Assert.assertTrue(guitar.isOnline());

    Assert.assertEquals(guitar.getPlace(), "Desk");
    guitar.setPlace("Concert");
    Assert.assertEquals(guitar.getPlace(), "Concert");
  }

  // tests the functionality of the users list.
  @Test
  public void testUserFunctionality() {
    init();

    // checks that the host is the first one to join the event.
    Assert.assertEquals(guitar.getHost(), "Dylan");
    Assert.assertEquals(fun.getHost(), "Dylan");
    Assert.assertEquals(sleep.getHost(), "Jake");

    // checks that add user functionality
    Assert.assertFalse(guitar.getUsers().contains("Jake"));
    guitar.addUser("Jake");
    Assert.assertTrue(guitar.getUsers().contains("Jake"));

    // checks the remove user functionality
    guitar.removeUser("Jake");
    Assert.assertFalse(guitar.getUsers().contains("Jake"));
  }


  // tests the functionality of the equals() method.
  @Test
  public void testEquals() {
    init();
    IEvent guitar2 = new Event("Guitar Playing", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    Assert.assertTrue(guitar2.equals(guitar));
    Assert.assertTrue(guitar2.equals(guitar2));
    Assert.assertFalse(guitar.equals(fun));
    Assert.assertFalse(sleep.equals(fun));
    guitar2.setPlace("Somewhere else");
    Assert.assertFalse(guitar.equals(guitar2));
  }


  // test the functionality of the total time methods.
  @Test
  public void testTotalStartAndEndTime() {
    IEvent event = new Event("Guitar", Day.SATURDAY, Day.SATURDAY,
            new Time(6,0), new Time(12, 0), false, "Desk");
    IEvent event2 = new Event("Fun", Day.THURSDAY, Day.FRIDAY,
            new Time(9,0), new Time(12, 0), false, "Out");
    IEvent event3 = new Event("SameTime", Day.SUNDAY, Day.SUNDAY,
            new Time(0,0), new Time(11, 0), false, "Out");

    Assert.assertEquals(event.totalTime(Day.MONDAY, 930), 10930);
    Assert.assertEquals(event.totalTime(Day.WEDNESDAY, 1130), 31130);
    Assert.assertEquals(event.totalTime(Day.SATURDAY, 645), 60645);
    Assert.assertEquals(event.totalTime(Day.SUNDAY, 0), 0);

    Assert.assertEquals(event3.totalStartTime(true), 0);
    Assert.assertEquals(event2.totalStartTime(true), 40900);
    Assert.assertEquals(event.totalStartTime(true), 60600);
    Assert.assertEquals(event3.totalEndTime(true), 1100);
    Assert.assertEquals(event2.totalEndTime(true), 51200);
    Assert.assertEquals(event.totalEndTime(true), 61200);

    Assert.assertEquals(event3.totalStartTime(false), 10000);
    Assert.assertEquals(event2.totalStartTime(false), 50900);
    Assert.assertEquals(event.totalStartTime(false), 600);
    Assert.assertEquals(event3.totalEndTime(false), 11100);
    Assert.assertEquals(event2.totalEndTime(false), 61200);
    Assert.assertEquals(event.totalEndTime(false), 1200);
  }
}
