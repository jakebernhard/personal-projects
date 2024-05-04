import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.samegame.model.hw02.GamePiece;
import cs3500.samegame.model.hw02.SameGameModel;
import cs3500.samegame.model.hw04.AutoMatchSameGame;
import cs3500.samegame.view.SameGameTextView;
import cs3500.samegame.view.SameGameView;

/**
 * Class for testing new AutoMatch same game and its new features.
 */
public class ExamplesSameGameAutomatch {

  // Test for ensuring board is unaltered when the game is started
  @Test
  public void checkAutoMatchDoesNotAlterAtStart() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);
    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(r,r,r));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(g,g,g));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(r,r,r));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new AutoMatchSameGame();
    model.startGame(board,10);
    Assert.assertEquals(model.pieceAt(0,0), r);
    Assert.assertEquals(model.pieceAt(1,1), g);
    Assert.assertEquals(model.pieceAt(2,2), r);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "R R R\nG G G\nR R R");
  }

  // Test for auto match is applied when a move is made
  @Test
  public void checkAutoMatchAppliedAfterMove() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(r,r,g));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(b,g,g));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(b,b,b));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new AutoMatchSameGame();
    model.startGame(board,10);
    Assert.assertEquals(model.pieceAt(0,0), r);
    Assert.assertEquals(model.pieceAt(1,1), g);
    Assert.assertEquals(model.pieceAt(2,2), b);
    model.removeMatch(2,0);
    Assert.assertEquals(model.pieceAt(0,0), r);
    Assert.assertEquals(model.pieceAt(1,1), null);
    Assert.assertEquals(model.pieceAt(2,2), null);
    Assert.assertEquals(model.pieceAt(2,0), null);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "R R X\nX X X\nX X X");
  }

  // Test for ensuring auto match is applied correctly after a horizontal swap is made
  @Test
  public void checkAutoMatchAppliedAfterHorizontalSwap() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(g,r,b));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(g,r,b));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(b,b,g));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new AutoMatchSameGame();
    model.startGame(board,10);
    Assert.assertEquals(model.pieceAt(0,0), g);
    Assert.assertEquals(model.pieceAt(1,0), g);
    Assert.assertEquals(model.pieceAt(2,0), b);
    model.swap(2,0,2,2);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,0), null);
    Assert.assertEquals(model.pieceAt(2,0),  null);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "X R X\nX R X\nX X X");
  }

  // Test for ensuring auto match is applied after a vertical swap is made
  @Test
  public void checkAutoMatchAppliedAfterVerticalSwap() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(b,r,r));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(g,b,g));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(r,b,r));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new AutoMatchSameGame();
    model.startGame(board,10);
    Assert.assertEquals(model.pieceAt(0,0), b);
    Assert.assertEquals(model.pieceAt(1,0), g);
    Assert.assertEquals(model.pieceAt(2,0), r);
    model.swap(0,0,2,0);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,0), g);
    Assert.assertEquals(model.pieceAt(2,0), null);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "X X X\nG X G\nX X R");
  }

  // test for running a full game of AutoMatchSameGame
  @Test
  public void autoMatchFullGame() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(r,r,r));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(g,g,g));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(b,b,b));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new AutoMatchSameGame();
    model.startGame(board,10);
    model.removeMatch(2,2);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,1), null);
    Assert.assertEquals(model.pieceAt(2,2), null);
    Assert.assertEquals(model.gameOver(), true);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "X X X\nX X X\nX X X");
  }

}
