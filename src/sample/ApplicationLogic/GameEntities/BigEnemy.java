package sample.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BigEnemy extends Enemy{

    private final String ARCHER = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\archer.png";
    private final String MAGE = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\dark-mage.png";
    private final String LUMBERJACK = System.getProperty("user.dir") + "\\src\\sample\\ApplicationLogic\\GameEntities\\images\\lumberjack.png";
    private final String CONFIGURATION_FILE = System.getProperty("user.dir") +  "\\src\\sample\\configuration.txt";

    private double attackSpeed;
    private double shootCooldown;
    private int amountOfProjectile;
    private ArrayList<Bullet> bullets;
    private int attackDamage;
    private int type;

    // constructor
    public BigEnemy(Image image, double xPos, double yPos, double xVelocity, double yVelocity, int health, int collisionDmg, int xp, int score, boolean visibility, double attackSpeed, int amountOfProjectile, int attackDamage) {
        super(image, xPos, yPos, xVelocity, yVelocity, health, collisionDmg, xp, score, visibility, "Big Enemy");
        setAttackSpeed(attackSpeed);
        setAmountOfProjectile(amountOfProjectile);
        setAttackDamage(attackDamage);
        bullets = new ArrayList<>();
    }

    // default constructor
    public BigEnemy(){
    }
    // constructor
    BigEnemy(double xPos, double yPos){
        super(xPos, yPos);
    }
    // mapLvl contructor
    public BigEnemy(double xPos, double yPos, int mapLvl) throws FileNotFoundException {
        super(xPos, yPos);

        File file = new File(CONFIGURATION_FILE);
        Scanner sc = new Scanner(file);
        String game = sc.next();

        if( game.equals("Shooter")) {
            int choice = (int) (Math.random() * 2) + 1;
            type = choice;
            if (choice == 1) {
                // create Jelly
                setVelocity(-15, (int) (Math.random() * -70) + 35);
                setSpriteImage(new Image(new FileInputStream(MAGE)));
                setHealth(60 + 35 * mapLvl);
                setCollisionDmg(20 + 10 * mapLvl);
                setExperiencePrize(75 + 50 * mapLvl);
                setScorePrize(75 + 50 * mapLvl);
                setAttackDamage(9 + 3 * mapLvl);
                setAmountOfProjectile(3);
                setAttackSpeed(5);
            } else {
                // create Sea horse
                setVelocity(-15, (int) (Math.random() * -35) + 17);
                setSpriteImage(new Image(new FileInputStream(ARCHER)));
                setHealth(45 + 30 * mapLvl);
                setCollisionDmg(20 + 15 * mapLvl);
                setExperiencePrize(75 + 25 * mapLvl);
                setScorePrize(75 + 25 * mapLvl);
                setAttackDamage(10 + 8 * mapLvl);
                setAmountOfProjectile(1);
                setAttackSpeed(2.5);
            }
        }
        if( game.equals("Quest")) {
            setSpriteImage(new Image(new FileInputStream(LUMBERJACK)));
            setHealth(425);
            setVelocity(0, -50);
            setAttackDamage(25);
            setAmountOfProjectile(1);
            setAttackSpeed(2.4);
            type = 3;
        }
        setType("Big Enemy");
        bullets = new ArrayList<>();
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        if( attackSpeed >= 0) {
            this.attackSpeed = attackSpeed;
            this.shootCooldown = 0;
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

    public double getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        if( attackDamage >= 0)
            this.attackDamage = attackDamage;
        else
            throw new ArrayIndexOutOfBoundsException("Invalid value is entered");
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /*  index 0 = damage of the bullets
            index 1 = x position of bullets
            others = y positions of bullets
         */
    public void shoot(){
        try{
            if( shootCooldown <= 0 && isVisible()) {
                double[] arr = new double[amountOfProjectile + 2];
                arr[1] = getXPos();
                for (int i = 2; i < amountOfProjectile + 2; i++) {
                    arr[i] = getYPos() + (getSpriteImage().getHeight()/2) + (25 * (int)(( i - 1)  / 2) * Math.pow(-1, i - 1));
                    Bullet x;

                        if (type == 1) {
                            x = new Bullet(arr[1], arr[i], attackDamage, 2);
                        } else {
                            x = new Bullet(arr[1], arr[i], attackDamage, 3);
                        }

                    bullets.add(x);
                }
                shootCooldown = attackSpeed;

            }
            double[] arr = {-1};

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(double time){
        super.update(time);
        if( shootCooldown > 0)
            shootCooldown = shootCooldown - time;
        if( (getYPos() > 450 - getHeight()) && (getYVelocity() > 0) || (getYPos() <= 5) && (getYVelocity() < 0))
            setVelocity(getXVelocity(), -getYVelocity());
    }

    @Override
    public void disappearAnimation() throws FileNotFoundException {
        super.disappearAnimation();
    }
}
