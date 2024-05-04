package cs3500.samegame.model.hw04;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import cs3500.samegame.model.hw02.GamePiece;
import cs3500.samegame.model.hw02.SameGameModel;

/**
 * An abstract class for representing a model of SameGameModel. Fields include a 2D list of
 * game pieces for the board, a boolean for whether the game is on, and integers
 * for the score and number of swaps.
 */
public abstract class ASameGameModel implements SameGameModel<GamePiece> {

  protected List<List<GamePiece>> board;

  private boolean gameOn;

  private int swaps;

  private int score;

  /**
   * Constructor for ASameGameModel that sets gameOn to false and score to 0.
   */
  public ASameGameModel() {
    this.gameOn = false;
    this.score = 0;
  }

  /**
   * Essentially the swap method but was renamed into a helper to allow for greater
   * abstraction.
   * @param fromRow the row of the first piece being swapped.
   * @param fromCol the column of the first piece being swapped.
   * @param toRow the row of the second piece being swapped.
   * @param toCol the column of the second piece being swapped.
   */
  protected void swapHelper(int fromRow, int fromCol, int toRow, int toCol) {
    if (!gameOn) {
      throw new IllegalStateException("Game has not started");
    }
    if (fromRow < 0 || fromRow >= this.width() || toRow < 0 || toRow >= this.width()) {
      throw new IllegalArgumentException("Invalid row coordinate");
    }
    if (fromCol < 0 || fromCol >= this.length() || toCol < 0 || toCol >= this.length()) {
      throw new IllegalArgumentException("Invalid column coordinate");
    }
    if (this.pieceAt(fromRow,fromCol) == null
            && this.pieceAt(toRow,toCol) == null) {
      throw new IllegalStateException("Both coordinates do not have a piece");
    }
    if (fromRow != toRow && fromCol != toCol) {
      throw new IllegalStateException("Illegal swap coordinates");
    }
    if (fromRow == toRow && fromCol == toCol) {
      throw new IllegalStateException("Cannot swap with self");
    }
    if (this.swaps == 0) {
      throw new IllegalStateException("No swaps remaining");
    }
    GamePiece temp = this.pieceAt(fromRow,fromCol);
    this.board.get(fromRow).set(fromCol, this.pieceAt(toRow,toCol));
    this.board.get(toRow).set(toCol, temp);
    this.swaps--;
  }


  /**
   * Essentially the removeMatch method but was renamed into a helper to allow for greater
   * abstraction.
   * @param row the row of the piece being attempted to remove.
   * @param col the colum of the piece being attempted to remove.
   */
  protected void removeMatchHelper(int row, int col) {
    if (!gameOn) {
      throw new IllegalStateException("Game has not started");
    }
    if (row < 0 || row >= this.width() || col < 0 || col >= this.length()) {
      throw new IllegalArgumentException("Out of bound coordinates");
    }
    if (this.pieceAt(row,col) == null) {
      throw new IllegalStateException("No piece at coordinate");
    }
    List<String> toModify = new ArrayList<>();
    if (this.checkMatches(row,col,toModify).size() < 3) {
      throw new IllegalStateException("Invalid move");
    }
    score += checkMatches(row,col,toModify).size() - 2;
    for (String coordinate : toModify) {
      int r = Integer.parseInt(coordinate.substring(0,1));
      int c = Integer.parseInt(coordinate.substring(1,2));
      this.board.get(r).set(c, null);
    }
  }


  /**
   * Returns a list of the pieces that are a match to the given coordinates.
   * @param row the row.
   * @param col the col.
   * @param piecesToModify the list in which the matching pieces are added to.
   * @return a list of string coordinates of every piece in the matching block.
   */
  private List<String> checkMatches(int row, int col, List<String> piecesToModify) {
    GamePiece piece = this.pieceAt(row,col);
    piecesToModify.add(String.valueOf(row) + String.valueOf(col));
    this.checkMatchesHelper(row + 1,col,piece, piecesToModify);
    this.checkMatchesHelper(row - 1,col,piece, piecesToModify);
    this.checkMatchesHelper(row,col + 1,piece, piecesToModify);
    this.checkMatchesHelper(row,col - 1,piece, piecesToModify);
    return new ArrayList<>(new HashSet<>(piecesToModify));
  }

