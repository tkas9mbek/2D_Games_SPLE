package sample.ApplicationLogic.GameEntities;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Bullet extends GameObject {
    private final String FIREBALL = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\fireball.png";
    private final String WATER = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\waterball.png";
    private final String ARROW = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\arrowL.png";
    private final String ARROW_R = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\arrowR.png";
    private final String ORB = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\orb.png";
    private final String BLUE_FIRE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\dark-missile.png";
    private final String CONFIGURATION_FILE = System.getProperty("user.dir") +  "\\src\\sample\\configuration.txt";
    private final String AXE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\axe.png";

    private int damage;
    private int ID; // 1 for submarine bullets
    // 2 for enemy bullets
    // 3 for enemy skills

    // constructor
    public Bullet(Image image, double xPos, double yPos, double xVelocity, double yVelocity, int damage, int ID) throws FileNotFoundException {
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
            File file = new File(CONFIGURATION_FILE);
            Scanner sc = new Scanner(file);
            String game = sc.next();

            if( ID == 1){
                if( game.equals("Shooter") ) {
                    if (damage == 11) {
                        setSpriteImage(new Image(new FileInputStream(WATER)));
                    } else {
                        setSpriteImage(new Image(new FileInputStream(FIREBALL)));
                    }
                    setLocation(xPos, yPos);
                    setVelocity(450, 0);
                }
                if( game.equals("Quest") ) {
                    setSpriteImage(new Image(new FileInputStream(ARROW)));
                    setLocation(xPos, yPos);
                    setVelocity(-600, 0);
                }
            }
            else if( ID == 3){
                if( game.equals("Shooter") ) {
                    setLocation(xPos, yPos);
                    setVelocity(-500, 0);
                    File imageFile = new File(ARROW);
                    Image image = SwingFXUtils.toFXImage(ImageIO.read(imageFile), null);
                    setSpriteImage(image);
                }
                if( game.equals("Quest") ) {
                    setSpriteImage(new Image(new FileInputStream(AXE)));
                    setLocation(xPos, yPos);
                    setVelocity(450, (int) (Math.random() * -300) + 150);
                }
            }
            else if( ID == 2){
                if( game.equals("Shooter") ) {
                    if (damage > 27) {
                        setLocation(xPos, yPos);
                        setVelocity(-450, (int) (Math.random() * -300) + 150);
                        File imageFile = new File(ORB);
                        Image image = SwingFXUtils.toFXImage(ImageIO.read(imageFile), null);
                        setSpriteImage(image);
                    } else {
                        setLocation(xPos, yPos);
                        setVelocity(-250, 0);
                        File imageFile = new File(BLUE_FIRE);
                        Image image = SwingFXUtils.toFXImage(ImageIO.read(imageFile), null);
                        setSpriteImage(image);
                    }
                }
                if( game.equals("Quest") ) {
                    setSpriteImage(new Image(new FileInputStream(ARROW_R)));
                    setLocation(xPos, yPos);
                    setVelocity(600, 0);
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
