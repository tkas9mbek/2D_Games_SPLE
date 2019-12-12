package sample.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EscapePowerUp extends GameObject {

    private final String WINGS = System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\wings.png";
    private final String HELM = System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\helm.png";
    private final String TOXIC = System.getProperty("user.dir") +  "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\toxic.png";

    private int ID;
    private boolean used;

    // default constructor
    public EscapePowerUp() {
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    // ID constructor
    public EscapePowerUp( double xPos, double yPos, int ID) throws FileNotFoundException {
        super(xPos, yPos);
        setID( ID);
        setVelocity(-75, 0);

        if( ID == 1){
            setSpriteImage(new Image(new FileInputStream(WINGS)));
        }
        else if( ID == 2){
            setSpriteImage(new Image(new FileInputStream(HELM)));
        } else {
            setVelocity(-100, 0);
            setSpriteImage(new Image(new FileInputStream(TOXIC)));
        }
        setType("Power Up");
        used = false;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
