/**
 * 
 */
package game_cdg26;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.*;

/**
 * @author Cameron
 *
 */
public class ShootersII {
	public static final String TITLE = "Shooters II";
	private static final int SPRITE_SPEED = 200;
	private static final int SHOOTER_SPEED = 500;
	private static final double EASY_FIRE_PROB = 0.005;
	private static final double HARD_FIRE_PROB = 0.009;
	private static final String myFont = "Arial";

	private enum Difficulty {
		EASY, HARD
	}

	private Scene myScene;
	private DirectionImageSprite myShip;

	private int myWindowWidth;
	private int myWindowHeight;

	private Group myRoot;

	private boolean myShotFired = false;
	private boolean myRightPressed = false, myLeftPressed = false, myUpPressed = false, myDownPressed = false;

	private ArrayList<DirectionImageSprite> myShipShooters;
	private ArrayList<DirectionImageSprite> myEnemyShooters;
	private ArrayList<PathImageSprite> myEnemies;

	private boolean myStartScreen = false;
	private boolean myAnimating = false;
	private boolean myWinScreen = false;

	Difficulty myDifficulty = Difficulty.HARD;
	Polygon myTriangle;

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
		myShipShooters = new ArrayList<DirectionImageSprite>();
		myEnemyShooters = new ArrayList<DirectionImageSprite>();
		myEnemies = new ArrayList<PathImageSprite>();

		myRoot = new Group();
		myScene = new Scene(myRoot, width, height, Color.WHITE);

		// Respond to input
		myScene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
		myScene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
		// myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));

		showStartScreen();

