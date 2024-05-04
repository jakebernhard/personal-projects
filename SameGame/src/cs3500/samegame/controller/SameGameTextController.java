package cs3500.samegame.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cs3500.samegame.model.hw02.SameGameModel;
import cs3500.samegame.view.SameGameTextView;
import cs3500.samegame.view.SameGameView;

/**
 * A class representing a controller for a text game os SameGame.
 * @param <T> the type of gamePiece that will be used.
 */
public class SameGameTextController<T> implements SameGameController<T> {

  private  Readable rd;
  private Appendable ap;

  /**
   * Constructor for the class that takes in an appendable and a readable for input and output.
   * @param rd the readable for input.
   * @param ap the appendable for output.
   * @throws IllegalArgumentException when either of the parameters or null.
   */
  public SameGameTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (ap == null || rd == null) {
      throw new IllegalArgumentException("The given appendable and readable cannot be null.");
    }
    this.rd = rd;
    this.ap = ap;
  }


  @Override
  public void playGame(SameGameModel<T> model, int rows, int cols, int swaps, boolean isRandom) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      model.startGame(rows, cols, swaps, isRandom);
    } catch (IllegalArgumentException | IllegalStateException e) {
      throw new IllegalArgumentException("Game cannot be started");
    }
    runGame(model);
  }


  @Override
  public void playGame(SameGameModel<T> model, List<List<T>> board, int swaps) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      model.startGame(board, swaps);
    } catch (IllegalArgumentException | IllegalStateException e) {
      throw new IllegalArgumentException("Game cannot be started");
    }
    runGame(model);
  }


  /**
   * Runs the game through a while loop that checks for instructions and applies them until
   * the user quits or the game is over.
   * @param model the model of the game being played.
   */
  private void runGame(SameGameModel<T> model) {
    Scanner sc = new Scanner(rd);
    boolean quit = false;

    showGameState(model);

    if (!sc.hasNext()) {
      throw new IllegalStateException();
    }

    while (!quit && !model.gameOver() && sc.hasNext()) {
      String userInstruction = sc.next();
      if (userInstruction.equals("Q") || userInstruction.equals("q")) {
        quit = true;
      }
      else {
        try {
          processCommand(model, userInstruction, sc);
        } catch (IllegalArgumentException e) {
          if (e.getMessage().contains("quit")) {
            quit = true;
            break;
          } else {
            throw e;
          }
        }
      }
    }

    if (quit) {
      writeMessage("Game quit!" + System.lineSeparator());
      writeMessage("State of game when quit:" + System.lineSeparator());
      showGameState(model);
    } else {
      writeMessage("Game over." + System.lineSeparator());
      showGameState(model);
    }
  }


  /**
   * Appends the given message to the appendable.
   * @param message the message to be appended.
   * @throws IllegalStateException when an IOException is thrown.
   */
  private void writeMessage(String message) throws IllegalStateException {
    try {
      ap.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * Appends the game state to the appendable including a render of the board, swaps remaining,
   * and score.
   * @param model the model being played.
   */
  private void showGameState(SameGameModel<T> model) {
    SameGameView<T> view = new SameGameTextView<>(ap, model);
    try {
      view.render();

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
    writeMessage(System.lineSeparator());
    writeMessage("Remaining swaps: " + + model.remainingSwaps() + System.lineSeparator());
    writeMessage("Score: " + model.score() + System.lineSeparator());
  }

  /**
   * Applies the given instructions to the game board and appends the results to the appendable.
   * @param model the model being played by the user.
   * @param userInstruction the instructions that will be applied to the board.
   * @param sc scanner for reading in the parameters for the instructions.
   */
  private void processCommand(SameGameModel<T> model, String userInstruction, Scanner sc) {
    int fromRow;
    int fromCol;
    int toRow;
    int toCol;
    int row;
    int col;

    switch (userInstruction) {
      case "m":
        try {
          row = getValidInput(model, sc);
          col = getValidInput(model, sc);
          model.removeMatch(row - 1, col - 1);
          if (!model.gameOver()) {
            showGameState(model);
          }
        } catch (IllegalStateException | IllegalArgumentException e) {
          if (e.getMessage().contains("quit") || e.getMessage().contains("Scanner")) {
            throw e;
          }
          writeMessage("Invalid move. Try again. " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "s":
        try {
          fromRow = getValidInput(model, sc);
          fromCol = getValidInput(model, sc);
          toRow = getValidInput(model, sc);
          toCol = getValidInput(model, sc);
          model.swap(fromRow - 1,fromCol - 1,toRow - 1,toCol - 1);
          if (!model.gameOver()) {
            showGameState(model);
          }
        } catch (IllegalArgumentException | IllegalStateException e) {
          if (e.getMessage().contains("quit") || e.getMessage().contains("Scanner")) {
            throw e;
          }
          writeMessage("Invalid move. Try again. " + e.getMessage() + System.lineSeparator());
        }
        break;
      default:
        writeMessage("Invalid command. Try again. Valid commands are m and s."
                + System.lineSeparator());
    }
  }

  /**
   * Returns the nextInt or throws an IllegalArgumentException when q to only allow for valid input.
   * @param model the model being played by the user.
   * @param sc the scanner reading in input.
   * @return the next integer or an IllegalArgumentException if q.
   */
  private int getValidInput(SameGameModel<T> model, Scanner sc) {
    if (!sc.hasNext()) {
      throw new IllegalStateException("Scanner was expected more inputs.");
    }
    try {
      int num = sc.nextInt();
      if (num < 0) {
        return getValidInput(model, sc);
      }
      return num;
    } catch (InputMismatchException e) {
      if (!sc.hasNext()) {
        throw new IllegalStateException("Scanner was expected more inputs.");
      }
      String input  = sc.next();
      if (input.equalsIgnoreCase("q")) {
        throw new IllegalArgumentException("User wants to quit!");
      } else {
        return getValidInput(model,sc);
      }
    }
  }
}
