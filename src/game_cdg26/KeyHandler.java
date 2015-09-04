package game_cdg26;

import javafx.scene.input.KeyCode;
import levels.Level;
import sprites.DirectionImageSprite;

interface Callbacks {
	void callback();
}

public class KeyHandler {
	
	private Level myLevel;
	private DirectionImageSprite myShip;
	private boolean myShotFired = false;
	private boolean myRightPressed = false, myLeftPressed = false, myUpPressed = false, myDownPressed = false;
	private Callbacks myShowStartScreen, myChangeDifficulty, myStartLevel1, myStartLevel2;

	public KeyHandler(Level level, DirectionImageSprite ship, Callbacks showStartScreen,
			Callbacks changeDifficulty, Callbacks startLevel1, Callbacks startLevel2) {
		myLevel = level;
		myShip = ship;
		myShowStartScreen = showStartScreen;
		myChangeDifficulty = changeDifficulty;
		myStartLevel1 = startLevel1;
		myStartLevel2 = startLevel2;
	}
	
	public void setMyLevel(Level level) {
		myLevel = level;
	}
	
	public void setMyShip(DirectionImageSprite ship) {
		myShip = ship;
	}
	
	public void handleKeyPressedStartScreen(KeyCode code) {
		switch (code) {
		case UP:
		case DOWN:
			myChangeDifficulty.callback();
			break;
		case ENTER:
		case SPACE:
			myStartLevel1.callback();
			break;
		default:
			// do nothing
		}
	}

	public void handleKeyPressedWinScreen(KeyCode code) {
		switch (code) {
		case ENTER:
			myLevel.resetCanvas();
			myShowStartScreen.callback();
			break;
		default:
			// do nothing
		}
	}

	public void handleKeyPressedPassScreen(KeyCode code) {
		switch (code) {
		case ENTER:
			myLevel.resetCanvas();
			myStartLevel2.callback();
			break;
		default:
			// do nothing
		}
	}

	// What to do each time a key is pressed
	public void handleKeyPressed(KeyCode code) {
		switch (code) {
		case RIGHT:
			myShip.setRelativeXSpeed(1);
			myRightPressed = true;
			break;
		case LEFT:
			myShip.setRelativeXSpeed(-1);
			myLeftPressed = true;
			break;
		case UP:
			myShip.setRelativeYSpeed(-1);
			myUpPressed = true;
			break;
		case DOWN:
			myShip.setRelativeYSpeed(1);
			myDownPressed = true;
			break;
		case SPACE:
			if (!myShotFired) {
				myLevel.fireShipShooter();
				myShotFired = true;
			}
			break;
		case S:
			myLevel.stop_shooting();
			break;
		case B:
			myLevel.start_shooting();
			break;
		default:
			// do nothing
		}
	}

	// What to do each time a key is pressed
	public void handleKeyReleased(KeyCode code) {
		switch (code) {
		case RIGHT:
			myRightPressed = false;
			myShip.setRelativeXSpeed(myLeftPressed ? -1 : 0);
			break;
		case LEFT:
			myLeftPressed = false;
			myShip.setRelativeXSpeed(myRightPressed ? 1 : 0);
			break;
		case UP:
			myUpPressed = false;
			myShip.setRelativeYSpeed(myDownPressed ? 1 : 0);
			break;
		case DOWN:
			myDownPressed = false;
			myShip.setRelativeYSpeed(myUpPressed ? -1 : 0);
			break;
		case SPACE:
			myShotFired = false;
			break;
		default:
			// do nothing
		}
	}
}
