import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.samegame.model.hw02.GamePiece;
import cs3500.samegame.model.hw02.SameGameModel;
import cs3500.samegame.model.hw04.GravitySameGame;
import cs3500.samegame.view.SameGameTextView;
import cs3500.samegame.view.SameGameView;

/**
 * Class for testing new GravitySameGame and its application.
 */
public class ExamplesSameGameGravity {

  // Test for checking if gravity is applied at the start of the game
  @Test
  public void checkGravityAppliedToStartGame() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);

    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(r,r,r));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(g,g,g));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(null,null,null));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new GravitySameGame();
    model.startGame(board,10);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,1), r);
    Assert.assertEquals(model.pieceAt(2,2), g);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "X X X\nR R R\nG G G");
  }

  // Test for ensuring board is unaltered when gravity is already applied
  @Test
  public void checkGravityDoesNotAlterIncorrectly() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);

    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(r,r,r));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(g,g,g));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(r,r,r));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new GravitySameGame();
    model.startGame(board,10);
    Assert.assertEquals(model.pieceAt(0,0), r);
    Assert.assertEquals(model.pieceAt(1,1), g);
    Assert.assertEquals(model.pieceAt(2,2), r);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "R R R\nG G G\nR R R");
  }

  // Test for ensuring gravity is applied after a move is made
  @Test
  public void checkGravityAppliedAfterMove() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(r,r,g));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(b,g,g));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(b,b,b));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new GravitySameGame();
    model.startGame(board,10);
    Assert.assertEquals(model.pieceAt(0,0), r);
    Assert.assertEquals(model.pieceAt(1,1), g);
    Assert.assertEquals(model.pieceAt(2,2), b);
    model.removeMatch(2,0);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,1), r);
    Assert.assertEquals(model.pieceAt(2,2), g);
    Assert.assertEquals(model.pieceAt(2,0), r);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "X X X\nX R G\nR G G");
  }

  // Test for ensuring gravity is applied after a vertical swap is made
  @Test
  public void checkGravityAppliedAfterVerticalSwap() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(null,r,g));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(g,g,g));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(b,b,null));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new GravitySameGame();
    model.startGame(board,10);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,0), g);
    Assert.assertEquals(model.pieceAt(2,0), b);
    model.swap(0,0,2,0);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,0), b);
    Assert.assertEquals(model.pieceAt(2,0), g);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "X R X\nB G G\nG B G");
  }

  // Test for ensuring gravity is applied after a vertical swap is made
  @Test
  public void checkGravityAppliedAfterHorizontalSwap() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(null,r,null));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(g,g,g));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(b,b,null));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new GravitySameGame();
    model.startGame(board,10);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,0), g);
    Assert.assertEquals(model.pieceAt(2,0), b);
    model.swap(1,0,1,2);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,0), null);
    Assert.assertEquals(model.pieceAt(2,0), b);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "X R X\nX G G\nB B G");
  }

  @Test
  public void gravityFullGame() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece g = new GamePiece(GamePiece.Color.GREEN);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> l1 = new ArrayList<>(Arrays.asList(r,r,r));
    List<GamePiece> l2 = new ArrayList<>(Arrays.asList(g,g,g));
    List<GamePiece> l3 = new ArrayList<>(Arrays.asList(b,b,b));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(l1,l2,l3));
    SameGameModel<GamePiece> model = new GravitySameGame();
    model.startGame(board,10);
    model.removeMatch(2,2);
    model.removeMatch(2,2);
    model.removeMatch(2,2);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,1), null);
    Assert.assertEquals(model.pieceAt(2,2), null);
    Assert.assertEquals(model.gameOver(), true);
    SameGameView<GamePiece> view = new SameGameTextView<>(model);
    Assert.assertEquals(view.toString(), "X R X\nX X X\nX X X");
  }
}
