package cs3500.samegame.model.hw02;

import java.util.Objects;

/**
 * A class for representing a game piece for a game of same game. Every piece has a color that
 * can either be red, blue, yellow, green, or none.
 */
public class GamePiece {

  /**
   * An enum representing the color of a gamepiece.
   */
  public enum Color {

    RED, GREEN, BLUE, YELLOW
  }

  private Color color;

  /**
   * Constructor for a game piece.
   * @param c the color.
   */
  public GamePiece(Color c) {
    this.color = c;
  }


  /**
   * Returns a one character result of the game piece based on the color.
   * @return a string representing the game piece.
   */
  @Override
  public String toString() {
    switch (this.color) {
      case RED:
        return "R";
      case GREEN:
        return "G";
      case BLUE:
        return "B";
      case YELLOW:
        return "Y";
      default:
        return "X";
    }
  }


  /**
   * Checks if two objects are equal.
   * @param o The object the game piece is being compared to.
   * @return true if the objects are both game piece and have the same color.
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    GamePiece that = (GamePiece) o;
    return that.color == this.color;
  }

  /**
   * Creates a hash based on the color of the game piece.
   * @return a hash based on the color.
   */
  @Override
  public int hashCode() {
    return Objects.hash(color);
  }
}
