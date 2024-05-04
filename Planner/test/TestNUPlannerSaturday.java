import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import planner.plannermodel.Day;
import planner.plannermodel.Event;
import planner.plannermodel.IEvent;
import planner.plannermodel.ISchedule;
import planner.plannermodel.NUPlanner;
import planner.plannermodel.PlannerModel;
import planner.plannermodel.PlannerModelSaturday;
import planner.plannermodel.Time;
import planner.strategy.WorkHoursStrategy;

/**
 * Test the saturday model.
 */
public class TestNUPlannerSaturday {

  // tests the base constructor
  @Test
  public void testBaseConstructor() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    Assert.assertEquals(model.getUserList().size(), 0);
    Assert.assertEquals(model.getCopySchedules().size(), 0);
  }

  // tests the xml constructor and that it contains the correct events.
  @Test
  public void testXMLConstructor() {
    List<String> xmlDocs = new ArrayList<>();
    xmlDocs.add("XMLs/Jake-sched.xml");
    xmlDocs.add("XMLs/Dylan-sched.xml");
    NUPlanner model = new PlannerModelSaturday(xmlDocs, new WorkHoursStrategy(false));
    Assert.assertEquals(model.getUserList().size(), 2);
    Assert.assertEquals(model.getCopySchedules().size(), 2);
    Assert.assertTrue(model.getUserList().contains("Jake"));
    Assert.assertTrue(model.getUserList().contains("Dylan"));
    ISchedule schedule = model.getScheduleByUser("Dylan");
    Assert.assertEquals(schedule.copyEventList().size(), 2);
  }

  // tests a valid call to addSchedule.
  @Test
  public void testAddSchedule() {
    NUPlanner model = new PlannerModel(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    Assert.assertEquals(model.getUserList().size(), 1);
    Assert.assertEquals(model.getCopySchedules().size(), 1);
    Assert.assertEquals(model.getUserList().get(0), "Mock");
  }

  // tests tests a valid call to addEvent.
  @Test
  public void testAddEventCorrect() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    model.addSchedule("Second");
    IEvent event = new Event("Guitar", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    IEvent event2 = new Event("Fun", Day.THURSDAY, Day.FRIDAY,
            new Time(9,0), new Time(12, 0), false, "Out");
    IEvent event3 = new Event("Sleep", Day.SUNDAY, Day.SUNDAY,
            new Time(0,0), new Time(6, 0), false, "Bed");
    model.add(event, "Mock");
    model.add(event2, "Second");
    model.add(event3, "Mock");
    ISchedule schedule = model.getScheduleByUser("Mock");
    Assert.assertTrue(schedule.containsEvent(event));
    Assert.assertTrue(schedule.containsEvent(event3));
    Assert.assertFalse(schedule.containsEvent(event2));
    ISchedule schedule2 = model.getScheduleByUser("Second");
    Assert.assertFalse(schedule2.containsEvent(event));
    Assert.assertFalse(schedule2.containsEvent(event3));
    Assert.assertTrue(schedule2.containsEvent(event2));
  }

  // tests getScheduleByUser functionality.
  @Test
  public void testGetScheduleByUser() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    model.addSchedule("Second");
    IEvent event = new Event("Guitar", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    IEvent event2 = new Event("Fun", Day.THURSDAY, Day.FRIDAY,
            new Time(9,0), new Time(12, 0), false, "Out");
    IEvent event3 = new Event("Sleep", Day.SUNDAY, Day.SUNDAY,
            new Time(0,0), new Time(6, 0), false, "Bed");
    model.add(event, "Mock");
    model.add(event2, "Second");
    model.add(event3, "Mock");
    ISchedule schedule = model.getScheduleByUser("Mock");
    Assert.assertEquals(schedule.getName(), "Mock");
    Assert.assertEquals(schedule.copyEventList().size(), 2);
  }

  // test a valid call to removeEvent when caller is host.
  @Test
  public void testRemoveEventHostCorrect() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    model.addSchedule("Second");
    IEvent event = new Event("Guitar", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    model.add(event, "Mock");
    model.add(event, "Second");
    ISchedule schedule = model.getScheduleByUser("Mock");
    Assert.assertTrue(schedule.containsEvent(event));
    ISchedule schedule2 = model.getScheduleByUser("Second");
    Assert.assertTrue(schedule2.containsEvent(event));
    model.remove(event, "Mock");
    schedule = model.getScheduleByUser("Mock");
    Assert.assertFalse(schedule.containsEvent(event));
    Assert.assertFalse(schedule2.containsEvent(event));
    Assert.assertEquals(schedule2.copyEventList().size(), 0);
  }

  // test a valid call to removeEvent when caller is not host.
  @Test
  public void testRemoveEventGuestCorrect() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    model.addSchedule("Second");
    IEvent event = new Event("Guitar", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    model.add(event, "Mock");
    model.add(event, "Second");
    ISchedule schedule = model.getScheduleByUser("Mock");
    Assert.assertTrue(schedule.containsEvent(event));
    ISchedule schedule2 = model.getScheduleByUser("Second");
    Assert.assertTrue(schedule2.containsEvent(event));
    model.remove(event, "Second");
    schedule = model.getScheduleByUser("Mock");
    schedule2 = model.getScheduleByUser("Second");
    Assert.assertTrue(schedule.containsEvent(event));
    Assert.assertFalse(schedule2.containsEvent(event));
    Assert.assertEquals(schedule.copyEventList().size(), 1);
  }

  // tests an invalid call to addEvent.
  @Test
  public void testAddEventErrors() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    model.addSchedule("Second");
    IEvent event = new Event("Guitar", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    IEvent event2 = new Event("Dance", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(11,0), new Time(15, 0), false, "Desk");
    model.add(event, "Mock");
    model.add(event, "Second");
    Assert.assertThrows(IllegalArgumentException.class, () -> model.add(event, "Bobby"));
    Assert.assertThrows(IllegalStateException.class, () -> model.add(event, "Mock"));
    Assert.assertThrows(IllegalStateException.class, () -> model.add(event2, "Mock"));
  }

  // tests an invalid call to addUser.
  @Test
  public void testAddUserErrors() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    model.addSchedule("Second");
    Assert.assertThrows(IllegalArgumentException.class, () -> model.addSchedule(""));
    Assert.assertThrows(IllegalStateException.class, () -> model.addSchedule("Mock"));
  }

  // tests an invalid call to removeEvent.
  @Test
  public void testRemoveEventErrors() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    model.addSchedule("Second");
    IEvent event = new Event("Guitar", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    IEvent event2 = new Event("Fun", Day.THURSDAY, Day.FRIDAY,
            new Time(9,0), new Time(12, 0), false, "Out");
    IEvent event3 = new Event("Sleep", Day.SUNDAY, Day.SUNDAY,
            new Time(0,0), new Time(6, 0), false, "Bed");
    model.add(event, "Mock");
    model.add(event, "Second");
    model.add(event3, "Mock");
    Assert.assertThrows(IllegalArgumentException.class, () -> model.remove(event,"Hi"));
    Assert.assertThrows(IllegalStateException.class, () -> model.remove(event2, "Mock"));
  }

  // tests a valid call ot modifyEvent.
  @Test
  public void testModifyEventCorrect() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    model.addSchedule("Second");
    IEvent event = new Event("Guitar", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    IEvent event2 = new Event("Fun", Day.THURSDAY, Day.FRIDAY,
            new Time(9,0), new Time(12, 0), false, "Out");
    model.add(event, "Mock");
    model.add(event, "Second");
    ISchedule schedule = model.getScheduleByUser("Mock");
    ISchedule schedule2 = model.getScheduleByUser("Second");
    Assert.assertEquals(schedule.copyEventList().get(0).getName(), "Guitar");
    Assert.assertEquals(schedule2.copyEventList().get(0).getName(), "Guitar");
    model.modify(event, event2, "Mock");
    schedule = model.getScheduleByUser("Mock");
    schedule2 = model.getScheduleByUser("Second");
    Assert.assertEquals(schedule.copyEventList().get(0).getName(), "Fun");
    Assert.assertEquals(schedule2.copyEventList().get(0).getName(), "Fun");
  }

  // tests an invalid call to modifyEvent.
  @Test
  public void testModifyEventErrors() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    model.addSchedule("Second");
    IEvent event = new Event("Guitar", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    IEvent event2 = new Event("Fun", Day.THURSDAY, Day.FRIDAY,
            new Time(9,0), new Time(12, 0), false, "Out");
    IEvent event3 = new Event("SameTime", Day.THURSDAY, Day.FRIDAY,
            new Time(9,0), new Time(11, 0), false, "Out");
    model.add(event, "Mock");
    model.add(event, "Second");
    model.add(event2, "Mock");
    ISchedule schedule = model.getScheduleByUser("Mock");
    ISchedule schedule2 = model.getScheduleByUser("Second");
    Assert.assertThrows(IllegalStateException.class, () -> model.modify(event, event3, "Mock"));
    Assert.assertThrows(IllegalStateException.class, () -> model.modify(event, event3, "Second"));
  }

  // tests the first day of the planner is correct.
  @Test
  public void testValidateStartDays() {
    NUPlanner model = new PlannerModelSaturday(new WorkHoursStrategy(false));
    model.addSchedule("Mock");
    model.addSchedule("Second");
    IEvent event = new Event("Guitar", Day.WEDNESDAY, Day.WEDNESDAY,
            new Time(9,0), new Time(12, 0), false, "Desk");
    IEvent event2 = new Event("Fun", Day.THURSDAY, Day.FRIDAY,
            new Time(9,0), new Time(12, 0), false, "Out");
    model.add(event, "Mock");
    model.add(event, "Second");
    model.add(event2, "Mock");
    Assert.assertEquals(model.startDay(), Day.SATURDAY);
  }

}

