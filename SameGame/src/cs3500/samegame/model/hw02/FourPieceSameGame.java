package cs3500.samegame.model.hw02;

import java.util.List;

import cs3500.samegame.model.hw04.ASameGameModel;


/**
 * A traditional model of SameGame with no added features. The "base" version of the game.
 */
public class FourPieceSameGame extends ASameGameModel {

  /**
   * Constructor for FourPieceSameGame.
   */
  public FourPieceSameGame() {
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
  }

  @Override
  public void swap(int fromRow, int fromCol, int toRow, int toCol) {
    swapHelper(fromRow,fromCol,toRow,toCol);
  }
}
