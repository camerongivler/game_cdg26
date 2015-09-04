package game_cdg26;

import game_cdg26.ShootersII.Difficulty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

public class Shapes {
	
	private Group myRoot;
	private double myWindowWidth, myWindowHeight;
	private Polygon myTriangle;
	private static final String myFont = "Arial";

	public Shapes(Group root, int width, int height) {
		myRoot = root;
		myWindowWidth = width;
		myWindowHeight = height;
	}
	
	/**
	 * Shows a label on the screen at some scaled position (between 0 and 100) for x and y
	 * @param text The text to display
	 * @param leftPercent the scaled x coordinate (0-100)
	 * @param topPercent the scaled y coordinate (0-100)
	 */
	public void showLabel(String text, double leftPercent, double topPercent, int fontSize) {
		Label lbl = new Label(text);
		myRoot.getChildren().add(lbl);
		lbl.setFont(new Font(myFont, fontSize));
		lbl.applyCss();
		double pixelsW = (myWindowWidth - lbl.prefWidth(-1)) * leftPercent / 100;
		double pixelsH = (myWindowHeight - lbl.prefHeight(-1)) * topPercent / 100;
		lbl.setLayoutX(pixelsW);
		lbl.setLayoutY(pixelsH);
	}
	
	public void addTriangle() {
		myTriangle = new Polygon();
		myRoot.getChildren().add(myTriangle);
	}
	
	public void moveTriangle(Difficulty diff) {
		myTriangle.getPoints().clear();
		switch(diff) {
			case EASY:
				myTriangle.getPoints().addAll(new Double[]
						{	myWindowWidth / 2.0 - 90, myWindowHeight * 45 / 100.0 - 20,
							myWindowWidth / 2.0 - 90, myWindowHeight * 45 / 100.0 + 20,
							myWindowWidth / 2.0 - 60, myWindowHeight * 45 / 100.0 });
				break;
			case HARD:
				myTriangle.getPoints().addAll(new Double[]
						{ 	myWindowWidth / 2.0 - 90, myWindowHeight * 50 / 100.0 - 20,
							myWindowWidth / 2.0 - 90, myWindowHeight * 50 / 100.0 + 20,
							myWindowWidth / 2.0 - 60, myWindowHeight * 50 / 100.0 });
				break;
		}
	}

}
