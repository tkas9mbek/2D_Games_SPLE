package sample.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import java.io.*;
import java.util.Scanner;

public class PowerUp extends GameObject {

    private final String CONFIGURATION_FILE = System.getProperty("user.dir") +  "\\src\\sample\\configuration.txt";

    private final String WINGS = System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\wings.png";
    private final String HELM = System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\helm.png";
    private final String TOXIC = System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\toxic.png";
    private final String HEALTH = System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\health-potion.png";
    private final String MANA = System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\mana-potion.png";

    private int quantityOfEffect;
    private int ID;
    private int score;
    private boolean used;

    // ID constructor
    public PowerUp( double xPos, double yPos, int ID, int mapLvl) throws IOException {
        super(xPos, yPos);
        setID( ID);
        setScore( 100);

        File file = new File(CONFIGURATION_FILE);
        Scanner sc = new Scanner(file);
        String game = sc.next();

        if( game.equals("Escape") ) {
            if (ID == 1) {
                setVelocity(-65, 0);
                setSpriteImage(new Image(new FileInputStream(WINGS)));
            } else if (ID == 2) {
                setVelocity(-65, 0);
                setSpriteImage(new Image(new FileInputStream(HELM)));
            } else {
                setVelocity(-100, 0);
                setSpriteImage(new Image(new FileInputStream(TOXIC)));
                System.out.println("create toxic");
            }
        }

        if( game.equals("Shooter") ) {
            if (ID == 1) {
                setQuantityOfEffect(25 + 25 * mapLvl);
                setSpriteImage(new Image(new FileInputStream(HEALTH)));
                setVelocity(-75, -25);
            } else if (ID == 2) {
                setQuantityOfEffect(30 + 10 * mapLvl);
                setSpriteImage(new Image(new FileInputStream(MANA)));
                setVelocity(-75, -25);
            }
        }

        setType("Power Up");
        used = false;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getQuantityOfEffect() {
        return quantityOfEffect;
    }

    public void setQuantityOfEffect(int quantityOfEffect) {
        if( quantityOfEffect >= 0)
            this.quantityOfEffect = quantityOfEffect;
        else
            throw new ArrayIndexOutOfBoundsException("Invalid value is entered");
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if( score >= 0)
            this.score = score;
        else
            throw new ArrayIndexOutOfBoundsException("Invalid value is entered");
    }

    public boolean isUsed() {
        return used;
    }

    @Override
    public void update(double time) throws FileNotFoundException {
        super.update(time);
        if(used){
            disappearAnimation();
        }
        if( ( (getYPos() > 480 - getHeight()) && getYVelocity() > 0) || (getYPos() < 10 && getYVelocity() < 0))
            setVelocity(getXVelocity(), -getYVelocity());
    }
}
