package planner.plannerview;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Class for representing a schedule frame.
 */
public class ScheduleFrame extends JFrame {

  private JPanelSchedule schedulePanel;

  private List<Feature> features;

  private JButton scheduleButton = new JButton("Schedule event");

  /**
   * Constructor.
   * @param schedulePanel the schedule panel.
   */
  public ScheduleFrame(JPanelSchedule schedulePanel) {
    setTitle("Schedule Window");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.schedulePanel = schedulePanel;
    this.features = new ArrayList<>();
    this.add(schedulePanel);
    addButtons();
    this.pack();
    this.setLocationRelativeTo(null);
    this.scheduleButton.addActionListener(this::scheduleEventButtonClick);
  }

  /**
   * Adds a feature to the frame.
   * @param feature the feature to be added.
   */
  public void addFeatures(Feature feature) {
    features.add(feature);
    this.schedulePanel.addFeatures(feature);
  }

  private void addButtons() {
    scheduleButton.setBounds(5, 560, 590, 35);
    this.schedulePanel.add(scheduleButton);
  }

  private void scheduleEventButtonClick(ActionEvent e) {
    boolean flag = true;
    for (Feature feature : features) {
      if (this.schedulePanel.validateRequest()) {
        this.dispose();
        try {
          this.schedulePanel.createEvent();
        } catch (IllegalArgumentException | IllegalStateException err) {
          feature.openErrorMessage(err.getMessage());
          flag = false;
        }
        if (flag) {
          feature.schedule(this.schedulePanel.createEvent());
        }
      } else {
        this.dispose();
        feature.openErrorMessage("All data in the event frame must be filled out.");
      }
    }
  }

}
