package sample.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PrisonGuardian extends GameObject {

    private final String SPEARMAN = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\swordsman.png";

    // map level constructor
    PrisonGuardian( double xPos, double yPos, boolean visible) throws FileNotFoundException {
        setLocation(xPos, yPos);
        setVisible(visible);
        setVelocity(0, 0);
        setSpriteImage(new Image(new FileInputStream(SPEARMAN)));

        setType("Guardian");
    }

    @Override
    public void update(double time) throws FileNotFoundException {
        super.update(time);
    }
}
