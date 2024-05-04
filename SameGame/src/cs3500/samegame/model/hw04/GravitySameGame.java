package cs3500.samegame.model.hw04;

import java.util.List;

import cs3500.samegame.model.hw02.GamePiece;

/**
 * A model of SameGame that behaves the same as FourPieceSameGame except that gravity is at play
 * meaning pieces fall after a move is made or at the start of the game.
 */
public class GravitySameGame extends ASameGameModel {

  /**
   * Constructor for GravitySameGame.
   */
  public GravitySameGame() {
    super();
  }

  @Override
  public void startGame(int rows, int cols, int swaps, boolean random) {
    this.startGameHelper(rows,cols,swaps,random);
    this.gravityOnBoard();
  }

  @Override
  public void startGame(List<List<GamePiece>> board, int swaps) {
    this.startGameHelper(board,swaps);
    this.gravityOnBoard();
  }

  @Override
  public void removeMatch(int row, int col) {
    removeMatchHelper(row,col);
    gravityOnBoard();
  }

  @Override
  public void swap(int fromRow, int fromCol, int toRow, int toCol) {
    swapHelper(fromRow,fromCol,toRow,toCol);
    gravityOnBoard();
  }

  /**
   * Applies gravity on the given column, moving down each piece to fill in the null
   * values and putting new nulls on top.
   * @param column the column to where gravity is applied.
   */
  private void applyGravity(int column) {
    int rowsNull = 0;
    int row = this.width() - 1;
    while (row >= 0) {
      if (onlyXAbove(row, column)) {
        break;
      }
      if (this.pieceAt(row, column) == null) {
        if (row == 0) {
          break;
        }
        rowsNull++;
        for (int r = row; r > 0; r--) {
          this.board.get(r).set(column, pieceAt(r - 1, column)) ;
        }
      } else {
        row--;
      }
    }

    for (int nullRow = 0; nullRow < rowsNull; nullRow++) {
      this.board.get(nullRow).set(column, null);
    }
  }

  /**
   * Checks if there is no pieces above the given piece.
   * @param row the row of the piece to be checked.
   * @param col the column of the piece to be checked.
   * @return true if there is no pieces above the given piece.
   */
  private boolean onlyXAbove(int row, int col) {
    for (int r = row - 1; r >= 0; r--) {
      if (this.pieceAt(r,col) != null) {
        return false;
      }
    }
    return true;
  }


  /**
   * Applies gravity across the board using the applyGravity helper.
   */
  private void gravityOnBoard() {
    for (int col = 0; col < this.length(); col++) {
      this.applyGravity(col);
    }
  }
}
