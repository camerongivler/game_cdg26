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
	public static final String TITLE = "Shooters II";
	private static final int SPRITE_SPEED = 200;
	private static final int SHOOTER_SPEED = 500;

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

		addShip(width, height);
		addEnemies(6, 2);

		// Respond to input
		myScene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
		myScene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
		// myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}
	
	private void win() {
		System.out.println("You win.");
		System.exit(0);
	}
	
	private void lose() {
		System.out.println("You lose.");
		System.exit(0);
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
		double enemyWidth = 100;
		double enemyHeight = 100;
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
				enemy.setHealth(10);
				enemy.setRelativePath(new double[][] { { -enemySpaceX/2, 0 }, { -enemySpaceX/2, enemySpaceY }, { enemySpaceX/2, enemySpaceY }, { enemySpaceX/2, 0 } });

				enemy.init(myRoot);
				myEnemies.add(enemy);
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

		if(!myShip.step(elapsedTime)) {
			lose();
		}
		
		stepShooters(elapsedTime);
		checkCollisions();
		deleteShooters();
		stepEnemies(elapsedTime);
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
		for(PathImageSprite enemy: myEnemies) {
			if(enemy.randomFire()) {
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
		if(myEnemies.size() == 0) {
			win();
		}
	}

	private void checkCollisions() {
		for (DirectionImageSprite shooter : myShipShooters) {
			for(PathImageSprite enemy : myEnemies) {
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
	/*
	 * private void handleMouseInput(double x, double y) { }
	 */
}
