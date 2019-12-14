package sample.ApplicationLogic.GameEntities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.FileManagement.FileManager;
import sample.UserInterface.InputManagement.InputManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Skeleton extends GameObject {

    private int VELOCITY = 125;

    private static Skeleton hero;

    private boolean dead;
    private boolean shielded;

    public boolean isShielded() {
        return shielded;
    }

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }
    private String avatar = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\warlock.png";
    private String avatar2 = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\warlock_shielded.png";

    // default constructor
    private Skeleton() throws FileNotFoundException {
        super(200, 200);
        setDead(false);
        setSpriteImage( new Image(new FileInputStream(avatar)));
    }

    public static Skeleton getHero() throws FileNotFoundException {
        if (null == hero) {
            hero = new Skeleton();
        }
        return hero;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void update(double time){
        try{
            if(shielded){
                setSpriteImage( new Image(new FileInputStream(avatar2)));
            } else {
                setSpriteImage( new Image(new FileInputStream(avatar)));
            }
            if( ((getXPos() + time * getXVelocity()) <= 840 - getWidth())
                    && ((getXPos() + time * getXVelocity()) >= 0)
                    && ((getYPos() + time * getYVelocity()) <= 480 - getHeight())
                    && ((getYPos() + time * getYVelocity()) >= 0) ){
                super.update( time);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
    }

    public int getVELOCITY() {
        return VELOCITY;
    }

    public void setVELOCITY(int VELOCITY) {
        this.VELOCITY = VELOCITY;
    }

    public void controlSubmarine(){
        try{
            if(InputManager.getPressedKey() != null){
                String setting = new FileManager("Settings.txt").readFromFile();
                String lines[] = setting.split("\\r?\\n");
                String pressedKey = InputManager.getPressedKey().toString();
                System.out.println(lines[0]);
                if(pressedKey.toString().equals(lines[0])) {
                    setVelocity(0, -VELOCITY);
                }
                else if(pressedKey.toString().equals(lines[1])) {
                    setVelocity(0, VELOCITY);
                }
                else if(pressedKey.toString().equals(lines[2])) {
                    setVelocity(-VELOCITY, 0);
                }
                else if(pressedKey.toString().equals(lines[3])) {
                    setVelocity(VELOCITY, 0);
                }
            }
            else{
                setVelocity(-25,0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}