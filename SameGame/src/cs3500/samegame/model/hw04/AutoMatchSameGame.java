package cs3500.samegame.model.hw04;

import java.util.List;

import cs3500.samegame.model.hw02.GamePiece;

/**
 * A model of SameGame that behaves the same as FourPieceSameGame except that auto match is at play,
 * meaning after a swap or move is made, any matching blocks on the board are removed automatically.
 */
public class AutoMatchSameGame extends ASameGameModel {

  /**
   * Constructor for AutoMatchSameGame.
   */
  public AutoMatchSameGame() {
    super();
  }

  @Override
  public void startGame(int rows, int cols, int swaps, boolean random) {
    this.startGameHelper(rows,cols,swaps,random);
  }

  @Override
  public void startGame(List<List<GamePiece>> board, int swaps) {
    this.startGameHelper(board,swaps);
  }

  @Override
  public void removeMatch(int row, int col) {
    removeMatchHelper(row,col);
    removeAllMatches();
  }

  @Override
  public void swap(int fromRow, int fromCol, int toRow, int toCol) {
    swapHelper(fromRow,fromCol,toRow,toCol);
    removeAllMatches();
  }

  /**
   * Attempts to removeMatch at every piece on the board, catching errors if piece cannot be
   * removed.
   */
  private void removeAllMatches() {
    for (int r = 0; r < this.width(); r++) {
      for (int c = 0; c < this.length(); c++) {
        try {
          this.removeMatch(r, c);
        } catch (IllegalArgumentException | IllegalStateException e) {
          // empty catch block
        }
      }
    }
  }


}
