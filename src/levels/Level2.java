package levels;

import javafx.scene.Group;
import sprites.DirectionImageSprite;
import sprites.FollowerImageSprite;
import sprites.ImageSprite;
import sprites.PathImageSprite;

public class Level2 extends Level {

	private static final double ENEMY_SIZE = 150;
	private static final int BOSS_SPEED = 100;
	private static final int BOSS_HEALTH = 25;

	public Level2(double fireProb, int width, int height, DirectionImageSprite ship, Group root,
			CompleteLevel callback) {
		super(fireProb, width, height, ship, root, callback);
		addEnemy();
	}

	private void addEnemy() {
		double xPos = (myWindowWidth - ENEMY_SIZE) / 2.0;
		double yPos = 100;
		PathImageSprite enemy = new FollowerImageSprite("rcd_old.gif", myShip, xPos, yPos, ENEMY_SIZE, ENEMY_SIZE,
				BOSS_SPEED, myRoot);
		enemy.setHealth(BOSS_HEALTH);
		enemy.setFireProb(FIRE_PROB);
		myEnemies.add(enemy);
	}

	@Override
	public void step(double elapsedTime) {
		super.step(elapsedTime);
		fireRandomBullets();
	}

	private void fireRandomBullets() {
		for (PathImageSprite enemy : myEnemies) {
			if (enemy.randomFire()) {
				fireShooterAtShip(enemy);
			}
		}
	}

	private void fireShooterAtShip(ImageSprite ship) {
		double xdist = myShip.getX() - ship.getX() + myShip.getSpeed() * myShip.getRelativeXSpeed() / 1.5;
		double ydist = myShip.getY() - ship.getY() + myShip.getSpeed() * myShip.getRelativeYSpeed() / 1.5;
		double dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
		double xSpeed = xdist / dist;
		double ySpeed = ydist / dist;
		fireShooter(ship, xSpeed, ySpeed, myEnemyShooters);
	}
}
