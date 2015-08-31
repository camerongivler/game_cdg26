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
	public void step(double elapsedTime) {
		double xdist = myPath[myCurrentTarget][0] - myXPos;
		double ydist = myPath[myCurrentTarget][1] - myYPos;
		double dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
		double xspeed = mySpeed * xdist / dist;
		double yspeed = mySpeed * ydist / dist;
		setX(xspeed * elapsedTime + myXPos);
		setY(yspeed * elapsedTime + myYPos);
	}

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
