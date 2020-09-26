package games.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Obstacle extends GameObject {

    private final String PROJECT_DIR = System.getProperty("user.dir") + "\\game-assets\\";
    private final String BARREL = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\box.png";
    private final String WALL = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\wall.png";
    private final String TREE = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\tree.png";
    private final String CONFIGURATION_FILE = PROJECT_DIR + "configuration.txt";

    // map level constructor
    Obstacle( double xPos, double yPos, boolean visible, int choice) throws FileNotFoundException {

        setLocation(xPos, yPos);
        setVisible(visible);

        File file = new File(CONFIGURATION_FILE);
        Scanner sc = new Scanner(file);
        String game = sc.next();
        if(game.equals("Escape")) {
            setVelocity(-105, 0);
            if (choice == 1) {
                setSpriteImage(new Image(new FileInputStream(WALL)));
            } else {
                setSpriteImage(new Image(new FileInputStream(BARREL)));
            }
        }

        if(game.equals("Quest")) {
            setSpriteImage(new Image(new FileInputStream(TREE)));
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
