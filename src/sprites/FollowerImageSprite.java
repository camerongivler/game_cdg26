package sprites;

import javafx.scene.Group;

public class FollowerImageSprite extends PathImageSprite {

	private ImageSprite myLeader;

	public FollowerImageSprite(String location, ImageSprite leader) {
		super(location);
		
		myLeader = leader;
	}
	
	public FollowerImageSprite(String location, ImageSprite leader, double xPos, double yPos, double width, double height, double speed, Group root) {
		super(location, xPos, yPos, width, height, speed);
		myLeader = leader;
		init(root);
	}

	public void setLeaderSprite(ImageSprite leader) {
		myLeader = leader;
	}

	@Override
	public boolean step(double elapsedTime) {
		if(Math.abs(myLeader.getX() - myXPos) < mySpeed * elapsedTime) {
			setX(myLeader.getX());
			return stillAlive;
		}
		double speed = myLeader.getX() >= myXPos ? mySpeed : -mySpeed;
		double target = speed * elapsedTime + myXPos;
		
		checkOvershoot(target, speed);
		setX(target);
		return stillAlive;
	}

	private void checkOvershoot(double target, double speed) {
		if (target > myLeader.getX() && speed > 0 || target < myLeader.getX() && speed < 0) {
			target = myLeader.getX();
		}
	}
}
