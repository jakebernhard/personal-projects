package planner.plannerview;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * Class for representing an event frame.
 */
public class EventFrame extends JFrame {


  private JPanelEvent eventPanel;

  private List<Feature> features;

  private JButton createBox = new JButton("Create event");

  private JButton removeBox = new JButton("Remove event");

  private JButton modify = new JButton("Modify event");

  /**
   * Constructor.
   * @param eventPanel the event panel.
   */
  public EventFrame(JPanelEvent eventPanel) {
    setTitle("Event Window");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.eventPanel = eventPanel;
    this.features = new ArrayList<>();
    this.add(eventPanel);
    addButtons();
    this.pack();
    this.setLocationRelativeTo(null);
    createBox.addActionListener(this::createEventButtonClick);
    removeBox.addActionListener(this::removeEventButtonClick);
    modify.addActionListener(this::modifyEventButtonClick);
  }

  /**
   * Add feature to frame.
   * @param feature the feature to be added.
   */
  public void addFeatures(Feature feature) {
    features.add(feature);
    this.eventPanel.addFeatures(feature);
  }

  private void addButtons() {
    createBox.setBounds(5, 650, 120, 40);
    this.eventPanel.add(createBox);
    modify.setBounds(140, 650, 120, 40);
    this.eventPanel.add(modify);
    removeBox.setBounds(275, 650, 120, 40);
    this.eventPanel.add(removeBox);
  }

  private void createEventButtonClick(ActionEvent e) {
    boolean flag = true;
    for (Feature feature : features) {
      if (this.eventPanel.validateEvent()) {
        this.dispose();
        try {
          this.eventPanel.createEvent();
        } catch (IllegalArgumentException err) {
          feature.openErrorMessage(err.getMessage());
          flag = false;
        }
        if (flag) {
          feature.createNewEvent(this.eventPanel.createEvent());
        }
      } else {
        this.dispose();
        feature.openErrorMessage("All data in the event frame must be filled out.");
      }
    }
  }

  private void removeEventButtonClick(ActionEvent e) {
    for (Feature feature : features) {
      if (this.eventPanel.validateEvent()) {
        this.dispose();
        feature.removeEvent(this.eventPanel.createEvent());
      } else {
        this.dispose();
        feature.openErrorMessage("All data in the event frame must be filled out.");
      }
    }
  }

  private void modifyEventButtonClick(ActionEvent e) {
    boolean flag = true;
    for (Feature feature : features) {
      if (this.eventPanel.validateEvent()) {
        this.dispose();
        try {
          this.eventPanel.createEvent();
        } catch (IllegalArgumentException err) {
          feature.openErrorMessage(err.getMessage());
          flag = false;
        }
        if (flag) {
          feature.modifyEvent(eventPanel.getBaseEvent(), eventPanel.createEvent());
        }
      } else {
        this.dispose();
        feature.openErrorMessage("All data in the event frame must be filled out.");
      }
    }
  }

}
