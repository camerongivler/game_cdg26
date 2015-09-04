/**
 * 
 */
package sprites;

import javafx.scene.Group;

/**
 * @author Cameron
 *
 */
public class PathImageSprite extends ImageSprite {

	private double[][] myPath;
	private int myCurrentTarget;

	/**
	 * @param location
	 */
	public PathImageSprite(String location) {
		super(location);
	}
	
	public PathImageSprite(String location, double xPos, double yPos, double width, double height, double speed) {
		super(location, xPos, yPos, width, height, speed);
	}
	
	public PathImageSprite(String location, double xPos, double yPos, double width, double height, double speed, Group root) {
		super(location, xPos, yPos, width, height, speed, root);
	}

	@Override
	public boolean step(double elapsedTime) {
		double xdist = myPath[myCurrentTarget][0] - myXPos;
		double ydist = myPath[myCurrentTarget][1] - myYPos;
		double dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
		double xSpeed = mySpeed * xdist / dist;
		double ySpeed = mySpeed * ydist / dist;
		double targetX = xSpeed * elapsedTime + myXPos;
		double targetY = ySpeed * elapsedTime + myYPos;
		checkOvershoot(targetX, targetY, xSpeed, ySpeed);
		setX(targetX);
		setY(targetY);
		return super.step(elapsedTime);
	}

	private void incMyCurrentTarget() {
		myCurrentTarget++;
		if (myCurrentTarget >= myPath.length)
			myCurrentTarget = 0;
	}

	private void checkOvershoot(double targetX, double targetY, double xSpeed, double ySpeed) {
		if (targetX > myPath[myCurrentTarget][0] && xSpeed > 0 || targetX < myPath[myCurrentTarget][0] && xSpeed < 0
				|| targetY > myPath[myCurrentTarget][1] && ySpeed > 0
				|| targetY < myPath[myCurrentTarget][1] && ySpeed < 0) {
			targetX = myPath[myCurrentTarget][0];
			targetY = myPath[myCurrentTarget][1];
			incMyCurrentTarget();
		}
	}

	public void setPath(double[][] path) {
		myPath = path;
		myCurrentTarget = 0;
	}

	public void setRelativePath(double[][] relativepath) {
		myPath = new double[relativepath.length][relativepath[0].length];
		for (int i = 0; i < relativepath.length; i++) {
			myPath[i][0] = relativepath[i][0] + myXPos;
			myPath[i][1] = relativepath[i][1] + myYPos;
		}
		myCurrentTarget = 0;
	}
}
