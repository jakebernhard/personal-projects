import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cs3500.samegame.model.hw02.FourPieceSameGame;
import cs3500.samegame.model.hw02.GamePiece;
import cs3500.samegame.model.hw02.SameGameModel;
import cs3500.samegame.view.SameGameTextView;
import cs3500.samegame.view.SameGameView;

/**
 * A class for testing the public methods of samegame.
 */
public class ExamplesSameGame {

  /**
   * Test for game piece class.
   */
  @Test
  public void testGamePiece() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    GamePiece r2 = new GamePiece(GamePiece.Color.RED);
    Assert.assertEquals(r.toString(), "R");
    Assert.assertEquals(b.toString(), "B");
    Assert.assertFalse(r.equals(b));
    Assert.assertTrue(r.equals(r2));
  }

  /**
   * Test that the standard constructor operates as properly.
   */
  @Test
  public void testStartGame() {
    SameGameModel model = new FourPieceSameGame();
    SameGameView<GamePiece> view = new SameGameTextView<GamePiece>(model);
    model.startGame(5,7,5, false);
    Assert.assertEquals(model.length(), 7);
    Assert.assertEquals(model.width(), 5);
    Assert.assertEquals(model.score(), 0);
    Assert.assertEquals(model.remainingSwaps(), 5);
    Assert.assertEquals(model.gameOver(), false);
    Assert.assertEquals(view.toString(), "R G B Y R G B\n" +
            "Y R G B Y R G\n" +
            "B Y R G B Y R\n" +
            "G B Y R G B Y\n" +
            "R G B Y R G B");
  }

  /**
   * Tests that the random parameter makes the board random.
   */
  @Test
  public void testRandomStart() {
    SameGameModel model = new FourPieceSameGame();
    SameGameModel modelRandom = new FourPieceSameGame();
    SameGameView<GamePiece> view = new SameGameTextView<GamePiece>(model);
    SameGameView<GamePiece> viewRandom = new SameGameTextView<GamePiece>(modelRandom);
    model.startGame(5,7,5,false);
    modelRandom.startGame(5,7,5,true);
    Assert.assertNotEquals(view.toString(), viewRandom.toString());
    Assert.assertNotEquals(modelRandom.pieceAt(0,0), null);
  }

  @Test
  public void testStartErrors() {
    SameGameModel model = new FourPieceSameGame();
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.startGame(5,5,-5,false);
    });
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.startGame(5,0,5,false);
    });
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.startGame(-2,5,5,false);
    });
    model.startGame(5,5,5,false);
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.startGame(5,5,5,false);
    });
  }

  /**
   * Tests that the copy version of startGame works.
   */
  @Test
  public void testCopyBoard() {
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> list1 = new ArrayList<>(Arrays.asList(r,b,r));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(list1,list1,list1));
    SameGameModel model = new FourPieceSameGame();
    model.startGame(board,5);
    Assert.assertEquals(model.width(), 3);
    Assert.assertEquals(model.length(), 3);
    Assert.assertEquals(model.pieceAt(0,0), r);
    Assert.assertEquals(model.pieceAt(0,1), b);
  }

  /**
   * Testing to ensure start game throws when it should for the copy version.
   */
  @Test
  public void testCopyBoardErrors() {
    SameGameModel model = new FourPieceSameGame();
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> list1 = new ArrayList<>(Arrays.asList(r,b,r));
    List<GamePiece> list2 = new ArrayList<>(Arrays.asList(r,b));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(list1,list1,list1));
    List<List<GamePiece>> boardInvalid = new ArrayList<>(Arrays.asList(list1,list2,list1));
    List<List<GamePiece>> empty = new ArrayList<>();
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.startGame(null,5);
    });
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.startGame(board,-10);
    });
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.startGame(empty,5);
    });
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.startGame(boardInvalid,5);
    });
    model.startGame(board,0);
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.startGame(board,5);
    });
  }

  /**
   * Test to ensure that you cannot modify the given list to alter the game board.
   */
  @Test
  public void ensureNoModify() {
    SameGameModel model = new FourPieceSameGame();
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> list1 = new ArrayList<>(Arrays.asList(r,b,r));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(list1,list1,list1));
    model.startGame(board,5);
    Assert.assertEquals(model.pieceAt(0,0), r);
    board.get(0).set(0,b);
    Assert.assertEquals(model.pieceAt(0,0), r);
  }

  /**
   * Tests that the createListOfPieces method works properly.
   */
  @Test
  public void testCreateListOfPieces() {
    GamePiece[] result = new GamePiece[4];
    result[0] = new GamePiece(GamePiece.Color.RED);
    result[1] = new GamePiece(GamePiece.Color.GREEN);
    result[2] = new GamePiece(GamePiece.Color.BLUE);
    result[3] = new GamePiece(GamePiece.Color.YELLOW);
    SameGameModel model = new FourPieceSameGame();
    Assert.assertEquals(model.createListOfPieces(), result);
  }

  /**
   * Tests to ensure the width() and length() methods behave properly.
   */
  @Test
  public void testWidthAndLength() {
    SameGameModel model1 = new FourPieceSameGame();
    SameGameModel model2 = new FourPieceSameGame();
    Assert.assertThrows(IllegalStateException.class, () -> {
      model1.width();
    });
    Assert.assertThrows(IllegalStateException.class, () -> {
      model1.length();
    });
    model1.startGame(3,7,5,false);
    model2.startGame(5,5,5,true);
    Assert.assertEquals(model1.width(), 3);
    Assert.assertEquals(model2.width(), 5);
    Assert.assertEquals(model1.length(), 7);
    Assert.assertEquals(model2.length(), 5);
  }

  /**
   * Tests for the behavior of swap() and remainingSwaps().
   */
  @Test
  public void testSwapping() {
    SameGameModel model = new FourPieceSameGame();
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> list1 = new ArrayList<>(Arrays.asList(r,b,null));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(list1,list1,list1));
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.swap(1,1, 1,2);
    });
    model.startGame(board,2);
    Assert.assertEquals(model.remainingSwaps(), 2);
    //Tests for the errors
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.swap(5,1, 2,1);
    });
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.swap(2,-1, 2,1);
    });
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.swap(1,1, 2,2);
    });
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.swap(0,2, 1,2);
    });

    //Test the horizontal swap
    Assert.assertEquals(model.pieceAt(0,0), r);
    Assert.assertEquals(model.pieceAt(0,1), b);
    model.swap(0,0,0,1);
    Assert.assertEquals(model.pieceAt(0,0), b);
    Assert.assertEquals(model.pieceAt(0,1), r);
    Assert.assertEquals(model.remainingSwaps(), 1);

    //Test the vertical swap
    Assert.assertEquals(model.pieceAt(0,0), b);
    Assert.assertEquals(model.pieceAt(1,0), r);
    model.swap(0,0,1,0);
    Assert.assertEquals(model.pieceAt(0,0), r);
    Assert.assertEquals(model.pieceAt(1,0), b);
    Assert.assertEquals(model.remainingSwaps(),0);

    Assert.assertThrows(IllegalStateException.class, () -> {
      model.swap(0,1, 0,2);
    });
  }

  /**
   * Tests the behavior of removeMatch() and score().
   */
  @Test
  public void testRemoveMatchAndScore() {
    SameGameModel model = new FourPieceSameGame();
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> list1 = new ArrayList<>(Arrays.asList(r,b,null));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(list1,list1,list1));
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.removeMatch(0,0);
    });
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.score();
    });
    model.startGame(board,2);
    Assert.assertEquals(model.score(), 0);

    //Testing the throws
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.removeMatch(50,0);
    });
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.removeMatch(0,2);
    });
    model.swap(0,0,0,1);
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.removeMatch(0,0);
    });
    model.swap(0,0,0,1);

    //Testing when its used properly
    Assert.assertEquals(model.pieceAt(0,0), r);
    Assert.assertEquals(model.pieceAt(1,0), r);
    Assert.assertEquals(model.pieceAt(2,0), r);
    Assert.assertEquals(model.pieceAt(0,1), b);
    model.removeMatch(0,0);
    Assert.assertEquals(model.score(), 1);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertEquals(model.pieceAt(1,0), null);
    Assert.assertEquals(model.pieceAt(2,0), null);
    Assert.assertEquals(model.pieceAt(0,1), b);
    model.removeMatch(0,1);
    Assert.assertEquals(model.score(),2);
  }

  /**
   * Tests the behavior of the pieceAt() method.
   */
  @Test
  public void testPieceAt() {
    SameGameModel model = new FourPieceSameGame();
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> list1 = new ArrayList<>(Arrays.asList(r,b,null));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(list1,list1,list1));
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.pieceAt(0,0);
    });
    model.startGame(board,2);
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.pieceAt(100,0);
    });
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.pieceAt(0,-5);
    });
    Assert.assertEquals(model.pieceAt(0,0), r);
    Assert.assertEquals(model.pieceAt(0,1), b);
    Assert.assertEquals(model.pieceAt(0,2), null);
  }

  /**
   * Tests the gameOver() method and a full game.
   */
  @Test
  public void testGameOverByWin() {
    SameGameModel model = new FourPieceSameGame();
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> list1 = new ArrayList<>(Arrays.asList(r,b,r));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(list1,list1,list1));
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.gameOver();
    });
    model.startGame(board,2);
    Assert.assertEquals(model.gameOver(), false);
    model.removeMatch(0,0);
    model.removeMatch(0,1);
    model.removeMatch(0,2);
    Assert.assertEquals(model.gameOver(), true);

    //Checks if you cannot play after game is over
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.removeMatch(0,0);
    });
  }


  /**
   * Tests if the game is over by no remaining swaps.
   */
  @Test
  public void testOverBySwaps() {
    SameGameModel model = new FourPieceSameGame();
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> list1 = new ArrayList<>(Arrays.asList(r,b,r));
    List<GamePiece> list2 = new ArrayList<>(Arrays.asList(b,r,b));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(list1,list2,list1));
    model.startGame(board,1);
    Assert.assertEquals(model.gameOver(), false);
    model.swap(0,0,0,1);
    model.removeMatch(0,1);
    Assert.assertEquals(model.gameOver(), true);
  }

  // Tests if the game is over by no more moves left
  @Test
  public void testOverByMoves() {
    SameGameModel model = new FourPieceSameGame();
    GamePiece r = new GamePiece(GamePiece.Color.RED);
    GamePiece b = new GamePiece(GamePiece.Color.BLUE);
    List<GamePiece> list1 = new ArrayList<>(Arrays.asList(null,b,b));
    List<GamePiece> list2 = new ArrayList<>(Arrays.asList(null,r,b));
    List<GamePiece> list3 = new ArrayList<>(Arrays.asList(null,null,r));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(list1,list2,list3));
    model.startGame(board,0);
    Assert.assertEquals(model.gameOver(), false);
    model.removeMatch(0,2);
    Assert.assertEquals(model.gameOver(), true);
  }


  // Checks if the game is over at the start
  @Test
  public void testOverAtStart() {
    SameGameModel model = new FourPieceSameGame();
    List<GamePiece> list1 = new ArrayList<>(Arrays.asList(null,null,null));
    List<List<GamePiece>> board = new ArrayList<>(Arrays.asList(list1,list1,list1));
    model.startGame(board,0);
    Assert.assertEquals(model.gameOver(), true);
  }
}
