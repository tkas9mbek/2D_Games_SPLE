package games.ApplicationLogic.GameEntities;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Boss extends BigEnemy {

    private final String PROJECT_DIR = System.getProperty("user.dir") + "\\game-assets\\";
    private final String SKELETON = PROJECT_DIR + "ApplicationLogic\\GameEntities\\images\\skeleton_boss.png";

    private double abilityCooldown;
    private double currentCooldown;
    private double abilityDamage;
    private ArrayList<Bullet> bullets;

    public Boss() {
    }

    @Override
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Boss(double xPos, double yPos, int mapLvl) throws FileNotFoundException {
        super(xPos, yPos);

        // setting boss image
        setSpriteImage(new Image(new FileInputStream(SKELETON)));

        setVelocity(-10, -70);

        setHealth(100 + 250 * mapLvl);
        setCollisionDmg(100);
        setExperiencePrize(200 * mapLvl);
        setScorePrize( 200 * mapLvl);
        setAttackDamage(5 + 5 * mapLvl);
        setAmountOfProjectile( 1);
        setAttackSpeed(2.2);
        setAbilityCooldown(5);
        setAbilityDamage( 14 + 7 * mapLvl);
        setType("Boss");
        bullets = new ArrayList<>();
    }

    public double getAbilityCooldown() {
        return abilityCooldown;
    }

    public void setAbilityCooldown(double abilityCooldown) {
        if( abilityCooldown >= 0) {
            this.abilityCooldown = abilityCooldown;
            currentCooldown = abilityCooldown;
        }
        else
            throw new ArrayIndexOutOfBoundsException("Invalid value is entered");
    }

    public double getCurrentCooldown() {
        return currentCooldown;
    }

    public double getAbilityDamage() {
        return abilityDamage;
    }

    public void setAbilityDamage(double abilityDamage) {
        if( abilityDamage >= 0)
            this.abilityDamage = abilityDamage;
        else
            throw new ArrayIndexOutOfBoundsException("Invalid value is entered");
    }

    /*  index 0 = damage of the bullets
        index 1 = ID of bullets
        index 2 = x position of bullets
        index 3 = y position of bullets
     */
    public void useAbility(){
        if( currentCooldown <= 0 && isVisible())
        {
            bullets.add(new Bullet(getXPos(), getYPos() + getSpriteImage().getHeight() / 2 , (int)getAbilityDamage(), 4));
            currentCooldown = abilityCooldown;
        }
        if( getShootCooldown() <= 0 && isVisible()) {
            bullets.add(new Bullet(getXPos(), getYPos() + getSpriteImage().getHeight() / 2 , (int)getAttackDamage(), 5));
            setShootCooldown(getAttackSpeed());
        }
    }


    @Override
    public void update(double time){
        super.update(time);
        if( currentCooldown > 0)
            currentCooldown -= time;
    }
}
