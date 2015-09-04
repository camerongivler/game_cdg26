package levels;

import java.util.ArrayList;

import game_cdg26.Shapes;
import javafx.scene.Group;
import sprites.DirectionImageSprite;
import sprites.ImageSprite;
import sprites.PathImageSprite;

public class Level {

	protected final double FIRE_PROB;
	private static final int SHOOTER_SPEED = 500;
	
	protected double myWindowWidth, myWindowHeight;
	
	protected final Group myRoot;

	protected DirectionImageSprite myShip;

	private Shapes myShapes;
	
	private CompleteLevel myComplete;

	private ArrayList<DirectionImageSprite> myShipShooters;
	protected ArrayList<DirectionImageSprite> myEnemyShooters;
	protected ArrayList<PathImageSprite> myEnemies;
	
	public Level(double fireProb, int width, int height, DirectionImageSprite ship, Group root, CompleteLevel callback) {
		FIRE_PROB = fireProb;
		myWindowWidth = width;
		myWindowHeight = height;
		myShip = ship;
		myRoot = root;
		myShapes = new Shapes(root, width, height);
		myComplete = callback;
		initLists();
	}
	
	private void initLists() {
		myEnemies = new ArrayList<PathImageSprite>();
		myEnemyShooters = new ArrayList<DirectionImageSprite>();
		myShipShooters = new ArrayList<DirectionImageSprite>();
	}
	
	public void step(double elapsedTime) {
		stepSprites(elapsedTime, myShipShooters);
		stepSprites(elapsedTime, myEnemyShooters);
		stepSprites(elapsedTime, myEnemies);
		checkCollisions();
		deleteShooters();
		checkWin(elapsedTime);
	}
	
	private void checkWin(double elapsedTime) {
		if (myEnemies.size() == 0) {
			resetCanvas();
			myComplete.CallBack();
		}
	}
	
	private void stepSprites(double elapsedTime, ArrayList<? extends ImageSprite> shooters) {
		for (int i = 0; i < shooters.size(); i++) {
			ImageSprite shooter = shooters.get(i);
			if (!shooter.step(elapsedTime)) {
				shooters.remove(i);
				i--;
			}
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
	
	protected void fireShooter(ImageSprite ship, double xspeed, double yspeed, ArrayList<DirectionImageSprite> myList) {
		double fireXPos = ship.getX() + ship.getWidth() / 2;
		double fireYPos = ship.getY();
		DirectionImageSprite shooter = new DirectionImageSprite("shooter.jpg", fireXPos, fireYPos, 8.7, 15, SHOOTER_SPEED, myRoot);
		shooter.setHealth(1);
		shooter.setRelativeXSpeed(xspeed);
		shooter.setRelativeYSpeed(yspeed);
		myList.add(shooter);
	}
	
	public void fireShipShooter() {
		fireShooter(myShip, 0, -1, myShipShooters);
	}
	
	private void remove_references(ArrayList<? extends ImageSprite> sprites) {
		for (ImageSprite sprite : sprites) {
			sprite.remove();
		}
		sprites.clear();
	}
	
	public void resetCanvas() {
		remove_references(myShipShooters);
		remove_references(myEnemyShooters);
		remove_references(myEnemies);
		if(myShip != null)
			myShip.remove();
		myRoot.getChildren().clear();
	}

	public void start_shooting() {
		for (PathImageSprite enemy : myEnemies) {
			enemy.setFireProb(FIRE_PROB);
		}
	}
	
	public void stop_shooting() {
		for(PathImageSprite enemy : myEnemies) {
			enemy.setFireProb(0.0);
		}
	}
}
