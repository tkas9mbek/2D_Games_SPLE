package sample.ApplicationLogic.GameEntities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.FileManagement.FileManager;
import sample.UserInterface.InputManagement.InputManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Hunter extends AbstractHero {

    int VELOCITY = 100;

    private String avatar_left = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\hunter_left.png";
    private String avatar_right = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\hunter_right.png";
    private Health health;
    private boolean toLeft = true;
    private HunterSkillBuilder skills;
    private double attackSpeed;
    private double attackCooldown;
    private int attackDamage;
    private ArrayList<Bullet> bullets;

    public void setMultishot(int multishot) {
        this.multishot = multishot;
    }

    private int multishot = 0;

    Hunter() throws FileNotFoundException {
        super(610, 340);
        setSpriteImage( new Image(new FileInputStream(avatar_left)));
        updateStats();
        bullets = new ArrayList<>();
        skills = new HunterSkillBuilder();
    }

    public void updateStats() throws FileNotFoundException {
        health = new Health( 1);
        setAttackSpeed(1.2);
        setAttackDamage(25);
    }

    public void useSkill( int ID) throws FileNotFoundException {
        if(skills.getSkill(ID) != null && !skills.getSkill(ID).isOnCooldown()){
            if(ID == 1){
                skills.getSkill(ID).multishot( this );
            }
            if( ID == 2){
                skills.getSkill(ID).regeneration( this);
            }
        }
    }

    public void healthDecrease(int dmg) throws FileNotFoundException {
        health.update(-dmg);
    }

    public void regenHealth( int h) throws FileNotFoundException {
        health.update( h);
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        if( attackSpeed >= 0) {
            this.attackSpeed = attackSpeed;
            this.attackCooldown = attackSpeed;
        }
        else
            throw new ArrayIndexOutOfBoundsException("Invalid value is entered");
    }
    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        if( attackDamage >= 0)
            this.attackDamage = attackDamage;
        else
            throw new ArrayIndexOutOfBoundsException("Invalid value is entered");
    }

    /*  index 0 = damage of the bullets
        index 1 = ID of bullets
        index 2 = x position of bullets
        others = y positions of bullets
     */
    public void shoot(){
        if( attackCooldown <= 0) {
            double[] arr = new double[multishot + 4];

            arr[1] = 1;
            arr[2] = getXPos() + (toLeft ? -getWidth() : getWidth() );
            for (int i = 0; i < multishot + 1; i++) {
                arr[i + 3] = getYPos() + (getSpriteImage().getHeight() / 2) + (28 * (int)(( i + 1)  / 2) * Math.pow(-1, i + 1));
                try{
                    bullets.add(new Bullet(arr[2], arr[i + 3], attackDamage, toLeft ? 1 : 2));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            attackCooldown = attackSpeed;
            if(multishot > 0) {
                multishot = 0;
            }
        }
    }

    @Override
    public void update(double time){
        try{
            if( ((getXPos() + time * getXVelocity()) <= 860 - getWidth())
                    && ((getXPos() + time * getXVelocity()) >= 0)
                    && ((getYPos() + time * getYVelocity()) <= 480 - getHeight())
                    && ((getYPos() + time * getYVelocity()) >= 0) ){
                super.update( time);
            }
            skills.update( time);
            if( attackCooldown > 0)
                attackCooldown = attackCooldown - time;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        health.draw(gc);
        skills.draw(gc);
    }
    public void controlHero(){
        try{
            if(InputManager.getPressedKey() != null){
                String setting = new FileManager("Settings.txt").readFromFile();
                String lines[] = setting.split("\\r?\\n");
                String pressedKey = InputManager.getPressedKey().toString();
                if(pressedKey.equals(lines[0])) {
                    setVelocity(0, -VELOCITY);
                }
                else if(pressedKey.equals(lines[1])) {
                    setVelocity(0, VELOCITY);
                }
                else if(pressedKey.equals(lines[2])) {
                    setVelocity(-VELOCITY, 0);
                    setSpriteImage( new Image(new FileInputStream(avatar_left)));
                    toLeft = true;
                }
                else if(pressedKey.equals(lines[3])) {
                    setVelocity(VELOCITY, 0);
                    setSpriteImage( new Image(new FileInputStream(avatar_right)));
                    toLeft = false;
                }
                else if(pressedKey.equals(lines[4])) {
                    shoot();
                }
                else if(pressedKey.equals(lines[5])) {
                    useSkill(1);
                }
                else if(pressedKey.equals(lines[6])) {
                    useSkill(2);
                }
            }
            else{
                setVelocity(0,0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Health getHealth() {
        return health;
    }
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}