package planner.plannerview;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Class for representing a frame for entering a new user.
 */
public class NewUserFrame extends JFrame {

  private JPanelNewUser newUserPanel;

  private List<Feature> features;

  private JButton createNewUser;


  /**
   * Constructor.
   * @param newUserPanel the new user panel.
   */
  public NewUserFrame(JPanelNewUser newUserPanel) {
    setTitle("New User Window");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.newUserPanel = newUserPanel;
    this.features = new ArrayList<>();
    createNewUser = new JButton("Create New User");
    createNewUser.setBounds(50,55, 200,30);
    createNewUser.addActionListener(this::createNewUserButtonClick);
    this.add(createNewUser);
    this.add(newUserPanel);
    this.pack();
    this.setLocationRelativeTo(null);
  }

  /**
   * Add feature to frame.
   * @param feature the feature to be added.
   */
  public void addFeatures(Feature feature) {
    features.add(feature);
    this.newUserPanel.addFeatures(feature);
  }

  private void createNewUserButtonClick(ActionEvent e) {
    for (Feature feature : features) {
      dispose();
      feature.createNewUser(newUserPanel.getNewUserName());
    }
  }

}
