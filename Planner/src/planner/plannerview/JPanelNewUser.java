package planner.plannerview;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * CLass for representing a new user panel.
 */
public class JPanelNewUser extends JPanel {

  private JTextField nameText;

  private List<Feature> features;


  /**
   * Constructor.
   */
  public JPanelNewUser() {

    JLabel name = new JLabel("Name: ");
    this.add(name);

    nameText = new JTextField(20);
    this.add(nameText);

    features = new ArrayList<>();
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 400x400 pixels.
   * @return  Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(300, 100);
  }

  /**
   * Add features.
   * @param feature the feature to be added.
   */
  public void addFeatures(Feature feature) {
    features.add(feature);
  }

  /**
   * Returns the name entered in the name text box.
   * @return the written text in the name box.
   */
  public String getNewUserName() {
    return nameText.getText();
  }

}
