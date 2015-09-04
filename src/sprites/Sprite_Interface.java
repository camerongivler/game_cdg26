/**
 * 
 */
package sprites;

import javafx.scene.Group;

/**
 * @author Cameron
 *
 */
public interface Sprite_Interface {

	public void init(Group root);
	public boolean step(double elapsedTime);
	
	public double getX();
	public double getY();
	public double getHeight();
	public double getWidth();
	
	public void setX(double xPos);
	public void setY(double yPos);
	public void setHeight(double height);
	public void setWidth(double width);
	
	public void setBounds(double xmin, double xmax, double ymin, double ymax);
	
	public void setSpeed(double speed);
	
	public void remove();
}
