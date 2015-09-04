/**
 * 
 */
package game_cdg26;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import levels.Level;
import levels.Level1;
import levels.Level2;
import sprites.DirectionImageSprite;

/**
 * @author Cameron
 *
 */
public class ShootersII {
	private static final String TITLE = "Shooters II";
	private static final int SHIP_SPEED = 200;
	private static final double EASY_FIRE_PROB = 0.005;
	private static final double HARD_FIRE_PROB = 0.011;
	private static final double BOSS_EASY_FIRE_PROB = 0.09;
	private static final double BOSS_HARD_FIRE_PROB = 0.14;
	private static final double MY_SHIP_HEIGHT = 100;
	private static final double MY_SHIP_WIDTH = 118;
	private static final int MY_SHIP_HEALTH = 15;

	protected enum Difficulty {
		EASY, HARD
	}

	private Level myLevel;
	private Shapes myShapes;
	private Scene myScene;
	private Group myRoot;
	
	private DirectionImageSprite myShip;

	private int myWindowWidth, myWindowHeight;
	private boolean myAnimating = false;

	Difficulty myDifficulty = Difficulty.EASY;
	
	private KeyHandler myKeyhandle;

	/**
	 * Returns name of the game.
	 */
	public String getTitle() {
		return TITLE;
	}

	/**
	 * Create the game's scene
	 */
	public Scene init(int width, int height) {
		myWindowWidth = width;
		myWindowHeight = height;

		myRoot = new Group();
		myScene = new Scene(myRoot, width, height, Color.WHITE);
		myShapes = new Shapes(myRoot, width, height);
		
		myKeyhandle = new KeyHandler(myLevel, myShip, () -> showStartScreen(),
				() -> changeDifficulty(), () -> startLevel1(), () -> startLevel2());

		showStartScreen();

		return myScene;
	}

	private void showStartScreen() {
		myAnimating = false;
		myShapes.showLabel("Shooters II", 50, 20, 60);
		myShapes.showLabel("Easy", 50, 45, 40);
		myShapes.showLabel("Hard", 50, 50, 40);
		myDifficulty = Difficulty.EASY;
		myShapes.addTriangle();
		myShapes.moveTriangle(myDifficulty);
		myScene.setOnKeyPressed(e -> myKeyhandle.handleKeyPressedStartScreen(e.getCode()));
		myScene.setOnKeyReleased(null);
	}

	private void changeDifficulty() {
		myDifficulty = (myDifficulty == Difficulty.EASY ? Difficulty.HARD : Difficulty.EASY);
		myShapes.moveTriangle(myDifficulty);
	}

	private void advance() {
		myScene.setOnKeyPressed(e -> myKeyhandle.handleKeyPressedPassScreen(e.getCode()));
		myScene.setOnKeyReleased(null);
		intermediateScreen("Advancing to level 2...");
	}

	// When won is true, display that you won. Otherwise, display that you lost
	private void showWinScreen(boolean won) {
		myScene.setOnKeyPressed(e -> myKeyhandle.handleKeyPressedWinScreen(e.getCode()));
		myScene.setOnKeyReleased(null);
		String text = won ? "Congratulations!  You won!" : "Better luck next time!";
		intermediateScreen(text);
	}

	private void intermediateScreen(String text) {
		myAnimating = false;
		myShapes.showLabel(text, 50, 30, 60);
		myShapes.showLabel("Press Enter", 50, 50, 30);
	}

	private void addShip() {
		double xPos = (myWindowWidth - MY_SHIP_WIDTH) / 2;
		double yPos = myWindowHeight - MY_SHIP_HEIGHT - 50;
		myShip = new DirectionImageSprite("duke.gif", xPos, yPos, MY_SHIP_WIDTH, MY_SHIP_HEIGHT, SHIP_SPEED, myRoot);
		myShip.setHealth(MY_SHIP_HEALTH);
		myShip.setBounds(0, myWindowWidth, myWindowHeight / 2, myWindowHeight);
	}

	/**
	 * Change properties of shapes to animate them
	 * 
	 * Note, there are more sophisticated ways to animate shapes, but these
	 * simple ways work too.
	 */
	public void step(double elapsedTime) {
		if (!myAnimating) {
			return;
		}

		if (!myShip.step(elapsedTime)) { // You lost...
			myLevel.resetCanvas();
			showWinScreen(false);
			return;
		}

		myLevel.step(elapsedTime);
	}

	private void startLevel1() {
		resetCanvas();
		addShip();
		myKeyhandle.setMyShip(myShip);
		double fireProb = (myDifficulty == Difficulty.EASY ? EASY_FIRE_PROB : HARD_FIRE_PROB);
		myLevel = new Level1(fireProb, myWindowWidth, myWindowHeight, myShip, myRoot, () -> advance());
		myKeyhandle.setMyLevel(myLevel);
		setAnimationKeyHandlers();
	}

	private void startLevel2() {
		myShip.init(myRoot);
		double fireProb = (myDifficulty == Difficulty.EASY ? BOSS_EASY_FIRE_PROB : BOSS_HARD_FIRE_PROB);
		myLevel = new Level2(fireProb, myWindowWidth, myWindowHeight, myShip, myRoot, () -> showWinScreen(true));
		myKeyhandle.setMyLevel(myLevel);
		setAnimationKeyHandlers();
	}
	
	private void setAnimationKeyHandlers() {
		myScene.setOnKeyPressed(e -> myKeyhandle.handleKeyPressed(e.getCode()));
		myScene.setOnKeyReleased(e -> myKeyhandle.handleKeyReleased(e.getCode()));
		myAnimating = true;
	}

	private void resetCanvas() {
		myRoot.getChildren().clear();
	}
}
