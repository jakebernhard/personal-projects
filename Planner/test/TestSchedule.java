import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import planner.plannermodel.Day;
import planner.plannermodel.Event;
import planner.plannermodel.IEvent;
import planner.plannermodel.ISchedule;
import planner.plannermodel.Schedule;
import planner.plannermodel.Time;

public class TestSchedule {

  ISchedule mock = new Schedule("Mock", true);

  IEvent guitar;

  IEvent fun;

  IEvent sleep;

  IEvent anotherEvent;

  public void init() {
    guitar = new Event("Guitar Playing", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    fun = new Event("Fun", Day.THURSDAY, Day.FRIDAY,
            new Time(13,0), new Time(1, 0), true, "Outside");
    sleep = new Event("Sleep", Day.SUNDAY, Day.SUNDAY,
            new Time(0,0), new Time(6, 0), false, "Bed");
    anotherEvent = new Event("More", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(11,0), new Time(12, 0), false, "Here");

    mock.add(guitar);
    mock.add(fun);
  }

  // tests the functionality of a valid add or remove call.
  @Test
  public void testAddOrRemoveEvent() {
    init();
    Assert.assertFalse(mock.containsEvent(sleep));
    mock.add(sleep);
    Assert.assertTrue(mock.containsEvent(sleep));

    Assert.assertTrue(mock.containsEvent(fun));
    mock.remove(fun);
    Assert.assertFalse(mock.containsEvent(fun));
  }


  // tests that invalid calls to add or remove throws.
  @Test
  public void testAddOrRemoveErrors() {
    init();

    // tests that attempting to remove an event not in the schedule throws.
    Assert.assertThrows(IllegalStateException.class, () -> mock.remove(anotherEvent));
    Assert.assertThrows(IllegalStateException.class, () -> mock.remove(sleep));

    // tests that you cannot add the same event twice.
    Assert.assertThrows(IllegalStateException.class, () -> mock.add(guitar));
    Assert.assertThrows(IllegalStateException.class, () -> mock.add(fun));

    // tests you cannot add an event with a time overlap.
    Assert.assertThrows(IllegalStateException.class, () -> mock.add(anotherEvent));
  }


  // tests the functionality of the modify method.
  @Test
  public void testModify() {
    init();
    Assert.assertFalse(guitar.equals(fun));
    mock.modify(guitar, fun);
    Assert.assertTrue(guitar.equals(fun));
  }


  // tests the checkModifiedEvent, availableAtTime,  and validateEvent methods.
  @Test
  public void testValidatingMethods() {
    init();
    mock.add(sleep);

    // test for checkModifiedEvent().
    Assert.assertFalse(mock.checkModifiedEvent(anotherEvent, fun));
    Assert.assertFalse(mock.checkModifiedEvent(anotherEvent, sleep));
    Assert.assertTrue(mock.checkModifiedEvent(anotherEvent, guitar));

    // test for validateEvent().
    Assert.assertFalse(mock.validateEvent(anotherEvent));
    mock.remove(guitar);
    Assert.assertTrue(mock.validateEvent(anotherEvent));

    // test for availableAtTime().
    mock.add(guitar);
    Assert.assertTrue(mock.availableAtTime(10000));
    Assert.assertTrue(mock.availableAtTime(62100));
    Assert.assertTrue(mock.availableAtTime(600));
    Assert.assertFalse(mock.availableAtTime(300));
    Assert.assertFalse(mock.availableAtTime(31100));
  }


  // tests getName and copyEventList.
  @Test
  public void testGettersAndCopy() {
    init();
    Assert.assertEquals(mock.getName(), "Mock");
    Assert.assertEquals(mock.copyEventList(), new ArrayList<>(Arrays.asList(guitar,fun)));
  }
}