  /**
   * Checks if the piece at the given coordinates is the same as the given piece and if so,
   * adds it to the given list.
   * @param row the row.
   * @param col the column.
   * @param piece the piece to be compared to.
   * @param piecesToModify the list to which the piece will be added if equal.
   */
  private void checkMatchesHelper(int row, int col, GamePiece piece, List<String> piecesToModify) {
    if (row >= 0 && row < this.width() && col >= 0 && col < this.length()) {
      if (this.pieceAt(row, col) != null) {
        if (this.pieceAt(row, col).equals(piece)
                && !piecesToModify.contains(String.valueOf(row) + String.valueOf(col))) {
          piecesToModify.add(String.valueOf(row) + String.valueOf(col));
          checkMatches(row, col, piecesToModify);
        }
      }
    }
  }

  @Override
  public int width() {
    if (!gameOn) {
      throw new IllegalStateException("Game has not started");
    }
    return this.board.size();
  }

  @Override
  public int length() {
    if (!gameOn) {
      throw new IllegalStateException("Game has not started");
    }
    return this.board.get(0).size();
  }

  @Override
  public GamePiece pieceAt(int row, int col) {
    if (!gameOn) {
      throw new IllegalStateException("Game has not started");
    }
    if (row >= this.width() || col >= this.length() || row < 0 || col < 0) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
    List<GamePiece> r = this.board.get(row);
    return r.get(col);
  }

  @Override
  public int score() {
    if (!gameOn) {
      throw new IllegalStateException("Game has not started");
    }
    return this.score;
  }

  @Override
  public int remainingSwaps() {
    if (!gameOn) {
      throw new IllegalStateException("Game has not started");
    }
    return this.swaps;
  }

  @Override
  public boolean gameOver() {
    int numRed = 0;
    int numBlue = 0;
    int numGreen = 0;
    int numYellow = 0;
    for (int r = 0; r < this.width(); r++) {
      for (int c = 0; c < this.length(); c++) {
        if (this.pieceAt(r,c) != null) {
          if (this.checkMatches(r,c, new ArrayList<>()).size() >= 3) {
            return false;
          } else {
            switch (this.pieceAt(r,c).toString()) {
              case "R":
                numRed++;
                break;
              case "B":
                numBlue++;
                break;
              case "G":
                numGreen++;
                break;
              case "Y":
                numYellow++;
                break;
              default:
                break;
            }
          }
        }
      }
    }
    if (swaps > 0) {
      return numRed < 3 && numGreen < 3 && numBlue < 3 && numYellow < 3;
    }
    return true;
  }


  /**
   * Essentially the startGame method but was renamed into a helper to allow for greater
   * abstraction.
   * @param rows the rows of the game being started.
   * @param cols the columns of the game being started.
   * @param swaps the number of swaps in the game being started.
   * @param random whether the board will random at start or not.
   */
  protected void startGameHelper(int rows, int cols, int swaps, boolean random) {
    if (rows <= 0 || cols <= 0 || swaps < 0) {
      throw new IllegalArgumentException("Invalid parameters for starting game");
    }
    if (gameOn) {
      throw new IllegalStateException("Game has already been started");
    }
    this.gameOn = true;
    this.swaps = swaps;
    if (random) {
      this.board = this.createRandomBoard(rows, cols);
    } else {
      this.board = this.createBoard(rows,cols);
    }
  }

