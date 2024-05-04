package planner.writerreader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import planner.plannermodel.Day;
import planner.plannermodel.Event;
import planner.plannermodel.NUPlanner;
import planner.plannermodel.Time;


/**
 * Represents a Reader for a planner.
 */
public class PlannerReader {


  /**
   * Reads a xml file into a planner.
   * @param xml input xml file
   * @param planner planner to be read to
   */
  public void addUser(String xml, NUPlanner planner) {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = builder.parse(xml);
      doc.getDocumentElement().normalize();
      //ensureCorrectFormat(doc);
      Node uid = doc.getElementsByTagName("schedule").item(0);
      String name = uid.getAttributes().item(0).getNodeValue(); //get user's name
      planner.addSchedule(name); //make a user schedule with the name
      NodeList nodeList = uid.getChildNodes(); //get child nodes of events
      for (int i = 0; i < nodeList.getLength(); i++) {
        if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE &&
                nodeList.item(i).getNodeName().equals("event")) {
          planner.add(createEvent(nodeList.item(i).getChildNodes()), name);
        }
      }
    }
    catch (ParserConfigurationException | IOException | SAXException e) {
      throw new IllegalStateException("XML File");
    }
  }

  private void ensureCorrectFormat(Document doc) {
    Node s = doc.getFirstChild();
    if (!s.getNodeName().equals("schedule") ||
            !s.getAttributes().item(0).getNodeName().equals("id")) {
      throw new IllegalStateException("Invalid XML Format: Schedule");
    }
    NodeList firstChildren = s.getChildNodes();
    for (int i = 0; i < firstChildren.getLength(); i++) {
      if (!firstChildren.item(i).getNodeName().equals("event")) {
        throw new IllegalStateException("Invalid XML Format: Event");
      }
      NodeList eventChildrenNodes = firstChildren.item(i).getChildNodes();
      List<String> eventChildren = analyzeNodeList(eventChildrenNodes);
      if (eventChildren.size() != 4 ||
              !eventChildren.get(0).equals("name") ||
              !eventChildren.get(1).equals("time") ||
              !eventChildren.get(2).equals("location") ||
              !eventChildren.get(3).equals("users")) {
        throw new IllegalStateException("Invalid XML Format: Event Children");
      }
      NodeList timeChildren = eventChildrenNodes.item(1).getChildNodes();
      List<String> timeAspects = analyzeNodeList(timeChildren);
      if (timeChildren.getLength() != 4 ||
              !timeChildren.item(0).getNodeName().equals("start-day") ||
              !timeChildren.item(1).getNodeName().equals("start") ||
              !timeChildren.item(2).getNodeName().equals("end-day") ||
              !timeChildren.item(3).getNodeName().equals("end")) {
        throw new IllegalStateException("Invalid XML Format: Time");
      }
      NodeList locationChildren = eventChildrenNodes.item(2).getChildNodes();
      if (locationChildren.getLength() != 2 ||
              !locationChildren.item(0).getNodeName().equals("online") ||
              !locationChildren.item(1).getNodeName().equals("place")) {
        throw new IllegalStateException("Invalid XML Format: Location");
      }
      NodeList userChildren = eventChildrenNodes.item(3).getChildNodes();
      if (userChildren.getLength() < 1) {
        throw new IllegalStateException("Invalid XML Format: No Users");
      }
      for (int j = 0; j < userChildren.getLength(); j++) {
        if (!userChildren.item(j).getNodeName().equals("uid")) {
          throw new IllegalStateException("Invalid XML Format: Users");
        }
      }
    }
  }

  private List<String> analyzeNodeList(NodeList eventChildren) {
    List<String> result = new ArrayList<>();
    for (int j = 0; j < eventChildren.getLength(); j++) {
      Node n = eventChildren.item(j);
      if (n.getNodeType() != Node.TEXT_NODE) {
        result.add(n.getNodeName());
      }
    }
    return result;
  }

  private List<Node> getElementNodes(NodeList nodes) {
    ArrayList<Node> childNodes = new ArrayList();
    for (int i = 0; i < nodes.getLength(); i++) {
      if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
        childNodes.add(nodes.item(i));
      }
    }
    return childNodes;
  }

  private Event createEvent(NodeList nodes) {
    List<Node> childNodes = getElementNodes(nodes);
    String name = childNodes.get(0).getFirstChild().getNodeValue();
    List<Node> timeNodes = getElementNodes(childNodes.get(1).getChildNodes());
    String startDayString = timeNodes.get(0).getFirstChild().getNodeValue();
    int startTimeInt =
            Integer.parseInt(
                    timeNodes.get(1).getFirstChild().getNodeValue());
    String endDayString = timeNodes.get(2).getFirstChild().getNodeValue();
    int endTimeInt =
            Integer.parseInt(
                    timeNodes.get(3).getFirstChild().getNodeValue());
    Time startTime = intToTime(startTimeInt);
    Time endTime = intToTime(endTimeInt);
    Day startDay = toDay(startDayString);
    Day endDay = toDay(endDayString);
    List<Node> locationNodes = getElementNodes(childNodes.get(2).getChildNodes());
    boolean online =
            Boolean.parseBoolean(locationNodes.get(0).getFirstChild().getNodeValue());
    String location = locationNodes.get(1).getFirstChild().getNodeValue();
    List<String> userList = new ArrayList<>();
    List<Node> userNodes = getElementNodes(childNodes.get(3).getChildNodes());
    for (int i = 0; i < userNodes.size(); i++) {
      userList.add(userNodes.get(i).getFirstChild().getNodeValue());
    }
    return new Event(
            name, startDay, endDay, startTime, endTime, online, location, userList);
  }

  private Time intToTime(int num) {
    int hours = num / 100;
    int minutes = num % 100;
    return new Time(hours, minutes);
  }

  private Day toDay(String s) {
    if (s.equals("Sunday")) {
      return Day.SUNDAY;
    }
    if (s.equals("Monday")) {
      return Day.MONDAY;
    }
    if (s.equals("Tuesday")) {
      return Day.TUESDAY;
    }
    if (s.equals("Wednesday")) {
      return Day.WEDNESDAY;
    }
    if (s.equals("Thursday")) {
      return Day.THURSDAY;
    }
    if (s.equals("Friday")) {
      return Day.FRIDAY;
    }
    if (s.equals("Saturday")) {
      return Day.SATURDAY;
    }
    throw new IllegalArgumentException("Invalid string of a day");
  }

}