		return myScene;
	}

	private void showStartScreen() {
		myStartScreen = true;
		showLabel("Shooters II", 50, 20, 60);
		showLabel("Easy", 50, 45, 40);
		showLabel("Hard", 50, 50, 40);

		myTriangle = new Polygon();
		moveTriangle();
		myRoot.getChildren().add(myTriangle);
	}

	/**
	 * Shows a label on the screen at some scaled position (between 0 and 100) for x and y
	 * @param text The text to display
	 * @param leftPercent the scaled x coordinate (0-100)
	 * @param topPercent the scaled y coordinate (0-100)
	 */
	private void showLabel(String text, double leftPercent, double topPercent, int fontSize) {
		Label lbl = new Label(text);
		myRoot.getChildren().add(lbl);
		lbl.setFont(new Font(myFont, fontSize));
		lbl.applyCss();
		double pixelsW = (myWindowWidth - lbl.prefWidth(-1)) * leftPercent / 100;
		double pixelsH = (myWindowHeight - lbl.prefHeight(-1)) * topPercent / 100;
		lbl.setLayoutX(pixelsW);
		lbl.setLayoutY(pixelsH);
	}

	private void moveTriangle() {
		myTriangle.getPoints().clear();
		if (myDifficulty == Difficulty.EASY) {
			myTriangle.getPoints()
					.addAll(new Double[] { 	myWindowWidth / 2.0 - 90, myWindowHeight * 45 / 100.0 - 20,
											myWindowWidth / 2.0 - 90, myWindowHeight * 45 / 100.0 + 20,
											myWindowWidth / 2.0 - 60, myWindowHeight * 45 / 100.0 });
			myDifficulty = Difficulty.HARD;
		} else {
			myTriangle.getPoints()
					.addAll(new Double[] { 	myWindowWidth / 2.0 - 90, myWindowHeight * 50 / 100.0 - 20,
											myWindowWidth / 2.0 - 90, myWindowHeight * 50 / 100.0 + 20,
											myWindowWidth / 2.0 - 60, myWindowHeight * 50 / 100.0 });
			myDifficulty = Difficulty.EASY;
		}
	}

	private void showScene(int width, int height) {
		myRoot.getChildren().clear();
		addShip(width, height);
		addEnemies(6, 2);
		myAnimating = true;
	}

	private void resetCanvas() {
		myAnimating = false;
		myWinScreen = false;
		myStartScreen = false;
		remove_references(myShipShooters);
		remove_references(myEnemyShooters);
		remove_references(myEnemies);
		if(myShip != null) {
			myShip.remove();
			myShip = null;
		}
		myRoot.getChildren().clear();
	}

	private void remove_references(ArrayList<? extends ImageSprite> sprites) {
		for (ImageSprite sprite : sprites) {
			sprite.remove();
		}
		sprites.clear();
	}

	private void showWinScreen(boolean won) {
		myWinScreen = true;
		String text;
		if(won) {
			text = "Congratulations!  You won!";
		} else {
			text = "Better luck next time!";
		}
		
		showLabel(text, 50, 30, 60);
		showLabel("Press Enter", 50, 50, 30);
	}

	private void win() {
		resetCanvas();
		showWinScreen(true);
	}

	private void lose() {
		resetCanvas();
		showWinScreen(false);
	}

	private void addShip(int width, int height) {
		myShip = new DirectionImageSprite("duke.gif");
		myShip.setHeight(100);
		myShip.setWidth(118);
		myShip.setX((myWindowWidth - myShip.getWidth()) / 2);
		myShip.setY(myWindowHeight - myShip.getHeight());
		myShip.setSpeed(SPRITE_SPEED);
		myShip.setBounds(0, width, 0, height);

		myShip.init(myRoot);
	}

	private void addEnemies(int numWide, int numHigh) {
		double enemyWidth = 75;
		double enemyHeight = 75;
		double enemySpaceX = myWindowWidth / (numWide + 1);
		double enemySpaceY = enemyHeight;
		PathImageSprite enemy;

		for (int i = 0; i < numHigh; i++) {
			for (int j = 1; j <= numWide; j++) {
				enemy = new PathImageSprite("unc.gif");
				enemy.setHeight(enemyHeight);
				enemy.setWidth(enemyWidth);
				enemy.setX(enemySpaceX * (j - 0.5));
				enemy.setY(enemySpaceY * i + 50);
				enemy.setSpeed(SPRITE_SPEED);
				enemy.setHealth(7);
				enemy.setRelativePath(new double[][] { { -enemySpaceX / 2, 0 }, { -enemySpaceX / 2, enemySpaceY },
						{ enemySpaceX / 2, enemySpaceY }, { enemySpaceX / 2, 0 } });

				if (myDifficulty == Difficulty.HARD) {
					enemy.setFireProb(HARD_FIRE_PROB);
				} else {
					enemy.setFireProb(EASY_FIRE_PROB);
				}

				enemy.init(myRoot);
				myEnemies.add(enemy);
			}
		}
	}
	
	private void stop_shooting() {
		for(PathImageSprite enemy : myEnemies) {
			enemy.setFireProb(0.0);
		}
	}
	
	private void start_shooting() {
		for(PathImageSprite enemy : myEnemies) {
			if(myDifficulty == Difficulty.EASY) {
				enemy.setFireProb(EASY_FIRE_PROB);
			} else {
				enemy.setFireProb(HARD_FIRE_PROB);
			}
		}
	}

	/**
	 * Change properties of shapes to animate them
	 * 
	 * Note, there are more sophisticated ways to animate shapes, but these
	 * simple ways work too.
	 */
	public void step(double elapsedTime) {
		if (!myAnimating)
			return;

		if (!myShip.step(elapsedTime)) {
			lose();
			return;
		}

		stepShooters(elapsedTime);
		stepEnemies(elapsedTime);
		checkCollisions();
		deleteShooters();
		fireRandomBullets();
	}

	private void stepShooters(double elapsedTime) {
		for (int i = 0; i < myShipShooters.size(); i++) {
			DirectionImageSprite shooter = myShipShooters.get(i);
			if (!shooter.step(elapsedTime)) {
				myShipShooters.remove(i);
			}
		}

		for (int i = 0; i < myEnemyShooters.size(); i++) {
			DirectionImageSprite shooter = myEnemyShooters.get(i);
			if (!shooter.step(elapsedTime)) {
				myEnemyShooters.remove(i);
			}
		}
	}

	private void fireRandomBullets() {
		for (PathImageSprite enemy : myEnemies) {
			if (enemy.randomFire()) {
				myEnemyShooters.add(fireShooter(enemy, 0, 1));
			}
		}
	}

	private void stepEnemies(double elapsedTime) {
		for (int i = 0; i < myEnemies.size(); i++) {
			if (!myEnemies.get(i).step(elapsedTime)) {
				myEnemies.remove(i);
				i--;
			}
		}
		if (myEnemies.size() == 0) {
			win();
		}
	}

	private void checkCollisions() {
		for (DirectionImageSprite shooter : myShipShooters) {
			for (PathImageSprite enemy : myEnemies) {
				shooter.checkCollision(enemy);
			}
		}

		for (DirectionImageSprite shooter : myEnemyShooters) {
			shooter.checkCollision(myShip);
		}
	}

	private void deleteShooters() {
		for (int i = 0; i < myShipShooters.size(); i++) {
			DirectionImageSprite shooter = myShipShooters.get(i);
			if (shooter.getY() < 0 - shooter.getHeight()) {
				shooter.remove();
				myShipShooters.remove(i);
				i--;
			}
		}
	}

	private DirectionImageSprite fireShooter(ImageSprite ship, double xspeed, double yspeed) {
		DirectionImageSprite shooter = new DirectionImageSprite("shooter.jpg");

		double fireXPos = ship.myXPos + ship.myWidth / 2;
		double fireYPos = ship.myYPos;
		shooter.setHeight(15);
		shooter.setWidth(8.7);
		shooter.setX(fireXPos);
		shooter.setY(fireYPos);
		shooter.setSpeed(SHOOTER_SPEED);
		shooter.setHealth(1);
		shooter.setRelativeXSpeed(xspeed);
		shooter.setRelativeYSpeed(yspeed);
		shooter.init(myRoot);

		return shooter;
	}

	// What to do each time a key is pressed
	private void handleKeyPressed(KeyCode code) {

		if (myStartScreen) {
			switch (code) {
			case UP:
			case DOWN:
				moveTriangle();
				break;
			case ENTER:
			case SPACE:
				resetCanvas();
				showScene(myWindowWidth, myWindowHeight);
			default:
			}
			return;
		}
		
		if(myWinScreen) {
			switch (code) {
			case ENTER:
				resetCanvas();
				showStartScreen();
			default:
			}
			return;
		}

		switch (code) {
		case RIGHT:
			myShip.setRelativeXSpeed(1);
			myRightPressed = true;
			break;
		case LEFT:
			myShip.setRelativeXSpeed(-1);
			myLeftPressed = true;
			break;
		case UP:
			myShip.setRelativeYSpeed(-1);
			myUpPressed = true;
			break;
		case DOWN:
			myShip.setRelativeYSpeed(1);
			myDownPressed = true;
			break;
		case SPACE:
			if (!myShotFired) {
				myShipShooters.add(fireShooter(myShip, 0, -1));
				myShotFired = true;
			}
			break;
		case S:
			stop_shooting();
			break;
		case B:
			start_shooting();
			break;
		default:
			// do nothing
		}
	}

	// What to do each time a key is pressed
	private void handleKeyReleased(KeyCode code) {
		if (myStartScreen || myWinScreen) {
			return;
		}

		switch (code) {
		case RIGHT:
			myRightPressed = false;
			myShip.setRelativeXSpeed(myLeftPressed ? -1 : 0);
			break;
		case LEFT:
			myLeftPressed = false;
			myShip.setRelativeXSpeed(myRightPressed ? 1 : 0);
			break;
		case UP:
			myUpPressed = false;
			myShip.setRelativeYSpeed(myDownPressed ? 1 : 0);
			break;
		case DOWN:
			myDownPressed = false;
			myShip.setRelativeYSpeed(myUpPressed ? -1 : 0);
			break;
		case SPACE:
			myShotFired = false;
		default:
			// do nothing
		}
	}
}
