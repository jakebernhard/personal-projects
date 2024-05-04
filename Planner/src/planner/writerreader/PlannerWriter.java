package planner.writerreader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import planner.plannermodel.IEvent;
import planner.plannermodel.ISchedule;
import planner.plannermodel.NUPlanner;


/**
 * Represents a file write for a planner.
 */
public class PlannerWriter {


  /**
   * Writes a planner to a file.
   * @param planner input planner
   */
  public void writeAllFiles(NUPlanner planner, String directory) {
    for (ISchedule s : planner.getCopySchedules()) {
      writeFile(s, directory);
    }
  }

  /**
   * Writes the file with account of the new changes using the userEvents class.
   */
  private void writeFile(ISchedule s, String directory) {
    String fileName = directory + File.separator + s.getName() + "-sched.xml";
    try {
      Writer file = new FileWriter(fileName);
      file.write("<?xml version=\"1.0\"?>\n");
      file.write("<schedule id=\"" + s.getName() + "\">");
      for (IEvent e : s.copyEventList()) {
        writeEvent(e,file);
      }
      file.write("</schedule>");
      file.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  /**
   * Writes the xml for a single event.
   * @param event the event to be written.
   * @param file the file to be written to.
   */
  private void writeEvent(IEvent event, Writer file) {
    String newEvent = "<event>\n" +
            "                <name>" + event.getName() + "</name>\n" +
            "                <time>\n" +
            "                        <start-day>" + event.getStartDay().toString()
            + "</start-day>\n" +
            "                        <start>" + event.getStartTime() + "</start>\n" +
            "                        <end-day>" + event.getEndDay().toString() + "</end-day>\n" +
            "                        <end>" + event.getEndTime() + "</end>\n" +
            "                </time>\n" +
            "                <location>\n" +
            "                        <online>" + event.isOnline() + "</online>\n" +
            "                        <place>" + event.getPlace() + "</place>\n" +
            "                </location>\n" +
            "                <users>\n" +
            parseUsers(event) +
            "                </users>\n" +
            "        </event>";

    try {
      file.write(newEvent);
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }


  /**
   * Parses the users in xml for writing in files.
   * @return a string of the users in xml.
   */
  private String parseUsers(IEvent e) {
    String start = "";
    String buffer = "                        ";
    for (String user: e.getUsers()) {
      start += buffer + "<uid>" + user + "</uid>\n";
    }
    return start;
  }
}
