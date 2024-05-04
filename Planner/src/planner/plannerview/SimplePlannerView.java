package planner.plannerview;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


import planner.plannermodel.IEvent;
import planner.plannermodel.ReadOnlyNUPlanner;

/**
 * Represents a PlannerView.
 */
public class SimplePlannerView extends JFrame implements PlannerGUI {
  private final JPanelPlanner panel;

  private final ReadOnlyNUPlanner model;

  private List<Feature> features;

  /**
   * Constructs a SimplePlannerView.
   * @param model input model
   */
  public SimplePlannerView(ReadOnlyNUPlanner model) {
    this.model = model;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.features = new ArrayList<>();
    this.panel = new JPanelPlanner(model);
    this.add(panel);
    this.pack();
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void addFeatures(Feature feature) {
    features.add(feature);
    this.panel.addFeatures(feature);
  }

  @Override
  public void emptyEventWindow() {
    EventFrame event = new EventFrame(new JPanelEvent(panel.getSelectedUser()));
    for (Feature feature : features) {
      event.addFeatures(feature);
    }
    event.setVisible(true);
  }

  @Override
  public void openScheduleWindow() {
    ScheduleFrame scheduleFrame =
            new ScheduleFrame(new JPanelSchedule(model));
    for (Feature feature : features) {
      scheduleFrame.addFeatures(feature);
    }
    scheduleFrame.setVisible(true);
  }

  @Override
  public void populatedEventWindow(IEvent e) {
    EventFrame event = new EventFrame(new JPanelEvent(panel.getSelectedUser(), e));
    for (Feature feature : features) {
      event.addFeatures(feature);
    }
    event.setVisible(true);
  }

  @Override
  public void fileWindow() {
    JFileChooser fileWindow = new JFileChooser();
    fileWindow.setVisible(true);
    JFrame frame = placeOnFrame(fileWindow, "File Window");
    addFileBehavior(fileWindow, frame);
    cancelButtonBehavior(fileWindow, frame);
  }

  @Override
  public void directoryWindow() {
    JFileChooser fileSaveWindow = new JFileChooser();
    fileSaveWindow.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileSaveWindow.setVisible(true);
    JFrame frame = placeOnFrame(fileSaveWindow, "File Window");
    savesFilesBehavior(fileSaveWindow, frame);
    cancelButtonBehavior(fileSaveWindow, frame);
  }

  @Override
  public void refreshPanelWindow() {
    this.panel.refreshSchedule();
    this.panel.repaint();
  }

  @Override
  public void toggleHostColor() {
    this.panel.toggleHostVisibility();
    this.panel.repaint();
  }

  @Override
  public void openNewUserWindow() {
    NewUserFrame newUserFrame = new NewUserFrame(new JPanelNewUser());
    for (Feature feature : features) {
      newUserFrame.addFeatures(feature);
    }
    newUserFrame.setVisible(true);
  }


  @Override
  public String getCurrentUser() {
    return panel.getSelectedUser().toString();
  }

  @Override
  public void errorFrame(String message) {
    JOptionPane.showMessageDialog(null, message, "Error Window", JOptionPane.INFORMATION_MESSAGE );
  }

  @Override
  public void refreshUserBox() {
    this.panel.updateUserBox();
  }

  private JFrame placeOnFrame(Component component, String windowName) {
    JFrame frame = new JFrame(windowName);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().add(component);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    return frame;
  }

  private void addFileBehavior(JFileChooser fileChooser, JFrame frame) {
    ActionListener openButtonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
          for (Feature feature : features) {
            feature.addFile(fileChooser.getSelectedFile().getAbsolutePath().toString());
            frame.dispose();
          }
        }
      }
    };
    fileChooser.addActionListener(openButtonListener);
  }

  private void savesFilesBehavior(JFileChooser fileChooser, JFrame frame) {
    ActionListener openButtonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
          String directory = fileChooser.getSelectedFile().getAbsolutePath().toString();
          for (Feature feature : features) {
            feature.saveFiles(directory);
            frame.dispose();
          }
        }
      }
    };
    fileChooser.addActionListener(openButtonListener);
  }

  private void cancelButtonBehavior(JFileChooser fileChooser, JFrame frame) {
    ActionListener cancelButtonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
          frame.dispose();
        }
      }
    };
    fileChooser.addActionListener(cancelButtonListener);
  }

}
