package game_cdg26;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;

public class ImageSprite implements Sprite_Interface {

	protected double myXPos = 0, myYPos = 0, myHeight = 100, myWidth = 100, mySpeed = 0, myXMin = 0, myXMax = 0, myYMin = 0, myYMax = 0;
	protected ImageView mySprite;
	private Group myRoot;
	private boolean myBoundsSet;
	
	public ImageSprite(String location) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(location));
        mySprite = new ImageView(image);
	}
	
	@Override
	public void init(Group root) {
		myRoot = root;
		root.getChildren().add(mySprite);
	}

	@Override
	public void step(double elapsedTime) {}

	@Override
	public double getX() {
		return myXPos;
	}

	@Override
	public double getY() {
		return myYPos;
	}

	@Override
	public void setX(double xPos) {
		if(myBoundsSet && xPos > myXMax) {
			xPos = myXMax;
		} else if (myBoundsSet && xPos < myXMin) {
			xPos = myXMin;
		}

		mySprite.setX(xPos);
		myXPos = xPos;
	}

	@Override
	public void setY(double yPos) {
		if(myBoundsSet && yPos > myYMax) {
			yPos = myYMax;
		} else if (myBoundsSet && yPos < myYMin) {
			yPos = myYMin;
		}
		
		mySprite.setY(yPos);
		myYPos = yPos;
	}

	@Override
	public double getHeight() {
		return myHeight;
	}

	@Override
	public double getWidth() {
		return myWidth;
	}

	@Override
	public void setHeight(double height) {
		myHeight = height;
		mySprite.setFitHeight(height);
	}

	@Override
	public void setWidth(double width) {
		myWidth = width;
        mySprite.setFitWidth(width);
	}

	@Override
	public void setSpeed(double speed) {
		mySpeed = speed;
	}
	
	@Override
	public void remove() {
		myRoot.getChildren().remove(mySprite);
	}
	
	@Override
	public void setBounds(double xmin, double xmax, double ymin, double ymax) {
		myBoundsSet = true;
		myXMin = xmin;
		myXMax = xmax - myWidth;
		myYMin = ymin;
		myYMax = ymax - myHeight;
	}
	
	public void removeBounds() {
		myBoundsSet = false;
	}
	
}