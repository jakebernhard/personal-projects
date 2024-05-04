package cs3500.samegame.view;

import java.io.IOException;

import cs3500.samegame.model.hw02.SameGameModel;

/**
 * A class for representing a textual view of a samegame game.
 * @param <T> the type of gamepiece which will be in the game.
 */
public class SameGameTextView<T> implements SameGameView<T> {

  Appendable ap;
  private final SameGameModel<T> model;


  public SameGameTextView(SameGameModel<T> model) {
    this.model = model;
  }

  public SameGameTextView(Appendable ap, SameGameModel<T> model) {
    this.ap = ap;
    this.model = model;
  }



  @Override
  public String toString() {
    String result = "";
    for (int r = 0; r < this.model.width(); r++) {
      for (int c = 0; c < this.model.length(); c++) {
        if (this.model.pieceAt(r,c) == null) {
          result += "X";
          result += " ";
        } else {
          result += this.model.pieceAt(r,c).toString();
          result += " ";
        }
      }
      result = result.trim();
      result += "\n";
    }
    return result.trim();
  }

  @Override
  public void render() throws IOException {
    ap.append(toString());
  }
}
