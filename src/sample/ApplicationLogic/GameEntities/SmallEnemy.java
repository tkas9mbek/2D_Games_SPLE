package sample.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SmallEnemy extends Enemy {

    private final String CONFIGURATION_FILE = System.getProperty("user.dir") +  "\\src\\sample\\configuration.txt";

    private final String GOBLIN = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\goblin.png";
    private final String ORC = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\orc.png";
    private final String SPEARMAN = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\swordsman.png";

    // map level constructor
    SmallEnemy( double xPos, double yPos, boolean visible) throws FileNotFoundException {
        setLocation(xPos, yPos);
        setVisible(visible);

        File file = new File(CONFIGURATION_FILE);
        Scanner sc = new Scanner(file);
        String game = sc.next();

        if( game.equals("Escape")) {
            setLocation(xPos, yPos);
            setVisible(visible);
            setVelocity(0, 0);
            setSpriteImage(new Image(new FileInputStream(SPEARMAN)));
            setHealth(100000000);
            setType("Guardian");
        }

        if( game.equals("Shooter") ) {
            int choice = (int) (Math.random() * 2) + 1;
            if (choice == 1) {
                setVelocity(-155, 0);
                setSpriteImage(new Image(new FileInputStream(GOBLIN)));
                setHealth(20);
                setCollisionDmg(20);
                setExperiencePrize(45);
                setScorePrize(45);
            } else {
                setVelocity(-90, (int) ((Math.random() * -66) + 33));
                setSpriteImage(new Image(new FileInputStream(ORC)));
                setHealth(40);
                setCollisionDmg(40);
                setExperiencePrize(70);
                setScorePrize(70);
            }
            setType("Small Enemy");
        }
    }

    @Override
    public void update(double time){
        super.update(time);
        if( (getYPos() > 450 - getHeight()) && (getYVelocity() > 0) || (getYPos() <= 5) && (getYVelocity() < 0))
            setVelocity(getXVelocity(), -getYVelocity());
    }
}
