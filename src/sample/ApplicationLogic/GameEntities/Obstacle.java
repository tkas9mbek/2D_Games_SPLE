package sample.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Obstacle extends GameObject {

    private final String BARREL = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\box.png";
    private final String WALL = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\wall.png";

    // map level constructor
    Obstacle( double xPos, double yPos, boolean visible, int choice) throws FileNotFoundException {

        setLocation(xPos, yPos);
        setVisible(visible);
        setVelocity(-105, 0);

        if (choice == 1) {
            setSpriteImage(new Image(new FileInputStream(WALL)));
        } else {
            setSpriteImage(new Image(new FileInputStream(BARREL)));
        }

        setType("Obstacle");
    }

    @Override
    public void update(double time) throws FileNotFoundException {
        super.update(time);
        if( (getYPos() > 450 - getHeight()) && (getYVelocity() > 0) || (getYPos() <= 5) && (getYVelocity() < 0))
            setVelocity(getXVelocity(), -getYVelocity());
    }
}
