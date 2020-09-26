package games.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Trap extends GameObject {

    private final String PROJECT_DIR = System.getProperty("user.dir") + "\\game-assets\\";
    private final String SPIKE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\trap2.png";
    private final String TRAP = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\trap.png";
    private final String MINE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\mine.png";

    // map level constructor
    Trap( double xPos, double yPos, boolean visible) throws FileNotFoundException {

        int choice = (int) (Math.random() * 3) + 1;

        setLocation(xPos, yPos);
        setVisible(visible);
        setVelocity(-100, 0);

        if (choice == 1){
            setSpriteImage(new Image(new FileInputStream(MINE)));
        } else if (choice == 2) {
            setSpriteImage(new Image(new FileInputStream(SPIKE)));
            setLocation(xPos, 2);
        } else {
            setLocation(xPos, 417);
            setSpriteImage(new Image(new FileInputStream(TRAP)));
        }
        setType("Trap");
    }

    @Override
    public void update(double time) throws FileNotFoundException {
        super.update(time);
        if( (getYPos() > 450 - getHeight()) && (getYVelocity() > 0) || (getYPos() <= 5) && (getYVelocity() < 0))
            setVelocity(getXVelocity(), -getYVelocity());
    }
}