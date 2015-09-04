package levels;

import javafx.scene.Group;
import sprites.DirectionImageSprite;
import sprites.PathImageSprite;

public class Level1 extends Level {

	private static final int ENEMY_HEALTH = 7;
	private static final double ENEMY_SIZE = 75;
	private static final int ENEMY_SPEED = 200;

	public Level1(double fireProb, int width, int height, DirectionImageSprite ship, Group root, CompleteLevel complete) {
		super(fireProb, width, height, ship, root, complete);
		addEnemies(6, 2);
	}

	@Override
	public void step(double elapsedTime) {
		super.step(elapsedTime);
		fireRandomBullets();
	}
	
	private void fireRandomBullets() {
		for (PathImageSprite enemy : myEnemies) {
			if (enemy.randomFire()) {
				fireShooter(enemy, 0, 1, myEnemyShooters);
			}
		}
	}

	private void addEnemies(int numWide, int numHigh) {
		double enemySpaceX = (myWindowWidth - ENEMY_SIZE) / numWide;
		double enemySpaceY = ENEMY_SIZE * 1.5;
		PathImageSprite enemy;

		for (int i = 0; i < numHigh; i++) {
			for (int j = 1; j <= numWide; j++) {
				double xPos = enemySpaceX * (j - 0.5);
				double yPos = enemySpaceY * i;
				enemy = new PathImageSprite("unc.gif", xPos, yPos, ENEMY_SIZE, ENEMY_SIZE, ENEMY_SPEED, myRoot);
				enemy.setHealth(ENEMY_HEALTH);
				enemy.setRelativePath(new double[][] { { -enemySpaceX / 2, 0 }, { -enemySpaceX / 2, enemySpaceY },
						{ enemySpaceX / 2, enemySpaceY }, { enemySpaceX / 2, 0 } });

				enemy.setFireProb(FIRE_PROB);
				myEnemies.add(enemy);
			}
		}
	}
}
