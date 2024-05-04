package cs3500.samegame.model.hw04;

import cs3500.samegame.model.hw02.FourPieceSameGame;
import cs3500.samegame.model.hw02.GamePiece;
import cs3500.samegame.model.hw02.SameGameModel;

/**
 * A class for representing a creator of instances of SameGame depending on the specified GameType.
 */
public class SameGameCreator {

  /**
   * A enum for representing the type of SameGame to be played. Options are fourpiece which is
   * the traditional game, gravity, and automatch.
   */
  public enum GameType {
    FOURPIECE, GRAVITY, AUTOMATCH
  }

  /**
   * Static factory method that creates a new instance of a SameGame depending on the specified
   * game type.
   * @param type the game type of the game being instantiated.
   * @return the new game to be played.
   */
  public static SameGameModel<GamePiece> createGame(GameType type) {
    switch (type) {
      case FOURPIECE:
        return new FourPieceSameGame();
      case GRAVITY:
        return new GravitySameGame();
      case AUTOMATCH:
        return new AutoMatchSameGame();
      default:
        throw new IllegalArgumentException("Invalid game type!");
    }
  }

}