  /**
   * Essentially the startGame method but was renamed into a helper to allow for greater
   * abstraction.
   * @param board the board at the start of the game.
   * @param swaps the number of swaps in the game being started.
   */
  protected void startGameHelper(List<List<GamePiece>> board, int swaps) {
    if (swaps < 0) {
      throw new IllegalArgumentException("Swaps must be at least 0");
    }
    if (gameOn) {
      throw new IllegalStateException("Game has already been started");
    }
    if (board == null || board.isEmpty()) {
      throw new IllegalArgumentException("Can't start with empty board");
    }
    if (!this.validateBoard(board)) {
      throw new IllegalArgumentException("Invalid board");
    }
    this.gameOn = true;
    this.board = this.copyBoard(board);
    this.swaps = swaps;
  }

  @Override
  public GamePiece[] createListOfPieces() {
    GamePiece[] arr = new GamePiece[4];
    arr[0] = new GamePiece(GamePiece.Color.RED);
    arr[1] = new GamePiece(GamePiece.Color.GREEN);
    arr[2] = new GamePiece(GamePiece.Color.BLUE);
    arr[3] = new GamePiece(GamePiece.Color.YELLOW);
    return arr;
  }


  /**
   * Generates a random board with the given rows and columns.
   * @param rows the number of rows.
   * @param cols the number of columns.
   * @return a random board of the given dimensions.
   */
  private List<List<GamePiece>> createRandomBoard(int rows, int cols) {
    List<List<GamePiece>> newBoard = new ArrayList<>();
    Random random = new Random();
    for (int r = 0; r < rows; r++) {
      List<GamePiece> row = new ArrayList<>();
      for (int c = 0; c < cols; c++) {
        switch (random.nextInt(4)) {
          case 0:
            row.add(new GamePiece(GamePiece.Color.RED));
            break;
          case 1:
            row.add(new GamePiece(GamePiece.Color.GREEN));
            break;
          case 2:
            row.add(new GamePiece(GamePiece.Color.BLUE));
            break;
          case 3:
            row.add(new GamePiece(GamePiece.Color.YELLOW));
            break;
          default:
            break;
        }
      }
      newBoard.add(row);
    }
    return newBoard;
  }

  /**
   * Creates a board in the deterministic way.
   * @param rows the number of rows.
   * @param cols the number of columns.
   * @return a board with the given dimensions.
   */
  private List<List<GamePiece>> createBoard(int rows, int cols) {
    List<List<GamePiece>> newBoard = new ArrayList<>();
    int colorCount = 0;
    for (int r = 0; r < rows; r++) {
      List<GamePiece> row = new ArrayList<>();
      for (int c = 0; c < cols; c++) {
        switch (colorCount) {
          case 0:
            row.add(new GamePiece(GamePiece.Color.RED));
            colorCount++;
            break;
          case 1:
            row.add(new GamePiece(GamePiece.Color.GREEN));
            colorCount++;
            break;
          case 2:
            row.add(new GamePiece(GamePiece.Color.BLUE));
            colorCount++;
            break;
          case 3:
            row.add(new GamePiece(GamePiece.Color.YELLOW));
            colorCount = 0;
            break;
          default:
            break;
        }
      }
      newBoard.add(row);
    }
    return newBoard;
  }

  /**
   * Creates a copy of the given board.
   * @param board the board to be copied.
   * @return a copy of the given board that cannot be modified by the user.
   */
  private List<List<GamePiece>> copyBoard(List<List<GamePiece>> board) {
    List<List<GamePiece>> copy = new ArrayList<>();
    for (int r = 0; r < board.size(); r++) {
      List<GamePiece> row = new ArrayList<>();
      for (int c = 0; c < board.get(0).size(); c++) {
        row.add(board.get(r).get(c));
      }
      copy.add(row);
    }
    return copy;
  }

  /**
   * Checks if the given board has the same number of columns per row and that it is not empty.
   * @param board the board that is being checked.
   * @return true if dimensions are valid and non-empty.
   */
  private boolean validateBoard(List<List<GamePiece>> board) {
    int checker = board.get(0).size();
    if (checker == 0) {
      return false;
    }
    for (List<GamePiece> row : board) {
      if (row.size() != checker) {
        return false;
      }
    }
    return true;
  }

}



