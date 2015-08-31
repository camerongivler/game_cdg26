/**
 * 
 */
package game_cdg26;

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
		// TODO Auto-generated constructor stub
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
//		checkOvershoot(targetX, targetY, elapsedTime);
		checkOvershoot(targetX, targetY, xSpeed, ySpeed);
		setX(targetX);
		setY(targetY);
		return stillAlive;
	}
	
	private void incMyCurrentTarget() {
		myCurrentTarget++;
		if(myCurrentTarget >= myPath.length)
			myCurrentTarget = 0;
	}
	
	private void checkOvershoot(double targetX, double targetY, double xSpeed, double ySpeed) {
		if(targetX >= myPath[myCurrentTarget][0] && xSpeed > 0
				|| targetX <= myPath[myCurrentTarget][0] && xSpeed < 0
				|| targetY >= myPath[myCurrentTarget][1] && ySpeed > 0
				|| targetY <= myPath[myCurrentTarget][1] && ySpeed < 0) {
			targetX = myPath[myCurrentTarget][0];
			targetY = myPath[myCurrentTarget][1];
			incMyCurrentTarget();
		}
	}
	
/*	private void checkOvershoot(double targetX, double targetY, double elapsedTime) {
		double xOvershoot = 0, yOvershoot = 0;
		if(targetX >= myPath[myCurrentTarget][0] && myXSpeed > 0) {
			xOvershoot = targetX - myPath[myCurrentTarget][0];
			targetX = myPath[myCurrentTarget][0];
			incMyCurrentTarget();
		} else if(targetX <= myPath[myCurrentTarget][0] && myXSpeed < 0) {
			xOvershoot = myPath[myCurrentTarget][0] - targetX;
			targetX = myPath[myCurrentTarget][0];
			incMyCurrentTarget();
		}
		
		if(targetY >= myPath[myCurrentTarget][1] && myYSpeed > 0) {
			yOvershoot = targetY - myPath[myCurrentTarget][1];
			targetY = myPath[myCurrentTarget][1];
			incMyCurrentTarget();
		} else if(targetY <= myPath[myCurrentTarget][1] && myYSpeed < 0) {
			yOvershoot = myPath[myCurrentTarget][1] - targetY;
			targetY = myPath[myCurrentTarget][1];
			incMyCurrentTarget();
		}
		
		if(xOvershoot == 0 && yOvershoot == 0) {
			return;
		}
		double overshoot = Math.sqrt(Math.pow(xOvershoot, 2) + Math.pow(yOvershoot, 2));
		double dist = setMySpeed();
		double extraTime = overshoot / dist * elapsedTime;
		step(extraTime);
	}
	
	private double setMySpeed() {
		double xdist = myPath[myCurrentTarget][0] - myXPos;
		double ydist = myPath[myCurrentTarget][1] - myYPos;
		double dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
		myXSpeed = mySpeed * xdist / dist;
		myYSpeed = mySpeed * ydist / dist;
	} */
	
	public void setPath(double[][] path) {
		myPath = path;
		myCurrentTarget = 0;
	}

	public void setRelativePath(double[][] relativepath) {
		myPath = new double[relativepath.length][relativepath[0].length];
		for(int i = 0; i < relativepath.length; i++) {
			myPath[i][0] = relativepath[i][0] + myXPos;
			myPath[i][1] = relativepath[i][1] + myYPos;
		}
		myCurrentTarget = 0;
	}
}
