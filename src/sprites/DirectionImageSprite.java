package sprites;

import javafx.scene.Group;

public class DirectionImageSprite extends ImageSprite {

	double myRelativeXSpeed = 0;
	double myRelativeYSpeed = 0;

	/**
	 * @param location
	 */
	public DirectionImageSprite(String location) {
		super(location);
	}
	
	public DirectionImageSprite(String location, double xPos, double yPos, double width, double height, double speed, Group root) {
		super(location, xPos, yPos, width, height, speed, root);
	}

	@Override
	public boolean step(double elapsedTime) {
		setX(myRelativeXSpeed * mySpeed * elapsedTime + myXPos);
		setY(myRelativeYSpeed * mySpeed * elapsedTime + myYPos);
		return super.step(elapsedTime);
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
