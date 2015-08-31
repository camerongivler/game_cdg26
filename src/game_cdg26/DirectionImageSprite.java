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
	public void step(double elapsedTime) {
		setX(myRelativeXSpeed * mySpeed * elapsedTime + myXPos);
		setY(myRelativeYSpeed * mySpeed * elapsedTime + myYPos);
	}

	public void setRelativeXSpeed(double xSpeed) {
		// TODO Auto-generated method stub
		myRelativeXSpeed = xSpeed;
	}

	public void setRelativeYSpeed(double ySpeed) {
		// TODO Auto-generated method stub
		myRelativeYSpeed = ySpeed;
	}
	
	public double getRelativeXSpeed() {
		return myRelativeXSpeed;
	}
	
	public double getRelativeYSpeed() {
		return myRelativeYSpeed;
	}

}
