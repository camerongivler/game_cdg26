/**
 * 
 */
package game_cdg26;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * @author Cameron
 *
 */
public class ShootersII {
	public static final String TITLE = "Example JavaFX";
	private static final int SPRITE_SPEED = 200;
	private static final int SHOOTER_SPEED = 500;

	private Scene myScene;
	private DirectionImageSprite myShip;
	private PathImageSprite myEnemy;

	private int myWindowWidth;
	private int myWindowHeight;

	private Group myRoot;
	
	private boolean myShotFired = false;
	private boolean myRightPressed = false, myLeftPressed = false, myUpPressed = false, myDownPressed = false;

	private ArrayList<DirectionImageSprite> shooters;

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
		shooters = new ArrayList<DirectionImageSprite>();

		myRoot = new Group();
		myScene = new Scene(myRoot, width, height, Color.WHITE);

		myShip = new DirectionImageSprite("duke.gif");
		myShip.setHeight(100);
		myShip.setWidth(118);
		myShip.setX((myWindowWidth - myShip.getWidth())/2);
		myShip.setY(myWindowHeight - myShip.getHeight());
		myShip.setSpeed(SPRITE_SPEED);
		myShip.setBounds(0, width, 0, height);

		myShip.init(myRoot);

		myEnemy = new PathImageSprite("unc.gif");
		myEnemy.setHeight(100);
		myEnemy.setWidth(100);
		myEnemy.setX((myWindowWidth - myEnemy.getWidth())/2);
		myEnemy.setY(200);
		myEnemy.setSpeed(SPRITE_SPEED);
		myEnemy.setHealth(10);
		myEnemy.setPath(new double[][]{{100, 100}, {500, 800}, {200, 800}, {600, 0}});
		myEnemy.setBounds(0, width, 0, height);

		myEnemy.init(myRoot);

		// Respond to input
		myScene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
		myScene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}

	/**
	 * Change properties of shapes to animate them
	 * 
	 * Note, there are more sophisticated ways to animate shapes, but these
	 * simple ways work too.
	 */
	public void step(double elapsedTime) {

		myShip.step(elapsedTime);
		if(!myEnemy.step(elapsedTime)) {
			System.out.println("YOU WIN!");
			System.exit(0);
		}
			
		for (int i = 0; i < shooters.size(); i++) {
			DirectionImageSprite shooter = shooters.get(i);
			if(!shooter.step(elapsedTime)) {
				shooters.remove(i);
			}
		}

		checkCollisions();
		deleteShooters();
	}

	private void checkCollisions() {
		for(DirectionImageSprite shooter: shooters) {
			shooter.checkCollision(myEnemy);
		}
	}

	private void deleteShooters() {
		for (int i = 0; i < shooters.size(); i++) {
			DirectionImageSprite shooter = shooters.get(i);
			if (shooter.getY() < 0 - shooter.getHeight()) {
				shooter.remove();
				shooters.remove(i);
				i--;
			}
		}
	}

	private void fireShooter() {
		DirectionImageSprite shooter = new DirectionImageSprite("shooter.jpg");
		shooters.add(shooter);

		double fireXPos = myShip.myXPos + myShip.myWidth / 2;
		double fireYPos = myShip.myYPos;
		shooter.setHeight(15);
		shooter.setWidth(8.7);
		shooter.setX(fireXPos);
		shooter.setY(fireYPos);
		shooter.setSpeed(SHOOTER_SPEED);
		shooter.setHealth(1);
		shooter.setRelativeYSpeed(-1);
		shooter.init(myRoot);
		
		myShotFired = true;
	}

	// What to do each time a key is pressed
	private void handleKeyPressed(KeyCode code) {
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
			if(!myShotFired)
				fireShooter();
		default:
			// do nothing
		}
	}

	// What to do each time a key is pressed
	private void handleKeyReleased(KeyCode code) {
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

	// What to do each time a key is pressed
	private void handleMouseInput(double x, double y) {
		/*
		 * if (myBottomBlock.contains(x, y)) {
		 * myBottomBlock.setScaleX(myBottomBlock.getScaleX() * GROWTH_RATE);
		 * myBottomBlock.setScaleY(myBottomBlock.getScaleY() * GROWTH_RATE); }
		 */
	}
}
