package cs3500.samegame;

import java.io.InputStreamReader;

import cs3500.samegame.controller.SameGameController;
import cs3500.samegame.controller.SameGameTextController;
import cs3500.samegame.model.hw02.GamePiece;
import cs3500.samegame.model.hw02.SameGameModel;
import cs3500.samegame.model.hw04.SameGameCreator;


/**
 * A class for running the program of SameGame, has a main method that accepts command arguments
 * that specify the game to be played.
 */
public final class SameGame {

  /**
   * Main method that runs a game of SameGame depending on the arguments specified. The first
   * argument is the game type while the next three arguments are optional and represent the number
   * of rows, columns, and swaps respectively.
   * @param args the arguments passed into the main method.
   */
  public static void main(String[] args) {
    SameGameModel<GamePiece> model;
    int row = 10;
    int col = 10;
    int swaps = 10;
    boolean playGame = true;
    if (args.length > 0) {
      String type = args[0];
      switch (type) {
        case "fourpiece":
          model = SameGameCreator.createGame(SameGameCreator.GameType.FOURPIECE);
          break;
        case "gravity":
          model = SameGameCreator.createGame(SameGameCreator.GameType.GRAVITY);
          break;
        case "automatch":
          model =  SameGameCreator.createGame(SameGameCreator.GameType.AUTOMATCH);
          break;
        default:
          throw new IllegalArgumentException("Invalid game type!");
      }
    } else {
      throw new IllegalArgumentException("Invalid game type!");
    }
    if (args.length > 1) {
      row = makeInt(args[1], 0, "row");
      if (row == -1) {
        playGame = false;
      }
    }
    if (args.length > 2) {
      col = makeInt(args[2], 0, "column");
      if (col == -1) {
        playGame = false;
      }
    }
    if (args.length > 3) {
      swaps = makeInt(args[3], -1, "swaps");
      if (swaps == -1) {
        playGame = false;
      }
    }
    if (playGame) {
      Readable rd = new InputStreamReader(System.in);
      Appendable ap = System.out;
      SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
      controller.playGame(model, row, col, swaps, true);
    }
  }


  /**
   * Helper for the main function that takes in a string and a threshold, if the string can be
   * parsed as an integer and if it is greater than the threshold, it returns the string parsed as
   * an integer, else it returns -1.
   * @param s the string being checked.
   * @param threshold the threshold it has to pass.
   * @return either the new integer or -1.
   */
  private static int makeInt(String s, int threshold, String name) {
    try {
      int num = Integer.parseInt(s);
      if (num > threshold) {
        return num;
      } else {
        System.out.println("Invalid " + name);
        return -1;
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid entry");
      return -1;
    }
  }
}
