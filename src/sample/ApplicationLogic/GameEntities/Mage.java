package sample.ApplicationLogic.GameEntities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.ApplicationLogic.GameManagement.SoundEngine;
import sample.FileManagement.FileManager;
import sample.UserInterface.InputManagement.InputManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Mage extends AbstractHero {

    private static int MAX_LEVEL = 5;
    int VELOCITY = 125;

    private int subLevel;
    private String avatar = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\mage.png";
    private Health health;
    private Energy energy;
    private Experience experience;
    private SkillManager skills;
    private int amountOfProjectile;
    private double attackSpeed;
    private double attackCooldown;
    private int attackDamage;
    private ArrayList<Bullet> bullets;

    Mage(int lvl) throws FileNotFoundException {
        super(200, 200);
        setSubLevel(lvl);
        setSpriteImage( new Image(new FileInputStream(avatar)));
        updateStats();
        skills = new SkillManager(subLevel);
        experience = new Experience( lvl);
        bullets = new ArrayList<>();
    }

    public void updateStats() throws FileNotFoundException {
        health = new Health( subLevel);
        energy = new Energy( subLevel);
        setAmountOfProjectile(1);
        setAttackSpeed(0.8);
        setAttackDamage(12 + 8 * subLevel);
    }

    public int getSubLevel() {
        return subLevel;
    }

    public void setSubLevel(int subLevel) {
        if( subLevel >= 1 && subLevel <= MAX_LEVEL) {
            this.subLevel = subLevel;
        }
    }

    public void useSkill( int ID) throws FileNotFoundException {
        if(skills.getSkill(ID) != null && skills.getSkill(ID).getEnergyCost() <= energy.getEnergyAmount() && !skills.getSkill(ID).isOnCooldown()){
            energy.update( -skills.getSkill(ID).getEnergyCost());
            if(ID == 1){
                skills.getSkill(ID).waterFireballs( this );
                SoundEngine.getInstance().playSound(3);
            }
            if( ID == 2){
                skills.getSkill(ID).speedBooster( this);
            }
            if( ID == 3){
                skills.getSkill(ID).invulnerability( this);
            }
        }
    }

    public void updateExperience( int exp) throws FileNotFoundException {
        experience.update( exp, this);
    }

    public void healthDecrease(int dmg) throws FileNotFoundException {
        if( skills.getSkill(3) == null){
            health.update(-dmg);
        }
        else if ( !skills.getSkill(3).isOnEffect()) {
            health.update(-dmg);
        }
    }

    public void regenHealth( PowerUp pu) throws FileNotFoundException {
        health.update( pu.getQuantityOfEffect());
    }

    public void regenEnergy( PowerUp pu) throws FileNotFoundException {
        energy.update( pu.getQuantityOfEffect());
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

    public int getAmountOfProjectile() {
        return amountOfProjectile;
    }

    public void setAmountOfProjectile(int amountOfProjectile) {
        if( amountOfProjectile >= 0)
            this.amountOfProjectile = amountOfProjectile;
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
            double[] arr = new double[amountOfProjectile + 3];

            arr[1] = 1;
            arr[2] = getXPos() + getWidth();
            for (int i = 0; i < amountOfProjectile; i++) {
                arr[i + 3] = getYPos() + (getSpriteImage().getHeight() / 2) + (15 * (int)(( i + 1)  / 2) * Math.pow(-1, i + 1));
                try{
                    bullets.add(new Bullet(arr[2], arr[i + 3], attackDamage, 1));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            attackCooldown = attackSpeed;
            SoundEngine.getInstance().playSound(1);
        }
        double[] arr = {-1};

    }

    @Override
    public void update(double time){
        try{
            //updateStats();

            if( ((getXPos() + time * getXVelocity()) <= 425 - getWidth())
                                    && ((getXPos() + time * getXVelocity()) >= 0)
                                    && ((getYPos() + time * getYVelocity()) <= 480 - getHeight())
                                    && ((getYPos() + time * getYVelocity()) >= 0) ){
                super.update( time);
            }
            skills.update( time, this);
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
        energy.draw(gc);
        skills.draw(gc);
        experience.draw(gc);
    }
    public void controlHero(){
        try{
            if(InputManager.getPressedKey() != null){
                String setting = new FileManager("Settings.txt").readFromFile();
                String lines[] = setting.split("\\r?\\n");
                String pressedKey = InputManager.getPressedKey().toString();
                System.out.println(lines[0]);
                if(pressedKey.equals(lines[0])) {
                    setVelocity(0, -VELOCITY);
                }
                else if(pressedKey.equals(lines[1])) {
                    setVelocity(0, VELOCITY);
                }
                else if(pressedKey.equals(lines[2])) {
                    setVelocity(-VELOCITY, 0);
                }
                else if(pressedKey.equals(lines[3])) {
                    setVelocity(VELOCITY, 0);
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
                else if(pressedKey.equals(lines[7])) {
                    useSkill(3);
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