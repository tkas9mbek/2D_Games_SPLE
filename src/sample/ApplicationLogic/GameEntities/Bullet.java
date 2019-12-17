package sample.ApplicationLogic.GameEntities;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bullet extends GameObject {
    private final String FIREBALL = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\fireball.png";
    private final String WATER = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\waterball.png";
    private final String ARROW = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\arrow.png";
    private final String ORB = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\orb.png";
    private final String BLUE_FIRE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\dark-missile.png";

    private int damage;
    private int ID; // 1 for submarine bullets
    // 2 for enemy bullets
    // 3 for enemy skills

    // constructor
    public Bullet(Image image, double xPos, double yPos, double xVelocity, double yVelocity, int damage, int ID) {
        super(image, xPos, yPos, xVelocity, yVelocity);
        setVisible(true);
        setType("Bullet");
        setDamage( damage);
        setID( ID);
    }

    // ID constructor
    public Bullet(double xPos, double yPos, int damage, int ID) {
        try{
            setID(ID);
            setDamage(damage);

            if( ID == 1){
                if(damage == 11){
                    setSpriteImage( new Image(new FileInputStream(WATER)));
                } else {
                    setSpriteImage( new Image(new FileInputStream(FIREBALL)));
                }
                setLocation(xPos, yPos);
                setVelocity(450, 0);
            }
            else if( ID == 3){
                setLocation(xPos, yPos);
                setVelocity(-500, 0);
                File imageFile = new File(ARROW);
                Image image = SwingFXUtils.toFXImage(ImageIO.read(imageFile),null);
                setSpriteImage(image);
            }
            else if( ID == 2){
                if(damage > 27) {
                    setLocation(xPos, yPos);
                    setVelocity(-450, (int)(Math.random() * -300) + 150);
                    File imageFile = new File(ORB);
                    Image image = SwingFXUtils.toFXImage(ImageIO.read(imageFile),null);
                    setSpriteImage(image);
                } else {
                    setLocation(xPos, yPos);
                    setVelocity(-250, 0);
                    File imageFile = new File(BLUE_FIRE);
                    Image image = SwingFXUtils.toFXImage(ImageIO.read(imageFile), null);
                    setSpriteImage(image);
                }
            }
            setType("Bullet");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        if( damage >= 0)
            this.damage = damage;
        else
            throw new ArrayIndexOutOfBoundsException("Invalid value is entered");
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
