package sample.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SmallEnemy extends Enemy {

    private final String GOBLIN = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\goblin.png";
    private final String ORC = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\orc.png";

    // map level constructor
    SmallEnemy( double xPos, double yPos, boolean visible, int mapLvl) throws FileNotFoundException {
        int choice = (int) (Math.random() * 2) + 1;

        setLocation(xPos, yPos);
        setVisible(visible);

        if (choice == 1) {
            setVelocity(-155, 0);
            setSpriteImage(new Image(new FileInputStream(GOBLIN)));
            setHealth(15 + 5 * mapLvl);
            setCollisionDmg(10 + 5 * mapLvl);
            setExperiencePrize(20 + 10 * mapLvl);
            setScorePrize( 20 + 10 * mapLvl);
        } else {
            setVelocity(-90, (int)((Math.random() * -66) + 33));
            setSpriteImage(new Image(new FileInputStream(ORC)));
            setHealth(20 + 10 * mapLvl);
            setCollisionDmg(20 + 10 * mapLvl);
            setExperiencePrize(50 + 25 * mapLvl);
            setScorePrize(50 + 25 * mapLvl);
        }

        setType("Small Enemy");
    }

    @Override
    public void update(double time){
        super.update(time);
        if( (getYPos() > 450 - getHeight()) && (getYVelocity() > 0) || (getYPos() <= 5) && (getYVelocity() < 0))
            setVelocity(getXVelocity(), -getYVelocity());
    }
}
