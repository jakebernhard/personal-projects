import org.junit.Assert;
import org.junit.Test;
import java.io.StringReader;
import cs3500.samegame.controller.SameGameController;
import cs3500.samegame.controller.SameGameTextController;
import cs3500.samegame.model.hw02.FourPieceSameGame;
import cs3500.samegame.model.hw02.GamePiece;
import cs3500.samegame.model.hw02.SameGameModel;


/**
 * A class for testing the controller of SameGame.
 */
public class ExamplesSameGameController {

  // Tests that an illegal argument exception is thrown when the readable is null.
  @Test(expected = IllegalArgumentException.class)
  public void testNullReader() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(null, ap);
  }

  // Tests that an illegal argument exception is thrown when the appendable is null.
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("m 2 2");
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, null);
  }

  // Tests that when the reader has zero input it throws an illegal state exception
  @Test(expected = IllegalStateException.class)
  public void testZeroInputReader() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
  }

  // Tests then when a reader is stopped before move can be finished it throws error.
  @Test(expected = IllegalStateException.class)
  public void testControllerIllegalState() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("m 2");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
  }

  // Tests that q quits the game
  @Test
  public void testQuitLowerCase() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertTrue(ap.toString().contains("Game quit!"));
  }

  // Tests that Q quits the game
  @Test
  public void testQuitUpperCase() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("Q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertTrue(ap.toString().contains("Game quit!"));
  }

  // Tests that you can quit the game mid-making a move.
  @Test
  public void testQuitMidMove() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("s 1 1 q 2");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertTrue(ap.toString().contains("Game quit!"));
  }

  // Tests that you can quit the game mid-making a move.
  @Test
  public void testQuitMidMove2() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("m 2 q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertTrue(ap.toString().contains("Game quit!"));
  }

  // Tests that the swap correctly changes the model when valid inputs are given.
  @Test
  public void testSwapCommandWorks() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("s 1 1 1 2 q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertEquals(model.pieceAt(0,0), new GamePiece(GamePiece.Color.GREEN));
    Assert.assertEquals(model.pieceAt(0,1), new GamePiece(GamePiece.Color.RED));
    Assert.assertFalse(ap.toString().contains("Invalid move. Try again"));
    Assert.assertTrue(ap.toString().contains("Remaining swaps: 9"));
  }

  // Checks that row and column numbers were made to start at 1 to make things easier for the user.
  @Test
  public void testRowNumberUserFriendly() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("s 0 0 1 2 q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertEquals(model.pieceAt(0,0), new GamePiece(GamePiece.Color.RED));
    Assert.assertEquals(model.pieceAt(0,1), new GamePiece(GamePiece.Color.GREEN));
    Assert.assertTrue(ap.toString().contains("Invalid move. Try again"));
  }

  // Checks that coordinates for an invalid swap are shown as invalid on controller.
  @Test
  public void testInvalidSwapCoordinates() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("s 2 1 1 2 q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertEquals(model.pieceAt(0,0), new GamePiece(GamePiece.Color.RED));
    Assert.assertEquals(model.pieceAt(0,1), new GamePiece(GamePiece.Color.GREEN));
    Assert.assertTrue(ap.toString().contains("Invalid move. Try again"));
  }

  // Test that swap and score and correctly presented in the game.
  @Test
  public void testScoreAndSwapShown() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("s 1 1 1 2 q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertTrue(ap.toString().contains("Score: 0"));
    Assert.assertTrue(ap.toString().contains("Remaining swaps: 10"));
  }

  // Test that the board is correctly displayed in the game.
  @Test
  public void testBoardDisplayed() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("x q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertTrue(ap.toString().contains("R G B Y R G B\nY R G B Y R G\n" +
            "B Y R G B Y R\nG B Y R G B Y\nR G B Y R G B\n"));
  }

  // Tests that that move correctly changes the model when valid inputs are given.
  @Test
  public void testMoveCommandWorks() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("s 1 1 1 2 s 3 2 3 3 m 1 2 q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertEquals(model.pieceAt(0,1), null);
    Assert.assertEquals(model.pieceAt(1,1), null);
    Assert.assertEquals(model.pieceAt(2,1), null);
    Assert.assertFalse(ap.toString().contains("Invalid move. Try again"));
    Assert.assertTrue(ap.toString().contains("Score: 1"));
    Assert.assertTrue(ap.toString().contains("Remaining swaps: 8"));
  }

  // Tests that that move correctly gives an invalid message when given incorrect inputs
  @Test
  public void testInvalidMoveCommandWorks() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("m 1 2 q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertEquals(model.pieceAt(0,1), new GamePiece(GamePiece.Color.GREEN));
    Assert.assertTrue(ap.toString().contains("Invalid move. Try again"));
    Assert.assertTrue(ap.toString().contains("Score: 0"));
    Assert.assertTrue(ap.toString().contains("Remaining swaps: 10"));
  }

  // Tests that a valid command is either s m or q
  @Test
  public void testInvalidCommandFails() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("x 5 2 q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertEquals(model.pieceAt(0,1), new GamePiece(GamePiece.Color.GREEN));
    Assert.assertTrue(ap.toString().contains("Invalid command. " +
            "Try again. Valid commands are m and s."));
    Assert.assertTrue(ap.toString().contains("Score: 0"));
    Assert.assertTrue(ap.toString().contains("Remaining swaps: 10"));
  }

  // Tests that the program will keep searching for the right paramater for a move until it works.
  @Test
  public void testInvalidParametersSkipped() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("s -500 ivnvirn 1 2 blah #### 1 1 q");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,5,7,10,false);
    Assert.assertEquals(model.pieceAt(0,1), new GamePiece(GamePiece.Color.RED));
    Assert.assertEquals(model.pieceAt(0,0), new GamePiece(GamePiece.Color.GREEN));
    Assert.assertFalse(ap.toString().contains("Invalid move. Try again."));
    Assert.assertTrue(ap.toString().contains("Score: 0"));
    Assert.assertTrue(ap.toString().contains("Remaining swaps: 9"));
  }

  // Tests that the program will correctly run a whole game.
  @Test
  public void testFullGame() {
    SameGameModel<GamePiece> model = new FourPieceSameGame();
    Readable rd = new StringReader("s 1 3 1 2 " +
            "s 3 1 3 2 " +
            "m 1 2 " +
            "s 1 1 2 1 " +
            "s 2 1 2 5 " +
            "m 2 5 " +
            "s 3 4 3 2 " +
            "s 3 2 1 2 " +
            "m 1 1 " +
            "s 3 1 1 1 " +
            "s 1 1 1 3 " +
            "m 1 3");
    Appendable ap = new StringBuilder();
    SameGameController<GamePiece> controller = new SameGameTextController<GamePiece>(rd, ap);
    controller.playGame(model,3,5,10,false);
    Assert.assertEquals(model.pieceAt(0,1), null);
    Assert.assertEquals(model.pieceAt(0,0), null);
    Assert.assertFalse(ap.toString().contains("Invalid move. Try again."));
    Assert.assertTrue(ap.toString().contains("Score: 5"));
    Assert.assertTrue(ap.toString().contains("Remaining swaps: 2"));
    Assert.assertTrue(ap.toString().contains("Game over."));
  }
}
