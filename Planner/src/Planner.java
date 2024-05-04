import java.util.ArrayList;
import java.util.List;

import planner.plannermodel.NUPlanner;
import planner.plannermodel.PlannerModel;
import planner.plannermodel.PlannerModelSaturday;
import planner.plannerview.PlannerGUI;
import planner.plannerview.SimplePlannerView;
import planner.plannercontroller.PlannerController;
import planner.strategy.AnyTimeStrategy;
import planner.strategy.Strategy;
import planner.strategy.WorkHoursStrategy;

/**
 * Represents a Planner main.
 */
public class Planner {

  /**
   * Rum a program of planner with the given command line arguments.
   * @param args string inputs
   */
  public static void main(String[] args) {
    NUPlanner model;
    String selectedStrategy;
    List<String> xml = new ArrayList<>();
    xml.add("XMLs/Jake-sched.xml");
    xml.add("XMLs/Dylan-sched.xml");
    if (args.length > 0) {
      String strategy = args[0];
      switch (strategy) {
        case "anytime":
          selectedStrategy = "anytime";
          break;
        case "workhours":
          selectedStrategy = "work hours";
          break;
        default:
          throw new IllegalArgumentException("Invalid strategy.");
      }
    } else {
      throw new IllegalArgumentException("No strategy given.");
    }
    if (args.length > 1) {
      String startDay = args[1];
      switch (startDay) {
        case "saturday":
          if (selectedStrategy.equals("anytime")) {
            model = new PlannerModelSaturday(xml, new AnyTimeStrategy());
          } else {
            model = new PlannerModelSaturday(xml, new WorkHoursStrategy(false));
          }
          break;
        case "sunday":
          if (selectedStrategy.equals("anytime")) {
            model = new PlannerModel(xml, new AnyTimeStrategy());
          } else {
            model = new PlannerModel(xml, new WorkHoursStrategy(true));
          }
          break;
        default:
          throw new IllegalArgumentException("Invalid start day.");
      }
    } else {
      if (selectedStrategy.equals("anytime")) {
        model = new PlannerModel(xml, new AnyTimeStrategy());
      } else {
        model = new PlannerModel(xml, new WorkHoursStrategy(true));
      }
    }
    PlannerGUI view = new SimplePlannerView(model);
    PlannerController controller = new PlannerController(model, view);
    controller.run();
  }
}

