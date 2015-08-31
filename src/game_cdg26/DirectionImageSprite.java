package game_cdg26;

public class DirectionImageSprite extends ImageSprite {

	double myRelativeXSpeed = 0;
	double myRelativeYSpeed = 0;
	
	/**
	 * @param location
	 */
	public DirectionImageSprite(String location) {
		super(location);
	}
	
	@Override
	public boolean step(double elapsedTime) {
		setX(myRelativeXSpeed * mySpeed * elapsedTime + myXPos);
		setY(myRelativeYSpeed * mySpeed * elapsedTime + myYPos);
		return stillAlive;
	}

	public void setRelativeXSpeed(double xSpeed) {
		myRelativeXSpeed = xSpeed;
	}

	public void setRelativeYSpeed(double ySpeed) {
		myRelativeYSpeed = ySpeed;
	}
	
	public double getRelativeXSpeed() {
		return myRelativeXSpeed;
	}
	
	public double getRelativeYSpeed() {
		return myRelativeYSpeed;
	}

}
