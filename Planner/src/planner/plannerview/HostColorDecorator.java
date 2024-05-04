package planner.plannerview;

import java.awt.Graphics;
import java.awt.Color;

import planner.plannermodel.IEvent;

/**
 * A class for representing a decorator for a toggle color button.
 */
public class HostColorDecorator {

  private final Color hostColor = Color.CYAN;
  private final Color eventColor = Color.RED;

  /**
   * Changes the color of the event if the user is the host when the toggle is enabled.
   * @param e the event being drawn.
   * @param user the user checking to see if they are host.
   * @param graphics the graphic being modified.
   * @param enable boolean value to determine if it is enabled or not.
   */
  public void decorate(IEvent e, String user, Graphics graphics, boolean enable) {
    if (e.getHost().equals(user) && enable) {
      graphics.setColor(hostColor);
    } else {
      graphics.setColor(eventColor);
    }
  }

}
